package com.corneliadavis.cloudnative.repositories;

import com.corneliadavis.cloudnative.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by corneliadavis on 9/4/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
