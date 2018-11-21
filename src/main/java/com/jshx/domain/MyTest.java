package com.jshx.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;

/**
 * Created by YAO on 2017/6/2.
 */
@ConfigurationProperties(prefix = "test")
@Repository
public class MyTest {

    private String myName;

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }
}
