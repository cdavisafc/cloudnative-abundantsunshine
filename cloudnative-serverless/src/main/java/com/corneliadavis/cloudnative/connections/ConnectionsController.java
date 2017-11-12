package com.corneliadavis.cloudnative.connections;

import com.corneliadavis.cloudnative.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnectionsController {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionsController.class);
    private UserRepository userRepository;
    private ConnectionRepository connectionRepository;
    private Utils utils;

    @Autowired
    public ConnectionsController(UserRepository userRepository, ConnectionRepository connectionRepository, Utils utils) {
        this.userRepository = userRepository;
        this.connectionRepository = connectionRepository;
        this.utils = utils;
    }

	public Iterable<User> getUsers() {

        logger.info(utils.ipTag() + "getting users");
        Iterable<User> users;
        users = userRepository.findAll();

		return users;
	}

	public User getByUsername(String user) {
        String ipAddress = System.getenv("POD_IP");
        logger.info(utils.ipTag() + "getting user " + user);
        try {
            Long id = Long.parseLong(user);
            return userRepository.findOne(id);
        } catch(NumberFormatException e) {
            return userRepository.findByUsername(user);
        }
    }

    public void newUser(User newUser) {

        logger.info(utils.ipTag() + "Have a new user with username " + newUser.getUsername());
        userRepository.save(newUser);

    }

    public void updateUser(Long userId, User newUser) {

        logger.info(utils.ipTag() + "Updating user with id " + userId);
        User user = userRepository.findOne(userId);
        newUser.setId(userId);
        userRepository.save(newUser);

    }

    public Iterable<Connection> getConnections() {

        logger.info(utils.ipTag() + "getting connections");
        Iterable<Connection> connections;
        connections = connectionRepository.findAll();

        return connections;
    }

    public Iterable<Connection> getConnections(String username) {
        logger.info(utils.ipTag() + "getting connections for username " + username);
        Long userId = getByUsername(username).getId();
        Iterable<Connection> connections;
        connections = connectionRepository.findByFollower(userId);

        return connections;
    }

    public void newConnection(Connection newConnection) {

        logger.info(utils.ipTag() + "Have a new connection: " + newConnection.getFollower() + " is following " + newConnection.getFollowed());
        connectionRepository.save(newConnection);

    }

    public void deleteConnection(Long connectionId) {

        Connection connection = connectionRepository.findOne(connectionId);

        logger.info(utils.ipTag() + "deleting connection: " + connection.getFollower() + " is no longer following " + connection.getFollowed());
        connectionRepository.delete(connectionId);

    }


}
