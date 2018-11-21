package com.jshx.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/30.
 */
public class LiftRunTime implements Serializable {

    //运行数据获取时间戳
    private String timestamps;

    private String reallytime;


    public String getReallytime() {
        return reallytime;
    }

    public void setReallytime(String reallytime) {
        this.reallytime = reallytime;
    }

    public LiftRunTime(String timestamps, String reallytime) {
        this.timestamps = timestamps;
        this.timestamps = reallytime;
    }

    public String getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(String timestamps) {
        this.timestamps = timestamps;
    }

    public LiftRunTime() {
    }
}
