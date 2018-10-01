package com.corneliadavis.cloudnative.connections.apirepresentations;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by corneliadavis on 10/1/18.
 */
public class ApiConnection {

    private String follower;
    private String followed;

    protected ApiConnection() {}

    public ApiConnection(String follower, String followed) {
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
