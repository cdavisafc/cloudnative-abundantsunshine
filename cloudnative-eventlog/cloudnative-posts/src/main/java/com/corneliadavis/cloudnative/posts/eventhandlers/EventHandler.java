package com.corneliadavis.cloudnative.posts.eventhandlers;

import com.corneliadavis.cloudnative.eventschemas.UserEvent;
import com.corneliadavis.cloudnative.posts.localstorage.User;
import com.corneliadavis.cloudnative.posts.localstorage.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {

    private static final Logger logger = LoggerFactory.getLogger(EventHandler.class);
    private UserRepository userRepository;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public EventHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @KafkaListener(topics="user", groupId="postsconsumer")
    public void listenForUser(UserEvent userEvent) {

        logger.info("Posts UserEvent Handler processing - event: " + userEvent.getEventType());

        if (userEvent.getEventType().equals("created")) {

            // make event handler idempotent. If user already exists, do nothing
            User existingUser = userRepository.findByUsername(userEvent.getUsername());
            if (existingUser == null) {

                User user = new User(userEvent.getId(), userEvent.getUsername());
                userRepository.save(user);

                logger.info("New user cached in local storage " + user.getUsername());
            }
        } else if (userEvent.getEventType().equals("updated")) {
            logger.info("Updating user cached in local storage with username " + userEvent.getUsername());
            User existingUser = userRepository.findById(userEvent.getId()).get();
            if (existingUser != null) {
                existingUser.setUsername(userEvent.getUsername());
                userRepository.save(existingUser);
            } else
                logger.info("Something is odd - trying to update a user that doesn't existing in the local cache");
        }

    }

}
