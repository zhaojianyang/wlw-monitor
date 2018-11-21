package com.jshx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by YAO on 2017/6/13.
 */
@Entity
@Table(name = "department")
public class Department {

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

    @Column(name = "DELFLAG")
    private String delflag;

    @Column(name = "AREA_CODE")
    private String areaCode;

    @Column(name = "DEPT_CODE")
    private String deptCode;

    @Column(name = "DEPT_NAME")
    private String deptName;

    @Column(name = "DEPT_TYPE_CODE")
    private String deptTypeCode;

    @Column(name = "EXPIRATION_DATE")
    private String expirationDate;

    @Column(name = "HAS_CHILD")
    private String hasChild;

    @Column(name = "IS_PUBLIC")
    private String isPublic;

    @Column(name = "LINKED_DEPT_TYPE_CODE")
    private String linkedDeptTypeCode;

    @Column(name = "NEED_APPROVAL")
    private String needApproval;

    @Column(name = "NEED_SUB_FLOW")
    private String needSubFlow;

    @Column(name = "OPEN_DATE")
    private String openDate;

    @Column(name = "SORT_SQ")
    private String sortSq;

    @Column(name = "USER_LIMITED_NUMBER")
    private String userLimitedNumber;

    @Column(name = "PARENT_DEPT_ID")
    private String parentDeptId;

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

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
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

    public String getDeptTypeCode() {
        return deptTypeCode;
    }

    public void setDeptTypeCode(String deptTypeCode) {
        this.deptTypeCode = deptTypeCode;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getHasChild() {
        return hasChild;
    }

    public void setHasChild(String hasChild) {
        this.hasChild = hasChild;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public String getLinkedDeptTypeCode() {
        return linkedDeptTypeCode;
    }

    public void setLinkedDeptTypeCode(String linkedDeptTypeCode) {
        this.linkedDeptTypeCode = linkedDeptTypeCode;
    }

    public String getNeedApproval() {
        return needApproval;
    }

    public void setNeedApproval(String needApproval) {
        this.needApproval = needApproval;
    }

    public String getNeedSubFlow() {
        return needSubFlow;
    }

    public void setNeedSubFlow(String needSubFlow) {
        this.needSubFlow = needSubFlow;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getSortSq() {
        return sortSq;
    }

    public void setSortSq(String sortSq) {
        this.sortSq = sortSq;
    }

    public String getUserLimitedNumber() {
        return userLimitedNumber;
    }

    public void setUserLimitedNumber(String userLimitedNumber) {
        this.userLimitedNumber = userLimitedNumber;
    }

    public String getParentDeptId() {
        return parentDeptId;
    }

    public void setParentDeptId(String parentDeptId) {
        this.parentDeptId = parentDeptId;
    }
}
