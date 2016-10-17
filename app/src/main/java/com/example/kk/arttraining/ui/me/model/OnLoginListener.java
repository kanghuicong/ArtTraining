package com.example.kk.arttraining.ui.me.model;

import com.example.kk.arttraining.ui.me.bean.UserLoginBean;

/**
 * 作者：wschenyongyin on 2016/10/15 17:10
 * 说明:
 */
public interface OnLoginListener {

    void loginSuccess(UserLoginBean user);

    void loginFailed();

}
