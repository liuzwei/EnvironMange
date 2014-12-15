package com.area.EnvironMange.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liuzwei on 2014/12/9.
 */
public class Building {
    private String ID;
    private String MC;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMC() {
        return MC;
    }

    public void setMC(String MC) {
        this.MC = MC;
    }

    public Building() {

    }

    public static Building jsonObject2Object(JSONObject object){
        Building building = new Building();
        try {
            building.setID(object.getString("ID"));
            building.setMC(object.getString("MC"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return building;
    }

    public Building(String ID, String MC) {
        this.ID = ID;
        this.MC = MC;
    }
}
