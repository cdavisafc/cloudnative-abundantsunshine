package com.corneliadavis.cloudnative.connectionsposts;

import com.corneliadavis.cloudnative.Utils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;

@RefreshScope
@RestController
public class ConnectionsPostsController {

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class PostResult {
        @JsonProperty
        Long userId;
        @JsonProperty
        String title;
        @JsonProperty
        Date date;

        public Long getUserId() {
            return userId;
        }

        public String getTitle() {
            return title;
        }

        public Date getDate() {
            return date;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class ConnectionResult {
        @JsonProperty
        Long followed;

        public Long getFollowed() {
            return followed;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class UserResult {
        @JsonProperty
        String name;

        public String getName() {
            return name;
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(ConnectionsPostsController.class);

    @Value("${connectionpostscontroller.connectionsUrl}")
    private String connectionsUrl;
    @Value("${connectionpostscontroller.postsUrl}")
    private String postsUrl;
    @Value("${connectionpostscontroller.usersUrl}")
    private String usersUrl;

    private StringRedisTemplate template;

    private boolean isHealthy = true;

    @Autowired
    public ConnectionsPostsController(StringRedisTemplate template) {
        this.template = template;
    }

    @Autowired
    Utils utils;

    @RequestMapping(method = RequestMethod.GET, value="/connectionsposts")
    public Iterable<PostSummary> getByUsername(@CookieValue(value = "userToken", required=false) String token, HttpServletResponse response) {

        if (token == null) {
            logger.info(utils.ipTag() + "connectionsPosts access attempt without auth token");
            response.setStatus(401);
        } else {
            ValueOperations<String, String> ops = this.template.opsForValue();
            String username = ops.get(token);
            if (username == null) {
                logger.info(utils.ipTag() + "connectionsPosts access attempt with invalid token");
                response.setStatus(401);
            } else {

                ArrayList<PostSummary> postSummaries = new ArrayList<PostSummary>();
                logger.info(utils.ipTag() + "getting posts for user network " + username);

                String ids = "";
                RestTemplate restTemplate = new RestTemplate();

                // get connections
                String secretQueryParam = "?secret=" + utils.getConnectionsSecret();
                ResponseEntity<ConnectionResult[]> respConns = restTemplate.getForEntity(connectionsUrl + username + secretQueryParam, ConnectionResult[].class);
                ConnectionResult[] connections = respConns.getBody();
                for (int i = 0; i < connections.length; i++) {
                    if (i > 0) ids += ",";
                    ids += connections[i].getFollowed().toString();
                }
                logger.info(utils.ipTag() + "connections = " + ids);

                secretQueryParam = "&secret=" + utils.getPostsSecret();
                // get posts for those connections
                ResponseEntity<PostResult[]> respPosts = restTemplate.getForEntity(postsUrl + ids + secretQueryParam, PostResult[].class);
                PostResult[] posts = respPosts.getBody();

                for (int i = 0; i < posts.length; i++)
                    postSummaries.add(new PostSummary(getUsersname(posts[i].getUserId()), posts[i].getTitle(), posts[i].getDate()));

                return postSummaries;
            }
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value="/healthz")
    public void healthCheck(HttpServletResponse response) {

        if (this.isHealthy) response.setStatus(200);
        else response.setStatus(500);

    }


    private String getUsersname(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String secretQueryParam = "?secret=" + utils.getConnectionsSecret();
        ResponseEntity<UserResult> resp = restTemplate.getForEntity(usersUrl + id + secretQueryParam, UserResult.class);
        return resp.getBody().getName();
    }
}
