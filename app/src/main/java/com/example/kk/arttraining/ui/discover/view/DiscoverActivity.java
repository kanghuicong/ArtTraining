package com.example.kk.arttraining.ui.discover.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.discover.adapter.DiscoverActivityAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/11/9.
 * QQ邮箱:515849594@qq.com
 */
public class DiscoverActivity extends Activity {
    @InjectView(R.id.lv_discover_activity)
    ListView lvDiscoverActivity;

    DiscoverActivityAdapter discoverActivityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_activity);
        ButterKnife.inject(this);

        discoverActivityAdapter = new DiscoverActivityAdapter(this);
        lvDiscoverActivity.setAdapter(discoverActivityAdapter);

    }
}
