package com.corneliadavis.cloudnative.connectionsposts.eventhandlers;

import com.corneliadavis.cloudnative.connectionsposts.projection.*;
import com.corneliadavis.cloudnative.eventschemas.ConnectionEvent;
import com.corneliadavis.cloudnative.eventschemas.UserEvent;
import com.corneliadavis.cloudnative.eventschemas.PostEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by corneliadavis on 9/29/18.
 */


@Component
public class EventHandler {

    private static final Logger logger = LoggerFactory.getLogger(EventHandler.class);
    private UserRepository userRepository;
    private ConnectionRepository connectionRepository;
    private PostRepository postRepository;

    @Autowired
    public EventHandler(UserRepository userRepository,
                        ConnectionRepository connectionRepository,
                        PostRepository postRepository) {
        this.userRepository = userRepository;
        this.connectionRepository = connectionRepository;
        this.postRepository = postRepository;
    }

    @KafkaListener(topics="user", groupId = "connectionspostsconsumer", containerFactory = "kafkaListenerContainerFactory")
    public void userEvent(UserEvent userEvent) {

        logger.info("Posts UserEvent Handler processing - event: " + userEvent.getEventType());

        if (userEvent.getEventType().equals("created")) {

            // make event handler idempotent. If user already exists, do nothing
            User existingUser = userRepository.findByUsername(userEvent.getUsername());
            if (existingUser == null) {

                User user = new User(userEvent.getId(), userEvent.getName(), userEvent.getUsername());
                userRepository.save(user);

                logger.info("New user cached in local storage " + user.getUsername());
                userRepository.save(new User(userEvent.getId(), userEvent.getName(), userEvent.getUsername()));
            } else
                logger.info("Already existing user not ached again id " + userEvent.getId());
        } else if (userEvent.getEventType().equals("updated")) {
            logger.info("Updating user cached in local storage with username " + userEvent.getUsername());
            Optional<User> opt = userRepository.findById(userEvent.getId());
            if (opt.isPresent()) {
                User existingUser = opt.get();
                existingUser.setName(userEvent.getName());
                existingUser.setUsername(userEvent.getUsername());
                userRepository.save(existingUser);
            } else
                logger.info("Something is odd - trying to update a user that doesn't exist in the local cache");
        }


    }

    @KafkaListener(topics="connection", groupId = "connectionspostsconsumer", containerFactory = "kafkaListenerContainerFactory")
    public void connectionEvent(ConnectionEvent connectionEvent) {

        if (connectionEvent.getEventType().equals("created")) {
            Optional<Connection> opt = connectionRepository.findById(connectionEvent.getId());
            if (!opt.isPresent()) {
                logger.info("Creating a new connection in the cache: " + connectionEvent.getFollower() +
                        " is following " + connectionEvent.getFollowed());
                Connection newConnection = new Connection(connectionEvent.getId(), connectionEvent.getFollower(),
                        connectionEvent.getFollowed());
                connectionRepository.save(newConnection);
            } else {
                Connection existingConnection = opt.get();
                logger.info("Did not cache already existing connection with id " + existingConnection.getId());
            }
        } else if (connectionEvent.getEventType().equals("deleted")) {
            Optional<Connection> opt = connectionRepository.findById(connectionEvent.getId());
            if (opt.isPresent()) {
                Connection existingConnection = opt.get();
                logger.info("deleting from the cache connection: " + existingConnection.getFollower() +
                        " is no longer following " + existingConnection.getFollowed());
                connectionRepository.delete(existingConnection);
            } else
                logger.info("Could not delete cached connection with id ", connectionEvent.getId());
        }
    }

    @KafkaListener(topics="post", groupId = "connectionspostsconsumer", containerFactory = "kafkaListenerContainerFactory")
    public void postEvent(PostEvent postEvent) {

        if (postEvent.getEventType().equals("created")) {
            Optional<Post> opt = postRepository.findById(postEvent.getId());
            if (!opt.isPresent()) {
                logger.info("Creating a new post in the cache with title " + postEvent.getTitle());
                Post post = new Post(postEvent.getId(), postEvent.getDate(), postEvent.getUserId(), postEvent.getTitle());
                postRepository.save(post);
            } else
                logger.info("Did not create already cached post with id " + postEvent.getId());
        }



    }

}