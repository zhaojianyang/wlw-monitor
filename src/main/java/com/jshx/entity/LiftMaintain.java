package com.jshx.entity;

import java.util.List;

/**
 * Created by YAO on 2017/5/25.
 */
public class LiftMaintain {

    //电梯注册码
    private String regcode;
    //维保编号
    private String maintainid;
    //开始维保时间
    private String starttime;
    //结束维保时间
    private String endtime;
    //下次维保日期
    private String nextdate;
    //维保人员
    private String person;
    //维保人员手机
    private String mobile;
    //保养类型
    private String maintaintype;
    //维保任务数组
    private List<MaintainWork> maintainwork;
    //维保备注
    private String note;

    public String getRegcode() {
        return regcode;
    }

    public void setRegcode(String regcode) {
        this.regcode = regcode;
    }

    public String getMaintainid() {
        return maintainid;
    }

    public void setMaintainid(String maintainid) {
        this.maintainid = maintainid;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getNextdate() {
        return nextdate;
    }

    public void setNextdate(String nextdate) {
        this.nextdate = nextdate;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMaintaintype() {
        return maintaintype;
    }

    public void setMaintaintype(String maintaintype) {
        this.maintaintype = maintaintype;
    }

    public List<MaintainWork> getMaintainwork() {
        return maintainwork;
    }

    public void setMaintainwork(List<MaintainWork> maintainwork) {
        this.maintainwork = maintainwork;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
