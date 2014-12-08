package com.area.EnvironMange.model;


import java.io.Serializable;

/**
 * Created by liuzwei on 2014/12/8.
 */
public class SanitaionAreaAssementItem implements Serializable{
    private String projectID;
    private float fs;
//    private String AsItemID;

    public SanitaionAreaAssementItem(String projectID, float fs) {
        this.projectID = projectID;
        this.fs = fs;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public float getFs() {
        return fs;
    }

    public void setFs(float fs) {
        this.fs = fs;
    }

//    public String getAsItemID() {
//        return AsItemID;
//    }
//
//    public void setAsItemID(String asItemID) {
//        AsItemID = asItemID;
//    }
}
