package com.example.kk.arttraining;

import android.app.Service;
import android.os.Vibrator;
import android.support.multidex.MultiDexApplication;

import com.baidu.location.service.LocationService;
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

    @Override
    public void onCreate() {
        super.onCreate();
        //jpush初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

//腾讯Bugly
        CrashReport.initCrashReport(getApplicationContext(), "900058867", false);

        //定位
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
//        SDKInitializer.initialize(getApplicationContext());

//        MobclickAgent. startWithConfigure(UMAnalyticsConfig config);

        //友檬

        UMShareAPI.get(this);
        Config.DEBUG = true;
        PlatformConfig.setWeixin("wx7d6ed84ec930fb37", "c8988f2f6cce05a10cc9b83f187ee828");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        PlatformConfig.setQQZone("1105826339", "EhXtrXix4Mrlxgtd");


        initData();

    }

    private void initData() {
        //当程序发生Uncaught异常的时候,由该类来接管程序,一定要在这里初始化
//        CrashHandler.getInstance().init(this);
    }
}