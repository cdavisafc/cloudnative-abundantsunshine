package com.corneliadavis.cloudnative.config;

import com.corneliadavis.cloudnative.posts.Post;
import com.corneliadavis.cloudnative.posts.PostsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by corneliadavis on 9/4/17.
 */
@Component
public class RepositoriesPopulator implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(RepositoriesPopulator.class);
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Loading sample data");
        populate();
    }

    private void populate() {
        Post post1, post2, post3, post4;
        PostsController postsWriteController = applicationContext.getBean(PostsController.class);

        try {

            post1 = new Post(2L, "Max Title", "The body of the post");
            postsWriteController.newPost(post1, null);
            post2 = new Post(1L, "Cornelia Title", "The body of the post");
            postsWriteController.newPost(post2, null);
            post3 = new Post(1L, "Cornelia Title2", "The body of the post");
            postsWriteController.newPost(post3, null);
            post4 = new Post(3L, "Glen Title", "The body of the post");
            postsWriteController.newPost(post4, null);

        } catch (Exception e)
        {
            // slightly hacky but with a uniqueness constraint on the username for a User, can stop
            // repository population if it's already been done.
        }

    }

}
