package com.corneliadavis.cloudnative;

import com.corneliadavis.cloudnative.config.CloudnativeEventDrivenApplication;
import com.corneliadavis.cloudnative.connections.Connection;
import com.corneliadavis.cloudnative.connections.User;
import com.corneliadavis.cloudnative.posts.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertFalse;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudnativeEventDrivenApplication.class)
@AutoConfigureMockMvc
public class CloudnativeEventDrivenTests {

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

/*	@BeforeClass
    public static void initialize() throws Exception {
	    loadTestData();
    }

	public void loadTestData() throws Exception {

		user1 = new User("Cornelia", "cdavisafc");
		user2 = new User("Max", "madmax");
		user3 = new User( "Glen", "gmaxdavis");

		post1 = new Post(2L, "Max Title", "The body of the post");
		post2 = new Post(1L, "Cornelia Title", "The body of the post");
		post3 = new Post(1L, "Cornelia Title2", "The body of the post");
		post4 = new Post(3L, "Glen Title", "The body of the post");

		connection1 = new Connection(2L, 1L);
		connection2 = new Connection(1L, 2L);
		connection3 = new Connection(1L, 3L);

        mockMvc.perform(post("/users").content(mapper.writeValueAsString(user1)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(post("/users").content(mapper.writeValueAsString(user2)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(post("/users").content(mapper.writeValueAsString(user3)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/posts").content(mapper.writeValueAsString(post1)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(post("/posts").content(mapper.writeValueAsString(post2)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(post("/posts").content(mapper.writeValueAsString(post3)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(post("/posts").content(mapper.writeValueAsString(post4)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/connections").content(mapper.writeValueAsString(connection1)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(post("/connections").content(mapper.writeValueAsString(connection2)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(post("/connections").content(mapper.writeValueAsString(connection3)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

	} */
/*
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
    @Test
    public void	checkNewPostCounts() throws Exception {
        mockMvc.perform(get("/connectionsNewPosts/cdavisafc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(2)));
	}*/

/*	@Test
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
