package com.corneliadavis.cloudnative.connectionsposts.localstorage;

import javax.persistence.*;

/**
 * Created by corneliadavis on 9/4/17.
 */
@Entity
public class Connection {

    @Id
    private Long id;
    private Long follower;
    private Long followed;

    @ManyToOne
    private User followerUser;

    @ManyToOne
    private User followedUser;

    protected Connection() {}

    public Connection(Long id, Long follower, Long followed) {
        this.id = id;
        this.follower = follower;
        this.followed = followed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFollower() {
        return follower;
    }

   public Long getFollowed() {
        return followed;
    }

    public void setFollowerUser(User followerUser) {
        this.followerUser = followerUser;
    }

    public void setFollowedUser(User followedUser) {
        this.followedUser = followedUser;
    }
}
