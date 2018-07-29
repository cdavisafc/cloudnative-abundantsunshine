package com.corneliadavis.cloudnative.posts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import com.corneliadavis.cloudnative.Utils;

import javax.servlet.http.HttpServletResponse;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

@RefreshScope
@RestController
public class PostsController {

    private static final Logger logger = LoggerFactory.getLogger(PostsController.class);
    private PostRepository postRepository;

    private long healthTimeout = 0;

    @Autowired
    public PostsController(PostRepository postRepository) { this.postRepository = postRepository; }

    @Autowired
    Utils utils;

    @Autowired
    PostsService postsService;

    @Value("${postscontroller.infectionDuration}")
    private int infectionDuration;
    @Value("${postscontroller.sleepDuration}")
    private int sleepDuration;

    @RequestMapping(method = RequestMethod.GET, value="/posts")
    public Iterable<Post> getPostsByUserId(@RequestParam(value="userIds", required=false) String userIds, 
                                           @RequestParam(value="secret", required=true) String secret,  
                                           HttpServletResponse response) throws InterruptedException {

        long currentMillis = System.currentTimeMillis();
        if (currentMillis < healthTimeout) {
            Thread.sleep(sleepDuration);
            return null;
        } else if (utils.isValidSecret(secret)) {

            try {
                return postsService.getPostsByUserId(userIds, secret);
            } catch (Exception e) {
                logger.info(utils.ipTag() + "Unexpected Exception " + e.getClass().toString() + " " + e.getMessage());
                response.setStatus(500);
                return  null;
            }

        } else {
            response.setStatus(401);
            return null;
        }


    }

    @RequestMapping(method = RequestMethod.POST, value="/posts")
    public void newPost(@RequestBody Post newPost,
                        @RequestParam(value = "secret", required = true) String secret,
                        HttpServletResponse response) {

        if (utils.isValidSecret(secret)) {

            logger.info(utils.ipTag() + "Accessing posts using secret " + secret);

            logger.info(utils.ipTag() + "Have a new post with title " + newPost.getTitle());
            postRepository.save(newPost);
        } else {
            logger.info(utils.ipTag() + "Attempt to access Post service with secret " + secret + " (expecting one of " + utils.validSecrets() + ")");
            response.setStatus(401);
        }

    }

    @RequestMapping(method = RequestMethod.GET, value="/healthz")
    public void healthCheck(HttpServletResponse response) throws InterruptedException {

        long currentMillis = System.currentTimeMillis();
        if (currentMillis > healthTimeout) response.setStatus(200);
        else Thread.sleep(sleepDuration);

    }

    @RequestMapping(method = RequestMethod.POST, value="/infect")
    public void makeUnhealthy(HttpServletResponse response) {

        logger.info("Infecting this posts service instance");
        healthTimeout = System.currentTimeMillis() + infectionDuration;
        response.setStatus(200);

    }

    @RequestMapping(method = RequestMethod.POST, value="/heal")
    public void makeHealthy(HttpServletResponse response) {

        logger.info("Healing this posts service instance");
        healthTimeout = 0;
        response.setStatus(200);

    }


}
