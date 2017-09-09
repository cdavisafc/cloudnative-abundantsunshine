package com.corneliadavis.cloudnative.posts.write;

import com.corneliadavis.cloudnative.connections.ConnectionsController;
import com.corneliadavis.cloudnative.posts.Post;
import com.corneliadavis.cloudnative.posts.PostRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@RestController
public class PostsWriteController {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionsController.class);
    private PostRepository postRepository;

    @Autowired
    public PostsWriteController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @RequestMapping(method = RequestMethod.POST, value="/posts")
    public void newPost(@RequestBody Post newPost, HttpServletResponse response) {

        logger.info("Have a new post with title " + newPost.getTitle());
        postRepository.save(newPost);

    }

}
