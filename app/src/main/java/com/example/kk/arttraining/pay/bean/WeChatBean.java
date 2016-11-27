package com.example.kk.arttraining.pay.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

/**
 * 作者：wschenyongyin on 2016/11/27 09:01
 * 说明:后台返回微信支付信息bean
 *
 */
public class WeChatBean extends NoDataResponseBean{
    String appid;
    String noncestr;
    String pay_package;
    String partnerid;
    String sign;
    String timestamp;
    private String prepayid;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPay_package() {
        return pay_package;
    }

    public void setPay_package(String pay_package) {
        this.pay_package = pay_package;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public WeChatBean() {
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    @Override
    public String toString() {
        return "WeChatBean{" +
                "appid='" + appid + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", pay_package='" + pay_package + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", sign='" + sign + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", prepayid='" + prepayid + '\'' +
                '}';
    }
}
