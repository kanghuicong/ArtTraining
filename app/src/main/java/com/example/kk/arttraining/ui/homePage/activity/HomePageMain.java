package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.customview.MyListView;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.TopicAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.TrainingAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/10/17.
 * QQ邮箱:515849594@qq.com
 */
public class HomePageMain extends Activity {
    @InjectView(R.id.ll_homepage_search)
    LinearLayout llHomepageSearch;
    @InjectView(R.id.iv_homepage_message)
    ImageView ivHomepageMessage;

    @InjectView(R.id.ll_homepage_container)
    LinearLayout llHomepageContainer;
    @InjectView(R.id.lv_homepage_dynamic)
    MyListView lvHomepageDynamic;

    View training_view;
    TextView training_title;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_main);
        ButterKnife.inject(this);

        FindHeader(lvHomepageDynamic,"精选动态");

        DynamicAdapter dynamicadapter = new DynamicAdapter(this);
        lvHomepageDynamic.setAdapter(dynamicadapter);
    }

    private void FindHeader(ListView lv,String tv) {
        training_view = View.inflate(this, R.layout.homepage_title, null);
        training_title = (TextView)training_view.findViewById(R.id.tv_homepage_title);
        training_title.setText(tv);
        lv.addHeaderView(training_view);
    }
}
