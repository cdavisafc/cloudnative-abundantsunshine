package com.corneliadavis.cloudnative.config;

import com.corneliadavis.cloudnative.connections.Connection;
import com.corneliadavis.cloudnative.connections.User;
import com.corneliadavis.cloudnative.connections.write.ConnectionsWriteController;
import com.corneliadavis.cloudnative.posts.Post;
import com.corneliadavis.cloudnative.posts.write.PostsWriteController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by corneliadavis on 9/4/17.
 */
@Component
public class RepositoriesPopulator implements ApplicationListener<ApplicationReadyEvent>, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(RepositoriesPopulator.class);
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("Loading sample data");
        populate();
    }

    private void populate() {
        User user1, user2, user3;
        Post post1, post2, post3, post4;
        Connection connection1, connection2, connection3;
        ConnectionsWriteController connectionsWriteController = applicationContext.getBean(ConnectionsWriteController.class);
        PostsWriteController postsWriteController = applicationContext.getBean(PostsWriteController.class);

        user1 = new User("Cornelia", "cdavisafc");
        connectionsWriteController.newUser(user1,null);
        user2 = new User("Max", "madmax");
        connectionsWriteController.newUser(user2,null);
        user3 = new User( "Glen", "gmaxdavis");
        connectionsWriteController.newUser(user3,null);

        post1 = new Post(2L, "Chicken Pho", "This is my attempt to recreate what I ate in Vietnam...");
        postsWriteController.newPost(post1, null);
        post2 = new Post(1L, "Whole Orange Cake", "That's right, you blend up whole oranges, rind and all...");
        postsWriteController.newPost(post2, null);
        post3 = new Post(1L, "German Dumplings (Kloesse)", "Russet potatoes, flour (gluten free!) and more...");
        postsWriteController.newPost(post3, null);
        post4 = new Post(3L, "French Press Lattes", "We've figured out how to make these dairy free, but just as good!...");
        postsWriteController.newPost(post4, null);

        connection1 = new Connection(2L, 1L);
        connectionsWriteController.newConnection(connection1, null);
        connection2 = new Connection(1L, 2L);
        connectionsWriteController.newConnection(connection2, null);
        connection3 = new Connection(1L, 3L);
        connectionsWriteController.newConnection(connection3, null);

    }
}
