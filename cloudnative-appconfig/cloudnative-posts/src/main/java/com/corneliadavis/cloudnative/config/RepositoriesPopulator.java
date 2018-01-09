package com.corneliadavis.cloudnative.config;

import com.corneliadavis.cloudnative.posts.Post;
import com.corneliadavis.cloudnative.posts.PostsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by corneliadavis on 9/4/17.
 */
@Component
public class RepositoriesPopulator implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(RepositoriesPopulator.class);
    private ApplicationContext applicationContext;

    @Autowired
    Environment environment;

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
        PostsController postsController = applicationContext.getBean(PostsController.class);

        // hacky way of not loading data if posts already exist - could be race conditions but not worrying about that

        Iterable<Post> posts = postsController.getPostsByUserId("2", null);
        if (!posts.iterator().hasNext()) {

            post1 = new Post(2L, "Max Title", "The body of the post");
            postsController.newPost(post1,null);
            post2 = new Post(1L, "Cornelia Title", "The body of the post");
            postsController.newPost(post2,null);
            post3 = new Post(1L, "Cornelia Title2", "The body of the post");
            postsController.newPost(post3,null);
            post4 = new Post(3L, "Glen Title", "The body of the post");
            postsController.newPost(post4,null);

        }

    }

}
