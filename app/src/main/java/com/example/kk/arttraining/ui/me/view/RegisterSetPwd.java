package com.example.kk.arttraining.ui.me.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kk.arttraining.MainActivity;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.sqlite.dao.UserDao;
import com.example.kk.arttraining.sqlite.dao.UserDaoImpl;
import com.example.kk.arttraining.ui.me.presenter.RegisterPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.utils.PreferencesUtils;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/11/6 10:24
 * 说明:注册设置密码
 */
public class RegisterSetPwd extends BaseActivity implements IRegister {
    @InjectView(R.id.et_register_setpwd)
    EditText etRegisterSetpwd;
    @InjectView(R.id.et_register_setpwd_again)
    EditText etRegisterSetpwdAgain;
    @InjectView(R.id.btn_register_setpwd_ok)
    Button btnRegisterSetpwdOk;
    private Dialog loadingDialog;
    private RegisterPresenter registerPresenter;
    private String error_code;
    public static String FINISH_ACTION = "finish";
    private String mobile;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_register_setpwd);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        mobile = intent.getStringExtra("phoneNum");


        TitleBack.TitleBackActivity(RegisterSetPwd.this, "设置密码");
        registerPresenter = new RegisterPresenter(this);
        loadingDialog = DialogUtils.createLoadingDialog(RegisterSetPwd.this, "");
        btnRegisterSetpwdOk.setOnClickListener(this);
    }

    @OnClick(R.id.btn_register_setpwd_ok)
    public void onClick(View v) {
        showLoading();

//执行注册
        if (from.equals("register")) {
            registerPresenter.setPwd(etRegisterSetpwd.getText().toString(), etRegisterSetpwdAgain.getText().toString(), mobile);
        }
        //执行找回密码
        else {
            registerPresenter.setForgotPwd(etRegisterSetpwd.getText().toString(), etRegisterSetpwdAgain.getText().toString(), mobile);
        }

        onSuccess();
    }

    @Override
    public void onSuccess() {
        // TODO: 2016/11/6
        // 跳转到登陆成功主页面
        //发送广播关闭其他的页面


    }

    @Override
    public void RegisterSuccess(UserLoginBean userLoginBean) {
        PreferencesUtils.put(getApplicationContext(), "access_token", userLoginBean.getAccess_token());
        PreferencesUtils.put(getApplicationContext(), "user_code", userLoginBean.getUser_code());
        PreferencesUtils.put(getApplicationContext(), "uid", userLoginBean.getUid());
        UserDao userDao = new UserDaoImpl(getApplicationContext());
        userDao.Insert(userLoginBean);
        Intent intent = new Intent();
        intent.setAction(FINISH_ACTION);
        sendBroadcast(intent);
        startActivity(new Intent(RegisterSetPwd.this, MainActivity.class));

    }

    @Override
    public void checkRecommendSuccess() {

    }

    @Override
    public void checkIsRegisterSuccess() {

    }


    @Override
    public void onFailure(String error_code) {
        this.error_code = error_code;
//        UIUtil.ToastshowShort(this,error_code);
        mHandler.sendEmptyMessage(0);

    }

    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (error_code) {
                case Config.Connection_Failure:

                    break;
                case "101":
                    UIUtil.ToastshowShort(RegisterSetPwd.this, "您输入的两次密码不同！");
                    break;
                case "102":
                    UIUtil.ToastshowShort(RegisterSetPwd.this, "密码长度不符合规范！");
                    break;
                case "103":
                    UIUtil.ToastshowShort(RegisterSetPwd.this, "请输入密码！");
                    break;

            }
        }
    };
}
