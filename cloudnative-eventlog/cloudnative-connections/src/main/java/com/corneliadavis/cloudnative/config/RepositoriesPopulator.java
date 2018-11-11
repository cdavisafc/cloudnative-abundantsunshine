package com.corneliadavis.cloudnative.config;

import com.corneliadavis.cloudnative.connections.ConnectionApi;
import com.corneliadavis.cloudnative.connections.projection.Connection;
import com.corneliadavis.cloudnative.connections.projection.ConnectionRepository;
import com.corneliadavis.cloudnative.connections.projection.User;
import com.corneliadavis.cloudnative.connections.projection.UserRepository;
import com.corneliadavis.cloudnative.connections.write.ConnectionsWriteController;
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
        Connection connection1, connection2, connection3;
        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        ConnectionRepository connectionRepository = applicationContext.getBean(ConnectionRepository.class);

        logger.info("populating data");

        try {

            logger.info("new user cdavisafc");
            user1 = new User(1L,"Cornelia", "cdavisafc");
            userRepository.save(user1);
            user2 = new User(2L,"Max", "madmax");
            userRepository.save(user2);
            user3 = new User(3L, "Glen", "gmaxdavis");
            userRepository.save(user3);

            connection1 = new Connection(1L,2L, 1L);
            connectionRepository.save(connection1);
            connection2 = new Connection(2L,1L, 2L);
            connectionRepository.save(connection2);
            connection3 = new Connection(3L,1L, 3L);
            connectionRepository.save(connection3);

        } catch (Exception e)
        {
            // slightly hacky but with a uniqueness constraint on the username for a UserEvent, can stop
            // repository population if it's already been done.
        }

    }

}
