package com.example.kk.arttraining.sqlite.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.utils.SqlLiteUtils;

/**
 * 作者：wschenyongyin on 2016/10/18 14:12
 * 说明:用户数据库实现类
 */
public class UserDaoImpl implements UserDao {

    private SqlLiteUtils dbHelper;
    SQLiteDatabase db;
    private int status = 1;
    private String sql = null;

    public UserDaoImpl(Context context) {
        dbHelper = new SqlLiteUtils(context);
    }

    @Override
    public int Insert(UserLoginBean UserInfoBean) {
        db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("insert into userTable (user_code,uid,user_name,user_mobile,head_pic,user_sex,city,identity,school,email) values(?,?,?,?,?,?,?,?,?)",
                    new Object[]{UserInfoBean.getUser_code(), UserInfoBean.getUid(), UserInfoBean.getName(), UserInfoBean.getMobile(), UserInfoBean.getHead_pic(), UserInfoBean.getSex(), UserInfoBean.getCity(), UserInfoBean.getIdentity(), UserInfoBean.getSchool(), UserInfoBean.getEmail()});

        } catch (Exception e) {
            status = 0;
            e.printStackTrace();
        }
        db.close();
        return status;
    }

    @Override
    public int Update(String uid, String update_value, String update_type) {
        db = dbHelper.getWritableDatabase();
        sql = "update userTable set " + update_type + "=? where user_id=?";
        Object[] values = new Object[]{update_value, uid};
        try {
            db.execSQL(sql, values);
        } catch (Exception e) {
            status = 0;
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public int Delete(String uid) {
        db = dbHelper.getWritableDatabase();
        sql = "delete * from userTable where user_id=?";
        Object[] values = new Object[]{uid};
        try {
            db.execSQL(sql, values);
        } catch (Exception e) {
            status = 0;
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public UserLoginBean QueryAll(String user_code) {
        UserLoginBean userBean = new UserLoginBean();
        db = dbHelper.getWritableDatabase();
        sql = "select * from userTable where user_code=?";
        String[] values = new String[]{user_code};
        try {
            Cursor cursor = db.rawQuery(sql, values);
            while (cursor.moveToNext()) {
//user_id,user_name,user_mobile,head_pic,user_sex,city,identity,school,email
                userBean.setCity(cursor.getString(cursor.getColumnIndex("city")));
                userBean.setUser_code(cursor.getString(cursor.getColumnIndex("user_id")));
                userBean.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                userBean.setHead_pic(cursor.getString(cursor.getColumnIndex("head_pic")));
                userBean.setIdentity(cursor.getString(cursor.getColumnIndex("identity")));
                userBean.setName(cursor.getString(cursor.getColumnIndex("user_name")));
                userBean.setSchool(cursor.getString(cursor.getColumnIndex("school")));
                userBean.setMobile(cursor.getString(cursor.getColumnIndex("user_mobile")));
                userBean.setSex(cursor.getString(cursor.getColumnIndex("user_sex")));
            }

        } catch (Exception e) {
            userBean = null;
            e.printStackTrace();
        }
        return userBean;
    }

    @Override
    public int CheckUserExist(String user_code) {
        status = 0;
        db = dbHelper.getWritableDatabase();
        sql = "select * from userTable where user_code=?";
        String[] values = new String[]{user_code};
        try {
            Cursor cursor = db.rawQuery(sql, values);
            while (cursor.moveToNext()) {
                status = 1;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return status;
    }

    @Override
    public int CheckPwd(String user_code, String pwd) {

        db = dbHelper.getWritableDatabase();
        sql = "select user userTable where user_code=?";
        String[] values = new String[]{user_code};
        try {
            Cursor cursor = db.rawQuery(sql, values);
            while (cursor.moveToNext()) {
                status = 1;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return status;
    }
}
