package com.area.EnvironMange.util;

import android.app.Activity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.LinkedList;
import java.util.List;

/**
 * author: liuzwei
 * Date: 2014/7/31
 * Time: 17:57
 * 退出程序
 */
public class SystemExitUtil {
    private static SystemExitUtil instance;
    private List<Activity> activityList = new LinkedList<Activity>();
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public static SystemExitUtil getInstance(){
        if (instance == null){
            instance = new SystemExitUtil();
        }
        return instance;
    }

    public void addActivity(Activity activity){
        activityList.add(activity);
    }

    public void exit(){
        for (Activity activity : activityList){
            activity.finish();
        }

        System.exit(0);
    }
}
