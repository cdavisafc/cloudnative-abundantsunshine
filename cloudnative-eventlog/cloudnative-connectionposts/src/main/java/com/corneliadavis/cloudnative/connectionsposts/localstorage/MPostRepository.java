package com.corneliadavis.cloudnative.connectionsposts.localstorage;

import com.corneliadavis.cloudnative.connectionsposts.PostSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by corneliadavis on 9/9/17.
 */
public interface MPostRepository extends CrudRepository<MPost, Long> {

    Iterable<MPost> findBymUserId(Long userId);

    @Query("select p.title as title, p.date as date, p.userId as usersName "
            + "from MPost p")
    Iterable<PostSummary> findAllOfThem();

    @Query("select p.title as title, p.date as date, u.name as usersName "
            + "from MPost p inner join p.mUser u where u.username = :username")
    Iterable<PostSummary> findByUsername(@Param("username") String username);

    @Query("select p.title as title, p.date as date, u1.name as usersName " +
            "from MPost p " +
            "inner join p.mUser u1 " +
            "inner join u1.followed c " +
            "inner join c.followerUser u " +
            "where u.username = :username")
    Iterable<PostSummary> findForUsersConnections(@Param("username") String username);
}
