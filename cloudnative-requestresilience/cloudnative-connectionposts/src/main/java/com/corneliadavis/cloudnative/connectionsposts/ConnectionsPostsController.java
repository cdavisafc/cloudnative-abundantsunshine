package com.corneliadavis.cloudnative.connectionsposts;

import com.corneliadavis.cloudnative.Utils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;

@RefreshScope
@RestController
public class ConnectionsPostsController implements InitializingBean {

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
    @Value("${connectionpostscontroller.implementRetries}")
    private String implementRetriesS;
    private Boolean implementRetries = false;
    @Value("${connectionpostscontroller.connectTimeout}")
    private int connectTimeout;
    @Value("${connectionpostscontroller.readTimeout}")
    private int readTimeout;

    private StringRedisTemplate stringRedisTemplate;

    private boolean isHealthy = true;

    @Autowired
    public ConnectionsPostsController(StringRedisTemplate template) {
        this.stringRedisTemplate = template;
    }

    @Autowired
    Utils utils;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Autowired
    PostsServiceClient postsServiceClient;

    @Override
    public void afterPropertiesSet() {
        logger.info(utils.ipTag() + "Retry config is " + implementRetriesS);
        if (implementRetriesS.contentEquals("true")) {
            this.implementRetries = true;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value="/connectionsposts")
    public Iterable<PostSummary> getByUsername(@CookieValue(value = "userToken", required=false) String token, HttpServletResponse response) {

        if (token == null) {
            logger.info(utils.ipTag() + "connectionsPosts access attempt without auth token");
            response.setStatus(401);
        } else {
            ValueOperations<String, String> ops = this.stringRedisTemplate.opsForValue();
            String username = ops.get(token);
            if (username == null) {
                logger.info(utils.ipTag() + "connectionsPosts access attempt with invalid token");
                response.setStatus(401);
                return null;
            } else {

                logger.info(utils.ipTag() + "getting posts for user network " + username);

                ArrayList<PostSummary> postSummaries = new ArrayList<PostSummary>();
                String ids = "";
				RestTemplate restTemplate = restTemplateBuilder
												.setConnectTimeout(connectTimeout)
												.setReadTimeout(readTimeout)
												.build();

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
                // try getting the Posts for these users

                try {
                    postSummaries = postsServiceClient.getPosts(ids, restTemplate);
                    response.setStatus(200);
                    return postSummaries;
                } catch (HttpServerErrorException e) {
                    // Occurs when call to Posts service returned a 500
                    logger.info(utils.ipTag() + "Call to Posts service returned 500");
                    response.setStatus(500);
                    return null;
                } catch (ResourceAccessException e) {
                    logger.info(utils.ipTag() + "Call to Posts service timed out");
                    response.setStatus(500);
                    return null;
                } catch (Exception e) {
                    logger.info(utils.ipTag() + "(unexpected) Exception " + e.getMessage() + " Exception Class " + e.getClass());
                    return null;
                }
            }
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value="/healthz")
    public void healthCheck(HttpServletResponse response) {

        if (this.isHealthy) response.setStatus(200);
        else response.setStatus(500);

    }

}
