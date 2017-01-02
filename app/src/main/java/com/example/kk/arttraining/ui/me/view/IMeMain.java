package com.example.kk.arttraining.ui.me.view;

import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.ui.me.bean.UserCountBean;

/**
 * 作者：wschenyongyin on 2016/11/9 09:27
 * 说明:我的页面View接口
 */
public interface IMeMain {
    //获取用户信息
    void getUserInfo();

    //获取用户统计
    void getUserCount();

    //获取用户信息成功
    void getUserInfoSuccess(UserLoginBean userBean);

    //获取用户信息失败
    void getUserInfoFailure(String error_code);

    //获取用户统计成功
    void getUserCountSuccess(UserCountBean userCountBean);

    //获取用户统计信息失败
    void getUserCountFailure(String error_code);

//    //接收到广播后更新本地数据库
//    void updateCountNum(String type, String value);

//    //设置红色消息提示显示
//    void setRemindMsgBgVisible();
//    //设置红色消息提示隐藏
//    void setRemindMsgBgGone();

}
