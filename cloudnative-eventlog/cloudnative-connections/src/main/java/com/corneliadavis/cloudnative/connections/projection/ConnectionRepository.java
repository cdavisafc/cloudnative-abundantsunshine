package com.corneliadavis.cloudnative.connections.projection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by corneliadavis on 9/4/17.
 */
public interface ConnectionRepository extends CrudRepository<Connection, Long> {

    Iterable<Connection> findByFollower(Long follower);

    Connection findByFollowerAndFollowed(Long follower, Long followed);

    @Query("select max(c.id) from Connection c")
    List<Long> getMaxId();

}
