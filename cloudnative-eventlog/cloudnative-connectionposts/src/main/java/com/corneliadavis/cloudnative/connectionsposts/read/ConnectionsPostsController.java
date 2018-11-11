package com.corneliadavis.cloudnative.connectionsposts.read;

import com.corneliadavis.cloudnative.connectionsposts.PostSummary;
import com.corneliadavis.cloudnative.connectionsposts.projection.ConnectionRepository;
import com.corneliadavis.cloudnative.connectionsposts.projection.PostRepository;
import com.corneliadavis.cloudnative.connectionsposts.projection.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
public class ConnectionsPostsController {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionsPostsController.class);

    private UserRepository userRepository;
    private PostRepository postRepository;
    private ConnectionRepository connectionRepository;

    @Autowired
    public ConnectionsPostsController(UserRepository userRepository,
                                      PostRepository postRepository,
                                      ConnectionRepository connectionRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value="/connectionsposts/{username}")
    public Iterable<PostSummary> getByUsername(@PathVariable("username") String username,
                                               HttpServletResponse response) {

        Iterable<PostSummary> postSummaries;
        logger.info("getting posts for user network " + username);

        postSummaries = postRepository.findForUsersConnections(username);

        return postSummaries;
    }

}
