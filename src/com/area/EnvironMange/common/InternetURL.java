package com.area.EnvironMange.common;

/**
 * Created by liuzwei on 2014/12/8.
 */
public class InternetURL {
    private static String MAIN_URL = "http://222.135.111.86:9009/Service1.svc/";
    //登陆接口
    public static String LOGIN_URL = MAIN_URL+"Login";

    //获得建筑物
    public static String GET_BUILDING_URL = MAIN_URL+"GetJs";

    //获取所有宿舍
    public static String GET_SUSHE_URL = MAIN_URL+"GetSS";

    //获取教学楼的楼层信息
    public static String GET_FLOOR_URL = MAIN_URL+"GetLcByBuildingID";

    //获取某教学楼楼层的卫生区域
    public static String GET_BUILDING_AREA_URL = MAIN_URL+"GetAreaInfoByAreaid";

    //获取宿舍卫生区域
    public static String GET_SS_AREA_URL = MAIN_URL+"GetSSAreaInfoByBuildingID";

    //获取所有的建筑物的检查项目
    public static String GET_AREA_TYPES = MAIN_URL+"GetAreaTypesByAreaID";

    //保存某一区域卫生成绩
    public static String SAVE_AREA_SCORE = MAIN_URL+"SaveProjectFs";

    //提交某一区域卫生成绩
    public static String COMMIT_AREA_SCORE = MAIN_URL+"CommitProjectFs";

    //获取一座教学楼的教学公共区域
    public static String GET_TEACH_PUBLIC_URL = MAIN_URL+"GetJXGGArea";

    //获取所有的户外区域
    public static String GET_OUTDOOR_AREA_URL = MAIN_URL+"GetHwArea";



}
