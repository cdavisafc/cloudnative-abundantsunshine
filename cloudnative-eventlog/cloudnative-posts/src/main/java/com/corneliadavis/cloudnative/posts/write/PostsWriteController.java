package com.corneliadavis.cloudnative.posts.write;

import com.corneliadavis.cloudnative.eventschemas.PostEvent;
import com.corneliadavis.cloudnative.posts.projection.IdManager;
import com.corneliadavis.cloudnative.posts.projection.User;
import com.corneliadavis.cloudnative.posts.projection.UserRepository;
import com.corneliadavis.cloudnative.posts.projection.PostRepository;
import com.corneliadavis.cloudnative.posts.PostApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
public class PostsWriteController {

    private static final Logger logger = LoggerFactory.getLogger(PostsWriteController.class);
    private PostRepository postRepository;
    private UserRepository userRepository;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private IdManager idManager;

    @Autowired
    public PostsWriteController(PostRepository postRepository,
                                UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.POST, value="/posts")
    public void newPost(@RequestBody PostApi newPost, HttpServletResponse response) {

        logger.info("Have a new post with title " + newPost.getTitle());

        Long id = idManager.nextId();
        User user = userRepository.findByUsername(newPost.getUsername());
        if (user != null) {
            // send out new post event
            PostEvent postEvent = new PostEvent("created", id, new Date(), user.getId(),
                    newPost.getTitle(), newPost.getBody());
            kafkaTemplate.send("post", postEvent);
        } else
            logger.info("Something went awry with creating a new Post - user with username "
                    + newPost.getUsername() + " is not known");

    }
}
