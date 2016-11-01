package com.example.kk.arttraining.pay.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

/**
 * 作者：wschenyongyin on 2016/10/30 20:05
 * 说明:微信支付必要信息
 */
public class WeChat extends NoDataResponseBean {
    public String APP_ID;
    public String MCH_ID;
    public String API_KEY;

    public WeChat() {
    }

    public WeChat(String API_KEY, String APP_ID, String MCH_ID) {
        this.API_KEY = API_KEY;
        this.APP_ID = APP_ID;
        this.MCH_ID = MCH_ID;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public void setAPI_KEY(String API_KEY) {
        this.API_KEY = API_KEY;
    }

    public String getAPP_ID() {
        return APP_ID;
    }

    public void setAPP_ID(String APP_ID) {
        this.APP_ID = APP_ID;
    }

    public String getMCH_ID() {
        return MCH_ID;
    }

    public void setMCH_ID(String MCH_ID) {
        this.MCH_ID = MCH_ID;
    }
}
