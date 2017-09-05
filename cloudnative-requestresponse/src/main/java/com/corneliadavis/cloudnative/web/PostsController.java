package com.corneliadavis.cloudnative.web;

import com.corneliadavis.cloudnative.config.CloudnativeHelloworldApplication;
import com.corneliadavis.cloudnative.domain.Connection;
import com.corneliadavis.cloudnative.domain.Post;
import com.corneliadavis.cloudnative.repositories.ConnectionRepository;
import com.corneliadavis.cloudnative.repositories.PostRepository;
import com.corneliadavis.cloudnative.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@RestController
public class PostsController {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionsController.class);
    private PostRepository postRepository;

    @Autowired
    public PostsController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value="/posts")
    public Iterable<Post> getPostsByUserId(@RequestParam(value="userId", required=false) Long userId, HttpServletResponse response) {

        Iterable<Post> posts;

        if (userId == null) {
            logger.info("getting all posts");
            posts = postRepository.findAll();
        } else {
            logger.info("getting posts for userId " + userId);
            posts = postRepository.findByUserId(userId);
        }

        return posts;
    }


    /*@RequestMapping(value="/login", method = RequestMethod.POST)
    public void whoareyou(@RequestParam(value="name", required=false) String name, HttpServletResponse response) {
		
		if (name == null)
		    response.setStatus(400);
		else {
            UUID uuid = UUID.randomUUID();
            String userToken = uuid.toString();

            //CloudnativeHelloworldApplication.validTokens.put(userToken, name);
            response.addCookie(new Cookie("userToken", userToken));
        }


    }*/


}
