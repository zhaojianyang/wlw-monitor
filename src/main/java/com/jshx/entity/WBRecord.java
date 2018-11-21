package com.jshx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by YAO on 2017/6/12.
 */
@Entity
@Table(name = "wb_record")
public class WBRecord {

    @Id
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    @GeneratedValue(generator = "uuid-generator")
    @Column(name = "row_id")
    private String id;

    @Column(name = "CREATETIME")
    private Date createTime;

    @Column(name = "UPDATETIME")
    private Date updateTime;

    @Column(name = "DEPTID")
    private String deptId;
    //电梯注册码
    @Column(name = "REGCODE")
    private String regcode;
    //维保工单来源
    @Column(name = "SOURCE")
    private String source;
    //维保编号
    @Column(name = "FORMID")
    private String formId;
    //开始维保时间
    private String starttime;
    //结束维保时间
    @Column(name = "STOPTIME")
    private String stoptime;
    //下次维保日期
    @Column(name = "NEXTDATE")
    private String nextdate;
    //维保人员
    @ManyToOne
    @JoinColumn(name="MAINTAINERID")
    private Maintainer maintainer;
    //保养类型
    @Column(name = "MAINTAINTYPE")
    private String maintaintype;
    //维保任务数组
    @Column(name = "MAINTAINRECORDS")
    private String maintainRecords;
    //维保备注
    @Column(name = "MEMO")
    private String memo;

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getStoptime() {
        return stoptime;
    }

    public void setStoptime(String stoptime) {
        this.stoptime = stoptime;
    }

    public String getNextdate() {
        return nextdate;
    }

    public void setNextdate(String nextdate) {
        this.nextdate = nextdate;
    }


    public String getMaintaintype() {
        return maintaintype;
    }

    public void setMaintaintype(String maintaintype) {
        this.maintaintype = maintaintype;
    }

    public String getMaintainRecords() {
        return maintainRecords;
    }

    public void setMaintainRecords(String maintainRecords) {
        this.maintainRecords = maintainRecords;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Maintainer getMaintainer() {
        return maintainer;
    }

    public void setMaintainer(Maintainer maintainer) {
        this.maintainer = maintainer;
    }
}
