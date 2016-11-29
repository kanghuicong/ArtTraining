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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.GeneralBean;
import com.example.kk.arttraining.bean.NoDataResponseBean;
import com.example.kk.arttraining.bean.UserLoginBean;
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
import butterknife.OnClick;

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
    int count = 60;
    private String from;
    String code_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_register_checkcode);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        phoneNum = intent.getStringExtra("phoneNum");
        from = intent.getStringExtra("from");
        //设置只弹出数字键盘
        etRegisterCode.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        if (from.equals("register")) {
            code_type="reg_code";
        } else {
            code_type="identity_code";
        }

        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(RegisterSetPwd.FINISH_ACTION);
        registerReceiver(myReceiver, filter);

        TitleBack.TitleBackActivity(RegisterCheckVerificationCode.this, "填写验证码");
        registerPresenter = new RegisterPresenter(this);
        loadingDialog = DialogUtils.createLoadingDialog(RegisterCheckVerificationCode.this, "");
        Message message = TimingHandler.obtainMessage(1);     // Message  
        TimingHandler.sendMessageDelayed(message, 1000);
    }

    @OnClick({R.id.code_clean, R.id.again_getcode, R.id.btn_register_code_next})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.code_clean:
                etRegisterCode.setText("");
                break;
            //校验验证码
            case R.id.btn_register_code_next:
                String ver_code = etRegisterCode.getText().toString();
                if (ver_code.length() < 4) {
                    UIUtil.ToastshowShort(RegisterCheckVerificationCode.this, "请输入正确的验证码");
                } else {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("ver_code", ver_code);
                    map.put("mobile", phoneNum);
                    map.put("code_type",code_type);
                    registerPresenter.checkVerificatioCode(map);
//                    onSuccess();
                }


                break;
            //重新获取验证码
            case R.id.again_getcode:
                Map<String, String> map2 = new HashMap<String, String>();
                map2.put("mobile", phoneNum);
                registerPresenter.getVerificatioCode(map2);
                break;
        }

    }


    //成功
    @Override
    public void onSuccess(GeneralBean bean) {
        // TODO: 2016/11/5 验证校验码成功

        Intent intent = new Intent(RegisterCheckVerificationCode.this, RegisterSetPwd.class);
        intent.putExtra("phoneNum", phoneNum);
        intent.putExtra("from", from);
        startActivity(intent);

    }

    @Override
    public void RegisterSuccess(UserLoginBean userLoginBean) {

    }

    @Override
    public void checkRecommendSuccess() {

    }

    @Override
    public void checkIsRegisterSuccess() {

    }


    //失败
    @Override
    public void onFailure(String error_code) {
        this.error_code = error_code;

        UIUtil.ToastshowShort(this,error_code);
//        errorHandler.sendEmptyMessage(1);

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
                //设置字体颜色为灰色
                againGetcode.setTextColor(getResources().getColor(R.color.grey));
                //设置不可点击
                againGetcode.setClickable(false);
            } else {
                //设置字体颜色为黑色
                againGetcode.setTextColor(getResources().getColor(R.color.black));
                //设置可点击
                againGetcode.setClickable(true);
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
            UIUtil.showLog("1111111", "---->");
            finish();
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myReceiver != null) unregisterReceiver(myReceiver);

    }
}
