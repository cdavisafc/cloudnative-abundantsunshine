package com.corneliadavis.cloudnative.posts.projection;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by corneliadavis on 10/1/2018.
 */
@Entity
public class User {

    @Id
    private Long id;
    private String username;

    protected User() {}

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "MUser [id=" + id + ", username = " + username + "]";
    }
}
