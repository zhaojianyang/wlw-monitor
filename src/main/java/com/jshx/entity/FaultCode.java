package com.jshx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/6.
 */


@Entity
@Table(name="wlw_faultcode")
public class FaultCode implements Serializable {
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


    @Column(name = "CODE")
    private String code;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "MEMO")
    private String memo;


    public FaultCode() {
    }

    public FaultCode(Date createTime, Date updateTime, String createUserid, String updateUserid, String deptId, String delflag, String code, String content, String memo) {
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.createUserid = createUserid;
        this.updateUserid = updateUserid;
        this.deptId = deptId;
        this.delflag = delflag;
        this.code = code;
        this.content = content;
        this.memo = memo;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
