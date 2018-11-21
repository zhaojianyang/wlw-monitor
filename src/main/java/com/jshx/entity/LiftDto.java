package com.jshx.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/30.
 */
public class LiftDto implements Serializable {

    private String regcode;
    private String liftcode;
    private String liftaddress;
    private String latitude;
    private String longitude;
    private String maintainName;
    private String lastInspecttime;
    private String lastUptime;

    public LiftDto() {
    }

    public LiftDto(String regcode, String liftcode, String liftaddress, String latitude, String longitude, String maintainName) {
        this.regcode = regcode;
        this.liftcode = liftcode;
        this.liftaddress = liftaddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.maintainName = maintainName;
    }

    public LiftDto(String regcode, String liftcode, String liftaddress, String latitude, String longitude, String maintainName, String lastInspecttime, String lastUptime) {
        this.regcode = regcode;
        this.liftcode = liftcode;
        this.liftaddress = liftaddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.maintainName = maintainName;
        this.lastInspecttime = lastInspecttime;
        this.lastUptime = lastUptime;
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

    public String getLiftaddress() {
        return liftaddress;
    }

    public void setLiftaddress(String liftaddress) {
        this.liftaddress = liftaddress;
    }

    public String getMaintainName() {
        return maintainName;
    }

    public void setMaintainName(String maintainName) {
        this.maintainName = maintainName;
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

    public String getLastInspecttime() {
        return lastInspecttime;
    }

    public void setLastInspecttime(String lastInspecttime) {
        this.lastInspecttime = lastInspecttime;
    }

    public String getLastUptime() {
        return lastUptime;
    }

    public void setLastUptime(String lastUptime) {
        this.lastUptime = lastUptime;
    }
}
