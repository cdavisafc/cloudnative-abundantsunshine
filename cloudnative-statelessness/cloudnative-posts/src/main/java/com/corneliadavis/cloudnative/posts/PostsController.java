package com.corneliadavis.cloudnative.posts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.corneliadavis.cloudnative.Utils;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@RestController
public class PostsController {

    private static final Logger logger = LoggerFactory.getLogger(PostsController.class);
    private PostRepository postRepository;

    @Value("${INSTANCE_IP}")
    private String ip;
    @Value("${INSTANCE_PORT}")
    private String p;

    @Autowired
    public PostsController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value="/posts")
    public Iterable<Post> getPostsByUserId(@RequestParam(value="userIds", required=false) String userIds, HttpServletResponse response) {

        Iterable<Post> posts;

        if (userIds == null) {
            logger.info(Utils.ipTag(ip,p) + "getting all posts");
            posts = postRepository.findAll();
            return posts;
        } else {
            ArrayList<Post> postsForUsers = new ArrayList<Post>();
            String userId[] = userIds.split(",");
            for (int i = 0; i < userId.length; i++) {
                logger.info(Utils.ipTag(ip,p) + "getting posts for userId " + userId[i]);
                posts = postRepository.findByUserId(Long.parseLong(userId[i]));
                posts.forEach(post -> postsForUsers.add(post));
            }
            return postsForUsers;

        }

    }

    @RequestMapping(method = RequestMethod.POST, value="/posts")
    public void newPost(@RequestBody Post newPost, HttpServletResponse response) {

        logger.info(Utils.ipTag(ip,p) + "Have a new post with title " + newPost.getTitle());
        postRepository.save(newPost);

    }


}
