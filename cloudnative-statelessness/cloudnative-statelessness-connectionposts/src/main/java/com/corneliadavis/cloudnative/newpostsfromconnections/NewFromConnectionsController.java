package com.corneliadavis.cloudnative.newpostsfromconnections;

import com.corneliadavis.cloudnative.Utils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;

@RefreshScope
@RestController
public class NewFromConnectionsController {

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

    private static final Logger logger = LoggerFactory.getLogger(NewFromConnectionsController.class);

    @Value("${newfromconnectionscontroller.connectionsUrl}")
    private String connectionsUrl;
    @Value("${newfromconnectionscontroller.postsUrl}")
    private String postsUrl;
    @Value("${newfromconnectionscontroller.usersUrl}")
    private String usersUrl;
    @Value("${INSTANCE_IP}")
    private String ip;
    @Value("${INSTANCE_PORT}")
    private String p;


    @RequestMapping(method = RequestMethod.GET, value="/connectionsNewPosts/{username}")
    public Iterable<PostSummary> getByUsername(@PathVariable("username") String username, HttpServletResponse response) {

        ArrayList<PostSummary> postSummaries = new ArrayList<PostSummary>();
        logger.info(Utils.ipTag(ip,p) + "getting posts for user network " + username);

        String ids = "";
        RestTemplate restTemplate = new RestTemplate();

        // get connections
        ResponseEntity<ConnectionResult[]> respConns = restTemplate.getForEntity(connectionsUrl+username, ConnectionResult[].class);
        ConnectionResult[] connections = respConns.getBody();
        for (int i=0; i<connections.length; i++) {
            if (i > 0) ids += ",";
            ids += connections[i].getFollowed().toString();
        }
        logger.info(Utils.ipTag(ip,p) + "connections = " + ids);

        // get posts for those connections
        ResponseEntity<PostResult[]> respPosts = restTemplate.getForEntity(postsUrl+ids, PostResult[].class);
        PostResult[] posts = respPosts.getBody();

        for (int i=0; i<posts.length; i++)
            postSummaries.add(new PostSummary(getUsersname(posts[i].getUserId()), posts[i].getTitle(), posts[i].getDate()));

        return postSummaries;
    }

    private String getUsersname(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResult> resp = restTemplate.getForEntity(usersUrl+id, UserResult.class);
        return resp.getBody().getName();
    }
}
