package com.corneliadavis.cloudnative.posts.write;

import com.corneliadavis.cloudnative.posts.PostRepository;
import com.corneliadavis.cloudnative.posts.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
public class PostsWriteController {

    private static final Logger logger = LoggerFactory.getLogger(PostsWriteController.class);
    private PostRepository postRepository;

    @Autowired
    public PostsWriteController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Value("${connectionspostscontroller.url}")
    private String connectionsPostsControllerUrl;

    @RequestMapping(method = RequestMethod.POST, value="/posts")
    public void newPost(@RequestBody Post newPost, HttpServletResponse response) {

        logger.info("Have a new post with title " + newPost.getTitle());

        if (newPost.getDate() == null)
            newPost.setDate(new Date());
        postRepository.save(newPost);

        try {
            //event
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> resp = restTemplate.postForEntity(
                    connectionsPostsControllerUrl + "/posts", newPost, String.class);
            logger.info("[Post] resp " + resp.getStatusCode());
        } catch (Exception e) {
            // for now, do nothing
            // It's a known bad that the successful delivery of this event depends on successful connection
            // to Connections' Posts, right at this moment. This will be fixed shortly.
            logger.info("[Post] appears to have been a problem sending change event");
        }
    }

}
