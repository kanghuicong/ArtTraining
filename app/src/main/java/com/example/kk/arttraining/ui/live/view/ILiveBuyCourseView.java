package com.example.kk.arttraining.ui.live.view;

/**
 * 作者：wschenyongyin on 2017/3/4 10:00
 * 说明:购买课程页面
 */
public interface ILiveBuyCourseView {
    //查询用户当前云币数量
    void queryCloud();

    //查询用户云币数量成功
    void SuccessQueryCloud(double cloud);

    //cha
    void FailureQueryCloud(String error_code, String error_msg);

    //购买
    void Pay();

    //购买支付成功
    void SuccessPay();

    //购买失败
    void FailurePay(String error_code, String error_msg);

    //跳转到充值云币页面
    void skipRechargeCloudActivity();


}
