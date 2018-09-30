package com.corneliadavis.cloudnative.eventschemas.connections;

/**
 * Created by corneliadavis on 9/4/17.
 */
public class User {

    private Long id;
    private String name;
    private String username;

    public User(Long id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }

    public User() {}

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
}
