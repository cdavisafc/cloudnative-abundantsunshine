package com.corneliadavis.cloudnative.posts.apirepresentations;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by corneliadavis on 10/1/2018.
 */
public interface IApiPost {

    String getUsername();
    String getTitle();
    String getBody();
    Date getDate();
}
