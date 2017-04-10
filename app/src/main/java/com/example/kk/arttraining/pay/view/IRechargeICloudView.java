package com.example.kk.arttraining.pay.view;

import com.example.kk.arttraining.pay.bean.RechargeBean;
import com.example.kk.arttraining.pay.bean.WeChatBean;
import com.example.kk.arttraining.ui.me.bean.CouponBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2017/3/2 09:25
 * 说明:
 */
public interface IRechargeICloudView {


    void getRechargeList();

    void SuccessRechargeList(List<RechargeBean> rechargeDataList);

    void FailureRechargeList(String error_code,String error_msg);

    void wxRecharge(RechargeBean rechargeBean, int uid,CouponBean couponBean);

    void wxPay(WeChatBean weChatBean);

    void FailureRecharge(String error_code,String error_msg);

}
