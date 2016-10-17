package com.example.kk.arttraining.ui.me.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.UserInfoBean;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.me.presenter.UserLoginPresenter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/10/17 08:53
 * 说明:
 */
public class UserLoginActivity extends BaseActivity implements IUserLoginView {
    @InjectView(R.id.et_login_userId)
    EditText et_userId;
    @InjectView(R.id.et_login_password)
    EditText et_password;
    @InjectView(R.id.btn_login)
    Button btn_login;

    private UserLoginPresenter userLoginPresenter;

    @Override
    public void init() {
        ButterKnife.inject(this);
        userLoginPresenter=new UserLoginPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                userLoginPresenter.login();
                break;

            default:
                break;
        }

    }


    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showFailedError() {

    }

    @Override
    public void ToMainActivity(UserInfoBean userInfoBean) {

    }
}
