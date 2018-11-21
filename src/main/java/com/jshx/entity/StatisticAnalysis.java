package com.jshx.entity;

/**
 * Created by ZHAO on 2017/8/03.
 */
public class StatisticAnalysis {




    private String maintName;

    private String deptId;

    private String ownLift;

    private String joinLift;

    private String uploadCount;

    private String coverLift;

    private String createTime;

    private String updateTime;

    private String maintAddress;

    private String liftAddress;

    private String regcode;

    private String liftCode;

    private String placeType;

    private String companyId;

    private String companyName;

    private String companyAddress;

    private String maintId;

    private String areaId;

    private String liftAreaName;

    private String faultType;

    private String content;

    private String memo;

    //运行数据获取时间戳
    private String timeStamps;

    private String startTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
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

    public StatisticAnalysis() {
    }

    public StatisticAnalysis(String maintName, String deptId, String ownLift, String joinLift,
                             String uploadCount, String coverLift, String createTime, String updateTime,
                             String maintAddress, String liftAddress, String regcode, String liftCode,
                             String placeType, String companyId, String companyName, String companyAddress,
                             String maintId, String areaId, String liftAreaName, String faultType,
                             String content, String memo, String timeStamps, String startTime) {
        this.maintName = maintName;
        this.deptId = deptId;
        this.ownLift = ownLift;
        this.joinLift = joinLift;
        this.uploadCount = uploadCount;
        this.coverLift = coverLift;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.maintAddress = maintAddress;
        this.liftAddress = liftAddress;
        this.regcode = regcode;
        this.liftCode = liftCode;
        this.placeType = placeType;
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.maintId = maintId;
        this.areaId = areaId;
        this.liftAreaName = liftAreaName;
        this.faultType = faultType;
        this.content = content;
        this.memo = memo;
        this.timeStamps = timeStamps;
        this.startTime = startTime;
    }

    public String getMaintName() {
        return maintName;
    }

    public void setMaintName(String maintName) {
        this.maintName = maintName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getOwnLift() {
        return ownLift;
    }

    public void setOwnLift(String ownLift) {
        this.ownLift = ownLift;
    }

    public String getJoinLift() {
        return joinLift;
    }

    public void setJoinLift(String joinLift) {
        this.joinLift = joinLift;
    }

    public String getUploadCount() {
        return uploadCount;
    }

    public void setUploadCount(String uploadCount) {
        this.uploadCount = uploadCount;
    }

    public String getCoverLift() {
        return coverLift;
    }

    public void setCoverLift(String coverLift) {
        this.coverLift = coverLift;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getMaintAddress() {
        return maintAddress;
    }

    public void setMaintAddress(String maintAddress) {
        this.maintAddress = maintAddress;
    }

    public String getLiftAddress() {
        return liftAddress;
    }

    public void setLiftAddress(String liftAddress) {
        this.liftAddress = liftAddress;
    }

    public String getRegcode() {
        return regcode;
    }

    public void setRegcode(String regcode) {
        this.regcode = regcode;
    }

    public String getLiftCode() {
        return liftCode;
    }

    public void setLiftCode(String liftCode) {
        this.liftCode = liftCode;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getMaintId() {
        return maintId;
    }

    public void setMaintId(String maintId) {
        this.maintId = maintId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getLiftAreaName() {
        return liftAreaName;
    }

    public void setLiftAreaName(String liftAreaName) {
        this.liftAreaName = liftAreaName;
    }

    public String getTimeStamps() {
        return timeStamps;
    }

    public void setTimeStamps(String timeStamps) {
        this.timeStamps = timeStamps;
    }
}
