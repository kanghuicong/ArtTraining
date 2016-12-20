package com.example.kk.arttraining.ui.me.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.me.presenter.ExchangeCouponPresenter;
import com.example.kk.arttraining.utils.AutomaticKeyboard;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.KeyBoardUtils;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/11/28 11:47
 * 说明:
 */
public class ExchangeCouponActivity extends BaseActivity implements IExchageCouponView,TextWatcher {

    @InjectView(R.id.title_back)
    ImageView titleBack;
    @InjectView(R.id.title_barr)
    TextView titleBarr;
    @InjectView(R.id.title_tv_ok)
    TextView titleTvOk;
    @InjectView(R.id.et_exchange_)
    EditText etExchange;
    ExchangeCouponPresenter presenter;
    LoadingDialog progressHUD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_exchange_coupon);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {
        etExchange.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        AutomaticKeyboard.getClick(this,etExchange);
         progressHUD = LoadingDialog.getInstance(this);
        presenter = new ExchangeCouponPresenter(this);
        titleBarr.setText("兑换优惠券");
        titleTvOk.setText("确定");
        titleTvOk.setEnabled(false);
        etExchange.addTextChangedListener(this);
    }

    @OnClick({R.id.title_tv_ok,R.id.title_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tv_ok:

                exchageCoupon();
                break;
            case R.id.title_back:
                finish();
                break;
        }

    }

    //兑换优惠券
    @Override
    public void exchageCoupon() {
        String invite_code=etExchange.getText().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("invite_code",invite_code);
        if(!invite_code.equals("")){
            progressHUD.show();
            KeyBoardUtils.closeKeybord(etExchange,this);
            presenter.exchangeCoupon(map);
        }else {
            UIUtil.ToastshowShort(this,"请输入兑换码");
        }

    }

    //兑换优惠券成功
    @Override
    public void Success() {
        progressHUD.dismiss();
        setResult(CouponActivity.EXCHANGE_CODE);
        UIUtil.ToastshowShort(this, "兑换成功");
        finish();
    }

    //兑换失败
    @Override
    public void Failure(String error_code, String error_msg) {
        progressHUD.dismiss();
        if (error_code.equals(Config.TOKEN_INVALID)) {
            startActivity(new Intent(this,UserLoginActivity.class));
            UIUtil.ToastshowShort(this, getResources().getString(R.string.toast_token_nvalid));
        } else {
            UIUtil.ToastshowShort(this, error_msg);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(etExchange.getText().length()>0){
            titleTvOk.setEnabled(true);
            titleTvOk.setTextColor(getResources().getColor(R.color.white));
        }else {
            titleTvOk.setEnabled(false);
            titleTvOk.setTextColor(getResources().getColor(R.color.grey));
        }
    }
}
