package com.corneliadavis.cloudnative.connections.write;

import com.corneliadavis.cloudnative.connections.Connection;
import com.corneliadavis.cloudnative.connections.ConnectionRepository;
import com.corneliadavis.cloudnative.connections.User;
import com.corneliadavis.cloudnative.connections.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

@RefreshScope
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

    @RequestMapping(method = RequestMethod.POST, value="/users")
    public void newUser(@RequestBody User newUser, HttpServletResponse response) {

        logger.info("Have a new user with username " + newUser.getUsername());
        userRepository.save(newUser);

        //event
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity("http://localhost:8080/connectionsposts/users", newUser, String.class);

    }

    @RequestMapping(method = RequestMethod.PUT, value="/users/{id}")
    public void updateUser(@PathVariable("id") Long userId, @RequestBody User newUser, HttpServletResponse response) {

        logger.info("Updating user with id " + userId);
        User user = userRepository.findById(userId).get();
        newUser.setId(userId);
        userRepository.save(newUser);

        //event
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put("http://localhost:8080/connectionsposts/users/"+newUser.getId(), newUser);

    }

    @RequestMapping(method = RequestMethod.POST, value="/connections")
    public void newConnection(@RequestBody Connection newConnection, HttpServletResponse response) {

        logger.info("Have a new connection: " + newConnection.getFollower() +
                    " is following " + newConnection.getFollowed());
        connectionRepository.save(newConnection);

        //event
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.postForEntity(
                "http://localhost:8080/connectionsposts/connections", newConnection, String.class);
        logger.info("resp " + resp.getStatusCode());
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/connections/{id}")
    public void deleteConnection(@PathVariable("id") Long connectionId, HttpServletResponse response) {

        Connection connection = connectionRepository.findById(connectionId).get();

        logger.info("deleting connection: " + connection.getFollower() + " is no longer following " + connection.getFollowed());
        connectionRepository.delete(connection);

        //event
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete("http://localhost:8080/connectionsposts/connections/"+connectionId);

    }

}
