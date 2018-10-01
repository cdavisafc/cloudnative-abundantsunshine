package com.corneliadavis.cloudnative.config;

import com.corneliadavis.cloudnative.posts.apirepresentations.ApiPost;
import com.corneliadavis.cloudnative.posts.apirepresentations.IApiPost;
import com.corneliadavis.cloudnative.posts.localstorage.User;
import com.corneliadavis.cloudnative.posts.localstorage.UserRepository;
import com.corneliadavis.cloudnative.posts.write.PostsWriteController;
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
public class RepositoriesPopulator implements ApplicationContextAware {

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

    public void populate() throws InterruptedException {
        ApiPost post1, post2, post3, post4;
        PostsController postsController = applicationContext.getBean(PostsController.class);
        PostsWriteController postsWriteController = applicationContext.getBean(PostsWriteController.class);
        UserRepository userRepository = applicationContext.getBean(UserRepository.class);

//        String secret = secrets.split(",")[0]; // assuming there is at least one secret

        // hacky way of not loading data if posts already exist - could be race conditions but not worrying about that

        Iterable<IApiPost> posts = postsController.getPostsByUsername("cdavisafc",null);
        if (!posts.iterator().hasNext()) {
            logger.info("Loading sample data");

            // getting hokier and hokier but MVPing this
            // loading up the user cache so that post writes work
            User user = new User(1L, "cdavisafc");
            userRepository.save(user);
            user = new User(2L, "madmax");
            userRepository.save(user);
            user = new User(3L, "gmaxdavis");
            userRepository.save(user);

            post1 = new ApiPost("madmax", "Max Title", "The body of the post");
            postsWriteController.newPost(post1, null);
            post2 = new ApiPost("cdavisafc", "Cornelia Title", "The body of the post");
            postsWriteController.newPost(post2, null);
            post3 = new ApiPost("cdavisafc", "Cornelia Title2", "The body of the post");
            postsWriteController.newPost(post3, null);
            post4 = new ApiPost("gmaxdavis", "Glen Title", "The body of the post");
            postsWriteController.newPost(post4, null);

        } else
            logger.info("Sample data previously loaded");

    }

}
