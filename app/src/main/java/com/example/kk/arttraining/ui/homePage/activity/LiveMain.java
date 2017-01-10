package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.adapter.LiveAdapter;
import com.example.kk.arttraining.utils.TitleBack;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2017/1/7.
 * QQ邮箱:515849594@qq.com
 */
public class LiveMain extends Activity {


    LiveAdapter liveAdapter;
    @InjectView(R.id.gv_live_list)
    GridView gvLiveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_live_list);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this, "直播");

        liveAdapter = new LiveAdapter(this);
        gvLiveList.setAdapter(liveAdapter);

    }
}
