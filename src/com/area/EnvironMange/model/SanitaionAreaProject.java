package com.area.EnvironMange.model;

/**
 * Created by liuzwei on 2014/12/15.
 */
public class SanitaionAreaProject {
    private String ID;//检查项目ID
    private String Code;//检查项目编号
    private String Xmmc;//检查名称
    private String Zdfs;//最大分数

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getXmmc() {
        return Xmmc;
    }

    public void setXmmc(String xmmc) {
        Xmmc = xmmc;
    }

    public String getZdfs() {
        return Zdfs;
    }

    public void setZdfs(String zdfs) {
        Zdfs = zdfs;
    }
}
