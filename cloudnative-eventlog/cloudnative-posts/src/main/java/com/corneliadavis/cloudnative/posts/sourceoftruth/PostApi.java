package com.corneliadavis.cloudnative.posts.sourceoftruth;

import java.util.Date;

public class PostApi implements IPostApi {

    private String username;
    private String title;
    private String body;
    private Date date;

    public PostApi(String username, String title, String body) {
        this.username = username;
        this.title = title;
        this.body = body;
    }

    public PostApi() { }

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
