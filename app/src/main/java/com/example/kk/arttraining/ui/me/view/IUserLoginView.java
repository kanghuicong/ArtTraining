package com.example.kk.arttraining.ui.me.view;


import com.example.kk.arttraining.bean.modelbean.UserLoginBean;

/**
 * 作者：wschenyongyin on 2016/10/15 17:05
 * 说明:
 */
public interface IUserLoginView {

    String getUserName();

    String getPassword();

    void showLoading();

    void hideLoading();

    void showFailedError(String error_code);
    //跳转到main
    void ToMainActivity(UserLoginBean userBean);
    //保存用户信息
    void SaveUserInfo(UserLoginBean userBean);
    //第三方登录成功
    void VerifyPhone();

}
