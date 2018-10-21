package com.corneliadavis.cloudnative.config;

import com.corneliadavis.cloudnative.connections.ConnectionApi;
import com.corneliadavis.cloudnative.connections.write.ConnectionsWriteController;
import com.corneliadavis.cloudnative.connections.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by corneliadavis on 9/4/17.
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
        User user1, user2, user3;
        ConnectionApi connection1, connection2, connection3;
        ConnectionsWriteController connectionsWriteController = applicationContext.getBean(ConnectionsWriteController.class);

        logger.info("populating data");

        try {

            logger.info("new user cdavisafc");
            user1 = new User("Cornelia", "cdavisafc");
            connectionsWriteController.newUser(user1, null);
            user2 = new User("Max", "madmax");
            connectionsWriteController.newUser(user2, null);
            user3 = new User("Glen", "gmaxdavis");
            connectionsWriteController.newUser(user3, null);

            connection1 = new ConnectionApi("madmax", "cdavisafc");
            connectionsWriteController.newConnection(connection1, null);
            connection2 = new ConnectionApi("cdavisafc", "madmax");
            connectionsWriteController.newConnection(connection2, null);
            connection3 = new ConnectionApi("cdavisafc", "gmaxdavis");
            connectionsWriteController.newConnection(connection3, null);

        } catch (Exception e)
        {
            // slightly hacky but with a uniqueness constraint on the username for a UserEvent, can stop
            // repository population if it's already been done.
        }

    }

}
