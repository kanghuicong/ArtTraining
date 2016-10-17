package com.example.kk.arttraining.pay.wxapi;

import android.app.Activity;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class Constants {
    public static final String APP_ID = "wx7ffea3b252e0035c";
    public static final String MCH_ID = "1372345502";
    public static final String API_KEY = "8942ce6c05e6eabdb2d66e3b3a0a78d3";



    // 判断手机是否安装微信
    public static boolean isInstallWX(Activity activity) {
        IWXAPI api = WXAPIFactory.createWXAPI(activity, Constants.APP_ID, true);
        if(api.isWXAppInstalled() && api.isWXAppSupportAPI()) {
            return true;
        }else {
            return false;
        }
    }
}
