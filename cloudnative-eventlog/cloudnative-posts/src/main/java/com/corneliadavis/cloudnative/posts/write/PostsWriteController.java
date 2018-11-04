package com.corneliadavis.cloudnative.posts.write;

import com.corneliadavis.cloudnative.eventschemas.PostEvent;
import com.corneliadavis.cloudnative.posts.sourceoftruth.PostApi;
import com.corneliadavis.cloudnative.posts.sourceoftruth.PostRepository;
import com.corneliadavis.cloudnative.posts.localstorage.User;
import com.corneliadavis.cloudnative.posts.localstorage.UserRepository;
import com.corneliadavis.cloudnative.posts.sourceoftruth.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class PostsWriteController {

    private static final Logger logger = LoggerFactory.getLogger(PostsWriteController.class);
    private PostRepository postRepository;
    private UserRepository userRepository;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public PostsWriteController(PostRepository postRepository,
                                UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.POST, value="/posts")
    public void newPost(@RequestBody PostApi newPost, HttpServletResponse response) {

        logger.info("Have a new post with title " + newPost.getTitle());

        // store the post
        Post post
                = new Post(newPost.getTitle(), newPost.getBody());
        User user = userRepository.findByUsername(newPost.getUsername());
        logger.info("find by username output ");
        if (user == null)
            logger.info("user is null - username was " + newPost.getUsername());
        else
            logger.info("user username = " + user.getUsername() + " id = " + user.getId());
        post.setUserId(user.getId());
        postRepository.save(post);

        // send out new post event
        PostEvent postEvent = new PostEvent("created", post.getId(), post.getDate(), post.getUserId(),
                                  post.getTitle(), post.getBody());
        kafkaTemplate.send("post", postEvent);

    }
}
