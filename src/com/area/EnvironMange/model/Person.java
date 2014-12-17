package com.area.EnvironMange.model;

/**
 * author: ${zhanghailong}
 * Date: 2014/12/17
 * Time: 20:03
 * 类的功能、说明写在此处.
 */
public class Person {
    private String ID;
    private String Xm;//姓名
    private String Tel;//电话

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getXm() {
        return Xm;
    }

    public void setXm(String xm) {
        Xm = xm;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }
}
