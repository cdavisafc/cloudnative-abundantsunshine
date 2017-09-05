package com.corneliadavis.cloudnative.web;

import com.corneliadavis.cloudnative.domain.Connection;
import com.corneliadavis.cloudnative.repositories.ConnectionRepository;
import com.corneliadavis.cloudnative.repositories.UserRepository;
import com.corneliadavis.cloudnative.domain.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;

@RefreshScope
@RestController
public class ConnectionsController {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionsController.class);
    private UserRepository userRepository;
    private ConnectionRepository connectionRepository;

    @Autowired
    public ConnectionsController(UserRepository userRepository, ConnectionRepository connectionRepository) {
        this.userRepository = userRepository;
        this.connectionRepository = connectionRepository;
    }

	@RequestMapping(method = RequestMethod.GET, value="/users")
	public Iterable<User> getUsers(HttpServletResponse response) {

        logger.info("getting users");
        Iterable<User> users;
        users = userRepository.findAll();

		return users;
	}

	@RequestMapping(method = RequestMethod.GET, value="/users/{username}")
	public User getByUsername(@PathVariable("username") String username, HttpServletResponse response) {
        logger.info("getting user " + username);
        return userRepository.findByUsername(username);
    }

    @RequestMapping(method = RequestMethod.GET, value="/connections")
    public Iterable<Connection> getConnections(HttpServletResponse response) {

        logger.info("getting connections");
        Iterable<Connection> connections;
        connections = connectionRepository.findAll();

        return connections;
    }

    @RequestMapping(method = RequestMethod.GET, value="/connections/{username}")
    public Iterable<Connection> getConnections(@PathVariable("username") String username, HttpServletResponse response) {
        logger.info("getting connections for username " + username);
        Long userId = getByUsername(username, null).getId();
        Iterable<Connection> connections;
        connections = connectionRepository.findByFollower(userId);

        return connections;
    }



/*	private Recipe[] parseRecipes(Map<String, Object> resp) {

		List<Recipe> recipies = new ArrayList<Recipe>();

		Object[] recipiesIn = (Object[]) resp.get("recipes");
		for (int i=0; i<recipiesIn.length; i++) {
			Map<String, Object> recipe = (Map<String, Object>) recipiesIn[i];
			recipies.add(new Recipe((String)recipe.get("recipe_id"), (String)recipe.get("title")));
		}

		return recipies.toArray(new Recipe[0]);
	}*/

}
