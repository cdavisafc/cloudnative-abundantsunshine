package com.corneliadavis.cloudnative.connections.write;

import com.corneliadavis.cloudnative.connections.*;
import com.corneliadavis.cloudnative.eventschemas.ConnectionEvent;
import com.corneliadavis.cloudnative.eventschemas.UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ConnectionsWriteController {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionsWriteController.class);
    private UserRepository userRepository;
    private ConnectionRepository connectionRepository;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public ConnectionsWriteController(UserRepository userRepository, ConnectionRepository connectionRepository) {
        this.userRepository = userRepository;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(method = RequestMethod.POST, value="/users")
    public void newUser(@RequestBody User newUser, HttpServletResponse response) {

        logger.info("Have a new user with username " + newUser.getUsername());
        userRepository.save(newUser);

        // send event to Kafka
        UserEvent userEvent =
                new UserEvent(
                        "created",
                        newUser.getId(),
                        newUser.getName(),
                        newUser.getUsername());
        kafkaTemplate.send("user", userEvent);

    }

    @RequestMapping(method = RequestMethod.PUT, value="/users/{username}")
    public void updateUser(@PathVariable("username") String username, @RequestBody User newUser, HttpServletResponse response) {

        logger.info("Updating user with username " + username);
        User user = userRepository.findByUsername(username);
        newUser.setId(user.getId());
        userRepository.save(newUser);

        // send event to Kafka
        UserEvent userEvent =
                new UserEvent(
                        "updated",
                        newUser.getId(),
                        newUser.getName(),
                        newUser.getUsername());
        kafkaTemplate.send("user", userEvent);

    }

    @RequestMapping(method = RequestMethod.POST, value="/connections")
    public void newConnection(@RequestBody ConnectionApi newConnection, HttpServletResponse response) {

        logger.info("Have a new connection: " + newConnection.getFollower() +
                    " is following " + newConnection.getFollowed());
        Long followerId = userRepository.findByUsername(newConnection.getFollower()).getId();
        Long followedId = userRepository.findByUsername(newConnection.getFollowed()).getId();
        Connection newConnectionIds = new Connection(followerId, followedId);
        connectionRepository.save(newConnectionIds);

        // send event to Kafka
        ConnectionEvent connectionEvent =
                new ConnectionEvent("created",
                        newConnectionIds.getId(),
                        newConnectionIds.getFollower(),
                        newConnectionIds.getFollowed());
        kafkaTemplate.send("connection", connectionEvent);

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

        // send event to Kafka
        ConnectionEvent connectionEvent =
                new ConnectionEvent();
        connectionEvent.setEventType("deleted");
        connectionEvent.setId(connection.getId());
        kafkaTemplate.send("connection", connectionEvent);

    }

}
