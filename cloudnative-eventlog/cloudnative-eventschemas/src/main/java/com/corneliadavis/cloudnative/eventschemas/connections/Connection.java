package com.corneliadavis.cloudnative.eventschemas.connections;

/**
 * Created by corneliadavis on 9/4/17.
 */
public class Connection {

    private Long id;
    private Long follower;
    private Long followed;

    public Connection(Long id, Long follower, Long followed) {
        this.id = id;
        this.follower = follower;
        this.followed = followed;
    }

    public Connection() {}

    public Long getId() { return id; }

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
