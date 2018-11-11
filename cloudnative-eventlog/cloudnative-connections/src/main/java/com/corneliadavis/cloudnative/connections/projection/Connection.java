package com.corneliadavis.cloudnative.connections.projection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by corneliadavis on 9/4/17.
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

    public Long getFollower() {
        return follower;
    }

   public Long getFollowed() {
        return followed;
    }
}
