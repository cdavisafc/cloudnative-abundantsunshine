package com.corneliadavis.cloudnative.connectionsposts;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("PostSummaries")
public class PostResults implements Serializable {
    @Id
    private String ids;
    private String summariesJson;

    public PostResults (String ids, String summariesJson) {

        this.ids = ids;
        this.summariesJson = summariesJson;
    }

    public String getSummariesJson() {
        return summariesJson;
    }

    public String getIds() {
        return ids;
    }


}

