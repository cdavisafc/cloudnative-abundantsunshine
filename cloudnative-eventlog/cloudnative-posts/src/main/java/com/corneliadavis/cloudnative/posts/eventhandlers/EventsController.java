package com.corneliadavis.cloudnative.posts.eventhandlers;

import com.corneliadavis.cloudnative.posts.localstorage.User;
import com.corneliadavis.cloudnative.posts.localstorage.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value="/eventHandlers")
public class EventsController {

    private static final Logger logger = LoggerFactory.getLogger(EventsController.class);
    private UserRepository userRepository;

    @Autowired
    public EventsController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // despite the fact that we are writing a value here, this is not in the write controller because this
    // service is not the source of truth, rather the user table is just a cache, having picked up change events
    // (in this case a create event) from the Connections service. In this case it is not this service's
    // responsibility to send the "event" out.
    @RequestMapping(method = RequestMethod.POST, value="/users")
    public void newUser(@RequestBody User user, HttpServletResponse response) {

        logger.info("New user cached in local storage " + user.getUsername());

        userRepository.save(user);

    }



}
