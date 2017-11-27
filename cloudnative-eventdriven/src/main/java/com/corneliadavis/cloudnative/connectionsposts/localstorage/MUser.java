package com.corneliadavis.cloudnative.connectionsposts.localstorage;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by corneliadavis on 9/9/17.
 */
@Entity
public class MUser {

    @Id
    private Long id;
    private String name;
    private String username;

    @OneToMany(mappedBy ="mUser")
    private Collection<MPost> posts = new ArrayList<MPost>();

    @OneToMany(mappedBy = "followerUser")
    private Collection<MConnection> following = new ArrayList<MConnection>();

    @OneToMany(mappedBy = "followedUser")
    private Collection<MConnection> followed = new ArrayList<MConnection>();

    protected MUser() {}

    public MUser(Long id, String name, String username) {
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
        return "MUser [id=" + id + ", name = " + name + ", username = " + username + "]";
    }
}
