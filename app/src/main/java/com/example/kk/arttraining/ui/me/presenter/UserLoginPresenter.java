package com.example.kk.arttraining.ui.me.presenter;

import android.os.Handler;

import com.example.kk.arttraining.ui.me.bean.UserLoginBean;
import com.example.kk.arttraining.ui.me.model.IUserModel;
import com.example.kk.arttraining.ui.me.model.OnLoginListener;
import com.example.kk.arttraining.ui.me.model.UserModel;
import com.example.kk.arttraining.ui.me.view.IUserLoginView;

/**
 * 作者：wschenyongyin on 2016/10/15 17:04
 * 说明:
 */
public class UserLoginPresenter {
    private IUserLoginView iUserLoginView;
    private IUserModel iUserModel;
    private Handler handler = new Handler();

    public UserLoginPresenter(IUserLoginView iUserLoginView) {
        this.iUserLoginView = iUserLoginView;
        this.iUserModel = new UserModel();

    }

    public void Login() {
        iUserModel.login(iUserLoginView.getUserName(), iUserLoginView.getPassword(), new OnLoginListener() {
            @Override
            public void loginSuccess(final UserLoginBean user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
// TODO: 2016/10/17
                        iUserLoginView.toMainActivity(user);
                    }
                });
            }

            @Override
            public void loginFailed() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
// TODO: 2016/10/17
                    }
                });
            }
        });
    }
}
