package com.area.EnvironMange.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liuzwei on 2014/12/9.
 */
public class SanitaionAreaAssementItem {
    private String projectID;
    private float fs;

    public SanitaionAreaAssementItem(){}
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

    public static JSONObject fromObject2Json(SanitaionAreaAssementItem sa){
        JSONObject object = new JSONObject();
        try {
            object.put("projectID",sa.getProjectID());
            object.put("fs", sa.getFs());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
