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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudnativeApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = {
		"connectionspostscontroller.url:http://localhost:9999",
		"postscontroller.url:http://localhost:9999"})
@AutoConfigureMockMvc
public class CloudnativeEventDrivenTests implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Autowired
	private  MockMvc mockMvc;

	@Before
	public void loadData() {
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
	public void	checkUserCounts() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(3)));
    }
    @Test
    public void	checkConnectionCounts() throws Exception {
        mockMvc.perform(get("/connections"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(3)));

    }

}
