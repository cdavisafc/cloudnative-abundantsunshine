package com.corneliadavis.cloudnative.connectionsposts.projection;

import com.corneliadavis.cloudnative.connectionsposts.PostSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by corneliadavis on 9/9/18.
 */
public interface PostRepository extends CrudRepository<Post, Long> {

    Iterable<Post> findByUserId(Long userId);

    @Query("select p.title as title, p.date as date, u.name as usersName "
            + "from Post p, User u WHERE p.userId = u.id")
    Iterable<PostSummary> findAllOfThem();

    @Query("select p.title as title, p.date as date, u.name as usersName "
            + "from Post p, User u where u.username = :username AND u.id = p.userId")
    Iterable<PostSummary> findByUsername(@Param("username") String username);

    @Query("select p.title as title, p.date as date, u.name as usersName " +
            "from Post p, User u1, Connection c, User u " +
            "where u1.username = :username " +
            "AND u1.id = c.follower AND c.followed = p.userId AND p.userId = u.id")
    Iterable<PostSummary> findForUsersConnections(@Param("username") String username);

}
