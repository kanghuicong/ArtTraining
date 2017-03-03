package com.example.kk.arttraining.ui.me.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

import java.io.Serializable;

/**
 * 作者：wschenyongyin on 2016/11/1 19:12
 * 说明:优惠券
 */
public class CouponBean extends NoDataResponseBean implements Serializable {
    private int coupon_id;
    private String coupon_name;
    private String destribe;
    private String expiry_date;
    private String face_value;
    private String face_value_type;
    private int coupon_type;
    private int is_used;

    public CouponBean() {
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public int getCoupon_type() {
        return coupon_type;
    }

    public void setCoupon_type(int coupon_type) {
        this.coupon_type = coupon_type;
    }

    public String getDestribe() {
        return destribe;
    }

    public void setDestribe(String destribe) {
        this.destribe = destribe;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getFace_value() {
        return face_value;
    }

    public void setFace_value(String face_value) {
        this.face_value = face_value;
    }

    public String getFace_value_type() {
        return face_value_type;
    }

    public void setFace_value_type(String face_value_type) {
        this.face_value_type = face_value_type;
    }

    public int getIs_used() {
        return is_used;
    }

    public void setIs_used(int is_used) {
        this.is_used = is_used;
    }

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    @Override
    public String toString() {
        return "CouponBean{" +
                "coupon_id=" + coupon_id +
                ", coupon_name='" + coupon_name + '\'' +
                ", destribe='" + destribe + '\'' +
                ", expiry_date='" + expiry_date + '\'' +
                ", face_value='" + face_value + '\'' +
                ", face_value_type='" + face_value_type + '\'' +
                ", coupon_type=" + coupon_type +
                ", is_used=" + is_used +
                '}';
    }
}
