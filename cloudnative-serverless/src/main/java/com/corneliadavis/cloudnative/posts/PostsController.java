package com.corneliadavis.cloudnative.posts;

import com.corneliadavis.cloudnative.Utils;
import com.corneliadavis.cloudnative.connections.ConnectionsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PostsController {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionsController.class);
    private PostRepository postRepository;
    private Utils utils;

    @Autowired
    public PostsController(PostRepository postRepository, Utils utils) {

        this.postRepository = postRepository;
        this.utils = utils;
    }

    public Iterable<Post> getPostsByUserId(String userIds) {

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

    public void newPost(Post newPost) {

        logger.info(utils.ipTag() + "Have a new post with title " + newPost.getTitle());
        postRepository.save(newPost);

    }


}
