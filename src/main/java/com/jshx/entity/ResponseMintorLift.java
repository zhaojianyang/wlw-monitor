package com.jshx.entity;

import java.util.List;

/**
 * Created by YAO on 2017/5/25.
 */
public class ResponseMintorLift {




    private String errcode;

    private String errmsg;

    private List list;


    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
