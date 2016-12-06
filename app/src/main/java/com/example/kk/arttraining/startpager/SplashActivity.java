package com.example.kk.arttraining.startpager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import com.example.kk.arttraining.MainActivity;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.function.homepage.Location;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.GetSDKVersion;
import com.example.kk.arttraining.utils.PreferencesUtils;
import com.example.kk.arttraining.utils.ToolKits;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * 作者：wschenyongyin on 2016/9/22 16:10
 * 说明:启动页
 */
public class SplashActivity extends Activity {

    public static final String IS_FIRST = "is_first";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(SplashActivity.this, R.layout.activity_splash, null);
        context = getApplicationContext();
        Config.ACCESS_TOKEN = PreferencesUtils.get(getApplicationContext(), "access_token", "").toString();
        Config.UID = (int)PreferencesUtils.get(getApplicationContext(), "uid", 8);
        Config.User_Id= PreferencesUtils.get(getApplicationContext(), "user_code", "").toString();
        Config.USER_TITLE= PreferencesUtils.get(getApplicationContext(), "user_title", "").toString();
        Config.CITY = PreferencesUtils.get(getApplicationContext(), "province", "").toString();
        UIUtil.showLog("ACCESS_TOKEN------>",Config.ACCESS_TOKEN );
        UIUtil.showLog("UID-->", Config.UID +"");

        setJpushTag("13155822449");

        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(3000);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                skip();
            }
        });
        view.setAnimation(animation);
        setContentView(view);
    }

    private void skip() {

        if (GetSDKVersion.getAndroidSDKVersion() >= 21) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(SplashActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        001);
            } else {
                enty();
                Config.PermissionsState = 1;
            }
        } else {
            Config.PermissionsState = 1;
            enty();
        }
    }

    private void enty() {
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (!ToolKits
                        .fetchBooble(SplashActivity.this, IS_FIRST, false)) {
                    startActivity(new Intent(SplashActivity.this,
                            GuideActivity.class));
                    ToolKits.putBooble(SplashActivity.this, IS_FIRST, true);
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
                ToolKits.putBooble(SplashActivity.this, IS_FIRST, true);
                return true;
            }
        }).sendEmptyMessageDelayed(0, 0);
    }

    //权限获取回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 001) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1] == PackageManager.PERMISSION_GRANTED&&grantResults[2] == PackageManager.PERMISSION_GRANTED&&grantResults[3] == PackageManager.PERMISSION_GRANTED&&grantResults[4] == PackageManager.PERMISSION_GRANTED&&grantResults[5] == PackageManager.PERMISSION_GRANTED&&grantResults[6] == PackageManager.PERMISSION_GRANTED) {

                enty();
                Config.PermissionsState = 1;
            } else {
                Toast.makeText(SplashActivity.this, "获取权限失败,部分功能将无法使用", Toast.LENGTH_LONG).show();
                enty();
            }
        }
    }

    public void setJpushTag(String user_code) {
        mHandler2.sendMessage(mHandler2.obtainMessage(10001, user_code));
    }

    //设置极光推送的别名
    private final Handler mHandler2 = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10001:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
            }
        }
    };

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            UIUtil.showLog("设置jpush别名---》",code+"");
            String logs;
            switch (code) {
                case 0:
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    UIUtil.showLog("设置别名成功------->","true");
                    break;
                case 6002:
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler2.sendMessageDelayed(mHandler2.obtainMessage(10001, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;

            }
        }

    };
}
