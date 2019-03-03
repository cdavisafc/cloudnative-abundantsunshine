package com.corneliadavis.cloudnative.connections.write;

import com.corneliadavis.cloudnative.connections.UserRepository;
import com.corneliadavis.cloudnative.connections.Connection;
import com.corneliadavis.cloudnative.connections.ConnectionRepository;
import com.corneliadavis.cloudnative.connections.User;
import com.corneliadavis.cloudnative.connections.ConnectionApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

//@RefreshScope
@RestController
public class ConnectionsWriteController {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionsWriteController.class);
    private UserRepository userRepository;
    private ConnectionRepository connectionRepository;

    @Autowired
    public ConnectionsWriteController(UserRepository userRepository, ConnectionRepository connectionRepository) {
        this.userRepository = userRepository;
        this.connectionRepository = connectionRepository;
    }

    @Value("${connectionspostscontroller.url}")
    private String connectionsPostsControllerUrl;
    @Value("${postscontroller.url}")
    private String postsControllerUrl;

    @RequestMapping(method = RequestMethod.POST, value="/users")
    public void newUser(@RequestBody User newUser, HttpServletResponse response) {

        logger.info("Have a new user with username " + newUser.getUsername());
        userRepository.save(newUser);

        // posts needs to be notified of new users
        try {
            //event
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForEntity(postsControllerUrl+"/users", newUser, String.class);
        } catch (Exception e) {
            // for now, do nothing
            // It's a known bad that the successful delivery of this event depends on successful connection
            // to Connections' Posts, right at this moment. This will be fixed shortly.
            logger.info("[Connections] appears to have been a problem sending change event to Posts");
        }

        // connections posts needs to be notified of new users
        try {
            //event
            RestTemplate restTemplate = new RestTemplate();
            logger.info("url = " + connectionsPostsControllerUrl+"/users");
            restTemplate.postForEntity(connectionsPostsControllerUrl+"/users", newUser, String.class);
        } catch (Exception e) {
            // for now, do nothing
            // It's a known bad that the successful delivery of this event depends on successful connection
            // to Connections' Posts, right at this moment. This will be fixed shortly.
            logger.info("[Connections] appears to have been a problem sending change event to ConnectionsPosts");
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value="/users/{username}")
    public void updateUser(@PathVariable("username") String username, @RequestBody User newUser, HttpServletResponse response) {

        logger.info("Updating user with username " + username);
        User user = userRepository.findByUsername(username);
        newUser.setId(user.getId());
        userRepository.save(newUser);

        // posts needs to be notified of changes to users
        try {
            //event
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.put(postsControllerUrl+"/users/"+username, newUser);
        } catch (Exception e) {
            // for now, do nothing
            // It's a known bad that the successful delivery of this event depends on successful connection
            // to Connections' Posts, right at this moment. This will be fixed shortly.
            logger.info("[Connections] appears to have been a problem sending change event");
        }

        // connections posts needs to be notified of changes to users
        try {
            //event
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.put(connectionsPostsControllerUrl+"/users/"+username, newUser);
        } catch (Exception e) {
            // for now, do nothing
            // It's a known bad that the successful delivery of this event depends on successful connection
            // to Connections' Posts, right at this moment. This will be fixed shortly.
            logger.info("[Connections] appears to have been a problem sending change event");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value="/connections")
    public void newConnection(@RequestBody ConnectionApi newConnection, HttpServletResponse response) {

        logger.info("Have a new connection: " + newConnection.getFollower() +
                    " is following " + newConnection.getFollowed());
        Long followerId = userRepository.findByUsername(newConnection.getFollower()).getId();
        Long followedId = userRepository.findByUsername(newConnection.getFollowed()).getId();
        Connection newConnectionIds = new Connection(followerId, followedId);
        connectionRepository.save(newConnectionIds);

        // connections posts needs to be notified of new connections
        try {
            //event
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> resp = restTemplate.postForEntity(
                    connectionsPostsControllerUrl+"/connections", newConnectionIds, String.class);
            logger.info("resp " + resp.getStatusCode());
        } catch (Exception e) {
            // for now, do nothing
            // It's a known bad that the successful delivery of this event depends on successful connection
            // to Connections' Posts, right at this moment. This will be fixed shortly.
            logger.info("[Connections] appears to have been a problem sending change event");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/connections/{follower}/{followed}")
    public void deleteConnection(@PathVariable("follower") String followerUsername,
                                 @PathVariable("followed") String followedUsername, HttpServletResponse response) {



        logger.info("deleting connection: " + followerUsername +
                    " is no longer following " + followedUsername);
        Long followerId = userRepository.findByUsername(followerUsername).getId();
        Long followedId = userRepository.findByUsername(followedUsername).getId();
        logger.info("(ID) deleting connection: " + followerId +
                " is no longer following " + followedId);
        Connection connection = connectionRepository.findByFollowerAndFollowed(followerId,followedId);
        if (connection == null)
            logger.info("unable to find or delete that connection");
        else
            connectionRepository.delete(connection);

        // connections posts needs to be notified of deleted connections
        try {
            //event
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.delete(connectionsPostsControllerUrl+"/connections/"+followerUsername+"/"+followedUsername);
        } catch (Exception e) {
            // for now, do nothing
            // It's a known bad that the successful delivery of this event depends on successful connection
            // to Connections' Posts, right at this moment. This will be fixed shortly.
            logger.info("[Connections] appears to have been a problem sending change event");
        }

    }

}
