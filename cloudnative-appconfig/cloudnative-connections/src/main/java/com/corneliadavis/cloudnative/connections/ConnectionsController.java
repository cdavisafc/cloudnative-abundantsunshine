package com.corneliadavis.cloudnative.connections;

import com.corneliadavis.cloudnative.Utils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;

@RestController
public class ConnectionsController {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionsController.class);
    private UserRepository userRepository;
    private ConnectionRepository connectionRepository;

    @Autowired
    Utils utils;

    @Autowired
    public ConnectionsController(UserRepository userRepository, ConnectionRepository connectionRepository) {
        this.userRepository = userRepository;
        this.connectionRepository = connectionRepository;
    }

	@RequestMapping(method = RequestMethod.GET, value="/users")
	public Iterable<User> getUsers(@RequestParam(value="secret", required=true) String secret,
                                   HttpServletResponse response) {


            logger.info(utils.ipTag() + "getting users");
            Iterable<User> users;
            users = userRepository.findAll();
            return users;
    }

	@RequestMapping(method = RequestMethod.GET, value="/users/{user}")
	public User getByUsername(@PathVariable("user") String user,
                              @RequestParam(value="secret", required=true) String secret,
                              HttpServletResponse response) {

            logger.info(utils.ipTag() + "Accessing posts using secret " + secret);
            String ipAddress = System.getenv("POD_IP");
            logger.info(utils.ipTag() + "getting user " + user);
            try {
                Long id = Long.parseLong(user);
                return userRepository.findOne(id);
            } catch (NumberFormatException e) {
                return userRepository.findByUsername(user);
            }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public void newUser(@RequestBody User newUser,
                        @RequestParam(value = "secret", required = true) String secret,
                        HttpServletResponse response) {

            logger.info(utils.ipTag() + "Have a new user with username " + newUser.getUsername());
            userRepository.save(newUser);

    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
    public void updateUser(@PathVariable("id") Long userId,
                           @RequestBody User newUser,
                           @RequestParam(value = "secret", required = true) String secret,
                           HttpServletResponse response) {

            logger.info(utils.ipTag() + "Updating user with id " + userId);
            User user = userRepository.findOne(userId);
            newUser.setId(userId);
            userRepository.save(newUser);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/connections")
    public Iterable<Connection> getConnections(@RequestParam(value = "secret", required = true) String secret,
                                               HttpServletResponse response) {

            logger.info(utils.ipTag() + "getting connections");
            Iterable<Connection> connections;
            connections = connectionRepository.findAll();

            return connections;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/connections/{username}")
    public Iterable<Connection> getConnections(@PathVariable("username") String username,
                                               @RequestParam(value = "secret", required = true) String secret,
                                               HttpServletResponse response) {

            logger.info(utils.ipTag() + "getting connections for username " + username);
            Long userId = getByUsername(username, secret, null).getId();
            Iterable<Connection> connections;
            connections = connectionRepository.findByFollower(userId);

            return connections;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/connections")
    public void newConnection(@RequestBody Connection newConnection,
                              @RequestParam(value = "secret", required = true) String secret,
                              HttpServletResponse response) {

            logger.info(utils.ipTag() + "Have a new connection: " + newConnection.getFollower() + " is following " + newConnection.getFollowed());
            connectionRepository.save(newConnection);

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/connections/{id}")
    public void deleteConnection(@PathVariable("id") Long connectionId,
                                 @RequestParam(value = "secret", required = true) String secret,
                                 HttpServletResponse response) {

            Connection connection = connectionRepository.findOne(connectionId);

            logger.info(utils.ipTag() + "deleting connection: " + connection.getFollower() + " is no longer following " + connection.getFollowed());
            connectionRepository.delete(connectionId);

    }

}
