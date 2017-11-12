package com.corneliadavis.cloudnative;

import com.corneliadavis.cloudnative.config.CloudnativeApplication;
import com.corneliadavis.cloudnative.connections.Connection;
import com.corneliadavis.cloudnative.connections.ConnectionsController;
import com.corneliadavis.cloudnative.connections.User;
import com.corneliadavis.cloudnative.connections.UserRepository;
import com.corneliadavis.cloudnative.newpostsfromconnections.NewFromConnectionsController;
import com.corneliadavis.cloudnative.posts.Post;
import com.corneliadavis.cloudnative.posts.PostsController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CloudnativeApplication.class)
public class CloudnativeStatelessnessApplicationTests implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private ConnectionsController connectionsController;
    @Autowired
    private PostsController postsController;
    @Autowired
    private NewFromConnectionsController newFromConnectionsController;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
 /*       this.connectionsController = applicationContext.getBean(ConnectionsController.class);
        this.postsController = applicationContext.getBean(PostsController.class);
        this.newFromConnectionsController = applicationContext.getBean(NewFromConnectionsController.class);
        this.connectionsController = new ConnectionsController();
        this.postsController = applicationContext.getBean(PostsController.class);
        this.newFromConnectionsController = applicationContext.getBean(NewFromConnectionsController.class);*/
    }

    @Test
    public void contextLoads() {
    }

/*    @Test
    public void	actuator() throws Exception {

        mockMvc.perform(get("/env"))
                .andExpect(status().isOk());
    }*/


    @Test
    public void	checkUserCounts() throws Exception {
        Iterable<User> users = connectionsController.getUsers();
        assertEquals(((Collection<?>)users).size(), 3);
    }
    @Test
    public void	checkPostCounts() throws Exception {
        Iterable<Post> posts = postsController.getPostsByUserId(null);
        assertEquals(((Collection<?>)posts).size(), 4);
    }
    @Test
    public void	checkConnectionCounts() throws Exception {
        Iterable<Connection> users = connectionsController.getConnections();
        assertEquals(((Collection<?>)users).size(), 3);
    }

    @Test(expected = RuntimeException.class)
    public void	exceptionOnNewPostsWithoutUsername() throws Exception {
        newFromConnectionsController.getByUsername(null);
    }
/*    @Test
    public void	checkNewPostCounts() throws Exception {
        mockMvc.perform(get("/connectionsNewPosts/cdavisafc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(2)));
    }*/

    @Test(expected = Exception.class)
    public void exceptionOnNonUniqueUsername (){
        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        userRepository.save(new User("doesn't matter", "cdavisafc"));
    }

}
