package com.corneliadavis.cloudnative.posts;

import com.corneliadavis.cloudnative.config.CloudnativeApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PostsHandler {

    private static final Logger logger = LoggerFactory.getLogger(PostsHandler.class);
    private static ApplicationContext springContext = null;
    PostsController postsController;

    public PostsHandler() {
        logger.info("Creating PostsHandler instance");
        springContext = new AnnotationConfigApplicationContext(CloudnativeApplication.class);
        postsController = springContext.getBean(PostsController.class);
    }

    public Iterable<Post> getPosts(String userIds) {
        return postsController.getPostsByUserId(userIds);
    }

    public void newPost(Post newPost) {
        postsController.newPost(newPost);
    }
}