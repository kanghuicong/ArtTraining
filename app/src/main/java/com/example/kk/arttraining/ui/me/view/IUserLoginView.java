package com.example.kk.arttraining.ui.me.view;


import com.example.kk.arttraining.bean.UserInfoBean;

/**
 * 作者：wschenyongyin on 2016/10/15 17:05
 * 说明:
 */
public interface IUserLoginView {

    String getUserName();

    String getPassword();

    void showLoading();

    void hideLoading();

    void showFailedError();

    void ToMainActivity(UserInfoBean userInfoBean);

}
