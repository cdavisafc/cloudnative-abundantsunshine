package com.corneliadavis.cloudnative;

import com.corneliadavis.cloudnative.config.CloudnativeApplication;
import com.corneliadavis.cloudnative.config.RepositoriesPopulator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudnativeApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = {
        "spring.cloud.config.enabled:false",
        "connectionspostscontroller.url:http://localhost:9999"})
@AutoConfigureMockMvc
@DirtiesContext
public class CloudnativeStatelessnessApplicationTests implements ApplicationContextAware {

/*    public static class CustomLoader extends SpringBootContextLoader {

        @Override
        protected SpringApplication getSpringApplication() {
            SpringApplication app = super.getSpringApplication();
            app.
            return app;
        }
    }*/

    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Autowired
    private  MockMvc mockMvc;

    @Before
    public void loadData() throws InterruptedException {
        RepositoriesPopulator rp = applicationContext.getBean(RepositoriesPopulator.class);
        rp.populate();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void	actuator() throws Exception {

        mockMvc.perform(get("/actuator/env"))
                .andExpect(status().isOk());
    }

    @Test
    public void	checkPostCounts() throws Exception {

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(4)));

    }

    @Test
    public void	testHealthSimulation() throws Exception {

        mockMvc.perform(post("/infect"))
                .andExpect(status().isOk());
        mockMvc.perform(post("/heal"))
                .andExpect(status().isOk());
        //mockMvc.perform(get("/healthz"))
        //        .andExpect(status().isOk());
        // With the latest implementation will simply sleep for a long time after infected.

    }
}
