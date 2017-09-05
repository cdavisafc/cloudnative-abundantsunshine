package com.corneliadavis.cloudnative.newpostsfromconnections;

import java.util.Date;

/**
 * Created by corneliadavis on 9/4/17.
 */
public class PostSummary {

    private Date date;
    private String username;
    private String title;

    public PostSummary(String username, String title, Date date) {
        this.date = date;
        this.username = username;
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }

    public String getTitle() {
        return title;
    }

}
