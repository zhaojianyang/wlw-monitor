package com.jshx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by YAO on 2017/6/13.
 */
@Entity
@Table(name="base_lift")
public class LiftNew implements Serializable {
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

    @Column(name = "REGCODE")
    private String regcode;

    @Column(name = "LIFTCODE")
    private String liftcode;

    @Column(name = "LIFTID")
    private String liftid;

//    @Column(name = "MAINTAINID")
//    private String maintainid;

    @Column(name = "COMPANYID")
    private String companyid;

    @Column(name = "INSUREID")
    private String insureid;

    @Column(name = "BRANDID")
    private String brandid;

    @Column(name = "AREAID")
    private String areaid;

    @Column(name = "MAITAINTYPE")
    private String maitaintype;

    @Column(name = "LIFTADDRESS")
    private String liftaddress;

    @Column(name = "PLACETYPE")
    private String placetype;

    @Column(name = "FLOORNUM")
    private String floornum;

    @Column(name = "STATIONNUM")
    private String stationnum;

    @Column(name = "DOORNUM")
    private String doornum;

    @Column(name = "RATESPEED")
    private String ratespeed;

    @Column(name = "LIFT_LOAD")
    private String lift_load;

    @Column(name = "DRAGMODEL")
    private String dragmodel;

    @Column(name = "USESTATE")
    private String usestate;

    @Column(name = "INSPECT")
    private String inspect;

    @Column(name = "BULIDDATE")
    private String buliddate;

    @Column(name = "LASTINSPECT")
    private String lastinspect;

    @Column(name = "NEXTINSPECT")
    private String nextinspect;

    @Column(name = "LASTMAINTAIN")
    private String lastmaintain;

    @Column(name = "NEXTMAINTAIN")
    private String nextmaintain;

    @Column(name = "REMAKEDATE")
    private String remakedate;

    @Column(name = "MAITAINERID")
    private String maitainerid;

    @Column(name = "COMPANYERID")
    private String companyerid;

    @Column(name = "INSURERID")
    private String insurerid;

    @Column(name = "CONCERNNUM")
    private String concernnum;


    @Column(name = "LATITUDE")
    private String latitude;

    @Column(name = "LONGITUDE")
    private String longitude;

    @ManyToOne
    @JoinColumn(name = "MAINTAINID")
    private Maintain maintain;

    public LiftNew(){

    }



    public Maintain getMaintain() {
        return maintain;
    }

    public void setMaintain(Maintain maintain) {
        this.maintain = maintain;
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

    public String getRegcode() {
        return regcode;
    }

    public void setRegcode(String regcode) {
        this.regcode = regcode;
    }

    public String getLiftcode() {
        return liftcode;
    }

    public void setLiftcode(String liftcode) {
        this.liftcode = liftcode;
    }

    public String getLiftid() {
        return liftid;
    }

    public void setLiftid(String liftid) {
        this.liftid = liftid;
    }

//    public String getMaintainid() {
//        return maintainid;
//    }
//
//    public void setMaintainid(String maintainid) {
//        this.maintainid = maintainid;
//    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getInsureid() {
        return insureid;
    }

    public void setInsureid(String insureid) {
        this.insureid = insureid;
    }

    public String getBrandid() {
        return brandid;
    }

    public void setBrandid(String brandid) {
        this.brandid = brandid;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getMaitaintype() {
        return maitaintype;
    }

    public void setMaitaintype(String maitaintype) {
        this.maitaintype = maitaintype;
    }

    public String getLiftaddress() {
        return liftaddress;
    }

    public void setLiftaddress(String liftaddress) {
        this.liftaddress = liftaddress;
    }

    public String getPlacetype() {
        return placetype;
    }

    public void setPlacetype(String placetype) {
        this.placetype = placetype;
    }

    public String getFloornum() {
        return floornum;
    }

    public void setFloornum(String floornum) {
        this.floornum = floornum;
    }

    public String getStationnum() {
        return stationnum;
    }

    public void setStationnum(String stationnum) {
        this.stationnum = stationnum;
    }

    public String getDoornum() {
        return doornum;
    }

    public void setDoornum(String doornum) {
        this.doornum = doornum;
    }

    public String getRatespeed() {
        return ratespeed;
    }

    public void setRatespeed(String ratespeed) {
        this.ratespeed = ratespeed;
    }

    public String getLift_load() {
        return lift_load;
    }

    public void setLift_load(String lift_load) {
        this.lift_load = lift_load;
    }

    public String getDragmodel() {
        return dragmodel;
    }

    public void setDragmodel(String dragmodel) {
        this.dragmodel = dragmodel;
    }

    public String getUsestate() {
        return usestate;
    }

    public void setUsestate(String usestate) {
        this.usestate = usestate;
    }

    public String getInspect() {
        return inspect;
    }

    public void setInspect(String inspect) {
        this.inspect = inspect;
    }

    public String getBuliddate() {
        return buliddate;
    }

    public void setBuliddate(String buliddate) {
        this.buliddate = buliddate;
    }

    public String getLastinspect() {
        return lastinspect;
    }

    public void setLastinspect(String lastinspect) {
        this.lastinspect = lastinspect;
    }

    public String getNextinspect() {
        return nextinspect;
    }

    public void setNextinspect(String nextinspect) {
        this.nextinspect = nextinspect;
    }

    public String getLastmaintain() {
        return lastmaintain;
    }

    public void setLastmaintain(String lastmaintain) {
        this.lastmaintain = lastmaintain;
    }

    public String getNextmaintain() {
        return nextmaintain;
    }

    public void setNextmaintain(String nextmaintain) {
        this.nextmaintain = nextmaintain;
    }

    public String getRemakedate() {
        return remakedate;
    }

    public void setRemakedate(String remakedate) {
        this.remakedate = remakedate;
    }

    public String getMaitainerid() {
        return maitainerid;
    }

    public void setMaitainerid(String maitainerid) {
        this.maitainerid = maitainerid;
    }

    public String getCompanyerid() {
        return companyerid;
    }

    public void setCompanyerid(String companyerid) {
        this.companyerid = companyerid;
    }

    public String getInsurerid() {
        return insurerid;
    }

    public void setInsurerid(String insurerid) {
        this.insurerid = insurerid;
    }

    public String getConcernnum() {
        return concernnum;
    }

    public void setConcernnum(String concernnum) {
        this.concernnum = concernnum;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
