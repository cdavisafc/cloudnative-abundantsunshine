package com.corneliadavis.cloudnative.config;

import com.corneliadavis.cloudnative.connectionsposts.eventhandlers.EventHandler;
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
        EventHandler eventsController = applicationContext.getBean(EventHandler.class);

        user1 = new UserEvent("created", 1L,"Cornelia", "cdavisafc");
        eventsController.userEvent(user1);
        user2 = new UserEvent("created",2L,"Max", "madmax");
        eventsController.userEvent(user2);
        user3 = new UserEvent("created", 3L, "Glen", "gmaxdavis");
        eventsController.userEvent(user3);

        post1 = new PostEvent("created",1L, new Date(), 2L, "Max Title", "The body of the post");
        eventsController.postEvent(post1);
        post2 = new PostEvent("created",2L, new Date(), 1L, "Cornelia Title", "The body of the post");
        eventsController.postEvent(post2);
        post3 = new PostEvent("created",3L, new Date(), 1L, "Cornelia Title2", "The body of the post");
        eventsController.postEvent(post3);
        post4 = new PostEvent("created",4L, new Date(), 3L, "Glen Title", "The body of the post");
        eventsController.postEvent(post4);

        connection1 = new ConnectionEvent("created",1L, 2L, 1L);
        eventsController.connectionEvent(connection1);
        connection2 = new ConnectionEvent("created",2L, 1L, 2L);
        eventsController.connectionEvent(connection2);
        connection3 = new ConnectionEvent("created",3L, 1L, 3L);
        eventsController.connectionEvent(connection3);

    }
}
