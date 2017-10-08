package com.corneliadavis.cloudnative;

//import com.corneliadavis.cloudnative.config.CloudnativeEventDrivenApplication;
import com.corneliadavis.cloudnative.config.CloudnativeApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.Cookie;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudnativeApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class CloudnativeStatelessnessApplicationTests {

	@Autowired
	private  MockMvc mockMvc;

	@Test
	public void contextLoads() {
	}

	@Test
	public void	actuator() throws Exception {

		mockMvc.perform(get("/env"))
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
	public void	checkPostCounts() throws Exception {

		mockMvc.perform(get("/posts"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$", hasSize(4)));

	}
	@Test
	public void	checkConnectionCounts() throws Exception {
		mockMvc.perform(get("/connections"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$", hasSize(3)));

	}
/*	@Test
	public void	checkNewPostCounts() throws Exception {
		mockMvc.perform(get("/connectionsNewPosts/cdavisafc"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$", hasSize(2)));
	}*/

	@Test
	public void	newPostsNoToken() throws Exception {
		mockMvc.perform(get("/connectionsNewPosts"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void	newPostsInvalidToken() throws Exception {
		mockMvc.perform(get("/connectionsNewPosts").cookie(new Cookie("userToken", "1234")))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void	loginNoName() throws Exception {
		mockMvc.perform(post("/login"))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void	loginNamed() throws Exception {
		mockMvc.perform(post("/login").param("name", "cdavisafc"))
				.andExpect(cookie().exists("userToken"));
	}

	@Test
	public void	newPostsValidToken() throws Exception {
		assertFalse(CloudnativeApplication.validTokens.isEmpty());

		String validToken = CloudnativeApplication.validTokens.keySet().iterator().next();
		String validName = CloudnativeApplication.validTokens.get(validToken);

		String query = System.getenv("SEED_INGREDIENT");
		if (query == null) query = "rice%20flour";

		mockMvc.perform(get("/connectionsNewPosts").cookie(new Cookie("userToken", validToken)))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$", hasSize(2)));
	}

}
