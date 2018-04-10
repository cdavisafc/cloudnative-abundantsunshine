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
import java.util.Set;

@RefreshScope
@RestController
public class PostsController {

    private static final Logger logger = LoggerFactory.getLogger(PostsController.class);
    private PostRepository postRepository;

    private boolean isHealthy = true;

    @Autowired
    public PostsController(PostRepository postRepository) { this.postRepository = postRepository; }

    @Autowired
    Utils utils;

    @RequestMapping(method = RequestMethod.GET, value="/posts")
    public Iterable<Post> getPostsByUserId(@RequestParam(value="userIds", required=false) String userIds, 
                                           @RequestParam(value="secret", required=true) String secret,  
                                           HttpServletResponse response) {

        Iterable<Post> posts;

        if (utils.isValidSecret(secret)) {

            logger.info(utils.ipTag() + "Accessing posts using secret " + secret);

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
        } else {
            logger.info(utils.ipTag() + ".Attempt to access Post service with secret " + secret + " (expecting one of " + utils.validSecrets() + ")");
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

        if (this.isHealthy) response.setStatus(200);
        else Thread.sleep(400000);

    }

    @RequestMapping(method = RequestMethod.POST, value="/infect")
    public void makeUnhealthy(HttpServletResponse response) {

        this.isHealthy = false;
        response.setStatus(200);

    }

}
