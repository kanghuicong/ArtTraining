package com.example.kk.arttraining.ui.discover.view;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.MyGridView;
import com.example.kk.arttraining.custom.view.MyPageAdapter;
import com.example.kk.arttraining.custom.view.NoScrollViewPager;
import com.example.kk.arttraining.ui.homePage.activity.HomePageMain;
import com.example.kk.arttraining.ui.me.MeMainActivity;
import com.example.kk.arttraining.ui.school.view.SchoolMain;
import com.example.kk.arttraining.ui.valuation.activity.ValuationMain;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/9/19.
 * QQ邮箱:515849594@qq.com
 */
public class DiscoverMain extends Activity {
    @InjectView(R.id.iv_title_back)
    ImageView iv_back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_main);
        initView();
    }

    void initView() {
        ButterKnife.inject(this);
        iv_back.setVisibility(View.GONE);
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
