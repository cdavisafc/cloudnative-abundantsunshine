package com.corneliadavis.cloudnative.connectionsposts;

import java.util.Date;

/**
 * Created by corneliadavis on 9/4/17.
 */
public class PostSummary {

    private Date date;
    private String usersname;
    private String title;

    public PostSummary(String usersname, String title, Date date) {
        this.date = date;
        this.usersname = usersname;
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public String getUsersname() {
        return usersname;
    }

    public String getTitle() {
        return title;
    }

}
