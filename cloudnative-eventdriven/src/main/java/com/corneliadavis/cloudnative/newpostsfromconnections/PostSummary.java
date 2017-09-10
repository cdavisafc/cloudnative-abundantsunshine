package com.corneliadavis.cloudnative.newpostsfromconnections;

import java.util.Date;

/**
 * Created by corneliadavis on 9/9/17.
 */

public interface PostSummary {
    Date getDate();
    String getUsersName();
    String getTitle();
}
