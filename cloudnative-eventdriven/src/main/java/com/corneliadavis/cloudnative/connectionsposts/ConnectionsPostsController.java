package com.corneliadavis.cloudnative.connectionsposts;

import com.corneliadavis.cloudnative.connectionsposts.localstorage.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RefreshScope
@RestController
public class ConnectionsPostsController {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionsPostsController.class);

    private MUserRepository mUserRepository;
    private MPostRepository mPostRepository;
    private MConnectionRepository mConnectionRepository;

    @Autowired
    public ConnectionsPostsController(MUserRepository mUserRepository,
                                      MPostRepository mPostRepository,
                                      MConnectionRepository mConnectionRepository) {
        this.mUserRepository = mUserRepository;
        this.mPostRepository = mPostRepository;
        this.mConnectionRepository = mConnectionRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value="/connectionsposts/{username}")
    public Iterable<PostSummary> getByUsername(@PathVariable("username") String username,
                                               HttpServletResponse response) {

        Iterable<PostSummary> postSummaries;
        logger.info("getting posts for user network " + username);

        postSummaries = mPostRepository.findForUsersConnections(username);

        return postSummaries;
    }

}
