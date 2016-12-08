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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.sqlite.dao.UserDao;
import com.example.kk.arttraining.sqlite.dao.UserDaoImpl;
import com.example.kk.arttraining.ui.me.presenter.UserLoginPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.utils.PreferencesUtils;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/10/17 08:53
 * 说明:用户登陆
 */
public class UserLoginActivity extends BaseActivity implements IUserLoginView, TextWatcher, View.OnClickListener {
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
    @InjectView(R.id.iv_title_back)
    ImageView ivTitleBack;

    private UserLoginPresenter userLoginPresenter;
    private Dialog loadingDialog;
    private UserDao userDao;
    private String error_code;
    private Toast toast;
    public final static int REGISTER_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_login_activity);
        ButterKnife.inject(this);
        init();
    }

    public void init() {
        loadingDialog = DialogUtils.createLoadingDialog(UserLoginActivity.this, "正在登陆...");
        ButterKnife.inject(this);
        userLoginPresenter = new UserLoginPresenter(this);
        ivTitleBack.setVisibility(View.GONE);
        TitleBack.TitleBackActivity(this, "登录");
        Intent intent = getIntent();

        et_userId.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        et_userId.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        et_userId.addTextChangedListener(this);
        et_userId.setOnClickListener(this);
        et_password.addTextChangedListener(this);
        et_password.setOnClickListener(this);
        et_password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
    }

    //按钮点击事件
    @OnClick({R.id.btn_login, R.id.tv_register, R.id.tv_forget_pwd})
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

                Intent intentRegister = new Intent(this, RegisterSendPhone.class);
                intentRegister.putExtra("from", "register");
                startActivity(intentRegister);

                break;
            //找回密码
            case R.id.tv_forget_pwd:
                //注册广播
                IntentFilter filter2 = new IntentFilter();
                filter2.addAction(RegisterSetPwd.FINISH_ACTION);
                registerReceiver(myReceiver, filter2);

                Intent intentFind = new Intent(this, RegisterSendPhone.class);
                intentFind.putExtra("from", "find");
                startActivity(intentFind);
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
        hideLoading();
        this.error_code = error_code;
        UIUtil.showLog("error_code", error_code);
        mHandler.sendEmptyMessage(0);
    }

    //跳转到主页
    @Override
    public void ToMainActivity(UserLoginBean userBean) {
        UIUtil.showLog("用户信息:", userBean.toString());
        PreferencesUtils.put(getApplicationContext(), "access_token", userBean.getAccess_token());
        PreferencesUtils.put(getApplicationContext(), "user_code", userBean.getUser_code());
        PreferencesUtils.put(getApplicationContext(), "uid", userBean.getUid());
        PreferencesUtils.put(getApplicationContext(), "user_title", userBean.getTitle());
//        startActivity(new Intent(UserLoginActivity.this, MainActivity.class));
        finish();

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
//                    toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.login_usercode_hint), Toast.LENGTH_SHORT);
                    UIUtil.ToastshowShort(getApplicationContext(), getResources().getString(R.string.login_usercode_hint));
                    break;
                case "102":
                    UIUtil.ToastshowShort(getApplicationContext(), getResources().getString(R.string.login_pwd_hint));
//                    toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.login_pwd_hint), Toast.LENGTH_SHORT);
                    break;
                case "103":
                    UIUtil.ToastshowShort(getApplicationContext(), getResources().getString(R.string.login_usercode_error));
//                    toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.login_usercode_error), Toast.LENGTH_SHORT);
                    break;
                case "104":
                    UIUtil.ToastshowShort(getApplicationContext(), getResources().getString(R.string.login_pwdlength_error));
//                    toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.login_usercode_error), Toast.LENGTH_SHORT);
                    break;
                case Config.Connection_Failure:
                    UIUtil.ToastshowShort(getApplicationContext(), getResources().getString(R.string.connection_failure));
//                    toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.connection_failure), Toast.LENGTH_SHORT);
                    break;
                //密码错误
                case "20023":
                    UIUtil.ToastshowShort(getApplicationContext(), getResources().getString(R.string.login_pwd_error));
                    break;
                //账号不存在
                case "20022":
                    UIUtil.ToastshowShort(getApplicationContext(), getResources().getString(R.string.login_usercode_error));
                    break;

            }
//            toast.show();
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
        UIUtil.showLog("!!!!!!", resultCode + "____>" + requestCode);
        switch (requestCode) {
            case REGISTER_CODE:
                UIUtil.ToastshowShort(UserLoginActivity.this, data.getStringExtra("test"));
                break;
        }
    }

    //关闭activity
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (myReceiver != null) unregisterReceiver(myReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

//    @Override
//    public void onBackPressed() {
//
//    }
}
