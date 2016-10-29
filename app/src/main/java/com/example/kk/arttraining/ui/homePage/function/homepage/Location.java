package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.app.Activity;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.service.LocationService;
import com.example.kk.arttraining.MyApplication;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.concurrent.ExecutorService;

/**
 * Created by kanghuicong on 2016/10/29.
 * QQ邮箱:515849594@qq.com
 */
public class Location {
    public static LocationService locationService;
    static BDLocationListener mListener;

    public static void startLocationService(ExecutorService mThreadService, final Activity activity, final TextView tvHomepageAddress) {

        mThreadService.execute(new Runnable() {
            @Override
            public void run() {
                locationService = ((MyApplication) activity.getApplication()).locationService;
                locationService.registerListener(mListener);
                //注册监听
                int type = activity.getIntent().getIntExtra("from", 0);
                if (type == 0) {
                    locationService.setLocationOption(locationService.getDefaultLocationClientOption());
                } else if (type == 1) {
                    locationService.setLocationOption(locationService.getOption());
                }
                locationService.start();// 定位SDK
            }
        });


        // 定位结果回调
        mListener = new BDLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation location) {
                if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                    tvHomepageAddress.setText(location.getCity());
                    if (Config.CITY.equals("")) {
                        Config.CITY = tvHomepageAddress.getText().toString();
                    } else {
                        if (!Config.CITY.equals(tvHomepageAddress.getText().toString())) {
                            UIUtil.ToastshowShort(activity, "位置不对哦");
                        }
                    }
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    UIUtil.ToastshowShort(activity, "网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    UIUtil.ToastshowShort(activity, "无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
            }
        };
    }

    public static void stopLocationService() {
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
    }

}
