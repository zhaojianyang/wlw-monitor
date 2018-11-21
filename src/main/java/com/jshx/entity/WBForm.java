package com.jshx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ZHAO on 2018/11/30.
 */
@Entity
@Table(name = "wb_form")
public class WBForm {

    @Id
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    @GeneratedValue(generator = "uuid-generator")
    @Column(name = "row_id")
    private String id;

    @Column(name = "CREATETIME")
    private Date createTime;

    @Column(name = "UPDATETIME")
    private Date updateTime;

    @Column(name = "CREATEUSERID")
    private String createUserId;

    @Column(name = "UPDATEUSERID")
    private String updateUserId;

    @Column(name = "DEPTID")
    private String deptid;

    @Column(name = "DELFLAG")
    private Integer delflag;

    @Column(name = "FORM_NO")
    private String formNo;

    @ManyToOne
    @JoinColumn(name="MAINTAIN_ID")
    private Maintain maintainId;


    @Column(name = "LIFT_ID")
    private String liftId;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Maintainer clientId;

    @Column(name = "PLAN_DATE")
    private String planDate;

    @Column(name = "MAINT_TYPE")
    private String maintType;

    @Column(name = "MAINT_STARTTIME")
    private String maintStartTime;

    @Column(name = "MAINT_ENDTIME")
    private String maintEndTime;

    @Column(name = "LONGITUDE")
    private String longitude;

    @Column(name = "LATITUDE")
    private String latitude;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "FORM_STATUS")
    private String formsTATUS;

    @Column(name = "IS_OVERDUE")
    private String isOverdue;


    public WBForm(){

    }


    public WBForm(String maintStartTime, String maintEndTime,Maintain maintainId, Maintainer clientId, String maintType) {
        this.maintStartTime = maintStartTime;
        this.maintEndTime = maintEndTime;
        this.maintainId = maintainId;
        this.clientId = clientId;
        this.maintType = maintType;
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

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }

    public Maintain getMaintainId() {
        return maintainId;
    }

    public void setMaintainId(Maintain maintainId) {
        this.maintainId = maintainId;
    }

    public String getLiftId() {
        return liftId;
    }

    public void setLiftId(String liftId) {
        this.liftId = liftId;
    }

    public Maintainer getClientId() {
        return clientId;
    }

    public void setClientId(Maintainer clientId) {
        this.clientId = clientId;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getMaintType() {
        return maintType;
    }

    public void setMaintType(String maintType) {
        this.maintType = maintType;
    }

    public String getMaintStartTime() {
        return maintStartTime;
    }

    public void setMaintStartTime(String maintStartTime) {
        this.maintStartTime = maintStartTime;
    }

    public String getMaintEndTime() {
        return maintEndTime;
    }

    public void setMaintEndTime(String maintEndTime) {
        this.maintEndTime = maintEndTime;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFormsTATUS() {
        return formsTATUS;
    }

    public void setFormsTATUS(String formsTATUS) {
        this.formsTATUS = formsTATUS;
    }

    public String getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(String isOverdue) {
        this.isOverdue = isOverdue;
    }
}
