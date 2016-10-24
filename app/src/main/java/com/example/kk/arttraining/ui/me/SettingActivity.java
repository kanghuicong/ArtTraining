package com.example.kk.arttraining.ui.me;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.utils.PreferencesUtils;
import com.example.kk.arttraining.utils.StringUtils;
import com.example.kk.arttraining.utils.UIUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/9/22 09:25
 * 说明:
 */
public class SettingActivity extends BaseActivity {
    @InjectView(R.id.btn_logout)
    Button btn_logout;
    @InjectView(R.id.ll_wifi)
    LinearLayout ll_wifi;

    @InjectView(R.id.ll_about_update)
    LinearLayout ll_about_update;
    @InjectView(R.id.ll_help)
    LinearLayout ll_help;
    @InjectView(R.id.ll_account_manage)
    LinearLayout ll_account_manage;
    @InjectView(R.id.ll_cleanData)
    LinearLayout ll_cleanData;
    @InjectView(R.id.ll_about)
    LinearLayout ll_aboutUs;
    @InjectView(R.id.title_back)
    ImageView img_back;
    @InjectView(R.id.title_barr)
    TextView title_barr;
    @InjectView(R.id.wifi_setting)
    ImageView wifi_setting;


    private Context context;
    private int WIFI_SETTING_STATE;
    private String FRIST = "first";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_setting_activity);
        context = getApplicationContext();

        init();
    }

    @Override
    public void init() {
        ButterKnife.inject(this);

        Object first = PreferencesUtils.get(SettingActivity.this, FRIST, "");
        //判断用户是不是第一次进入 如果是则将默认的wifi设置为1
        if (!first.equals("yes")) {

            PreferencesUtils.put(SettingActivity.this, FRIST, "yes");
            PreferencesUtils.put(SettingActivity.this, "wifi_setting", 1);
        }
        Object wifi_state = PreferencesUtils.get(SettingActivity.this, "wifi_setting", Activity.MODE_PRIVATE);
        WIFI_SETTING_STATE = StringUtils.toInt(wifi_state);
        if (WIFI_SETTING_STATE == 1) {
            wifi_setting.setImageResource(R.mipmap.ab_on);
        } else {
            wifi_setting.setImageResource(R.mipmap.ab_off);
        }


        title_barr.setText("设置");
        btn_logout.setOnClickListener(this);
        ll_cleanData.setOnClickListener(this);
        ll_aboutUs.setOnClickListener(this);
        img_back.setOnClickListener(this);
        wifi_setting.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //账号管理
            case R.id.ll_account_manage:

                break;
            //帮助与建议
            case R.id.ll_help:

                break;
            //退出账号
            case R.id.btn_logout:

                break;
            //wifi设置
            case R.id.wifi_setting:
                if (WIFI_SETTING_STATE == 1) {
                    PreferencesUtils.put(SettingActivity.this, "wifi_setting", 0);
                    wifi_setting.setImageResource(R.mipmap.ab_off);
                } else {
                    PreferencesUtils.put(SettingActivity.this, "wifi_setting", 1);
                    wifi_setting.setImageResource(R.mipmap.ab_on);
                }

                break;
            //清除信息
            case R.id.ll_cleanData:

                break;
           //关于我们
            case R.id.ll_about:
                startActivity(new Intent(context, AboutActivity.class));

                break;
            case R.id.title_back:
                finish();
                break;

        }

    }


}
