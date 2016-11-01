package com.example.kk.arttraining.pay;

import android.app.Activity;
import android.content.Context;

import com.example.kk.arttraining.pay.alipay.AlipayUtil;
import com.example.kk.arttraining.pay.bean.AliPay;
import com.example.kk.arttraining.pay.bean.WeChat;
import com.example.kk.arttraining.pay.wxapi.Constants;
import com.example.kk.arttraining.pay.wxapi.WXPayUtils;
import com.example.kk.arttraining.ui.valuation.bean.CommitOrderBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/10/30 20:17
 * 说明:
 */
public class PayPresenter {
    private IPayActivity iPayActivity;
    private AliPay aliPay;
    private WeChat weChat;
    private Activity activity;
    private Boolean state = false;
    private AlipayUtil alipayUtil;

    public PayPresenter() {
    }

    public PayPresenter(IPayActivity iPayActivity, Activity activity) {
        this.iPayActivity = iPayActivity;
        this.activity = activity;
    }


    void AliPay(Map<String, String> map, String pay_type, CommitOrderBean commitOrderBean) {

        switch (pay_type) {
            //如果从后台获取支付宝密钥成功，调用支付宝进行支付
            case "alipay":
                if (getAlipayInfo(map)) {
                    alipayUtil = new AlipayUtil(activity);
                    alipayUtil.pay(commitOrderBean);
                } else {
                    sendFailure("500");
                }
                break;
            //如果从后台获取微信支付密钥成功，调用微信进行支付
            case "wechat":

                if (Constants.isInstallWX(activity)) {
                    if (getWeChatPayInfo(map)) {
                        WXPayUtils utils = new WXPayUtils(activity, Config.URL_ALIPAY_ASYNC);
                        utils.pay(commitOrderBean);
                    } else {

                    }
                } else {
                    sendFailure("600");
                }


                break;
        }

    }


    //获取支付宝支付必要信息
    Boolean getAlipayInfo(Map<String, String> map) {
        Callback<AliPay> aliPayCallback = new Callback<AliPay>() {
            @Override
            public void onResponse(Call<AliPay> call, Response<AliPay> response) {
                if (response.body() != null) {
                    aliPay = response.body();
                    if (aliPay.getError_code().equals("0")) {
                        iPayActivity.getAliPayPermissions(aliPay);
                        state = true;
                    } else {
                        iPayActivity.showFailure(aliPay.getError_code());
                    }
                } else {
                    iPayActivity.showFailure("failure");
                }


            }

            @Override
            public void onFailure(Call<AliPay> call, Throwable t) {
                iPayActivity.showFailure("failure");
            }
        };

        Call<AliPay> call = HttpRequest.getPayApi().aliPayData(map);
        call.enqueue(aliPayCallback);

        return state;
    }

    Boolean getWeChatPayInfo(Map<String, String> map) {
        Callback<WeChat> aliPayCallback = new Callback<WeChat>() {
            @Override
            public void onResponse(Call<WeChat> call, Response<WeChat> response) {
                if (response.body() != null) {
                    weChat = response.body();
                    if (aliPay.getError_code().equals("0")) {
                        iPayActivity.getWeChatPayPermissions(weChat);
                        state = true;
                    } else {
                        sendFailure(weChat.getError_code());
                    }
                } else {
                    sendFailure("failure");
                }


            }

            @Override
            public void onFailure(Call<WeChat> call, Throwable t) {
                sendFailure("failure");
            }
        };

        Call<WeChat> call = HttpRequest.getPayApi().weChatPayData(map);
        call.enqueue(aliPayCallback);
        return state;
    }

    void sendPayResult(Map<String, String> map) {
        Callback<WeChat> aliPayCallback = new Callback<WeChat>() {
            @Override
            public void onResponse(Call<WeChat> call, Response<WeChat> response) {
                if (response.body() != null) {
                    weChat = response.body();
                    if (aliPay.getError_code().equals("0")) {
                    } else {
                        sendFailure(weChat.getError_code());
                    }
                } else {
                    sendFailure("failure");
                }


            }

            @Override
            public void onFailure(Call<WeChat> call, Throwable t) {
                sendFailure("failure");
            }
        };

        Call<WeChat> call = HttpRequest.getPayApi().sendPayResult(map);
        call.enqueue(aliPayCallback);
    }


    public void sendSuccess() {
        iPayActivity.showSuccess();
    }

    public void sendFailure(String error_code) {
        iPayActivity.showFailure(error_code);
    }
}
