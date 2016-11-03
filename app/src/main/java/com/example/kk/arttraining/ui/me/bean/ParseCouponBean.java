package com.example.kk.arttraining.ui.me.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/1 19:35
 * 说明:解析优惠券数据
 */
public class ParseCouponBean extends NoDataResponseBean {

    List<CouponBean> coupons;

    public ParseCouponBean() {
    }

    public List<CouponBean> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<CouponBean> coupons) {
        this.coupons = coupons;
    }
}
