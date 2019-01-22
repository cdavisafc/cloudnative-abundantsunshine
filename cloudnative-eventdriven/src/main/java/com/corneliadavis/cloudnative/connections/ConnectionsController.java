package com.corneliadavis.cloudnative.connections;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;

@RefreshScope
@RestController
public class ConnectionsController {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionsController.class);
    private UserRepository userRepository;
    private ConnectionRepository connectionRepository;

    @Autowired
    public ConnectionsController(UserRepository userRepository, ConnectionRepository connectionRepository) {
        this.userRepository = userRepository;
        this.connectionRepository = connectionRepository;
    }

	@RequestMapping(method = RequestMethod.GET, value="/users")
	public Iterable<User> getUsers(HttpServletResponse response) {

        logger.info("getting users");
        Iterable<User> users;
        users = userRepository.findAll();

		return users;
	}

	@RequestMapping(method = RequestMethod.GET, value="/users/{user}")
	public User getByUsername(@PathVariable("user") String user, HttpServletResponse response) {
        logger.info("getting user " + user);
        try {
            Long id = Long.parseLong(user);
            return userRepository.findById(id).get();
        } catch(NumberFormatException e) {
            return userRepository.findByUsername(user);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value="/connections")
    public Iterable<Connection> getConnections(HttpServletResponse response) {

        logger.info("getting connections");
        Iterable<Connection> connections;
        connections = connectionRepository.findAll();

        return connections;
    }

    @RequestMapping(method = RequestMethod.GET, value="/connections/{username}")
    public Iterable<Connection> getConnections(@PathVariable("username") String username, HttpServletResponse response) {
        logger.info("getting connections for username " + username);
        Long userId = getByUsername(username, null).getId();
        Iterable<Connection> connections;
        connections = connectionRepository.findByFollower(userId);

        return connections;
    }

}
