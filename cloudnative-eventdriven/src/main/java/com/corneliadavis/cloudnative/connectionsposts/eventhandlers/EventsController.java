package com.corneliadavis.cloudnative.connectionsposts.eventhandlers;

import com.corneliadavis.cloudnative.connections.Connection;
import com.corneliadavis.cloudnative.connections.User;
import com.corneliadavis.cloudnative.connectionsposts.localstorage.*;
import com.corneliadavis.cloudnative.posts.Post;
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
@RequestMapping(value="/connectionsposts")
public class EventsController {

    private static final Logger logger = LoggerFactory.getLogger(EventsController.class);
    private MUserRepository mUserRepository;
    private MConnectionRepository mConnectionRepository;
    private MPostRepository mPostRepository;

    @Autowired
    public EventsController(MUserRepository mUserRepository,
                            MConnectionRepository mConnectionRepository,
                            MPostRepository mPostRepository) {
        this.mUserRepository = mUserRepository;
        this.mConnectionRepository = mConnectionRepository;
        this.mPostRepository = mPostRepository;
    }


    @RequestMapping(method = RequestMethod.POST, value="/users")
    public void newUser(@RequestBody User newUser, HttpServletResponse response) {

        logger.info("[ConnectionsPosts] Creating new user with username " + newUser.getUsername());
        mUserRepository.save(new MUser(newUser.getId(), newUser.getName(), newUser.getUsername()));

    }

    @RequestMapping(method = RequestMethod.PUT, value="/users/{id}")
    public void updateUser(@PathVariable("id") Long userId,
                           @RequestBody User newUser, HttpServletResponse response) {

        logger.info("Updating user with id " + userId);
        MUser mUser = mUserRepository.findById(userId).get();
        mUserRepository.save(mUser);

    }

    @RequestMapping(method = RequestMethod.POST, value="/connections")
    public void newConnection(@RequestBody Connection newConnection, HttpServletResponse response) {

        logger.info("Have a new connection: " + newConnection.getFollower() +
                    " is following " + newConnection.getFollowed());
        MConnection mConnection = new MConnection(newConnection.getId(), newConnection.getFollower(),
                                                  newConnection.getFollowed());
        // add connection to the users
        MUser mUser;
        mUser = mUserRepository.findById(newConnection.getFollower()).get();
        mConnection.setFollowerUser(mUser);
        mUser = mUserRepository.findById(newConnection.getFollowed()).get();
        mConnection.setFollowedUser(mUser);
        mConnectionRepository.save(mConnection);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/connections/{id}")
    public void deleteConnection(@PathVariable("id") Long connectionId, HttpServletResponse response) {

        MConnection mConnection = mConnectionRepository.findById(connectionId).get();

        logger.info("deleting connection: " + mConnection.getFollower() +
                    " is no longer following " + mConnection.getFollowed());
        mConnectionRepository.delete(mConnection);

    }

    @RequestMapping(method = RequestMethod.POST, value="/posts")
    public void newPost(@RequestBody Post newPost, HttpServletResponse response) {

        logger.info("Have a new post with title " + newPost.getTitle());
        MPost mPost = new MPost(newPost.getId(), newPost.getDate(), newPost.getUserId(), newPost.getTitle());
        MUser mUser;
        mUser = mUserRepository.findById(newPost.getUserId()).get();
        mPost.setmUser(mUser);
        mPostRepository.save(mPost);

    }

}
