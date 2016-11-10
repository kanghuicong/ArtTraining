package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.adapter.PerformanceAdapter;
import com.example.kk.arttraining.utils.TitleBack;

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
        TitleBack.TitleBackActivity(this,"商演");

        adapter = new PerformanceAdapter(this);
        lvPerformance.setAdapter(adapter);
        lvPerformance.setOnItemClickListener(new PerformanceItemClick());
    }

    private class PerformanceItemClick implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(ThemePerformance.this, ThemePerformanceContent.class);
            startActivity(intent);
        }
    }
}
