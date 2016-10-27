package com.example.kk.arttraining;

import android.app.Service;

import android.os.Environment;


import android.app.Service;
import android.os.Vibrator;

import com.baidu.location.service.LocationService;
//import com.baidu.mapapi.SDKInitializer;

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
        /**
         * 拍摄小视频初始化
         */
//        VCamera.setVideoCachePath( Environment.getExternalStorageDirectory().getAbsolutePath() + "/videoRecord/");
//        // 开启log输出,ffmpeg输出到logcat
//        VCamera.setDebugMode(true);
//        // 初始化拍摄SDK，必须
//        VCamera.initialize(this);

        //定位
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
//        SDKInitializer.initialize(getApplicationContext());
    }
}