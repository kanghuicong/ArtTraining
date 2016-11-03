package com.example.kk.arttraining.sqlite.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kk.arttraining.bean.SearchEntity;
import com.example.kk.arttraining.utils.SqlLiteUtils;
import com.example.kk.arttraining.utils.UIUtil;

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
    public void addData(String user_id,String user_search) {
        db = dbHelper.getWritableDatabase();
        db.execSQL("insert into search (user_id,user_search) values(?,?)",
                new Object[]{user_id,user_search});
        db.close();
    }

    //查询历史搜索
    public List<SearchEntity> findData(String user_id) {
        db = dbHelper.getWritableDatabase();// 初始化SQLiteDatabase对象
        List<SearchEntity> list = new ArrayList<SearchEntity>() ;
        int i =0;

        Cursor cursor = db.rawQuery("select * from search where user_id = ?",
                new String[]{user_id});
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

    //检验查询信息是否已经查询
    public Boolean contrastData(String user_id,String user_search){
        db = dbHelper.getWritableDatabase();// 初始化SQLiteDatabase对象
        Cursor cursor = db.rawQuery("select * from search where user_id = ?",
                new String[]{user_id});
        int i =0;
        Boolean result = false;
        while (cursor.moveToNext() && i<5){
            if (user_search.equals(cursor.getString(cursor.getColumnIndex("user_search")))){
                UIUtil.showLog("user_search",user_search);
                result=true;
                i++;
            }
        }
        cursor.close();
        return result;
    }

    //清除历史记录
    public void deleteData(String user_id) {
        db = dbHelper.getWritableDatabase();
        db.execSQL("delete from search where user_id = ?",new String[]{user_id});
        db.close();
    }
}
