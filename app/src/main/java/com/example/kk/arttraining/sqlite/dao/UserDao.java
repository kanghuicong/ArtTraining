package com.example.kk.arttraining.sqlite.dao;

import com.example.kk.arttraining.bean.modelbean.UserLoginBean;
import com.example.kk.arttraining.ui.me.bean.UserCountBean;

/**
 * 作者：wschenyongyin on 2016/10/18 14:12
 * 说明:对用户本地数据库操作的接口
 */
public interface UserDao {
    //插入新用户
    int Insert(UserLoginBean UserInfoBean);

    //更新用户信息
    int Update(int uid, String update_value, String update_type);

    //删除用户信息
    int Delete(int uid);

    //获取用户信息
    UserLoginBean QueryAll(int uid);

    //检查本地数据库用户是否存在
    int CheckUserExist(String uid);

    //判断后台用户密码和本地用户密码是否一样
    int CheckPwd(String uid, String pwd);


    void updateCountAll(UserCountBean countBean);


}
