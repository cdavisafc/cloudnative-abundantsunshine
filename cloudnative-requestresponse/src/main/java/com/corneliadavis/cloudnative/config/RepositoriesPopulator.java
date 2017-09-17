package com.corneliadavis.cloudnative.config;

import com.corneliadavis.cloudnative.connections.Connection;
import com.corneliadavis.cloudnative.connections.ConnectionRepository;
import com.corneliadavis.cloudnative.connections.UserRepository;
import com.corneliadavis.cloudnative.posts.Post;
import com.corneliadavis.cloudnative.connections.User;
import com.corneliadavis.cloudnative.posts.PostRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by corneliadavis on 9/4/17.
 */
@Component
public class RepositoriesPopulator implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().equals(applicationContext)) {
            UserRepository userRepository =
                    BeanFactoryUtils.beanOfTypeIncludingAncestors(applicationContext, UserRepository.class);

            ConnectionRepository connectionRepository =
                    BeanFactoryUtils.beanOfTypeIncludingAncestors(applicationContext, ConnectionRepository.class);

            PostRepository postRepository =
                    BeanFactoryUtils.beanOfTypeIncludingAncestors(applicationContext, PostRepository.class);

            if (userRepository != null && userRepository.count() == 0) {
                populate(userRepository, connectionRepository, postRepository);
            }
        }

    }

    private void populate(UserRepository userRepository, ConnectionRepository connectionRepository, PostRepository postRepository) {
        User user;
        user = new User("Cornelia", "cdavisafc");    userRepository.save(user);
        user = new User("Max", "madmax");      userRepository.save(user);
        user = new User("Glen", "gmaxdavis");        userRepository.save(user);

        Connection connection;
        connection = new Connection(userRepository.findByUsername("cdavisafc").getId(),
                userRepository.findByUsername("madmax").getId());
        connectionRepository.save(connection);
        connection = new Connection(userRepository.findByUsername("cdavisafc").getId(),
                userRepository.findByUsername("gmaxdavis").getId());    connectionRepository.save(connection);
        connection = new Connection(userRepository.findByUsername("gmaxdavis").getId(),
                userRepository.findByUsername("madmax").getId());    connectionRepository.save(connection);
        connection = new Connection(userRepository.findByUsername("madmax").getId(),
                userRepository.findByUsername("cdavisafc").getId());    connectionRepository.save(connection);

        Post post;
        post = new Post(userRepository.findByUsername("cdavisafc").getId(),"First Post", "This is the body of the first post");
        postRepository.save(post);
        post = new Post(userRepository.findByUsername("cdavisafc").getId(),"Second Post", "This is the body of the second post");
        postRepository.save(post);
        post = new Post(userRepository.findByUsername("cdavisafc").getId(),"Third Post", "This is the body of the third post");
        postRepository.save(post);
        post = new Post(userRepository.findByUsername("madmax").getId(),"First Post", "This is the body of Max's first post");
        postRepository.save(post);



    }

}
