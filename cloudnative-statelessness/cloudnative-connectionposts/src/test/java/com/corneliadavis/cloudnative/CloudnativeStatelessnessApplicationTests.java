package com.corneliadavis.cloudnative;

import com.corneliadavis.cloudnative.config.CloudnativeApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudnativeApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = {
        "newfromconnectionscontroller.connectionsUrl:http://localhost:8080/connections/",
        "newfromconnectionscontroller.postsUrl:http://localhost:8080/posts?userIds=",
        "newfromconnectionscontroller.usersUrl:http://localhost:8080/users/"})
@AutoConfigureMockMvc
public class CloudnativeStatelessnessApplicationTests implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Autowired
    private  MockMvc mockMvc;

    @Test
    public void contextLoads() {
    }

    @Test
    public void	actuator() throws Exception {

        mockMvc.perform(get("/actuator/env"))
                .andExpect(status().isOk());
    }

    @Test
    public void	helloInvalidToken() throws Exception {
        mockMvc.perform(get("/connectionsposts").cookie(new Cookie("userToken", "1234")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void	loginNoName() throws Exception {
        mockMvc.perform(post("/login"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void	loginNamed() throws Exception {
        mockMvc.perform(post("/login").param("username", "cdavisafc"))
                .andExpect(cookie().exists("userToken"));
    }

    // TODO: Fix this test - it's currently an integration test in that it requires connections and posts services
    // need to mock those for this test.
    /*@Test
    public void	helloValidToken() throws Exception {
        assertFalse(CloudnativeApplication.validTokens.isEmpty());

        String validToken = CloudnativeApplication.validTokens.keySet().iterator().next();
        String validName = CloudnativeApplication.validTokens.get(validToken);

        mockMvc.perform(get("/connectionsPosts").cookie(new Cookie("userToken", validToken)))
                .andExpect(status().isOk());
    }*/

}
