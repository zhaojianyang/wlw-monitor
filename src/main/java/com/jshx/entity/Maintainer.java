package com.jshx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by YAO on 2017/6/12.
 */
@Entity
@Table(name = "base_maintainer")
public class Maintainer {

    @Id
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    @GeneratedValue(generator = "uuid-generator")
    @Column(name = "ROW_ID")
    private String id;

    @Column(name = "CREATETIME")
    private Date createTime;

    @Column(name = "UPDATETIME")
    private Date updateTime;

    @Column(name = "CREATEUSERID")
    private String createUserid;

    @Column(name = "UPDATEUSERID")
    private String updateUserid;

    @Column(name = "DEPTID")
    private String deptId;

    @Column(name = "DELFLAG")
    private String delflag;

    @ManyToOne
    @JoinColumn(name = "MAINTAINID")
    private Maintain maintain;

    @Column(name = "MAINTAIN_PEOPLE_NAME")
    private String peopleName;

    @Column(name = "LICENCE")
    private String licence;

    @Column(name = "SEX")
    private String sex;

    @Column(name = "PEOPLE_AGE")
    private String peopleAge;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "IMEI")
    private String imei;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "LASTLOGINTIME")
    private String lastLogintime;

    @Column(name = "TOTALTIME")
    private String totalTime;

    @Column(name = "LASTLOCATION")
    private String lastLocation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(String createUserid) {
        this.createUserid = createUserid;
    }

    public String getUpdateUserid() {
        return updateUserid;
    }

    public void setUpdateUserid(String updateUserid) {
        this.updateUserid = updateUserid;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag;
    }

    public Maintain getMaintain() {
        return maintain;
    }

    public void setMaintain(Maintain maintain) {
        this.maintain = maintain;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPeopleAge() {
        return peopleAge;
    }

    public void setPeopleAge(String peopleAge) {
        this.peopleAge = peopleAge;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLastLogintime() {
        return lastLogintime;
    }

    public void setLastLogintime(String lastLogintime) {
        this.lastLogintime = lastLogintime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(String lastLocation) {
        this.lastLocation = lastLocation;
    }
}
