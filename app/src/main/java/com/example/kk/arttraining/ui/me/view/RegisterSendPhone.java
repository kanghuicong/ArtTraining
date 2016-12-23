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
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.GeneralBean;
import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.me.presenter.RegisterPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.StringUtils;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/11/5 19:12
 * 说明:注册第一个页面 填写用户手机号码  并发送手机号码
 */
public class RegisterSendPhone extends BaseActivity implements IRegister, TextWatcher {

    @InjectView(R.id.et_login_password)
    EditText etLoginPassword;
    @InjectView(R.id.btn_register_next)
    Button btnRegisterNext;
    @InjectView(R.id.et_recommend)
    EditText etRecommend;
    @InjectView(R.id.ress_hint)
    TextView ressHint;
    @InjectView(R.id.ress_hint2)
    TextView ressHint2;
    @InjectView(R.id.ll_recommend)
    LinearLayout llRecommend;

    private String error_code;
    private String phoneNum;
    private LoadingDialog loadingDialog;
    private RegisterPresenter registerPresenter;
    private String from = null;//标记是从哪里过来的
    private String code_type;
    private String recommend_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_resigster_sendphone);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {

        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        if (from.equals("register")) {
            TitleBack.TitleBackActivity(RegisterSendPhone.this, "注册");
            code_type = "reg_code";
        } else if (from.equals("find")) {
            code_type = "identity_code";
            TitleBack.TitleBackActivity(RegisterSendPhone.this, "找回密码");
            ressHint.setVisibility(View.GONE);
            ressHint2.setVisibility(View.GONE);
            llRecommend.setVisibility(View.GONE);
        } else if (from.equals("change")) {
            code_type = "change_code";
            TitleBack.TitleBackActivity(RegisterSendPhone.this, "修改密码");
            ressHint.setVisibility(View.GONE);
            ressHint2.setVisibility(View.GONE);
            llRecommend.setVisibility(View.GONE);
        }
        etLoginPassword.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(RegisterSetPwd.FINISH_ACTION);
        registerReceiver(myReceiver, filter);

        etLoginPassword.addTextChangedListener(this);
        etLoginPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        btnRegisterNext.setEnabled(false);

        registerPresenter = new RegisterPresenter(this);
        loadingDialog = LoadingDialog.getInstance(this);

    }

    /**
     * 1.如果是注册：先判断是否填写邀请码，如果填写了邀请码，则先验证邀请码，邀请码验证通过再验证手机号码是否注册过，若没有则获取验证码操作
     * 2.如果不是注册：则判断手机号码是否注册过，若没有注册过，那么获取验证码
     *
     * @param v
     */
    @OnClick(R.id.btn_register_next)
    public void onClick(View v) {
        phoneNum = etLoginPassword.getText().toString();
        if (from.equals("register")) {
            recommend_code = etRecommend.getText().toString();
            if (StringUtils.isPhone(phoneNum)) {
                if (recommend_code != null && !recommend_code.equals("")) {
                    //校验邀请码
                    checkRecommend();
                } else {
                    //检查手机号码是否注册过
                    checkIsRegister();
                }
            } else {
                UIUtil.ToastshowShort(this, "请输入正确的手机号码");
            }
        } else {
            if (StringUtils.isPhone(phoneNum)) {
                checkIsRegister();
            } else {
                UIUtil.ToastshowShort(this, "请输入正确的手机号码");
            }
        }

    }


    //验证手机是否注册过
    void checkIsRegister() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", phoneNum);
        registerPresenter.checkIsRegister(map);
    }

    //验证邀请是否有效
    void checkRecommend() {
        if (recommend_code.length() != 4) {
            UIUtil.ToastshowShort(this, "您输入正确的邀请码");
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("invite_code", recommend_code);
        registerPresenter.checkRecommend(map);
    }

    //获取验证码
    void getVerificationCode() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", phoneNum);
        map.put("code_type", code_type);
        registerPresenter.getVerificatioCode(map);
    }


    //成功
    @Override
    public void onSuccess(GeneralBean bean) {
        hideLoading();
        Intent intent = new Intent(RegisterSendPhone.this, RegisterCheckVerificationCode.class);
        intent.putExtra("phoneNum", phoneNum);
        intent.putExtra("from", from);
        startActivity(intent);
    }

    @Override
    public void RegisterSuccess(UserLoginBean userLoginBean) {

    }

    //检查推荐成功
    @Override
    public void checkRecommendSuccess() {
        getVerificationCode();

    }

    //检查用户是否注册成功
    @Override
    public void checkIsRegisterSuccess() {
        getVerificationCode();
    }

    //失败
    @Override
    public void onFailure(String error_code) {
        hideLoading();
        this.error_code = error_code;
//        UIUtil.ToastshowShort(this, error_code);
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
                case "20024":
                    if (from.equals("register")) {
                        UIUtil.ToastshowShort(RegisterSendPhone.this, "手机号码已注册");
                    } else {

                        getVerificationCode();
                    }
                    break;
            }
        }
    };

    //关闭activity
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            UIUtil.showLog("2222222222", "---->");
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
//

    }

    private CharSequence wordNum;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        wordNum = s;
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (wordNum.length() == 11) {
            btnRegisterNext.setEnabled(true);
            btnRegisterNext.setBackgroundColor(getResources().getColor(R.color.blue_overlay));
        }
    }
}
