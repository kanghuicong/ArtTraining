package com.example.kk.arttraining.utils;

/**
 * Created by kanghuicong on 2016/9/21.
 * QQ邮箱:515849594@qq.com
 * 配置信息
 */

public class Config {

    /* 接口相关-start */
    public final static String SERVER_IP = "192.168.1.1";
    public final static String SERVER_MH = ":";
    public final static String SYSTEM_PORT = "8080";
    public final static String SYSTEM_NAME = "ArtTraining";
    public final static String BASE_URL = "http://" + SERVER_IP + SERVER_MH + SYSTEM_PORT + SYSTEM_NAME;
    public final static String URL_LOGIN = "";


    /* 接口相关-start */
    public final static String URL_PAY_ASYNC = BASE_URL + "";//服务器异步通知页面接口

     /* 数据库相关-start */

    /* 数据库相关-end */


    /* 全局变量-start */
    public static String User_Id = "123";
    public static int PermissionsState = 0;//权限状态
    public final static String USER_HEADER_Url = "http://img.fuwo.com/attachment/1608/09/245f26de5e1811e6abcf00163e00254c.jpg";
    /* 全局变量-end */

}
