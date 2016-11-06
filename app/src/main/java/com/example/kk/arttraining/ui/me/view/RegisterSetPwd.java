package com.example.kk.arttraining.ui.me.view;

import android.app.Dialog;
import android.content.Intent;
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

import butterknife.ButterKnife;
import butterknife.InjectView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_register_setpwd);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {
        registerPresenter = new RegisterPresenter(this);
        loadingDialog = DialogUtils.createLoadingDialog(RegisterSetPwd.this, "");
        btnRegisterSetpwdOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        showLoading();
        registerPresenter.setPwd(etRegisterSetpwd.getText().toString(), etRegisterSetpwdAgain.getText().toString());
    }

    @Override
    public void onSuccess() {
        // TODO: 2016/11/6
        // 跳转到登陆成功主页面

        //发送广播关闭其他的页面
        Intent intent = new Intent();
        intent.setAction(FINISH_ACTION);
        sendBroadcast(intent);

    }

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

                    break;
            }
        }
    };
}
