package com.example.kk.arttraining.ui.me.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kk.arttraining.MainActivity;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.UserInfoBean;
import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.dao.UserDao;
import com.example.kk.arttraining.dao.UserDaoImpl;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.me.presenter.UserLoginPresenter;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.utils.UIUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/10/17 08:53
 * 说明:
 */
public class UserLoginActivity extends BaseActivity implements IUserLoginView, TextWatcher {
    @InjectView(R.id.et_login_userId)
    EditText et_userId;
    @InjectView(R.id.et_login_password)
    EditText et_password;
    @InjectView(R.id.btn_login)
    Button btn_login;

    private UserLoginPresenter userLoginPresenter;
    private Dialog loadingDialog;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_login_activity);
        init();
    }

    @Override
    public void init() {
//        loadingDialog = DialogUtils.createLoadingDialog(UserLoginActivity.this, "正在登陆...");
        ButterKnife.inject(this);
        userLoginPresenter = new UserLoginPresenter(this);
        btn_login.setOnClickListener(this);

        et_userId.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        et_userId.addTextChangedListener(this);
        et_userId.setOnClickListener(this);
        et_password.addTextChangedListener(this);
        et_password.setOnClickListener(this);
        et_password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
    }

    //按钮点击事件
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

    //获得输入的账号
    @Override
    public String getUserName() {
        return et_userId.getText().toString();
    }

    //获得输入的密码
    @Override
    public String getPassword() {
        return et_password.getText().toString();
    }

    //显示登陆dialog
    @Override
    public void showLoading() {
//        loadingDialog.show();
        ;
    }

    //隐藏登陆dialog
    @Override
    public void hideLoading() {
//        loadingDialog.dismiss();
    }

    //登陆失败
    @Override
    public void showFailedError() {
        UIUtil.ToastshowShort(UserLoginActivity.this, "登录失败");
    }

    //跳转到主页
    @Override
    public void ToMainActivity(UserLoginBean userBean) {
        startActivity(new Intent(UserLoginActivity.this, MainActivity.class));

    }

    @Override
    public void SaveUserInfo(UserLoginBean userBean) {
        userDao = new UserDaoImpl(getApplicationContext());
        userDao.Insert(userBean);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
// TODO: 2016/10/17 监听输入框状态 改变登陆按钮颜色 以及点击状态

    }
}
