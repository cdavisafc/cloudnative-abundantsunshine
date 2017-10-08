package com.corneliadavis.cloudnative.newpostsfromconnections;

import com.corneliadavis.cloudnative.newpostsfromconnections.localstorage.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;


@RefreshScope
@RestController
public class NewFromConnectionsController {

    private static final Logger logger = LoggerFactory.getLogger(NewFromConnectionsController.class);

    private MUserRepository mUserRepository;
    private MPostRepository mPostRepository;
    private MConnectionRepository mConnectionRepository;

    @Autowired
    public NewFromConnectionsController(MUserRepository mUserRepository,
                                        MPostRepository mPostRepository,
                                        MConnectionRepository mConnectionRepository) {
        this.mUserRepository = mUserRepository;
        this.mPostRepository = mPostRepository;
        this.mConnectionRepository = mConnectionRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value="/connectionsNewPosts/{username}")
    public Iterable<PostSummary> getByUsername(@PathVariable("username") String username,
                                               HttpServletResponse response) {

        Iterable<PostSummary> postSummaries;
        logger.info("getting posts for user network " + username);

        postSummaries = mPostRepository.findForUsersConnections(username);

        return postSummaries;
    }

}
