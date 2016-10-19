package com.example.kk.arttraining;

import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.example.kk.arttraining.ui.me.MeMainActivity;
import com.example.kk.arttraining.custom.view.MyPageAdapter;
import com.example.kk.arttraining.ui.discover.activity.DiscoverMain;
import com.example.kk.arttraining.ui.homePage.activity.HomePageMain;

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

public class MainActivity extends TabActivity {

    @InjectView(android.R.id.tabs)
    TabWidget tabs;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    @InjectView(R.id.rb_homepage)
    RadioButton rbHomepage;
    @InjectView(R.id.rb_valuation)
    RadioButton rbValuation;
    @InjectView(R.id.rb_discover)
    RadioButton rbDiscover;
    @InjectView(R.id.rb_me)
    RadioButton rbMe;
    @InjectView(R.id.radioGroup)
    RadioGroup radioGroup;
    @InjectView(android.R.id.tabcontent)
    FrameLayout tabContent;

    List<View> listViews;
    Context context;
    LocalActivityManager manager;
    TabHost tabHost;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);
        listViews = new ArrayList<View>();
        listViews = new ArrayList<View>();
        initTabHost();
        initPage();
        getTextColor();
    }

    private void getTextColor() {
        initColor(rbHomepage);
        initColor(rbValuation);
        initColor(rbDiscover);
        initColor(rbMe);
    }

    private void initColor(RadioButton rb) {
        if (rb.isChecked()){
            rb.setTextColor(this.getResources().getColor(R.color.green));
        }else {
            rb.setTextColor(this.getResources().getColor(R.color.ViewLine));
        }
    }

    private void initTabHost() {

        tabHost = getTabHost();
        tabHost.setup(manager);
        context = MainActivity.this;

        Intent i1 = new Intent(context, HomePageMain.class);
        listViews.add(getView("T1Activity", i1));
        Intent i2 = new Intent(context, ValuationMain.class);
        listViews.add(getView("T2Activity", i2));
        Intent i3 = new Intent(context, DiscoverMain.class);
        listViews.add(getView("T3Activity", i3));
        Intent i4 = new Intent(context, MeMainActivity.class);
        listViews.add(getView("T4Activity", i4));

        tabHost.addTab(tabHost.newTabSpec("A").setIndicator("A").setContent(i1));
        tabHost.addTab(tabHost.newTabSpec("B").setIndicator("B").setContent(i2));
        tabHost.addTab(tabHost.newTabSpec("C").setIndicator("C").setContent(i3));
        tabHost.addTab(tabHost.newTabSpec("D").setIndicator("D").setContent(i4));
    }

    /**
     *
     */
    private void initPage() {
        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(new MyPageAdapter(listViews));
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rbHomepage.setChecked(true);
                        getTextColor();
                        break;
                    case 1:
                        rbValuation.setChecked(true);
                        getTextColor();
                        break;
                    case 2:
                        rbDiscover.setChecked(true);
                        getTextColor();
                        break;
                    case 3:
                        rbMe.setChecked(true);
                        getTextColor();
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}
            @Override
            public void onPageScrollStateChanged(int arg0) {}
        });
    }

    private View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();
    }

    @OnClick({R.id.rb_homepage, R.id.rb_valuation, R.id.rb_discover, R.id.rb_me})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_homepage:
                getTextColor();
                pager.setCurrentItem(0);
                break;
            case R.id.rb_valuation:
                getTextColor();
                pager.setCurrentItem(1);
                break;
            case R.id.rb_discover:
                getTextColor();
                pager.setCurrentItem(2);
                break;
            case R.id.rb_me:
                getTextColor();
                pager.setCurrentItem(3);
                break;
        }
    }


}
