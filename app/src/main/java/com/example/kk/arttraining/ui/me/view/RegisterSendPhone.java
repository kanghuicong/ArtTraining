package com.example.kk.arttraining.ui.me.view;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.me.presenter.RegisterPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/11/5 19:12
 * 说明:注册第一个页面 填写用户手机号码  并发送手机号码
 */
public class RegisterSendPhone extends BaseActivity implements IRegister {

    @InjectView(R.id.et_login_password)
    EditText etLoginPassword;
    @InjectView(R.id.btn_register_next)
    Button btnRegisterNext;
    private String error_code;
    private String phoneNum;
    private Dialog loadingDialog;
    private RegisterPresenter registerPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_resigster_sendphone);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {

        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(RegisterSetPwd.FINISH_ACTION);
        registerReceiver(myReceiver, filter);

        TitleBack.TitleBackActivity(RegisterSendPhone.this, "注册");
        registerPresenter = new RegisterPresenter(this);
        loadingDialog = DialogUtils.createLoadingDialog(RegisterSendPhone.this, "");

    }

    @Override
    public void onClick(View v) {
        phoneNum = etLoginPassword.getText().toString();
        Map<String, String> map = new HashMap<String, String>();
        registerPresenter.getVerificatioCode(map);

    }


    //成功
    @Override
    public void onSuccess() {
        Intent intent = new Intent(RegisterSendPhone.this, RegisterCheckVerificationCode.class);
        intent.putExtra("phoneNum",phoneNum);
        startActivityForResult(intent, UserLoginActivity.REGISTER_CODE);
        finish();
    }

    //失败
    @Override
    public void onFailure(String error_code) {
        this.error_code = error_code;
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
                    UIUtil.ToastshowShort(RegisterSendPhone.this, getResources().getString(R.string.connection_failure));
                    break;
            }
        }
    };

    //关闭activity
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }

    };
}
