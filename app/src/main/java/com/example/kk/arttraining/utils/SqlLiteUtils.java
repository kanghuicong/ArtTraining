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
        db.execSQL("create table search(user_id varchar(20),user_search varchar (200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
