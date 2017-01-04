package com.example.kk.arttraining.download.updateapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.UIUtil;

import java.security.PublicKey;
import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/30 16:39
 * 说明:检测更新app
 */
public class UpdateAppUtils {
    Context context;


    public UpdateAppUtils(Context context){
        this.context=context;
    }

    public void showDownloadSetting() {
        String packageName = "com.android.providers.downloads";
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        if (intentAvailable(intent)) {
            context.startActivity(intent);
        }
    }

    public boolean intentAvailable(Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }


    public boolean canDownloadState() {
        try {
            int state = context.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");

            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void download(String url) {
        if (!canDownloadState()) {
            Toast.makeText(context, "下载服务不用,请您启用", Toast.LENGTH_SHORT).show();
            showDownloadSetting();
            return;
        }
        ApkUpdateUtils.download(context, url, context.getResources().getString(R.string.app_name));
    }

    //获取app版本号
    public int getVersionCode() {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo("com.example.kk.arttraining", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
    //获取app版本名称
    public String getVersionName() {
        String versionName = null;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionName = context.getPackageManager().getPackageInfo("com.example.kk.arttraining", 0).versionName;
            int versionCode = context.getPackageManager().getPackageInfo("com.example.kk.arttraining", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        UIUtil.showLog("versionName", versionName);
        return versionName;

    }
}
