package com.corneliadavis.cloudnative.connectionsposts;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsResultsRepository extends CrudRepository<PostResults, String> {
}