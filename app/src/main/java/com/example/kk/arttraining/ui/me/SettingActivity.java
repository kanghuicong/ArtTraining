package com.example.kk.arttraining.ui.me;

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
    @InjectView(R.id.ll_location)
    LinearLayout ll_location;
    @InjectView(R.id.ll_cleanData)
    LinearLayout ll_cleanData;
    @InjectView(R.id.ll_download)
    LinearLayout ll_download;
    @InjectView(R.id.ll_about)
    LinearLayout ll_about;
    @InjectView(R.id.title_back)
    ImageView img_back;
    @InjectView(R.id.title_barr)
    TextView title_barr;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_setting_activity);
        context=getApplicationContext();

        init();
    }

    @Override
    public void init() {

        ButterKnife.inject(this);

        title_barr.setText("设置");
        btn_logout.setOnClickListener(this);
        ll_wifi.setOnClickListener(this);
        ll_location.setOnClickListener(this);
        ll_cleanData.setOnClickListener(this);
        ll_download.setOnClickListener(this);
        ll_about.setOnClickListener(this);
        img_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:

                break;
            case R.id.ll_wifi:

                break;
            case R.id.ll_location:

                break;
            case R.id.ll_cleanData:

                break;
            case R.id.ll_download:

                break;
            case R.id.ll_about:
                startActivity(new Intent(context,AboutActivity.class));

                break;
            case R.id.title_back:
                finish();
                break;

        }

    }


}
