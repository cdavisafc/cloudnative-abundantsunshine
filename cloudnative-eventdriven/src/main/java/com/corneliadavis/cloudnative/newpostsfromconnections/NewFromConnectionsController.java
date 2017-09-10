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
    public Iterable<PostSummary> getByUsername(@PathVariable("username") String username, HttpServletResponse response) {

        loadTestData();

        Iterable<PostSummary> postSummaries;
        logger.info("getting posts for user network " + username);

        //postSummaries = mPostRepository.findAllOfThem();
        //postSummaries = mPostRepository.findByUsername(username);
        postSummaries = mPostRepository.findForUsersConnections(username);

        return postSummaries;
    }

    private void loadTestData () {
        MUser user1 = new MUser();
        user1.setId(1L);
        user1.setUsername("cdavisafc");
        user1.setName("Cornelia");
        mUserRepository.save(user1);
        MUser user2 = new MUser();
        user2.setId(2L);
        user2.setUsername("madmax");
        user2.setName("Max");
        mUserRepository.save(user2);
        MUser user3 = new MUser();
        user3.setId(3L);
        user3.setUsername("gmaxdavis");
        user3.setName("Glen");
        mUserRepository.save(user3);

        MPost post1 = new MPost();
        post1.setId(1L);
        post1.setDate(new Date());
        post1.setUserId(2L);
        post1.setTitle("The Title");
        post1.setmUser(user2);
        mPostRepository.save(post1);
        MPost post2 = new MPost();
        post2.setId(2L);
        post2.setDate(new Date());
        post2.setUserId(1L);
        post2.setTitle("C Title");
        post2.setmUser(user1);
        mPostRepository.save(post2);
        post2.setId(3L);
        post2.setDate(new Date());
        post2.setUserId(1L);
        post2.setTitle("C Title again");
        post2.setmUser(user1);
        mPostRepository.save(post2);
        post2.setId(4L);
        post2.setDate(new Date());
        post2.setUserId(1L);
        post2.setTitle("Glen Title");
        post2.setmUser(user3);
        mPostRepository.save(post2);

        MConnection connection = new MConnection();
        connection.setId(1L);
        connection.setFollowerUser(user2);
        connection.setFollowedUser(user1);
        mConnectionRepository.save(connection);
        connection.setId(2L);
        connection.setFollowerUser(user1);
        connection.setFollowedUser(user2);
        mConnectionRepository.save(connection);
        connection.setId(3L);
        connection.setFollowerUser(user1);
        connection.setFollowedUser(user3);
        mConnectionRepository.save(connection);

    }
}
