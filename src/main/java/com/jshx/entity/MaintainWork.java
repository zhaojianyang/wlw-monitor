package com.jshx.entity;

/**
 * Created by YAO on 2017/5/25.
 */
public class MaintainWork {
    //保养内容编码
    private String code;
    //保养结果
    private String result;
    //操作说明
    private String operate;
    //是否有部件更换
    private String replacement;
    //部件名称，如果有多个部件，通过英文分号间隔
    private String parts;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

    public String getParts() {
        return parts;
    }

    public void setParts(String parts) {
        this.parts = parts;
    }
}
