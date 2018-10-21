package com.corneliadavis.cloudnative.eventschemas;

import java.util.Date;

/**
 * Created by corneliadavis on 9/28/18.
 */
public class PostEvent {

    private String eventType;

    private Long id;
    private Date date;
    private Long userId;
    private String title;
    private String body;

    public PostEvent(String eventType, Long id, Date date, Long userId, String title, String body) {
        this.eventType = eventType;
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    protected PostEvent() {}

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
