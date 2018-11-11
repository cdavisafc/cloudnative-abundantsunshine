package com.corneliadavis.cloudnative.connectionsposts.projection;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by corneliadavis on 9/4/18.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
