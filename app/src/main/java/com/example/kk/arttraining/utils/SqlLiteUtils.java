package com.example.kk.arttraining.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 作者：wschenyongyin on 2016/9/21 16:41
 * 说明:本地数据库工具类
 */
public class SqlLiteUtils extends SQLiteOpenHelper {
    static String name = "user.db";
    static int dbVersion = 1;

    public SqlLiteUtils(Context context) {
        super(context, name, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        UIUtil.showLog("SqlLiteUtils---->","onCreate");
        db.execSQL("create table search(user_id varchar(20),user_search varchar (200))");
        db.execSQL("create table uploadTable(file_path varchar (200),order_id varchar(30),progress int,type varchar(20),create_time varchar(20),order_title varchar(30),order_pic varchar(50))");
        db.execSQL("create table userTable(user_code varchar(20) ,user_name varchar (30),user_mobile varchar(20),head_pic varchar(100),user_sex varchar(10),city varchar(10),identity varchar(20),school varchar(20),email varchar(30))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
