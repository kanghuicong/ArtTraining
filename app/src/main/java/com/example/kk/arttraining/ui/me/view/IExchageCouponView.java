package com.example.kk.arttraining.ui.me.view;

/**
 * 作者：wschenyongyin on 2016/11/28 11:45
 * 说明:
 */
public interface IExchageCouponView {

    void exchageCoupon();

    void Success();

    void Failure(String error_code, String error_msg);
}
