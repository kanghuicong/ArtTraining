package com.example.kk.arttraining;

import android.app.Service;
import android.os.Vibrator;

import com.baidu.location.service.LocationService;

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
        // 设置拍摄视频缓存路径
//        File dcim = Environment
//                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
//        if (DeviceUtils.isZte()) {
//            if (dcim.exists()) {
//                VCamera.setVideoCachePath(dcim + "/recoder/");
//            } else {
//                VCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/",
//                        "/sdcard-ext/")
//                        + "/recoder/");
//            }
//        } else {
//            VCamera.setVideoCachePath(dcim + "/WeChatJuns/");
//        }
//      VCamera.setVideoCachePath(FileUtils.getRecorderPath());
//        // 开启log输出,ffmpeg输出到logcat
//        VCamera.setDebugMode(true);
//        // 初始化拍摄SDK，必须
//        VCamera.initialize(this);

        //定位
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
//        SDKInitializer.initialize(getApplicationContext());
    }
}