package com.example.kk.arttraining.prot;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.utils.ActivityManage;
import com.example.kk.arttraining.utils.GetSDKVersion;
import com.jaeger.library.StatusBarUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * 作者：wschenyongyin on 2016/9/21 09:31
 * 说明:
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener {
    public abstract void init();

    @Override
    public abstract void onClick(View v);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加Activity到堆栈
        ActivityManage.getAppManager().addActivity(this);
        if(GetSDKVersion.getAndroidSDKVersion()>19){
            try {
                StatusBarUtil.setColor(this, getResources().getColor(R.color.blue_overlay));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity&从堆栈中移除
        ActivityManage.getAppManager().finishActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
