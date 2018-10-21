package com.corneliadavis.cloudnative.eventschemas;

/**
 * Created by corneliadavis on 9/4/17.
 */
public class ConnectionEvent {

    private String eventType;

    private Long id;
    private Long follower;
    private Long followed;

    public ConnectionEvent(String eventType, Long id, Long follower, Long followed) {
        this.eventType = eventType;
        this.id = id;
        this.follower = follower;
        this.followed = followed;
    }

    public ConnectionEvent() {}

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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
