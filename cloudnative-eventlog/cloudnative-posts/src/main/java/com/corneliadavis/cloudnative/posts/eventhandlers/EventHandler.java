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

//@RestController
//@RequestMapping(value="/eventHandlers")
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

            User user = new User(userEvent.getId(), userEvent.getUsername());
            userRepository.save(user);

            logger.info("New user cached in local storage " + user.getUsername());
        }


            /* The only thing that can be changed for users is the name and we don't store that locally in the
               Posts service, so do not do anything with "updated" events
             */
    }

    // despite the fact that we are writing a value here, this is not in the write controller because this
    // service is not the source of truth, rather the user table is just a cache, having picked up change events
    // (in this case a create event) from the Connections service. In this case it is not this service's
    // responsibility to send the "event" out.
/*    @RequestMapping(method = RequestMethod.POST, value="/users")
    public void newUser(@RequestBody UserEvent user, HttpServletResponse response) {

        logger.info("New user cached in local storage " + user.getUsername());

        userRepository.save(user);

    }

    @RequestMapping(method = RequestMethod.PUT, value="/users/{username}")
    public void updateUser(@PathVariable("username") String username, @RequestBody UserEvent newUser, HttpServletResponse response) {

        logger.info("Updating user cached in local storage with username " + username);
        UserEvent user = userRepository.findByUsername(username);
        newUser.setId(user.getId());
        userRepository.save(newUser);

    } */




}
