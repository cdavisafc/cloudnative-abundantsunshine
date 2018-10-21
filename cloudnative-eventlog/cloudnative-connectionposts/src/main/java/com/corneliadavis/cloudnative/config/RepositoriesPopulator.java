package com.corneliadavis.cloudnative.config;

import com.corneliadavis.cloudnative.connectionsposts.eventhandlers.EventsController;
import com.corneliadavis.cloudnative.eventschemas.ConnectionEvent;
import com.corneliadavis.cloudnative.eventschemas.UserEvent;
import com.corneliadavis.cloudnative.eventschemas.PostEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by corneliadavis on 9/29/18.
 */
@Component
public class RepositoriesPopulator implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(RepositoriesPopulator.class);
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void populate() {
        UserEvent user1, user2, user3;
        PostEvent post1, post2, post3, post4;
        ConnectionEvent connection1, connection2, connection3;
        EventsController eventsController = applicationContext.getBean(EventsController.class);

        user1 = new UserEvent(1L,"Cornelia", "cdavisafc");
        eventsController.newUser(user1,null);
        user2 = new UserEvent(2L,"Max", "madmax");
        eventsController.newUser(user2,null);
        user3 = new UserEvent( 3L, "Glen", "gmaxdavis");
        eventsController.newUser(user3,null);

        post1 = new PostEvent(1L, new Date(), 2L, "Max Title", "The body of the post");
        eventsController.newPost(post1, null);
        post2 = new PostEvent(2L, new Date(), 1L, "Cornelia Title", "The body of the post");
        eventsController.newPost(post2, null);
        post3 = new PostEvent(3L, new Date(), 1L, "Cornelia Title2", "The body of the post");
        eventsController.newPost(post3, null);
        post4 = new PostEvent(4L, new Date(), 3L, "Glen Title", "The body of the post");
        eventsController.newPost(post4, null);

        connection1 = new ConnectionEvent(1L, 2L, 1L);
        eventsController.newConnection(connection1, null);
        connection2 = new ConnectionEvent(2L, 1L, 2L);
        eventsController.newConnection(connection2, null);
        connection3 = new ConnectionEvent(3L, 1L, 3L);
        eventsController.newConnection(connection3, null);

    }
}
