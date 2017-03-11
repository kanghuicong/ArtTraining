package com.example.kk.arttraining;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.baidu.location.service.LocationService;
//import com.squareup.leakcanary.LeakCanary;
import com.example.kk.arttraining.utils.Cockroach;
import com.example.kk.arttraining.utils.StringUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.jpush.android.api.JPushInterface;

/**
 * 作者：wschenyongyin on 2016/10/15 08:55
 * 说明:
 */
public class MyApplication extends MultiDexApplication {

    public LocationService locationService;
    public Vibrator mVibrator;

    //Application单例
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //jpush初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        instance = this;

//腾讯Bugly
        CrashReport.initCrashReport(getApplicationContext(), "900058867", false);

        //定位
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
//        SDKInitializer.initialize(getApplicationContext());

//        MobclickAgent. startWithConfigure(UMAnalyticsConfig config);

        //友檬

        UMShareAPI.get(this);
//        Config.DEBUG = true;
        PlatformConfig.setWeixin("wx7d6ed84ec930fb37", "c8988f2f6cce05a10cc9b83f187ee828");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        PlatformConfig.setQQZone("1105826339", "EhXtrXix4Mrlxgtd");

        //捕获异常
//        Cockroach.install(new Cockroach.ExceptionHandler() {
//            @Override
//            public void handlerException(final Thread thread, final Throwable throwable) {
//                try {
//                    Cockroach.sendExceptionInfo(StringUtils.Throwable2String(throwable));
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });

//        LeakCanary.install(this);
    }


}