package com.corneliadavis.cloudnative.connectionsposts.localstorage;

import javax.persistence.*;

/**
 * Created by corneliadavis on 9/4/17.
 */
@Entity
public class MConnection {

    @Id
    private Long id;
    private Long follower;
    private Long followed;

    @ManyToOne
    private MUser followerUser;

    @ManyToOne
    private MUser followedUser;

    protected  MConnection() {}

    public MConnection(Long id, Long follower, Long followed) {
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

    public void setFollowerUser(MUser followerUser) {
        this.followerUser = followerUser;
    }

    public void setFollowedUser(MUser followedUser) {
        this.followedUser = followedUser;
    }
}
