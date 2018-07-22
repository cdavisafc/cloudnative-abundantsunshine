package com.corneliadavis.cloudnative.connectionsposts;

import com.corneliadavis.cloudnative.Utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class PostsServiceClient {

    private PostsResultsRepository postResultsRepository;

    private static final Logger logger = LoggerFactory.getLogger(ConnectionsPostsController.class);

    @Value("${connectionpostscontroller.postsUrl}")
    private String postsUrl;
    @Value("${connectionpostscontroller.usersUrl}")
    private String usersUrl;

    @Autowired
    Utils utils;

    @Autowired
    public PostsServiceClient(PostsResultsRepository postResultsRepository) {
        this.postResultsRepository = postResultsRepository;
    }

    @Retryable( value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 500))
    public ArrayList<PostSummary> getPosts(String ids, RestTemplate restTemplate) throws Exception {

        ArrayList<PostSummary> postSummaries = new ArrayList<PostSummary>();

        String secretQueryParam = "&secret=" + utils.getPostsSecret();

        logger.info("Trying getPosts: " + postsUrl + ids + secretQueryParam);

        ResponseEntity<ConnectionsPostsController.PostResult[]> respPosts = restTemplate.getForEntity(postsUrl + ids + secretQueryParam, ConnectionsPostsController.PostResult[].class);
        if (respPosts.getStatusCode().is5xxServerError()) {
            throw new HttpServerErrorException(respPosts.getStatusCode(), "Exception thrown in obtaining Posts");
        } else {
            ConnectionsPostsController.PostResult[] posts = respPosts.getBody();
            for (int i = 0; i < posts.length; i++)
                postSummaries.add(new PostSummary(getUsersname(posts[i].getUserId(), restTemplate), posts[i].getTitle(), posts[i].getDate()));
            ObjectMapper objectMapper = new ObjectMapper();
            String postSummariesJson = objectMapper.writeValueAsString(postSummaries);
            PostResults postResults = new PostResults(ids, postSummariesJson);
            postResultsRepository.save(postResults);
            return postSummaries;
        }

    }

    @Recover
    public ArrayList<PostSummary> returnCached(ResourceAccessException e, String ids, RestTemplate restTemplate) throws Exception {
        logger.info("Failed to connect to or obtain results from Posts service - returning cached results");

        PostResults postResults = postResultsRepository.findById(ids).get();
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<PostSummary> postSummaries;
        try {
            postSummaries = objectMapper.readValue(postResults.getSummariesJson(), new TypeReference<ArrayList<PostSummary>>() {});
        } catch (Exception ec) {
            logger.info("Exception on deserialization " + ec.getClass() + " message = " + ec.getMessage());
            return null;
        }
        return postSummaries;

    }

    private String getUsersname(Long id, RestTemplate restTemplate) {
        String secretQueryParam = "?secret=" + utils.getConnectionsSecret();
        ResponseEntity<ConnectionsPostsController.UserResult> resp = restTemplate.getForEntity(usersUrl + id + secretQueryParam, ConnectionsPostsController.UserResult.class);
        return resp.getBody().getName();
    }


}
