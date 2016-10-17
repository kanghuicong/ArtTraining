package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.customview.MyListView;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicAdapter;

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
    @InjectView(R.id.lv_homepage_dynamic)
    ListView lvHomepageDynamic;

    View training_view,authority_view;
    TextView training_title,authority_title;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_main);
        ButterKnife.inject(this);
        FindTitle();

        DynamicAdapter dynamicadapter = new DynamicAdapter(getApplicationContext());
        lvHomepageDynamic.setAdapter(dynamicadapter);

    }

    private void FindTitle() {
        //为listview添加头部
        training_view = View.inflate(this, R.layout.homepage_title, null);
        training_title = (TextView)training_view.findViewById(R.id.tv_homepage_title);
        training_title.setText("精选动态");
        lvHomepageDynamic.addHeaderView(training_view);

        //为测评权威添加标题
        authority_view = (View)findViewById(R.id.layout_authority_title);
        authority_title = (TextView)authority_view.findViewById(R.id.tv_homepage_title);
        authority_title.setText("测评权威");
    }
}
