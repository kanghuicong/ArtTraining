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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.me.presenter.RegisterPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.utils.TitleBack;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/11/5 20:51
 * 说明:
 */
public class RegisterCheckVerificationCode extends BaseActivity implements IRegister {
    @InjectView(R.id.code_clean)
    ImageView codeClean;
    @InjectView(R.id.again_getcode)
    TextView againGetcode;
    @InjectView(R.id.btn_register_code_next)
    Button btnRegisterCodeNext;
    @InjectView(R.id.et_register_code)
    EditText etRegisterCode;
    @InjectView(R.id.ll_user_info)
    LinearLayout llUserInfo;
    private String error_code;
    private String phoneNum;
    private Dialog loadingDialog;
    private RegisterPresenter registerPresenter;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_register_checkcode);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {
        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(RegisterSetPwd.FINISH_ACTION);
        registerReceiver(myReceiver, filter);


        TitleBack.TitleBackActivity(RegisterCheckVerificationCode.this, "注册");
        registerPresenter = new RegisterPresenter(this);
        loadingDialog = DialogUtils.createLoadingDialog(RegisterCheckVerificationCode.this, "");
        Message message = TimingHandler.obtainMessage(1);     // Message  
        TimingHandler.sendMessageDelayed(message, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.code_clean:
                etRegisterCode.setText("");
                break;
            case R.id.btn_register_code_next:
                Map<String, String> map = new HashMap<String, String>();
                registerPresenter.checkVerificatioCode(map);
                break;
            case R.id.again_getcode:
                Map<String, String> map2 = new HashMap<String, String>();
                registerPresenter.getVerificatioCode(map2);
                break;
        }
    }

    //成功
    @Override
    public void onSuccess() {
        // TODO: 2016/11/5 验证校验码成功

    }

    //失败
    @Override
    public void onFailure(String error_code) {
        this.error_code = error_code;
        errorHandler.sendEmptyMessage(1);

    }

    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    Handler TimingHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            count--;
            if (count > 0) {
                Message message = TimingHandler.obtainMessage(1);
                TimingHandler.sendMessageDelayed(message, 1000);
                againGetcode.setText("重新获取（" + count + ")");
            } else {
                againGetcode.setText("重新获取");
            }
        }
    };

    Handler errorHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (error_code) {
                case Config.Connection_Failure:
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
