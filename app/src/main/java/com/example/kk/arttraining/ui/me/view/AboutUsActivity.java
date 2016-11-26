package com.example.kk.arttraining.ui.me.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/11/17 15:12
 * 说明:关于我们
 */
public class AboutUsActivity extends BaseActivity {


    @InjectView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @InjectView(R.id.tv_title_bar)
    TextView tvTitleBar;
    @InjectView(R.id.update_app)
    Button updateApp;
    @InjectView(R.id.tv_user_agreement)
    TextView tvUserAgreement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_about_us);
        ButterKnife.inject(this);
        tvTitleBar.setText("关于我们");
    }

    @Override
    public void init() {

    }

    @OnClick({R.id.iv_title_back, R.id.update_app, R.id.tv_user_agreement})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.update_app:

                break;
            case R.id.tv_user_agreement:

                break;
        }
    }

}
