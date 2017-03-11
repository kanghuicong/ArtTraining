package com.example.kk.arttraining.ui.me.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.modelbean.UserLoginBean;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.sqlite.dao.UserDao;
import com.example.kk.arttraining.sqlite.dao.UserDaoImpl;
import com.example.kk.arttraining.ui.me.presenter.UserLoginPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.PreferencesUtils;
import com.example.kk.arttraining.utils.UIUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

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
    @InjectView(R.id.tv_title_bar)
    TextView tvTitleBar;
    @InjectView(R.id.wx_login)
    ImageView wxLogin;
    @InjectView(R.id.qq_login)
    ImageView qqLogin;
    @InjectView(R.id.sina_login)
    ImageView sinaLogin;

    private UserLoginPresenter userLoginPresenter;
    private LoadingDialog loadingDialog;
    private UserDao userDao;
    private String error_code;
    private Toast toast;
    public final static int REGISTER_CODE = 101;
    private String from = null;
    private UMShareAPI mShareAPI;

    private boolean IS_REGISTER = false;

    private String login_type = null;

    private String UM_Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_login_activity);
        ButterKnife.inject(this);
        init();
    }

    public void init() {
        mShareAPI = UMShareAPI.get(this);

        loadingDialog = LoadingDialog.getInstance(this);
        loadingDialog.setMessage("正在登陆");
        ButterKnife.inject(this);
        userLoginPresenter = new UserLoginPresenter(getApplicationContext(), this);
        tvTitleBar.setText("登录");
        Intent intent = getIntent();
        from = intent.getStringExtra("from");

        et_userId.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        et_userId.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        et_userId.addTextChangedListener(this);
        et_userId.setOnClickListener(this);
        et_password.addTextChangedListener(this);
        et_password.setOnClickListener(this);
        et_password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
    }

    //按钮点击事件
    @OnClick({R.id.wx_login, R.id.btn_login, R.id.tv_register, R.id.tv_forget_pwd, R.id.iv_title_back, R.id.qq_login, R.id.sina_login})
    public void onClick(View v) {
        switch (v.getId()) {
            //登陆
            case R.id.btn_login:
                userLoginPresenter.Login();
                break;
            //注册
            case R.id.tv_register:
                //注册广播
                IS_REGISTER = true;
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
            case R.id.iv_title_back:
                if (from != null && from.equals("exit")) {
                    Config.EXIT_FLAG = "exit";
                    finish();
                } else {
                    finish();
                }
                break;
            //微信登录
            case R.id.wx_login:
                login_type = "wx";
                mShareAPI.getPlatformInfo(this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            //qq登录
            case R.id.qq_login:
                login_type = "qq";
                mShareAPI.getPlatformInfo(this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            //新浪微博登陆
            case R.id.sina_login:
                login_type = "sina";
                mShareAPI.getPlatformInfo(this, SHARE_MEDIA.SINA, umAuthListener);
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
        try {
            if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }


        Config.ACCESS_TOKEN = userBean.getAccess_token();
        Config.UID = userBean.getUid();
        Config.USER_NAME=userBean.getName();
        UIUtil.showLog("用户信息:", userBean.toString());
        //设置别名
        UIUtil.showLog("设置别名token:", userBean.getAccess_token());
        userLoginPresenter.setJpushTag(userBean.getAccess_token() + "");
        PreferencesUtils.put(getApplicationContext(), "access_token", userBean.getAccess_token() + "");
        PreferencesUtils.put(getApplicationContext(), "user_code", userBean.getUser_code() + "");
        PreferencesUtils.put(getApplicationContext(), "uid", userBean.getUid());
        PreferencesUtils.put(getApplicationContext(), "user_title", userBean.getTitle() + "");
        PreferencesUtils.put(getApplicationContext(), "user_name", userBean.getName() + "");
//        startActivity(new Intent(UserLoginActivity.this, MainActivity.class));
        finish();

    }

    //保存用户信息
    @Override
    public void SaveUserInfo(UserLoginBean userBean) {
        userDao = new UserDaoImpl(getApplicationContext());
        userDao.Insert(userBean);
    }

    //绑定手机号码
    @Override
    public void VerifyPhone() {
        loadingDialog.dismiss();
        Intent intent = new Intent(this, UmBindPhoneActivity.class);
        intent.putExtra("um_uid", UM_Uid);
        intent.putExtra("login_way", login_type);
        startActivity(intent);
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
        }
    };


    //第三方登陆回调
    UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            loadingDialog.show();
            UM_Uid = (map.get("uid"));
            map.put("login_way", login_type);
            userLoginPresenter.umLoginRequest(map);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            UIUtil.ToastshowShort(getApplicationContext(), "第三方登录失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {

        }
    };


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //监听用户输入密码和账号
    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(getUserName()) || TextUtils.isEmpty(getPassword())) {
            btn_login.setBackgroundColor(getResources().getColor(R.color.grey));
            btn_login.setClickable(false);
        } else {
            btn_login.setBackgroundColor(getResources().getColor(R.color.blue_overlay));
            btn_login.setClickable(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
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
            if (IS_REGISTER = true && myReceiver != null) unregisterReceiver(myReceiver);
            if (loadingDialog.isShowing()) loadingDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (from != null && from.equals("exit")) {
            Config.EXIT_FLAG = "exit";
            finish();
        } else {
            finish();
        }
    }

}
