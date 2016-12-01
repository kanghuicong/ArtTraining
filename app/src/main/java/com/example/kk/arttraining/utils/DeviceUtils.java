package com.example.kk.arttraining.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.kk.arttraining.bean.AppInfoBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：wschenyongyin on 2016/10/17 09:47
 * 说明:获取手机信息
 */
public class DeviceUtils {


    /**
     * 获取应用程序的IMEI号
     */
    public static String getIMEI(Context context) {
        if (context == null) {
        }
        TelephonyManager telecomManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telecomManager.getDeviceId();
        return imei;
    }

    /**
     * 获取设备的系统版本号
     */
    public static int getDeviceSDK() {
        int sdk = android.os.Build.VERSION.SDK_INT;
        return sdk;
    }

    /**
     * 获取设备的型号
     */
    public static String getDeviceName() {
        String model = android.os.Build.MODEL;
        return model;
    }

    /**
     * 获取手机品牌
     *
     * @return
     */
    public static String getPhoneBrand() {
        return android.os.Build.BRAND;
    }

    //获取手机安装的app
    private ArrayList<AppInfoBean> getInstalledApps(boolean getSysPackages, Context context) {
        ArrayList<AppInfoBean> appList = new ArrayList<AppInfoBean>();
        List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue;
            }
            AppInfoBean appInfo = new AppInfoBean();
            appInfo.setAppName(p.applicationInfo.loadLabel(context.getPackageManager())
                    .toString());
            appInfo.setpName(p.packageName);
//            appInfo.setIcon(p.applicationInfo.loadIcon(context.getPackageManager()));
            appInfo.setVersion_name(p.versionName);
            appInfo.setVersion_no(p.versionCode);


            appList.add(appInfo);
        }
        return appList;
    }

    //获取app包名
    private void listPackages(Context context) {
        ArrayList<AppInfoBean> apps = getInstalledApps(false, context);
        Map<String, Object> item;
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        final int max = apps.size();
        for (int i = 0; i < max; i++) {
            item = new HashMap<String, Object>();
            item.put("appname", apps.get(i).getAppName());
            item.put("pname", apps.get(i).getpName());
            data.add(item);
        }
    }
}

