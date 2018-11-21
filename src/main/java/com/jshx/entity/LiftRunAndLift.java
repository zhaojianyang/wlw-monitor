package com.jshx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Zhao on 2017/9/20.
 */

public class LiftRunAndLift implements Serializable {


    private Date createTime;

    private Date updateTime;

    private String deptId;

    //电梯注册码
    private String regcode;
    //是否在线。1：是，0：否
    private String isonline;
    //运行数据获取时间戳
    private String timestamps;
    //总接触器吸合或是断开
    private String maincontactor;
    //运行接触器吸合或是断开
    private String runcontactor;
    //安全回路是否断开
    private String circuit;
    //当前服务模式。0：停止服务1：正常运行2：检修3：消防返回4、制动力自监测5：应急电源运行
    private String mode;
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
    //轿厢是否有人
    private String passenger;
    //运行小时数，保留到小数点后两位
    private String hours;
    //运行次数
    private String times;
    //base_lift表中的电梯识别码
    private String liftCode;
    private Lift lift;

    //base_lift表中的维保单位ID
    private String maintId;


    //base_maintain表中的维保单位maintain_name
    private String maintName;


    public LiftRunAndLift(){

    }
    public LiftRunAndLift(String regcode,String liftcode){
        this.regcode = regcode;
        this.isonline = liftcode;

    }

    public LiftRunAndLift(String deptId, String regcode, String isonline, String timestamps, String maincontactor,
                          String runcontactor, String circuit, String mode, String carstatus,
                          String direction, String iszone, String floor, String doorstatus,
                          String uplimit, String downlimit, String alarm, String passenger,
                          String hours, String times, String liftCode,Date updateTime/*,Lift lift*/) {
        this.deptId = deptId;
        this.regcode = regcode;
        this.isonline = isonline;
        this.timestamps = timestamps;
        this.maincontactor = maincontactor;
        this.runcontactor = runcontactor;
        this.circuit = circuit;
        this.mode = mode;
        this.carstatus = carstatus;
        this.direction = direction;
        this.iszone = iszone;
        this.floor = floor;
        this.doorstatus = doorstatus;
        this.uplimit = uplimit;
        this.downlimit = downlimit;
        this.alarm = alarm;
        this.passenger = passenger;
        this.hours = hours;
        this.times = times;
        this.liftCode = liftCode;
        this.updateTime = updateTime;
        /*this.lift = lift;*/
    }

    public String getMaintId() {
        return maintId;
    }

    public void setMaintId(String maintId) {
        this.maintId = maintId;
    }

    public String getMaintName() {
        return maintName;
    }

    public void setMaintName(String maintName) {
        this.maintName = maintName;
    }

    public String getLiftCode() {
        return liftCode;
    }

    public void setLiftCode(String liftCode) {
        this.liftCode = liftCode;
    }

    public Lift getLift() {
        return lift;
    }

    public void setLift(Lift lift) {
        this.lift = lift;
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

    public String getIsonline() {
        return isonline;
    }

    public void setIsonline(String isonline) {
        this.isonline = isonline;
    }

    public String getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(String timestamps) {
        this.timestamps = timestamps;
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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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

    public String getPassenger() {
        return passenger;
    }

    public void setPassenger(String passenger) {
        this.passenger = passenger;
    }

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
}
