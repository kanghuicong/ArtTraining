package com.example.kk.arttraining.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kk.arttraining.bean.SearchEntity;
import com.example.kk.arttraining.utils.SqlLiteUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanghuicong on 2016/9/23.
 * QQ邮箱:515849594@qq.com
 */
public class SearchDao {
    private SqlLiteUtils dbHelper;
    SQLiteDatabase db;

    public SearchDao(Context context) {
        dbHelper = new SqlLiteUtils(context);
    }

    //添加历史搜索
    public void add(String user_id,String user_search) {
        db = dbHelper.getWritableDatabase();
        db.execSQL("insert into search (user_id,user_search) values(?,?)",
                new Object[]{user_id,user_search});
        db.close();
    }

    //查询历史搜索
    public List<SearchEntity> find(String id) {
        db = dbHelper.getWritableDatabase();// 初始化SQLiteDatabase对象
        List<SearchEntity> list = new ArrayList<SearchEntity>() ;
        int i =0;

        Cursor cursor = db.rawQuery("select * from search where user_id = ?",
                new String[]{id});
        while (cursor.moveToNext() && i<5)// 遍历查找到的便签信息
        {
            SearchEntity info = new SearchEntity();
            info.setUser_id(cursor.getString(cursor.getColumnIndex("user_id")));
            info.setUser_search(cursor.getString(cursor.getColumnIndex("user_search")));
            list.add(info);
            i++;
        }
        cursor.close();
        return list;
    }
}
