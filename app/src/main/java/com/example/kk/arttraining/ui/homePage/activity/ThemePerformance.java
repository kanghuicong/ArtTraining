package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.adapter.PerformanceAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/10/18.
 * QQ邮箱:515849594@qq.com
 */
public class ThemePerformance extends Activity {
    @InjectView(R.id.lv_performance)
    ListView lvPerformance;

    PerformanceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_performance);
        ButterKnife.inject(this);

        adapter = new PerformanceAdapter(this);
        lvPerformance.setAdapter(adapter);
    }
}
