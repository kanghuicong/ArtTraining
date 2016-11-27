package com.example.kk.arttraining.pay.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

/**
 * 作者：wschenyongyin on 2016/10/30 20:05
 * 说明:微信支付必要信息
 */
public class WeChat extends NoDataResponseBean {
    WeChatBean model;

    public WeChatBean getModel() {
        return model;
    }

    public void setModel(WeChatBean model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "WeChat{" +
                "model=" + model +
                '}';
    }
}
