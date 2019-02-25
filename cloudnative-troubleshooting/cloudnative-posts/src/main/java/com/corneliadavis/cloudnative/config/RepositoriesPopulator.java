package com.corneliadavis.cloudnative.config;

import com.corneliadavis.cloudnative.posts.Post;
import com.corneliadavis.cloudnative.posts.PostsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${com.corneliadavis.cloudnative.posts.secrets}")
    private String secrets;

    @Autowired
    Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            logger.info("Loading sample data");
            populate();
        } catch (Exception e) {
            logger.error("Error populating Posts database - exception: " + e.getMessage());
        }
    }

    private void populate() throws InterruptedException {
        Post post1, post2, post3, post4;
        PostsController postsController = applicationContext.getBean(PostsController.class);

        String secret = secrets.split(",")[0]; // assuming there is at least one secret

        // hacky way of not loading data if posts already exist - could be race conditions but not worrying about that

        Iterable<Post> posts = postsController.getPostsByUserId("2", secret, null);
        if (!posts.iterator().hasNext()) {

            post1 = new Post(2L, "Chicken Pho", "This is my attempt to recreate what I ate in Vietnam...");
            postsController.newPost(post1, secret,null);
            post2 = new Post(1L, "Whole Orange Cake", "That's right, you blend up whole oranges, rind and all...");
            postsController.newPost(post2, secret,null);
            post3 = new Post(1L, "German Dumplings (Kloesse)", "Russet potatoes, flour (gluten free!) and more...");
            postsController.newPost(post3, secret,null);
            post4 = new Post(3L, "French Press Lattes", "We've figured out how to make these dairy free, but just as good!...");
            postsController.newPost(post4, secret,null);

        }

    }

}
