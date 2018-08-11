package com.corneliadavis.cloudnative.posts;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import com.corneliadavis.cloudnative.Utils;
import java.security.AccessControlException;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

@Service
public class PostsService {

    private static final Logger logger = LoggerFactory.getLogger(PostsService.class);
    private PostRepository postRepository;

    private long healthTimeout = 0;

    @Autowired
    public PostsService(PostRepository postRepository) { this.postRepository = postRepository; }

    @Autowired
    Utils utils;

    @Value("${postscontroller.infectionDuration}")
    private int infectionDuration;
    @Value("${postscontroller.sleepDuration}")
    private int sleepDuration;

    @HystrixCommand(fallbackMethod = "getSponsoredPosts")
    public Iterable<Post> getPostsByUserId(String userIds,
                                           String secret) throws Exception {

        logger.info(utils.ipTag() + "Attempting getPostsByUserId");

        Iterable<Post> posts;

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

    public Iterable<Post> getSponsoredPosts(String userIds,
                                            String secret) {
        logger.info(utils.ipTag() + "Accessing Hystrix fallback getSponsoredPosts");
        ArrayList<Post> posts = new ArrayList<Post>();
        posts.add(new Post(999L, "Some catchy title", "Some great sponsored content"));
        posts.add(new Post(999L, "Another catchy title", "Some more great sponsored content"));
        return posts;
    }
}
