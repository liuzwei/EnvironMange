package com.area.EnvironMange.model;

/**
 * author: ${zhanghailong}
 * Date: 2014/12/10
 * Time: 15:42
 * 类的功能、说明写在此处.
 */
public class Center {
    private String name;
    private String id;

    public Center(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
