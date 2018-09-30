package com.corneliadavis.cloudnative.posts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import com.corneliadavis.cloudnative.Utils;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

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

    @Value("${postscontroller.infectionDuration}")
    private int infectionDuration;
    @Value("${postscontroller.sleepDuration}")
    private int sleepDuration;

    @RequestMapping(method = RequestMethod.GET, value="/posts")
    public Iterable<Post> getPostsByUserId(@RequestParam(value="userIds", required=false) String userIds, 
                                           HttpServletResponse response) throws InterruptedException {

        Iterable<Post> posts;

        long currentMillis = System.currentTimeMillis();
        if (currentMillis < healthTimeout) {
            Thread.sleep(sleepDuration);
            return null;
        } else {

            logger.info(utils.ipTag() + "Accessing posts ");

            if (userIds == null) {
                logger.info(utils.ipTag() + "getting all posts");
                posts = postRepository.findAll();
                return posts;
            } else {
                ArrayList<Post> postsForUsers = new ArrayList<Post>();
                String userId[] = userIds.split(",");
                for (int i = 0; i < userId.length; i++) {
                    logger.info(utils.ipTag() + "getting posts for userId " + userId[i]);
                    posts = postRepository.findByUserId(Long.parseLong(userId[i]));
                    posts.forEach(post -> postsForUsers.add(post));
                }
                return postsForUsers;

            }
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
