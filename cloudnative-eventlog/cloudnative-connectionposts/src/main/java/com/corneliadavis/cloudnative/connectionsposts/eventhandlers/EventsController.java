package com.corneliadavis.cloudnative.connectionsposts.eventhandlers;

import com.corneliadavis.cloudnative.connectionsposts.localstorage.*;
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
@RequestMapping(value="/connectionsPosts")
public class EventsController {

    private static final Logger logger = LoggerFactory.getLogger(EventsController.class);
    private MUserRepository userRepository;
    private MConnectionRepository connectionRepository;
    private MPostRepository postRepository;

    @Autowired
    public EventsController(MUserRepository userRepository,
                            MConnectionRepository connectionRepository,
                            MPostRepository postRepository) {
        this.userRepository = userRepository;
        this.connectionRepository = connectionRepository;
        this.postRepository = postRepository;
    }


    @RequestMapping(method = RequestMethod.POST, value="/users")
    public void newUser(@RequestBody com.corneliadavis.cloudnative.eventschemas.connections.User newUser, HttpServletResponse response) {

        logger.info("[NewPosts] Creating new user with username " + newUser.getUsername());
        userRepository.save(new MUser(newUser.getId(), newUser.getName(), newUser.getUsername()));

    }

    @RequestMapping(method = RequestMethod.PUT, value="/users/{id}")
    public void updateUser(@PathVariable("id") Long userId,
                           @RequestBody com.corneliadavis.cloudnative.eventschemas.connections.User newUser, HttpServletResponse response) {

        logger.info("Updating user with id " + userId);
        MUser user = userRepository.findOne(userId);
        userRepository.save(user);

    }

    @RequestMapping(method = RequestMethod.POST, value="/connections")
    public void newConnection(@RequestBody com.corneliadavis.cloudnative.eventschemas.connections.Connection newConnection, HttpServletResponse response) {

        logger.info("Have a new connection: " + newConnection.getFollower() +
                    " is following " + newConnection.getFollowed());
        MConnection connection = new MConnection(newConnection.getId(), newConnection.getFollower(),
                                                  newConnection.getFollowed());
        // add connection to the users
        MUser user;
        user = userRepository.findOne(newConnection.getFollower());
        connection.setFollowerUser(user);
        user = userRepository.findOne(newConnection.getFollowed());
        connection.setFollowedUser(user);
        connectionRepository.save(connection);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/connections/{id}")
    public void deleteConnection(@PathVariable("id") Long connectionId, HttpServletResponse response) {

        MConnection connection = connectionRepository.findOne(connectionId);

        logger.info("deleting connection: " + connection.getFollower() +
                    " is no longer following " + connection.getFollowed());
        connectionRepository.delete(connectionId);

    }

    @RequestMapping(method = RequestMethod.POST, value="/posts")
    public void newPost(@RequestBody com.corneliadavis.cloudnative.eventschemas.posts.Post newPost, HttpServletResponse response) {

        logger.info("Have a new post with title " + newPost.getTitle());
        MPost post = new MPost(newPost.getId(), newPost.getDate(), newPost.getUserId(), newPost.getTitle());
        MUser user;
        user = userRepository.findOne(newPost.getUserId());
        post.setmUser(user);
        postRepository.save(post);

    }

}
