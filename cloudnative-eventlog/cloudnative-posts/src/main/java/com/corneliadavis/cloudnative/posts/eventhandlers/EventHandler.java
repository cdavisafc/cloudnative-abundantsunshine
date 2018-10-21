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

    @KafkaListener(topics="user", group="kafka-intro")
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
        }


        // The only thing that can be changed for users is the name and we don't store that locally in the
        // Posts service, so do not do anything with "updated" events

    }

}
