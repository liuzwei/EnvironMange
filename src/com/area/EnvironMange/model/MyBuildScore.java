package com.area.EnvironMange.model;

/**
 * author: ${zhanghailong}
 * Date: 2014/12/10
 * Time: 14:37
 * 类的功能、说明写在此处.
 */
public class MyBuildScore {
    private String name;
    private String datetime;
    private String score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public MyBuildScore(String name,String score, String datetime) {
        this.name = name;
        this.datetime = datetime;
        this.score = score;
    }
}
