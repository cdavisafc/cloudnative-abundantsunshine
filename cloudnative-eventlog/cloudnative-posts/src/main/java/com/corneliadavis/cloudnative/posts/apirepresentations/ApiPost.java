package com.corneliadavis.cloudnative.posts.apirepresentations;

import java.util.Date;

public class ApiPost implements IApiPost {

    private String username;
    private String title;
    private String body;
    private Date date;

    public ApiPost(String username, String title, String body) {
        this.username = username;
        this.title = title;
        this.body = body;
    }

    public ApiPost() { }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public Date getDate() {
        return date;
    }


}
