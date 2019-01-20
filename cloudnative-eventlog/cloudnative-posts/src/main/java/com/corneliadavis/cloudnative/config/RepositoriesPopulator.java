package com.corneliadavis.cloudnative.config;

import com.corneliadavis.cloudnative.posts.IPostApi;
import com.corneliadavis.cloudnative.posts.projection.Post;
import com.corneliadavis.cloudnative.posts.projection.PostRepository;
import com.corneliadavis.cloudnative.posts.projection.User;
import com.corneliadavis.cloudnative.posts.projection.UserRepository;
import com.corneliadavis.cloudnative.posts.read.PostsController;
import com.corneliadavis.cloudnative.posts.write.PostsWriteController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by corneliadavis on 9/4/17.
 */
@Component
public class RepositoriesPopulator implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(RepositoriesPopulator.class);
    private ApplicationContext applicationContext;

    @Autowired
    Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void populate() throws InterruptedException {
        Post post1, post2, post3, post4;
        PostsController postsController = applicationContext.getBean(PostsController.class);
        PostsWriteController postsWriteController = applicationContext.getBean(PostsWriteController.class);
        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        PostRepository postRepository = applicationContext.getBean(PostRepository.class);

        // hacky way of not loading data if posts already exist - could be race conditions but not worrying about that

        Iterable<IPostApi> posts = postsController.getPostsByUsername("cdavisafc",null);
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

            post1 = new Post(1L, new Date(), 2L, "Max Title", "The body of the post");
            postRepository.save(post1);
            post2 = new Post(2L, new Date(), 1L, "Cornelia Title", "The body of the post");
            postRepository.save(post2);
            post3 = new Post(3L, new Date(), 1L, "Cornelia Title2", "The body of the post");
            postRepository.save(post3);
            post4 = new Post(4L, new Date(), 3L, "Glen Title", "The body of the post");
            postRepository.save(post4);

        } else
            logger.info("Sample data previously loaded");

    }

}
