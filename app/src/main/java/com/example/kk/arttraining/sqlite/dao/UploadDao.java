package com.example.kk.arttraining.sqlite.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kk.arttraining.sqlite.bean.UploadBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.SqlLiteUtils;
import com.example.kk.arttraining.utils.UIUtil;

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

    public int insert(UploadBean uploadBean) {
        if(!exist(uploadBean.getOrder_id())){
            db = dbHelper.getWritableDatabase();// 初始化SQLiteDatabase对象
            UIUtil.showLog("UploadDao-->insert", "----->" + uploadBean.toString());
            db.execSQL("insert into uploadTable (file_path,order_id,progress,type,create_time,order_title,att_type,att_size,att_length,pay_type,uid) values(?,?,?,?,?,?,?,?,?,?,?)",
                    new Object[]{uploadBean.getFile_path(), uploadBean.getOrder_id(), uploadBean.getProgress(), "0", uploadBean.getCreate_time(), uploadBean.getOrder_title(), uploadBean.getAtt_type(), uploadBean.getAtt_size(), uploadBean.getAtt_length(), uploadBean.getPay_type(), Config.UID});
        }
        else {

        }
        db.close();
        return 0;
    }

    public List<UploadBean> query(String type) {
        UIUtil.showLog("UploadDao-->", "query");
        List<UploadBean> uploadBeanList = new ArrayList<UploadBean>();
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from uploadTable where type=? and uid=?",
                new String[]{type,Config.UID+""});
        while (cursor.moveToNext()) {
            UploadBean uploadBean = new UploadBean();
            uploadBean.setFile_path(cursor.getString(cursor.getColumnIndex("file_path")));
            uploadBean.setOrder_id(cursor.getString(cursor.getColumnIndex("order_id")));
            uploadBean.setCreate_time(cursor.getString(cursor.getColumnIndex("create_time")));
            uploadBean.setOrder_title(cursor.getString(cursor.getColumnIndex("order_title")));
            uploadBean.setProgress(cursor.getInt(cursor.getColumnIndex("progress")));
            uploadBean.setOrder_pic(cursor.getString(cursor.getColumnIndex("order_pic")));
            uploadBean.setAtt_length(cursor.getString(cursor.getColumnIndex("att_length")));
            uploadBean.setAtt_type(cursor.getString(cursor.getColumnIndex("att_type")));
            UIUtil.showLog("query---->uploadBean",uploadBean.toString());
            uploadBeanList.add(uploadBean);
        }
        cursor.close();
        db.close();
        return uploadBeanList;
    }

    public int update(String update_type, Object value, String order_id) {
        db = dbHelper.getWritableDatabase();
        UIUtil.showLog("UploadDao-->", "update");
        String sql = "update uploadTable set " + update_type + "=? where order_id=?";
        Object[] values = new Object[]{value, order_id};
        try {
            db.execSQL(sql, values);
            return 1;
        } catch (Exception e) {

            e.printStackTrace();
            return 0;
        }


    }


    public UploadBean queryOrder(String order_id) {
        UIUtil.showLog("UploadDao-->", "query");
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from uploadTable where order_id=?",
                new String[]{order_id});
        while (cursor.moveToNext()) {
            UploadBean uploadBean = new UploadBean();
            uploadBean.setFile_path(cursor.getString(cursor.getColumnIndex("file_path")));
            uploadBean.setOrder_id(cursor.getString(cursor.getColumnIndex("order_id")));
            uploadBean.setCreate_time(cursor.getString(cursor.getColumnIndex("create_time")));
            uploadBean.setOrder_title(cursor.getString(cursor.getColumnIndex("order_title")));
            uploadBean.setProgress(cursor.getInt(cursor.getColumnIndex("progress")));
            uploadBean.setOrder_pic(cursor.getString(cursor.getColumnIndex("order_pic")));
            uploadBean.setAtt_size(cursor.getString(cursor.getColumnIndex("att_size")));
            uploadBean.setPay_type(cursor.getString(cursor.getColumnIndex("pay_type")));
            uploadBean.setAtt_length(cursor.getString(cursor.getColumnIndex("att_length")));
            uploadBean.setAtt_type(cursor.getString(cursor.getColumnIndex("att_type")));

            return uploadBean;
        }
        cursor.close();
        db.close();
        return null;
    }

    public int delete(String order_id) {
        db = dbHelper.getWritableDatabase();// 初始化SQLiteDatabase对象

        db.execSQL("delete from uploading where order_id = ?", new String[]{order_id});
        db.close();
        return 0;
    }

    public boolean exist(String order_id){
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from uploadTable where order_id=?",
                new String[]{order_id});
        while (cursor.moveToNext()) {
            return true;
        }
        db.close();
        return false;
    };
    public void cleanTable(){
        db = dbHelper.getWritableDatabase();
        db.execSQL("delete from uploadTable");
        db.close();
    }
}
