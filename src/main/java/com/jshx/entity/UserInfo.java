package com.jshx.entity;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * Created by ZHAO on 2017/8/25.
 */
public class UserInfo implements Serializable {


    private String deptId;

    private String deptCode;

    private String deptName;

    private String displayName;

    private String parentDeptId;

    public UserInfo(){

    }

    public UserInfo(String deptId, String deptCode, String deptName, String displayName, String parentDeptId) {
        this.deptId = deptId;
        this.deptCode = deptCode;
        this.deptName = deptName;
        this.displayName = displayName;
        this.parentDeptId = parentDeptId;
    }

    public String getParentDeptId() {
        return parentDeptId;
    }

    public void setParentDeptId(String parentDeptId) {
        this.parentDeptId = parentDeptId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
