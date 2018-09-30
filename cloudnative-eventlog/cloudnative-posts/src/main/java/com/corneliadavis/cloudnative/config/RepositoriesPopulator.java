package com.corneliadavis.cloudnative.config;

import com.corneliadavis.cloudnative.posts.write.PostsWriteController;
import com.corneliadavis.cloudnative.posts.Post;
import com.corneliadavis.cloudnative.posts.PostsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by corneliadavis on 9/4/17.
 */
@Component
public class RepositoriesPopulator implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(RepositoriesPopulator.class);
    private ApplicationContext applicationContext;

//    @Value("${com.corneliadavis.cloudnative.posts.secrets}")
//    private String secrets;

    @Autowired
    Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            populate();
        } catch (Exception e) {
            logger.error("Error populating Posts database - exception: " + e.getMessage());
        }
    }

    private void populate() throws InterruptedException {
        Post post1, post2, post3, post4;
        PostsController postsController = applicationContext.getBean(PostsController.class);
        PostsWriteController postsWriteController = applicationContext.getBean(PostsWriteController.class);

//        String secret = secrets.split(",")[0]; // assuming there is at least one secret

        // hacky way of not loading data if posts already exist - could be race conditions but not worrying about that

        Iterable<Post> posts = postsController.getPostsByUserId("2",null);
        if (!posts.iterator().hasNext()) {
            logger.info("Loading sample data");

            post1 = new Post(2L, "Max Title", "The body of the post");
            postsWriteController.newPost(post1, null);
            post2 = new Post(1L, "Cornelia Title", "The body of the post");
            postsWriteController.newPost(post2, null);
            post3 = new Post(1L, "Cornelia Title2", "The body of the post");
            postsWriteController.newPost(post3, null);
            post4 = new Post(3L, "Glen Title", "The body of the post");
            postsWriteController.newPost(post4, null);

        } else
            logger.info("Sample data previously loaded");

    }

}
