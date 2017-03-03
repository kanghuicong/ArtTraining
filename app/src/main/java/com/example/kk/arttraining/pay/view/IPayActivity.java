package com.example.kk.arttraining.pay.view;

import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.pay.bean.AliPay;
import com.example.kk.arttraining.pay.bean.WeChat;
import com.example.kk.arttraining.pay.bean.WeChatBean;

/**
 * 作者：wschenyongyin on 2016/10/30 19:47
 * 说明:
 */
public interface IPayActivity {

    //从服务器获取支付宝所需的数据
    void getAliPayPermissions(AliPay aliPay);

    //从服务器获取微信支付所需的数据
    void wxPay(WeChatBean weChat);

    //将支付结果提交到服务器
    void sendPayResult();

    //支付失败
    void showFailure(String error_code, String error_msg);

    //获取订单支付剩余时间成功
    void SuccessRemainTime(int remainTime);
    //支付成功
    void showSuccess();

    //取消订单成功
    void cancelOrderSuccess();

    //获取订单支付剩余时间成功
    void FailureRemainTime();
    //取消订单失败
    void cancelOrderFailure(String error_code, String error_msg);


}
