package com.area.EnvironMange.model;

/**
 * Created by liuzwei on 2014/12/15.
 *
 * 卫生区域
 */

public class SanitationArea {
    private String ID;//卫生区域ID
    private String mc;//卫生区域名称
    private String code;//卫生区域编号
    private String pbid;//排班ID

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPbid() {
        return pbid;
    }

    public void setPbid(String pbid) {
        this.pbid = pbid;
    }
}
