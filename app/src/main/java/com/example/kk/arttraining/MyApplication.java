package com.example.kk.arttraining;

import android.app.Service;
import android.os.Vibrator;

import com.baidu.location.service.LocationService;
import com.tencent.bugly.crashreport.CrashReport;

import cn.jpush.android.api.JPushInterface;

/**
 * 作者：wschenyongyin on 2016/10/15 08:55
 * 说明:
 */
public class MyApplication extends android.app.Application {

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
        initData();

    }

    private void initData() {
        //当程序发生Uncaught异常的时候,由该类来接管程序,一定要在这里初始化
//        CrashHandler.getInstance().init(this);
    }
}