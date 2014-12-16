package com.area.EnvironMange.model;

/**
 * Created by liuzwei on 2014/12/16.
 */
public class SanitaionAreaAssementItemView {
    private String AsItemID;//打分id
    private String AreaAssementID;//主表id
    private String projectID;//项目id
    private String projectMC;//项目名称
    private String projectfs;//项目分数
    private String kfyy;//扣分原因

    public String getAsItemID() {
        return AsItemID;
    }

    public void setAsItemID(String asItemID) {
        AsItemID = asItemID;
    }

    public String getAreaAssementID() {
        return AreaAssementID;
    }

    public void setAreaAssementID(String areaAssementID) {
        AreaAssementID = areaAssementID;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectMC() {
        return projectMC;
    }

    public void setProjectMC(String projectMC) {
        this.projectMC = projectMC;
    }

    public String getProjectfs() {
        return projectfs;
    }

    public void setProjectfs(String projectfs) {
        this.projectfs = projectfs;
    }

    public String getKfyy() {
        return kfyy;
    }

    public void setKfyy(String kfyy) {
        this.kfyy = kfyy;
    }
}
