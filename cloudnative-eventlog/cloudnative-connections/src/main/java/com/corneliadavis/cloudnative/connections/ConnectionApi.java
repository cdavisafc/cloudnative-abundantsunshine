package com.corneliadavis.cloudnative.connections;

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
