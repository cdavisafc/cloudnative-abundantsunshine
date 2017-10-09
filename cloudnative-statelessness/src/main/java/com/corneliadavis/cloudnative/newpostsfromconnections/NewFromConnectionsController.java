package com.corneliadavis.cloudnative.newpostsfromconnections;

import com.corneliadavis.cloudnative.config.CloudnativeApplication;
import com.corneliadavis.cloudnative.connections.Connection;
import com.corneliadavis.cloudnative.posts.Post;
import com.corneliadavis.cloudnative.connections.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@RefreshScope
@RestController
public class NewFromConnectionsController {

    private static final Logger logger = LoggerFactory.getLogger(NewFromConnectionsController.class);

    @Value("${newfromconnectionscontroller.connectionsUrl}")
    private String connectionsUrl;
    @Value("${newfromconnectionscontroller.postsUrl}")
    private String postsUrl;
    @Value("${newfromconnectionscontroller.usersUrl}")
    private String usersUrl;


    @RequestMapping(method = RequestMethod.GET, value="/connectionsNewPosts")
    public Iterable<PostSummary> getByUsername(@CookieValue(value = "userToken", required=false) String token, HttpServletResponse response) {

        if (token == null)
            response.setStatus(401);
        else {
            String username = CloudnativeApplication.validTokens.get(token);
            if (username == null)
                response.setStatus(401);
            else {

                ArrayList<PostSummary> postSummaries = new ArrayList<PostSummary>();
                logger.info("getting posts for user network " + username);

                String ids = "";
                RestTemplate restTemplate = new RestTemplate();

                // get connections
                ResponseEntity<Connection[]> respConns = restTemplate.getForEntity(connectionsUrl + username, Connection[].class);
                Connection[] connections = respConns.getBody();
                for (int i = 0; i < connections.length; i++) {
                    if (i > 0) ids += ",";
                    ids += connections[i].getFollowed().toString();
                }
                logger.info("connections = " + ids);

                // get posts for those connections
                ResponseEntity<Post[]> respPosts = restTemplate.getForEntity(postsUrl + ids, Post[].class);
                Post[] posts = respPosts.getBody();

                for (int i = 0; i < posts.length; i++)
                    postSummaries.add(new PostSummary(getUsersname(posts[i].getUserId()), posts[i].getTitle(), posts[i].getDate()));

                return postSummaries;
            }
        }
        return null;
    }

    private String getUsersname(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User> resp = restTemplate.getForEntity(usersUrl+id, User.class);
        return resp.getBody().getName();
    }
}
