package com.example.kk.arttraining.ui.me.view;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.modelbean.UserLoginBean;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.sqlite.dao.UserDao;
import com.example.kk.arttraining.sqlite.dao.UserDaoImpl;
import com.example.kk.arttraining.ui.me.presenter.UpdatePhonePresenter;
import com.example.kk.arttraining.utils.ActivityManage;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.PreferencesUtils;
import com.example.kk.arttraining.utils.StringUtils;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2017/1/4 14:09
 * 说明:第三方登录绑定手机号码
 */
public class UmBindPhoneActivity extends BaseActivity implements IUpdatePhone, TextWatcher {

    @InjectView(R.id.et_bind_phone)
    EditText etBindPhone;
    @InjectView(R.id.et_bind_code)
    EditText etBindCode;
    @InjectView(R.id.btn_bind_getcode)
    Button btnBindGetcode;
    @InjectView(R.id.btn_bind_phone_next)
    Button btnBindPhoneNext;
    private UpdatePhonePresenter presenter;
    private String mobile;
    private String ver_code;
    private LoadingDialog loadingDialog;
    //倒计时60秒
    private int recLen = 60;
    //uid
    private String Um_uid;
    //第三方登录方式
    private String login_way;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_umbind_phone);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this, "绑定号码");
        init();
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        Um_uid = intent.getStringExtra("um_uid");
        login_way = intent.getStringExtra("login_way");
        loadingDialog = LoadingDialog.getInstance(this);
        presenter = new UpdatePhonePresenter(this);
        etBindPhone.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        etBindPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        etBindCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        etBindPhone.addTextChangedListener(this);
    }

    @OnClick({R.id.btn_bind_getcode, R.id.btn_bind_phone_next})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bind_getcode:
                loadingDialog.show();

                verifyPhoneReg();
                break;
            case R.id.btn_bind_phone_next:
                if (etBindCode.getText().toString().equals("")) {
                    UIUtil.ToastshowShort(this, "请输入验证码");
                } else if (etBindCode.getText().toString().length() != 4) {
                    UIUtil.ToastshowShort(this, "请输入正确的验证码");
                } else {
                    VerifyCode();
                }
                break;
        }
    }

    //判断手机号码是否注册过
    @Override
    public void verifyPhoneReg() {
        mobile = etBindPhone.getText().toString();
        if (StringUtils.isPhone(mobile)) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("mobile", mobile);
            map.put("login_way", login_way);
            presenter.UmVerifyPhoner(map);
        } else {
            UIUtil.ToastshowShort(this, "请输入正确的手机号码");
        }
    }

    //获取验证码
    @Override
    public void getVerifyCode() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", mobile);
        map.put("code_type", "reg_code");
        presenter.getVerificatioCode(map);
    }

    //校验验证码
    @Override
    public void VerifyCode() {
        loadingDialog.show();
        ver_code = etBindCode.getText().toString();
        if (ver_code.length() != 4) {
            UIUtil.ToastshowShort(this, "请输入正确的验证码");
            loadingDialog.dismiss();
        } else {
            Map<String, String> map = new HashMap<String, String>();
            map.put("ver_code", ver_code);
            map.put("mobile", mobile);
            map.put("code_type", "reg_code");
            presenter.checkVerificatioCode(map);
        }
    }

    //保存用户手机号码
    @Override
    public void savePhone() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("mobile", mobile);
        presenter.savePhone(map);
    }

    @Override
    public void SuccessPhoneReg() {
        //背景设置为灰色，不可点击
        btnBindGetcode.setBackgroundColor(UIUtil.getColor(R.color.grey));
        btnBindGetcode.setEnabled(false);
        //开始倒计时
        handler.postDelayed(runnable, 1000);
        getVerifyCode();
    }

    //验证码已发送
    @Override
    public void SuccessVerifyCode() {
        loadingDialog.dismiss();
        UIUtil.ToastshowShort(this, "验证码已发送");
    }

    //验证码校验成功
    @Override
    public void SuccessVerify() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mobile", mobile);
        map.put("login_way", login_way);
        map.put("uid", Um_uid);
        presenter.UmCreateUser(map);
    }

    //修改手机号码成功
    @Override
    public void SuccessChangePhone() {
    }

    //绑定手机号码成功
    @Override
    public void SuccessumBind(UserLoginBean userLoginBean) {
        Config.ACCESS_TOKEN = userLoginBean.getAccess_token();
        Config.UID = userLoginBean.getUid();
        PreferencesUtils.put(getApplicationContext(), "access_token", userLoginBean.getAccess_token() + "");
        PreferencesUtils.put(getApplicationContext(), "user_code", userLoginBean.getUser_code() + "");
        PreferencesUtils.put(getApplicationContext(), "uid", userLoginBean.getUid());
        PreferencesUtils.put(getApplicationContext(), "user_title", userLoginBean.getTitle() + "");
        presenter.setJpushTag(userLoginBean.getAccess_token() + "");
        UserDao userDao = new UserDaoImpl(getApplicationContext());
        userDao.Insert(userLoginBean);
//        ActivityManage.getAppManager().finishActivity(UserLoginActivity.class);
        ActivityManage.getAppManager().finishAllActivity();
//        finish();
    }

    //失败
    @Override
    public void Failure(String error_msg) {
        UIUtil.ToastshowShort(this, error_msg);
        try {
            if (loadingDialog.isShowing()) loadingDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //监听输入状态
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
        if (wordNum.length() > 10) {
            btnBindPhoneNext.setBackgroundColor(UIUtil.getColor(R.color.blue_overlay));
            if (recLen == 60) {
                btnBindGetcode.setBackgroundColor(UIUtil.getColor(R.color.blue_overlay));
                btnBindGetcode.setEnabled(true);
            }
        } else {
            btnBindGetcode.setBackgroundColor(UIUtil.getColor(R.color.grey));
            btnBindGetcode.setEnabled(false);
        }
    }

    //获取验证码倒计时
    Handler handler = new Handler();


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen--;
            btnBindGetcode.setText(recLen + "s");
            if (recLen == 0) {
                recLen = 60;
                //背景设置为蓝色，可点击
                btnBindGetcode.setBackgroundColor(UIUtil.getColor(R.color.blue_overlay));
                btnBindGetcode.setText("获取验证码");
                btnBindGetcode.setEnabled(true);
            } else {
                handler.postDelayed(this, 1000);
            }
        }
    };

    //将handler释放
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != handler) {
            handler.removeCallbacks(runnable);
            handler = null;
        }
    }
}
