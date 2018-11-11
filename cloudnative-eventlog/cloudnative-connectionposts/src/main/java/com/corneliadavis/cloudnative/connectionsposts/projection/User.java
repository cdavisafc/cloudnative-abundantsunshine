package com.corneliadavis.cloudnative.connectionsposts.projection;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by corneliadavis on 9/9/18.
 */
@Entity
public class User {

    @Id
    private Long id;
    private String name;
    private String username;

    protected User() {}

    public User(Long id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name = " + name + ", username = " + username + "]";
    }
}
