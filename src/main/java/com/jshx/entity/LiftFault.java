package com.jshx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by YAO on 2017/5/25.
 */
@Entity
@Table(name="wlw_faultrecord")
public class LiftFault {

    @Id
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    @GeneratedValue(generator = "uuid-generator")
    @Column(name="row_id")
    private String id;

    @Column(name="createtime")
    private Date createTime;

    @Column(name="updatetime")
    private Date updateTime;

    @Column(name="deptid")
    private String deptId;

    @Column(name="delflag")
    private String delflag;
    //电梯注册码
    private String regcode;
    //故障编号
    @Column(name="FAULTID")
    private String faultid;
    //故障发生时间
    private String starttime;
//    //故障类型
    @ManyToOne
    @JoinColumn(name = "FAULT_TYPE")
    private FaultCode faultCode;

    //处置状态
    private String dealstatus;
    //处置完成时间
    private String dealtime;
    //处置人
    private String dealperson;
    //现场确认是否困人。0：不困人，1：困人
    private String istrap;
    //是否自恢复
    private String selfrepair;
    //处置说明
    private String dealnote;
    //总接触器吸合或是断开
    private String maincontactor;
    //运行接触器吸合或是断开
    private String runcontactor;
    //安全回路是否断开
    private String circuit;
    //轿厢运行状态
    private String carstatus;
    //方向
    private String direction;
    //轿厢是否在门区
    private String iszone;
    //当前物理楼层
    private String floor;
    //关门到位
    private String doorstatus;
    //上极限是否动作
    private String uplimit;
    //下极限是否动作
    private String downlimit;
    // 轿厢报警按钮是否动作
    private String alarm;

/*

    private String content;


    private String memo;
*/

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag;
    }

    // 累计运行小时数
    private String hours;
    // 累计运行次数
    private String times;

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

/*    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }*/
/*

    //对应故障表中的故障类型
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
*/

    public LiftFault() {
    }

    public LiftFault(Date createTime, Date updateTime, String deptId, String regcode, String faultid,
                     String starttime,  String dealstatus, String dealtime, String dealperson,
                     String istrap, String selfrepair, String dealnote, String maincontactor,
                     String runcontactor, String circuit, String carstatus, String direction,
                     String iszone, String floor, String doorstatus, String uplimit, String downlimit,
                     String alarm,/*String content,String memo,*/FaultCode faultCode,String hours,String times ) {
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.deptId = deptId;
        this.regcode = regcode;
        this.faultid = faultid;
        this.starttime = starttime;
        this.dealstatus = dealstatus;
        this.dealtime = dealtime;
        this.dealperson = dealperson;
        this.istrap = istrap;
        this.selfrepair = selfrepair;
        this.dealnote = dealnote;
        this.maincontactor = maincontactor;
        this.runcontactor = runcontactor;
        this.circuit = circuit;
        this.carstatus = carstatus;
        this.direction = direction;
        this.iszone = iszone;
        this.floor = floor;
        this.doorstatus = doorstatus;
        this.uplimit = uplimit;
        this.downlimit = downlimit;
        this.alarm = alarm;
/*        this.content = content;
        this.memo = memo;*/
        this.faultCode = faultCode;
        this.hours = hours;
        this.times = times;
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

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getRegcode() {
        return regcode;
    }

    public void setRegcode(String regcode) {
        this.regcode = regcode;
    }

    public String getFaultid() {
        return faultid;
    }

    public void setFaultid(String faultid) {
        this.faultid = faultid;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public FaultCode getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(FaultCode faultCode) {
        this.faultCode = faultCode;
    }

    public String getDealstatus() {
        return dealstatus;
    }

    public void setDealstatus(String dealstatus) {
        this.dealstatus = dealstatus;
    }

    public String getDealtime() {
        return dealtime;
    }

    public void setDealtime(String dealtime) {
        this.dealtime = dealtime;
    }

    public String getDealperson() {
        return dealperson;
    }

    public void setDealperson(String dealperson) {
        this.dealperson = dealperson;
    }

    public String getIstrap() {
        return istrap;
    }

    public void setIstrap(String istrap) {
        this.istrap = istrap;
    }

    public String getSelfrepair() {
        return selfrepair;
    }

    public void setSelfrepair(String selfrepair) {
        this.selfrepair = selfrepair;
    }

    public String getDealnote() {
        return dealnote;
    }

    public void setDealnote(String dealnote) {
        this.dealnote = dealnote;
    }

    public String getMaincontactor() {
        return maincontactor;
    }

    public void setMaincontactor(String maincontactor) {
        this.maincontactor = maincontactor;
    }

    public String getRuncontactor() {
        return runcontactor;
    }

    public void setRuncontactor(String runcontactor) {
        this.runcontactor = runcontactor;
    }

    public String getCircuit() {
        return circuit;
    }

    public void setCircuit(String circuit) {
        this.circuit = circuit;
    }

    public String getCarstatus() {
        return carstatus;
    }

    public void setCarstatus(String carstatus) {
        this.carstatus = carstatus;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getIszone() {
        return iszone;
    }

    public void setIszone(String iszone) {
        this.iszone = iszone;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getDoorstatus() {
        return doorstatus;
    }

    public void setDoorstatus(String doorstatus) {
        this.doorstatus = doorstatus;
    }

    public String getUplimit() {
        return uplimit;
    }

    public void setUplimit(String uplimit) {
        this.uplimit = uplimit;
    }

    public String getDownlimit() {
        return downlimit;
    }

    public void setDownlimit(String downlimit) {
        this.downlimit = downlimit;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }
}
