package com.example.kk.arttraining.ui.me.view;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kk.arttraining.MainActivity;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.sqlite.dao.UserDao;
import com.example.kk.arttraining.sqlite.dao.UserDaoImpl;
import com.example.kk.arttraining.ui.me.presenter.UserLoginPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.utils.PreferencesUtils;
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
    @InjectView(R.id.tv_register)
    TextView tvRegister;
    @InjectView(R.id.tv_forget_pwd)
    TextView tvForgetPwd;

    private UserLoginPresenter userLoginPresenter;
    private Dialog loadingDialog;
    private UserDao userDao;
    private String error_code;
    private Toast toast;
    public static int REGISTER_CODE=0001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_login_activity);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {
        loadingDialog = DialogUtils.createLoadingDialog(UserLoginActivity.this, "正在登陆...");
        ButterKnife.inject(this);
        toast = new Toast(getApplicationContext());
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
            //登陆
            case R.id.btn_login:
                userLoginPresenter.Login();
                break;
            //注册
            case R.id.tv_register:
                //注册广播
                IntentFilter filter = new IntentFilter();
                filter.addAction(RegisterSetPwd.FINISH_ACTION);
                registerReceiver(myReceiver, filter);

                Intent intentRegister=new Intent(this,RegisterSendPhone.class);
                startActivityForResult(intentRegister,REGISTER_CODE);
                break;
            //注册
            case R.id.tv_forget_pwd:
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
        loadingDialog.show();
    }

    //隐藏登陆dialog
    @Override
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    //登陆失败
    @Override
    public void showFailedError(String error_code) {
        this.error_code = error_code;
        mHandler.sendEmptyMessage(0);
    }

    //跳转到主页
    @Override
    public void ToMainActivity(UserLoginBean userBean) {
        PreferencesUtils.put(getApplicationContext(), "access_token", Config.ACCESS_TOKEN);
        PreferencesUtils.put(getApplicationContext(), "user_code", userBean.getUser_code());
        PreferencesUtils.put(getApplicationContext(), "uid", userBean.getUid());
        startActivity(new Intent(UserLoginActivity.this, MainActivity.class));

    }

    //保存用户信息
    @Override
    public void SaveUserInfo(UserLoginBean userBean) {
        userDao = new UserDaoImpl(getApplicationContext());
        userDao.Insert(userBean);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (error_code) {
                case "101":
                    toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.login_usercode_hint), Toast.LENGTH_SHORT);
                    break;
                case "102":
                    toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.login_pwd_hint), Toast.LENGTH_SHORT);
                    break;
                case "103":
                    toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.login_usercode_error), Toast.LENGTH_SHORT);
                    break;
                case Config.Connection_Failure:
                    toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.connection_failure), Toast.LENGTH_SHORT);
                    break;

            }
            toast.show();
        }
    };


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    //关闭activity
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }

    };
}
