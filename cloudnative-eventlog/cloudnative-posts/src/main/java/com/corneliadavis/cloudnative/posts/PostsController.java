package com.corneliadavis.cloudnative.posts;

import com.corneliadavis.cloudnative.posts.apirepresentations.IApiPost;
import com.corneliadavis.cloudnative.posts.localstorage.User;
import com.corneliadavis.cloudnative.posts.localstorage.UserRepository;
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
    private UserRepository userRepository;

    private long healthTimeout = 0;

    @Autowired
    public PostsController(PostRepository postRepository,
                           UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Autowired
    Utils utils;

    @Value("${postscontroller.infectionDuration}")
    private int infectionDuration;
    @Value("${postscontroller.sleepDuration}")
    private int sleepDuration;

    @RequestMapping(method = RequestMethod.GET, value="/posts")
    public Iterable<IApiPost> getPostsByUsername(@RequestParam(value="usernames", required=false) String usernames,
                                                 HttpServletResponse response) throws InterruptedException {

        Iterable<IApiPost> posts;

        long currentMillis = System.currentTimeMillis();
        if (currentMillis < healthTimeout) {
            Thread.sleep(sleepDuration);
            return null;
        } else {

            logger.info(utils.ipTag() + "Accessing posts ");

            if (usernames == null) {
                logger.info(utils.ipTag() + "getting all posts");
                posts = postRepository.findAllPosts();
                return posts;
            } else {
                ArrayList<IApiPost> postsForUsers = new ArrayList<IApiPost>();
                String username[] = usernames.split(",");
                for (int i = 0; i < username.length; i++) {
                    logger.info(utils.ipTag() + "getting posts for username " + username[i]);
                    //posts = postRepository.findByUserId(Long.parseLong(userId[i]));
                    posts = postRepository.findByUsername(username[i]);
                    posts.forEach(post -> postsForUsers.add(post));
                }
                return postsForUsers;

            }
        }

    }


    // despite the fact that we are writing a value here, this is not in the write controller because this
    // service is not the source of truth, rather the user table is just a cache, having picked up change events
    // (in this case a create event) from the Connections service. In this case it is not this service's
    // responsibility to send the "event" out.
    @RequestMapping(method = RequestMethod.POST, value="/eventHandlers/users")
    public void newUser(@RequestBody User user, HttpServletResponse response) {

        logger.info("New user cached in local storage " + user.getUsername());

        userRepository.save(user);

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
