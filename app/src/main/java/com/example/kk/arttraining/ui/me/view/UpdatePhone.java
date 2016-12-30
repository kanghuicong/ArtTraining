package com.example.kk.arttraining.ui.me.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.me.AboutActivity;
import com.example.kk.arttraining.ui.me.presenter.UpdatePhonePresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.utils.StringUtils;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/11/13 11:41
 * 说明:修改手机号码
 */
public class UpdatePhone extends BaseActivity implements IUpdatePhone, TextWatcher {

    @InjectView(R.id.title_back)
    ImageView titleBack;
    @InjectView(R.id.title_barr)
    TextView titleBarr;
    @InjectView(R.id.title_tv_ok)
    TextView titleTvOk;
    @InjectView(R.id.et_update_phone)
    EditText etUpdatePhone;
    @InjectView(R.id.et_code)
    EditText etCode;
    @InjectView(R.id.btn_getcode)
    Button btnGetcode;

    private UpdatePhonePresenter presenter;
    private String mobile;
    private String ver_code;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_update_phone);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {
        loadingDialog = LoadingDialog.getInstance(this);
        presenter = new UpdatePhonePresenter(this);
        etUpdatePhone.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        etUpdatePhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        etCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        etUpdatePhone.addTextChangedListener(this);
        titleBarr.setText("更换号码");
        titleTvOk.setText("确定");
    }

    @OnClick({R.id.title_back, R.id.title_tv_ok, R.id.btn_getcode})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_tv_ok:
                VerifyCode();
                break;
            case R.id.btn_getcode:
                loadingDialog.show();
                verifyPhoneReg();
                break;
        }
    }

    //判断手机号码是否注册过
    @Override
    public void verifyPhoneReg() {
        mobile = etUpdatePhone.getText().toString();
        if (StringUtils.isPhone(mobile)) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("mobile", mobile);
            presenter.checkIsRegister(map);
        } else {
            UIUtil.ToastshowShort(this, "请输入正确的手机号码");
        }
    }

    //获取验证码
    @Override
    public void getVerifyCode() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", mobile);
        map.put("code_type", "identity_code");
        presenter.getVerificatioCode(map);
    }

    //校验验证码
    @Override
    public void VerifyCode() {
        loadingDialog.show();
        ver_code = etCode.getText().toString();
        if (ver_code.length() != 4) {
            UIUtil.ToastshowShort(this, "请输入正确的验证码");
            loadingDialog.dismiss();
        } else {
            Map<String, String> map = new HashMap<String, String>();
            map.put("ver_code", ver_code);
            map.put("mobile", mobile);
            map.put("code_type", "identity_code");
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
        UIUtil.ToastshowShort(getApplicationContext(),"已发送");
        getVerifyCode();
    }

    @Override
    public void SuccessVerifyCode() {
        loadingDialog.dismiss();
//        VerifyCode();
    }

    //验证码校验成功
    @Override
    public void SuccessVerify() {
        savePhone();
    }

    //修改手机号码成功
    @Override
    public void SuccessChangePhone() {
        loadingDialog.dismiss();
        UIUtil.ToastshowShort(this, "修改号码成功");
        Intent intent = new Intent();
        intent.putExtra("mobile", mobile);
        setResult(AboutActivity.UPDATE_PHONE, intent);
        finish();
    }

    @Override
    public void Failure(String error_msg) {
        UIUtil.ToastshowShort(this, error_msg);
        loadingDialog.dismiss();
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
            btnGetcode.setBackgroundColor(getResources().getColor(R.color.blue_overlay));
            titleTvOk.setTextColor(getResources().getColor(R.color.white));
            btnGetcode.setEnabled(true);
            titleTvOk.setEnabled(true);
        } else {
            btnGetcode.setBackgroundColor(getResources().getColor(R.color.grey));
            titleTvOk.setTextColor(getResources().getColor(R.color.grey));
            btnGetcode.setEnabled(false);
            titleTvOk.setEnabled(false);
        }
    }
}
