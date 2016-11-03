package com.example.kk.arttraining.ui.me.view;

import com.example.kk.arttraining.ui.me.bean.CouponBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/29 09:11
 * 说明:优惠券
 */
public interface ICouponActivity {
    //获取数据
    void getDatas(List<CouponBean> couponBeanList);

    //显示加载dialog
    void showLoading();

    //隐藏加载dialog
    void hideLoading();

    //请求失败
    void onFailure(String error_code);
}
