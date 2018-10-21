package com.corneliadavis.cloudnative.eventschemas;

/**
 * Created by corneliadavis on 9/4/17.
 */
public class UserEvent {

    private String eventType;

    private Long id;
    private String name;
    private String username;

    public UserEvent(String eventType, Long id, String name, String username) {
        this.eventType = eventType;
        this.id = id;
        this.name = name;
        this.username = username;
    }

    public UserEvent() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
