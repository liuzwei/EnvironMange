package com.area.EnvironMange.model;

/**
 * Created by liuzwei on 2014/12/16.
 */
public class SanitationAreaAssessment implements Comparable{
    private String ID;//主键
    private String jcsj;//检查时间
    private String fs;//合计分数
    private String areamc;//检查区域名称
    private String areaid;//检查区域ID
    private String bz;//备注
    private String SanitaionreaCleanID;//排班id

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getJcsj() {
        return jcsj;
    }

    public void setJcsj(String jcsj) {
        this.jcsj = jcsj;
    }

    public String getFs() {
        return fs;
    }

    public void setFs(String fs) {
        this.fs = fs;
    }

    public String getAreamc() {
        return areamc;
    }

    public void setAreamc(String areamc) {
        this.areamc = areamc;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @Override
    public int compareTo(Object another) {
        SanitationAreaAssessment assessment = (SanitationAreaAssessment) another;

        return assessment.getJcsj().compareTo(this.getJcsj());
    }

    public String getSanitaionreaCleanID() {
        return SanitaionreaCleanID;
    }

    public void setSanitaionreaCleanID(String sanitaionreaCleanID) {
        SanitaionreaCleanID = sanitaionreaCleanID;
    }
}
