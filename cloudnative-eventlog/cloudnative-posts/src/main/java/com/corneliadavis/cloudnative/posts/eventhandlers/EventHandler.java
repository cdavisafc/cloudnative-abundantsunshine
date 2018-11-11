package com.corneliadavis.cloudnative.posts.eventhandlers;

import com.corneliadavis.cloudnative.eventschemas.PostEvent;
import com.corneliadavis.cloudnative.eventschemas.UserEvent;
import com.corneliadavis.cloudnative.posts.projection.Post;
import com.corneliadavis.cloudnative.posts.projection.PostRepository;
import com.corneliadavis.cloudnative.posts.projection.User;
import com.corneliadavis.cloudnative.posts.projection.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EventHandler {

    private static final Logger logger = LoggerFactory.getLogger(EventHandler.class);
    private UserRepository userRepository;
    private PostRepository postRepository;

    @Autowired
    public EventHandler(UserRepository userRepository, PostRepository postRepository) {

        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @KafkaListener(topics="user", groupId="postsconsumer", containerFactory = "kafkaListenerContainerFactory")
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
	
    @KafkaListener(topics="post", groupId = "postsconsumer", containerFactory = "kafkaListenerContainerFactory")
    public void postEvent(PostEvent postEvent) {

        logger.info("PostEvent Handler processing - event: " + postEvent.getEventType());

        if (postEvent.getEventType().equals("created")) {
            Optional<Post> opt = postRepository.findById(postEvent.getId());
            if (!opt.isPresent()) {
                logger.info("Creating a new post in the cache with title " + postEvent.getTitle());
                Post post = new Post(postEvent.getId(), postEvent.getDate(), postEvent.getUserId(),
                        postEvent.getTitle(), postEvent.getBody());
                postRepository.save(post);
            } else
                logger.info("Did not create already cached post with id " + postEvent.getId());
        }
    }
}
