package com.corneliadavis.cloudnative.utilities;

import com.corneliadavis.cloudnative.connections.Connection;
import com.corneliadavis.cloudnative.connections.ConnectionRepository;
import com.corneliadavis.cloudnative.connections.User;
import com.corneliadavis.cloudnative.connections.UserRepository;
import com.corneliadavis.cloudnative.connections.write.ConnectionsWriteController;
import com.corneliadavis.cloudnative.posts.Post;
import com.corneliadavis.cloudnative.posts.write.PostsWriteController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
public class InitializationController implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(InitializationController.class);
    private UserRepository userRepository;
    private ConnectionRepository connectionRepository;
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        applicationContext = ctx;
    }

    @Autowired
    public InitializationController(UserRepository userRepository, ConnectionRepository connectionRepository) {
        this.userRepository = userRepository;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(method = RequestMethod.POST, value="/populateSampleData")
    public void populate(HttpServletResponse response) {
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

        post1 = new Post(2L, "Max Title", "The body of the post");
        postsWriteController.newPost(post1, null);
        post2 = new Post(1L, "Cornelia Title", "The body of the post");
        postsWriteController.newPost(post2, null);
        post3 = new Post(1L, "Cornelia Title2", "The body of the post");
        postsWriteController.newPost(post3, null);
        post4 = new Post(3L, "Glen Title", "The body of the post");
        postsWriteController.newPost(post4, null);

        connection1 = new Connection(2L, 1L);
        connectionsWriteController.newConnection(connection1, null);
        connection2 = new Connection(1L, 2L);
        connectionsWriteController.newConnection(connection2, null);
        connection3 = new Connection(1L, 3L);
        connectionsWriteController.newConnection(connection3, null);

    }


}
