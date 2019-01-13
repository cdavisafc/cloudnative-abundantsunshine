package com.corneliadavis.cloudnative.posts;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by corneliadavis on 11/15/18.
 */
public interface PostRepository extends CrudRepository<Post, Long> {

    Iterable<Post> findByUserId(Long userId);
}
