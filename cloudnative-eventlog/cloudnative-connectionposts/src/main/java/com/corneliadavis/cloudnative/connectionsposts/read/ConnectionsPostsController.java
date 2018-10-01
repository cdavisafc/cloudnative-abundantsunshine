package com.corneliadavis.cloudnative.connectionsposts.read;

import com.corneliadavis.cloudnative.connectionsposts.PostSummary;
import com.corneliadavis.cloudnative.connectionsposts.localstorage.MConnectionRepository;
import com.corneliadavis.cloudnative.connectionsposts.localstorage.MPostRepository;
import com.corneliadavis.cloudnative.connectionsposts.localstorage.MUserRepository;
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
                                      MPostRepository postRepository,
                                      MConnectionRepository connectionRepository) {
        this.mUserRepository = mUserRepository;
        this.mPostRepository = postRepository;
        this.mConnectionRepository = connectionRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value="/connectionsPosts/{username}")
    public Iterable<PostSummary> getByUsername(@PathVariable("username") String username,
                                               HttpServletResponse response) {

        Iterable<PostSummary> postSummaries;
        logger.info("getting posts for user network " + username);

        postSummaries = mPostRepository.findForUsersConnections(username);

        return postSummaries;
    }

}
