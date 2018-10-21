package com.corneliadavis.cloudnative.connectionsposts.eventhandlers;

import com.corneliadavis.cloudnative.connectionsposts.localstorage.*;
import com.corneliadavis.cloudnative.eventschemas.ConnectionEvent;
import com.corneliadavis.cloudnative.eventschemas.UserEvent;
import com.corneliadavis.cloudnative.eventschemas.PostEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by corneliadavis on 9/10/17.
 */

@RefreshScope
@RestController
@RequestMapping(value="/eventHandlers")
public class EventsController {

    private static final Logger logger = LoggerFactory.getLogger(EventsController.class);
    private UserRepository userRepository;
    private ConnectionRepository connectionRepository;
    private PostRepository postRepository;

    @Autowired
    public EventsController(UserRepository userRepository,
                            ConnectionRepository connectionRepository,
                            PostRepository postRepository) {
        this.userRepository = userRepository;
        this.connectionRepository = connectionRepository;
        this.postRepository = postRepository;
    }


    @RequestMapping(method = RequestMethod.POST, value="/users")
    public void newUser(@RequestBody UserEvent newUser, HttpServletResponse response) {

        logger.info("Creating new user in the cache with username " + newUser.getUsername());
        userRepository.save(new User(newUser.getId(), newUser.getName(), newUser.getUsername()));

    }

    @RequestMapping(method = RequestMethod.PUT, value="/users/{username}")
    public void updateUser(@PathVariable("username") String username, @RequestBody User newUser, HttpServletResponse response) {

        logger.info("Updating user cached in local storage with username " + username);
        User user = userRepository.findByUsername(username);
        newUser.setId(user.getId());
        userRepository.save(newUser);

    }

    @RequestMapping(method = RequestMethod.POST, value="/connections")
    public void newConnection(@RequestBody ConnectionEvent newConnectionEvent, HttpServletResponse response) {

        logger.info("Have a new connection in the cache: " + newConnectionEvent.getFollower() +
                    " is following " + newConnectionEvent.getFollowed());
        Connection connection = new Connection(newConnectionEvent.getId(), newConnectionEvent.getFollower(),
                                                  newConnectionEvent.getFollowed());
        // add connection to the users
        User user;
        user = userRepository.findById(newConnectionEvent.getFollower()).get();

        connection.setFollowerUser(user);
        user = userRepository.findById(newConnectionEvent.getFollowed()).get();
        connection.setFollowedUser(user);
        connectionRepository.save(connection);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/connections/{follower}/{followed}")
    public void deleteConnection(@PathVariable("follower") String followerUsername,
                                 @PathVariable("followed") String followedUsername, HttpServletResponse response) {



        logger.info("deleting from the cache connection: " + followerUsername +
                " is no longer following " + followedUsername);
        User follower = userRepository.findByUsername(followerUsername);
        User followed = userRepository.findByUsername(followedUsername);
        Connection connection = connectionRepository.findByFollowerUserAndFollowedUser(follower,followed);
        if (connection == null)
            logger.info("unable to find or delete that connection");
        else
            connectionRepository.delete(connection);

    }

    @RequestMapping(method = RequestMethod.POST, value="/posts")
    public void newPost(@RequestBody PostEvent newPost, HttpServletResponse response) {

        logger.info("Have a new post in the cache with title " + newPost.getTitle());
        Post post = new Post(newPost.getId(), newPost.getDate(), newPost.getUserId(), newPost.getTitle());
        User user;
        user = userRepository.findById(newPost.getUserId()).get();
        post.setUser(user);

        postRepository.save(post);

    }

}
