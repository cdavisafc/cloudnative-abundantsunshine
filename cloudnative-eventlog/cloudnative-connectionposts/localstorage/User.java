package com.corneliadavis.cloudnative.connectionsposts.localstorage;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by corneliadavis on 9/9/17.
 */
@Entity
public class User {

    @Id
    private Long id;
    private String name;
    private String username;

    @OneToMany(mappedBy ="user")
    private Collection<Post> posts = new ArrayList<Post>();

    @OneToMany(mappedBy = "followerUser")
    private Collection<Connection> following = new ArrayList<Connection>();

    @OneToMany(mappedBy = "followedUser")
    private Collection<Connection> followed = new ArrayList<Connection>();

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
        return "UserEvent [id=" + id + ", name = " + name + ", username = " + username + "]";
    }
}
