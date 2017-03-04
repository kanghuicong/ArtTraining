package com.example.kk.arttraining.pay.bean;

import com.example.kk.arttraining.bean.modelbean.NoDataResponseBean;

/**
 * 作者：wschenyongyin on 2016/10/30 19:53
 * 说明:支付宝支付所需要的信息
 */
public class AliPay extends NoDataResponseBean {

    private String appID;

    public static String PARTNER;
    // 商户收款账号
    public String SELLER;
    // 商户私钥，pkcs8格式
    public String RSA_PRIVATE;


    public AliPay() {
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public static String getPARTNER() {
        return PARTNER;
    }

    public static void setPARTNER(String PARTNER) {
        AliPay.PARTNER = PARTNER;
    }

    public String getRSA_PRIVATE() {
        return RSA_PRIVATE;
    }

    public void setRSA_PRIVATE(String RSA_PRIVATE) {
        this.RSA_PRIVATE = RSA_PRIVATE;
    }

    public String getSELLER() {
        return SELLER;
    }

    public void setSELLER(String SELLER) {
        this.SELLER = SELLER;
    }
}
