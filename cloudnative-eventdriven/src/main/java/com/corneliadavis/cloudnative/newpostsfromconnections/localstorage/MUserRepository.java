package com.corneliadavis.cloudnative.newpostsfromconnections.localstorage;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by corneliadavis on 9/4/17.
 */
public interface MUserRepository extends CrudRepository<MUser, Long> {

    MUser findByUsername(String username);
}
