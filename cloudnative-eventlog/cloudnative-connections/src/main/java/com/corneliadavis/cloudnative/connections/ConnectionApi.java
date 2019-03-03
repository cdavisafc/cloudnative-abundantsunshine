package com.corneliadavis.cloudnative.connections;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by corneliadavis on 10/1/18.
 */
public class ConnectionApi {

    private String follower;
    private String followed;

    protected ConnectionApi() {}

    public ConnectionApi(String follower, String followed) {
        this.follower = follower;
        this.followed = followed;
    }

    public String getFollower() {
        return follower;
    }

    public String getFollowed() {
        return followed;
    }
}
