package com.corneliadavis.cloudnative.posts;

import com.corneliadavis.cloudnative.posts.localstorage.User;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by corneliadavis on 9/4/17.
 */
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Date date;
    //private Long userId;
    private String title;
    private String body;

    @ManyToOne
    private User user;

    protected Post() {}

    public Post(String title, String body) {
        this.date = new Date();
        this.title = title;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    //public void setDate(Date date) { this.date = date; }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public void setUser(User user) { this.user = user; }

    public User getUser() { return user; }
}
