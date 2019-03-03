package com.corneliadavis.cloudnative.posts.write;

import com.corneliadavis.cloudnative.posts.PostRepository;
//import com.corneliadavis.cloudnative.posts.Post;
import com.corneliadavis.cloudnative.posts.PostApi;
import com.corneliadavis.cloudnative.posts.localstorage.User;
import com.corneliadavis.cloudnative.posts.localstorage.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.corneliadavis.cloudnative.eventschemas.posts.Post;

import javax.servlet.http.HttpServletResponse;

@RestController
public class PostsWriteController {

    private static final Logger logger = LoggerFactory.getLogger(PostsWriteController.class);
    private PostRepository postRepository;
    private UserRepository userRepository;

    @Autowired
    public PostsWriteController(PostRepository postRepository,
                                UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Value("${connectionspostscontroller.url}")
    private String connectionsPostsControllerUrl;

    @RequestMapping(method = RequestMethod.POST, value="/posts")
    public void newPost(@RequestBody PostApi newPost, HttpServletResponse response) {

        logger.info("Have a new post with title " + newPost.getTitle());

        // store the post
        com.corneliadavis.cloudnative.posts.Post post
                = new com.corneliadavis.cloudnative.posts.Post(newPost.getTitle(), newPost.getBody());
        User user = userRepository.findByUsername(newPost.getUsername());
        logger.info("find by username output ");
        if (user == null)
            logger.info("user is null - username was " + newPost.getUsername());
        else
            logger.info("user username = " + user.getUsername() + " id = " + user.getId());
        post.setUser(user);
        postRepository.save(post);

        // send out new post "event"
        Post postEvent = new Post(post.getId(), post.getDate(), post.getUser().getId(),
                                  post.getTitle(), post.getBody());

        try {
            //event
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> resp = restTemplate.postForEntity(
                    connectionsPostsControllerUrl + "/posts", postEvent, String.class);
            logger.info("[Post] resp " + resp.getStatusCode());
        } catch (Exception e) {
            // for now, do nothing
            // It's a known bad that the successful delivery of this event depends on successful connection
            // to Connections' Posts, right at this moment. This will be fixed shortly.
            logger.info("[Post] appears to have been a problem sending change event");
        }
    }
}
