package com.example.kk.arttraining.sqlite.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kk.arttraining.sqlite.bean.UploadBean;
import com.example.kk.arttraining.utils.SqlLiteUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/2 16:21
 * 说明:保存上传文件
 */
public class UploadDao {
    private SqlLiteUtils dbHelper;
    SQLiteDatabase db;

    public UploadDao(Context context) {
        dbHelper = new SqlLiteUtils(context);
    }

    int insert(String file_path, String order_id) {
        db = dbHelper.getWritableDatabase();// 初始化SQLiteDatabase对象

        db.execSQL("insert into uploading (file_path,order_id) values(?,?)",
                new Object[]{file_path, order_id});
        db.close();
        return 0;
    }

    List<UploadBean> query() {
        List<UploadBean> uploadBeanList = new ArrayList<UploadBean>();
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from uploading ",
                new String[]{});
        while (cursor.moveToNext()) {
            UploadBean uploadBean = new UploadBean();
            uploadBean.setFile_path(cursor.getString(cursor.getColumnIndex("file_path")));
            uploadBean.setOrder_id(cursor.getString(cursor.getColumnIndex("order_id")));
            uploadBean.setCreate_time(cursor.getString(cursor.getColumnIndex("create_time")));
            uploadBean.setOrder_title(cursor.getString(cursor.getColumnIndex("order_title")));

            uploadBeanList.add(uploadBean);
        }
        cursor.close();
        db.close();
        return uploadBeanList;
    }

    int delete(String order_id) {
        db = dbHelper.getWritableDatabase();// 初始化SQLiteDatabase对象

        db.execSQL("delete from uploading where order_id = ?", new String[]{order_id});
        db.close();
        return 0;
    }
}
