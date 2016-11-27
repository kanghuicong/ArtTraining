package com.example.kk.arttraining.pay;

import android.app.Activity;

import com.example.kk.arttraining.pay.alipay.AlipayUtil;
import com.example.kk.arttraining.pay.bean.AliPay;
import com.example.kk.arttraining.pay.bean.WeChat;
import com.example.kk.arttraining.pay.wxapi.Constants;
import com.example.kk.arttraining.pay.wxapi.WXPayUtils;
import com.example.kk.arttraining.ui.valuation.bean.CommitOrderBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

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
    private WeChat weChatBean;
    private Activity activity;
    private Boolean state = false;
    private AlipayUtil alipayUtil;

    public PayPresenter() {
    }

    public PayPresenter(IPayActivity iPayActivity, Activity activity) {
        this.iPayActivity = iPayActivity;
        this.activity = activity;
    }


    void AliPay(Map<String, Object> map, String pay_type, CommitOrderBean commitOrderBean) {

        switch (pay_type) {
            //如果从后台获取支付宝密钥成功，调用支付宝进行支付
            case "alipay":
                if (getAlipayInfo(map)) {
                    alipayUtil = new AlipayUtil(activity);
                    alipayUtil.pay(commitOrderBean);
                } else {
                    sendFailure("500","");
                }
                break;
            //如果从后台获取微信支付密钥成功，调用微信进行支付
            case "wechat":

                if (Constants.isInstallWX(activity)) {
                    if (getWeChatPayInfo(map)) {
//                        WXPayUtils utils = new WXPayUtils(activity, Config.URL_ALIPAY_ASYNC);
//                        utils.pay(commitOrderBean,weChatBean.getModel(),activity);
                        //如果获取微信支付必要信息成功  将订单保存到数据库
                        iPayActivity.showSuccess();
                    } else {

                    }




                } else {
                    sendFailure("600","");
                }


                break;
        }

    }


    //获取支付宝支付必要信息
    Boolean getAlipayInfo(Map<String, Object> map) {
        Callback<AliPay> aliPayCallback = new Callback<AliPay>() {
            @Override
            public void onResponse(Call<AliPay> call, Response<AliPay> response) {

                if (response.body() != null) {
                    aliPay = response.body();
                    if (aliPay.getError_code().equals("0")) {
                        iPayActivity.getAliPayPermissions(aliPay);
                        state = true;
                    } else {
                        iPayActivity.showFailure(aliPay.getError_code(),aliPay.getError_msg());
                    }
                } else {
                    iPayActivity.showFailure(response.code()+"", Config.Connection_ERROR_TOAST);
                }


            }

            @Override
            public void onFailure(Call<AliPay> call, Throwable t) {
                iPayActivity.showFailure(Config.Connection_Failure, Config.Connection_ERROR_TOAST);
            }
        };

        Call<AliPay> call = HttpRequest.getPayApi().aliPayData(map);
        call.enqueue(aliPayCallback);

        return state;
    }

    Boolean getWeChatPayInfo(Map<String, Object> map) {
        Callback<WeChat> aliPayCallback = new Callback<WeChat>() {
            @Override
            public void onResponse(Call<WeChat> call, Response<WeChat> response) {
                UIUtil.showLog("getWeChatPayInfo--->response",response.code()+"--->"+response.message());
                if (response.body() != null) {
                    weChatBean = response.body();
                    UIUtil.showLog("getWeChatPayInfo--->weChatBean","--->"+weChatBean.toString());
                    if (weChatBean.getError_code().equals("0")) {
                        iPayActivity.getWeChatPayPermissions(weChatBean.getModel());
                        state = true;
                    } else {
                        sendFailure(weChatBean.getError_code(),weChatBean.getError_msg());
                    }
                } else {
                    sendFailure(response.code()+"",Config.Connection_ERROR_TOAST);
                }


            }

            @Override
            public void onFailure(Call<WeChat> call, Throwable t) {
                UIUtil.showLog("getWeChatPayInfo--->onFailure","--->"+t.getMessage()+"---->"+t.getCause());
                sendFailure(Config.Connection_Failure,Config.Connection_ERROR_TOAST);
            }
        };

        Call<WeChat> call = HttpRequest.getPayApi().weChatPayData(map);
        call.enqueue(aliPayCallback);
        return state;
    }

    //将支付结果返回给服务器
    void sendPayResult(Map<String, String> map) {
        Callback<WeChat> aliPayCallback = new Callback<WeChat>() {
            @Override
            public void onResponse(Call<WeChat> call, Response<WeChat> response) {
                if (response.body() != null) {
                    weChatBean = response.body();
                    if (aliPay.getError_code().equals("0")) {
                    } else {
                        sendFailure(weChatBean.getError_code(),weChatBean.getError_msg());
                    }
                } else {
                    sendFailure(response.code()+"",Config.Connection_ERROR_TOAST);
                }


            }

            @Override
            public void onFailure(Call<WeChat> call, Throwable t) {
                sendFailure(Config.Connection_Failure,Config.Connection_ERROR_TOAST);
            }
        };

        Call<WeChat> call = HttpRequest.getPayApi().sendPayResult(map);
        call.enqueue(aliPayCallback);
    }



    public void sendSuccess() {
        iPayActivity.showSuccess();
    }

    public void sendFailure(String error_code,String error_msg) {
        iPayActivity.showFailure(error_code, error_msg);
    }
}
