package com.corneliadavis.cloudnative.posts.sourceoftruth;

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
