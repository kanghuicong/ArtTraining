package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.kk.arttraining.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/10/20.
 * QQ邮箱:515849594@qq.com
 */
public class HeadLinesContent extends Activity {
    @InjectView(R.id.tv_headlines_content)
    TextView tvHeadlinesContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_headlines_content);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        String tv = intent.getStringExtra("headlines");
        tvHeadlinesContent.setText(tv);
    }
}
