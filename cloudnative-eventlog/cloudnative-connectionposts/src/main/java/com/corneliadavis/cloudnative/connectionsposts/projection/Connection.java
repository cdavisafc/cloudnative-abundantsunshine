package com.corneliadavis.cloudnative.connectionsposts.projection;

import javax.persistence.*;

/**
 * Created by corneliadavis on 9/4/18.
 */
@Entity
public class Connection {

    @Id
    private Long id;
    private Long follower;
    private Long followed;

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

    public void setFollower(Long follower) {
        this.follower = follower;
    }

    public Long getFollowed() {
        return followed;
    }

    public void setFollowed(Long followed) {
        this.followed = followed;
    }


}
