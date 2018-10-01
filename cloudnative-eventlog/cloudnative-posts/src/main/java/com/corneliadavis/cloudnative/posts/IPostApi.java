package com.corneliadavis.cloudnative.posts;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by corneliadavis on 10/1/2018.
 */
public interface IPostApi {

    String getUsername();
    String getTitle();
    String getBody();
    Date getDate();
}
