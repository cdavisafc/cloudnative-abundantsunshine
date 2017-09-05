package com.corneliadavis.cloudnative.repositories;

import com.corneliadavis.cloudnative.domain.Connection;
import com.corneliadavis.cloudnative.domain.Post;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by corneliadavis on 9/4/17.
 */
public interface PostRepository extends CrudRepository<Post, Long> {

    Iterable<Post> findByUserId(Long userId);
}
