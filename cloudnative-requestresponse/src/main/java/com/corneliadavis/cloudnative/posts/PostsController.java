package com.corneliadavis.cloudnative.posts;

import com.corneliadavis.cloudnative.connections.ConnectionsController;
import com.corneliadavis.cloudnative.posts.Post;
import com.corneliadavis.cloudnative.posts.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@RestController
public class PostsController {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionsController.class);
    private PostRepository postRepository;

    @Autowired
    public PostsController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value="/posts")
    public Iterable<Post> getPostsByUserId(@RequestParam(value="userIds", required=false) String userIds, HttpServletResponse response) {

        Iterable<Post> posts;

        if (userIds == null) {
            logger.info("getting all posts");
            posts = postRepository.findAll();
            return posts;
        } else {
            ArrayList<Post> postsForUsers = new ArrayList<Post>();
            String userId[] = userIds.split(",");
            for (int i = 0; i < userId.length; i++) {
                logger.info("getting posts for userId " + userId[i]);
                posts = postRepository.findByUserId(Long.parseLong(userId[i]));
                posts.forEach(post -> postsForUsers.add(post));
            }
            return postsForUsers;

        }

    }

}
