package com.corneliadavis.cloudnative.connections.projection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by corneliadavis on 9/4/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    User findById(Long id);

    @Query("select max(u.id) from User u")
    List<Long> getMaxId();

}
