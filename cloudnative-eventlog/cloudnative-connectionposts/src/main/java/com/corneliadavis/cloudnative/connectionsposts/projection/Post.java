package com.corneliadavis.cloudnative.connectionsposts.projection;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by corneliadavis on 9/9/18.
 */
@Entity
public class Post {

    @Id
    private Long id;
    private Date date;
    private Long userId;
    private String title;

    protected Post() {}

    public Post(Long id, Date date, Long userId, String title) {
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public Long getUserId() { return userId; }

    public String getTitle() {
        return title;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUserId(Long userId) { this.userId = userId; }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() { return "PostEvent [date=" + date + ", userId = " + userId + ", title = " + title + "]"; }
    //public String toString() { return "PostEvent [date=" + date + ", title = " + title + "]";}

}