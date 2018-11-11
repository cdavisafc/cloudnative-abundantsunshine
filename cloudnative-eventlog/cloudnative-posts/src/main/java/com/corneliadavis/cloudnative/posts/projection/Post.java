package com.corneliadavis.cloudnative.posts.projection;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by corneliadavis on 9/4/17.
 */
@Entity
public class Post {

    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Date date;
    private Long userId;
    private String title;
    private String body;

    protected Post() {}

    public Post(Long id, Date date, Long userId, String title, String body) {
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public void setUserId(Long user) { this.userId = user; }

    public Long getUserId() { return userId; }
}
