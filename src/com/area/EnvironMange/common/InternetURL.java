package com.area.EnvironMange.common;

/**
 * Created by liuzwei on 2014/12/8.
 */
public class InternetURL {
    private static String MAIN_URL = "http://222.135.111.86:9009/Service1.svc/";

    //登陆接口
    public static String LOGIN_URL = MAIN_URL + "Login";

    //登陆并返回用户ID
    public static String LOGIN_URL_ID = MAIN_URL + "LoginApp";

    //获得建筑物
    public static String GET_BUILDING_URL = MAIN_URL + "GetJs";
    public static String GET_BUILDING_URL_PUBLIC = MAIN_URL + "GetJx";

    //获取所有宿舍
    public static String GET_SUSHE_URL = MAIN_URL + "GetSS";

    //获取教学楼的楼层信息
    public static String GET_FLOOR_URL = MAIN_URL + "GetLcByBuildingID";

    //获取某教学楼楼层的卫生区域
    public static String GET_BUILDING_AREA_URL = MAIN_URL + "GetAreaInfoByAreaid";

    //获取宿舍卫生区域
    public static String GET_SS_AREA_URL = MAIN_URL + "GetSSAreaInfoByBuildingID";

    //获取所有的建筑物的检查项目
    public static String GET_AREA_TYPES = MAIN_URL + "GetAreaTypesByAreaID";

    //保存某一区域卫生成绩
    public static String SAVE_AREA_SCORE = MAIN_URL + "SaveProjectFs";

    //提交某一区域卫生成绩
    public static String COMMIT_AREA_SCORE = MAIN_URL + "CommitProjectFs";

    //获取一座教学楼的教学公共区域
    public static String GET_TEACH_PUBLIC_URL = MAIN_URL + "GetJXGGArea";

    //获取所有的户外区域
    public static String GET_OUTDOOR_AREA_URL = MAIN_URL + "GetHwArea";

    //获取所有保存但未提交的成绩
    public static String GET_SAVE_FS_URL = MAIN_URL + "GetSavedFsByAreaID";

    //获得所有提交的分数成绩
    public static String GET_COMMIT_FS_URL = MAIN_URL + "GetCommitedFsByAreaID";

    //根据主键id获取已经保存的明细成绩
    public static String GET_SAVE_DETAIL_URL = MAIN_URL + "GetSavedDetailProjects";

    //根据主键id获取已经提交的明细成绩
    public static String GET_COMMIT_DETAIL_URL = MAIN_URL + "GetCommitedDetailProjects";

    //判断一个卫生区域示范可以打分
    public static String IS_CAN_DF = MAIN_URL + "CheckIsCanDF";

    //更改密码
    public static String UPDATE_PASSWORD_URL = MAIN_URL + "UpdateUserInfo";

    //更新保存分数
    public static String UPDATE_SAVE_FS_URL = MAIN_URL + "UpdateSaveProjectFs";

    //提交保存但未提交的
    public static String SAVE_NOT_COMMIT = MAIN_URL + "CommitSavedProjectFs";

    //获取所有联系人
    public static String GET_ALL_LXR_URL = MAIN_URL + "GetAllLxr";

    //根据姓名查找联系人
    public static String GET_LXR_BY_NAME = MAIN_URL + "GetAllLxrByXm";
}
