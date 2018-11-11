package com.corneliadavis.cloudnative.connectionsposts.projection;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by corneliadavis on 9/4/18.
 */
public interface ConnectionRepository extends CrudRepository<Connection, Long> {

    Connection findByFollowerAndFollowed(Long follower, Long followed);

}
