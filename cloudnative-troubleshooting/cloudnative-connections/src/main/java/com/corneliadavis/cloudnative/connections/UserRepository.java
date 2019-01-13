package com.corneliadavis.cloudnative.connections;

import com.corneliadavis.cloudnative.connections.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by corneliadavis on 11/15/18.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
