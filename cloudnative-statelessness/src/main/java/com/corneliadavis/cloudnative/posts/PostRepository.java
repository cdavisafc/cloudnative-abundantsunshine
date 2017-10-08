package com.corneliadavis.cloudnative.posts;

import com.corneliadavis.cloudnative.posts.Post;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by corneliadavis on 9/4/17.
 */
public interface PostRepository extends CrudRepository<Post, Long> {

    Iterable<Post> findByUserId(Long userId);
}
