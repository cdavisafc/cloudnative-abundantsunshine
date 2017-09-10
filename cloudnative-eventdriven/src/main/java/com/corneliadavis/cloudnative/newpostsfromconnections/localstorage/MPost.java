package com.corneliadavis.cloudnative.newpostsfromconnections.localstorage;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by corneliadavis on 9/9/17.
 */
@Entity
public class MPost {

    @Id
    private Long id;
    private Date date;
    private Long userId;
    private String title;

    @ManyToOne
    private MUser mUser;

    public Date getDate() {
        return date;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setmUser(MUser mUser) {
        this.mUser = mUser;
    }

    @Override
    public String toString() {
        return "Post [date=" + date + ", userId = " + userId + ", title = " + title + "]";
    }

}