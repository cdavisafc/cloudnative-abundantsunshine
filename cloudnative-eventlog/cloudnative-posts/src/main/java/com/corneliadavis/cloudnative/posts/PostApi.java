package com.corneliadavis.cloudnative.posts;

public class PostApi implements IPostApi {

    private String username;
    private String title;
    private String body;

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

}