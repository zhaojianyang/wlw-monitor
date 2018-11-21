package com.jshx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by YAO on 2017/6/12.
 */
@Entity
@Table(name = "base_maintain")
public class Maintain implements Serializable {
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

    @Column(name = "MAINTAIN_NAME")
    private String maintainName;

    @Column(name = "ORGCODE")
    private String orgcode;

    @Column(name = "LICENCE")
    private String licence;

    @Column(name = "LICENCEDATE")
    private String licenceDate;

    @Column(name = "REGADDR")
    private String regaddr;

    @Column(name = "OFFICEADDR")
    private String officeaddr;

    @Column(name = "MAINTAINLEVEL")
    private String maintainlevel;

    @Column(name = "CONTACT")
    private String contact;

    /*
     判断是否属于监控的维保单位,0代表否,1代表是
     */
    @Column(name = "MAINT_MONITOR")
    private String maitMonitor;


    public String getMaitMonitor() {
        return maitMonitor;
    }

    public void setMaitMonitor(String maitMonitor) {
        this.maitMonitor = maitMonitor;
    }

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

    public String getMaintainName() {
        return maintainName;
    }

    public void setMaintainName(String maintainName) {
        this.maintainName = maintainName;
    }

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getLicenceDate() {
        return licenceDate;
    }

    public void setLicenceDate(String licenceDate) {
        this.licenceDate = licenceDate;
    }

    public String getRegaddr() {
        return regaddr;
    }

    public void setRegaddr(String regaddr) {
        this.regaddr = regaddr;
    }

    public String getOfficeaddr() {
        return officeaddr;
    }

    public void setOfficeaddr(String officeaddr) {
        this.officeaddr = officeaddr;
    }

    public String getMaintainlevel() {
        return maintainlevel;
    }

    public void setMaintainlevel(String maintainlevel) {
        this.maintainlevel = maintainlevel;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
