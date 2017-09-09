package com.corneliadavis.cloudnative;

import com.corneliadavis.cloudnative.config.CloudnativeHelloworldApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
public class CloudnativeHelloworldApplicationTests {

	@Test
	public void contextLoads() {
	}

/*	@Test
	public void	actuator() throws Exception {

		mockMvc.perform(get("/env"))
				.andExpect(status().isOk());
	}

	@Test
	public void	helloNoToken() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void	helloInvalidToken() throws Exception {
		mockMvc.perform(get("/").cookie(new Cookie("userToken", "1234")))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void	loginNoName() throws Exception {
		mockMvc.perform(post("/login"))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void	loginNamed() throws Exception {
		mockMvc.perform(post("/login").param("name", "Cornelia"))
				.andExpect(cookie().exists("userToken"));
	}

	@Test
	public void	helloValidToken() throws Exception {
		assertFalse(CloudnativeHelloworldApplication.validTokens.isEmpty());

		String validToken = CloudnativeHelloworldApplication.validTokens.keySet().iterator().next();
		String validName = CloudnativeHelloworldApplication.validTokens.get(validToken);

		String query = System.getenv("SEED_INGREDIENT");
		if (query == null) query = "rice%20flour";

		mockMvc.perform(get("/").cookie(new Cookie("userToken", validToken)))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(validName)))
				.andExpect(content().string(containsString(query)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.recipes").isEmpty());
	}*/

}
