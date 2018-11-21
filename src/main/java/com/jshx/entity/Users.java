package com.jshx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Zhao on 2017/7/17.
 */
@Entity
@Table(name="users")
public class Users implements Serializable {
    @Id
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    @GeneratedValue(generator = "uuid-generator")
    @Column(name = "ROW_ID")
    private String id;

    @Column(name = "CREATETIME")
    private Date createTime;

    @Column(name = "CREATEUSERID")
    private String createUserid;

    @Column(name = "datetime")
    private Date dateTime;

    @Column(name = "UPDATEUSERID")
    private String updateUserid;

    @Column(name = "CSS_ID")
    private String cssId;

    @Column(name = "DEPT_CODE")
    private String deptCode;

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @Column(name = "DISPLAY_NUM")
    private BigDecimal displayNum;

    @Column(name = "DUTY")
    private String duty;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "LOGIN_ID")
    private String loginId;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "SORT_SQ")
    private BigDecimal sortSq;

    @Column(name = "TEL")
    private String tel;

    @Column(name = "DEPT_ID")
    private String deptId;


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

    public String getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(String createUserid) {
        this.createUserid = createUserid;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getUpdateUserid() {
        return updateUserid;
    }

    public void setUpdateUserid(String updateUserid) {
        this.updateUserid = updateUserid;
    }

    public String getCssId() {
        return cssId;
    }

    public void setCssId(String cssId) {
        this.cssId = cssId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public BigDecimal getDisplayNum() {
        return displayNum;
    }

    public void setDisplayNum(BigDecimal displayNum) {
        this.displayNum = displayNum;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getSortSq() {
        return sortSq;
    }

    public void setSortSq(BigDecimal sortSq) {
        this.sortSq = sortSq;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
}
