package com.corneliadavis.cloudnative.posts.projection;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by corneliadavis on 9/4/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
