package com.example.kk.arttraining;

import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import com.example.kk.arttraining.ui.discover.activity.Discover_Main;
import com.example.kk.arttraining.ui.homePage.activity.HomePage_Main;
import com.example.kk.arttraining.ui.me.MeMainActivity;
import com.example.kk.arttraining.ui.valuation.activity.Valuation_Mian;
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
    }

    private void initTabHost() {

        tabHost = getTabHost();
        tabHost.setup(manager);
        context = MainActivity.this;

        Intent i1 = new Intent(context, HomePage_Main.class);
        listViews.add(getView("T1Activity", i1));
        Intent i2 = new Intent(context, Valuation_Mian.class);
        listViews.add(getView("T2Activity", i2));
        Intent i3 = new Intent(context, Discover_Main.class);
        listViews.add(getView("T3Activity", i3));
        Intent i4 = new Intent(context, MeMainActivity.class);
        listViews.add(getView("T3Activity", i4));

        tabHost.addTab(tabHost.newTabSpec("A").setIndicator("A").setContent(i1));
        tabHost.addTab(tabHost.newTabSpec("B").setIndicator("B").setContent(i2));
        tabHost.addTab(tabHost.newTabSpec("C").setIndicator("C").setContent(i3));
        tabHost.addTab(tabHost.newTabSpec("D").setIndicator("D").setContent(i4));
    }
    /**
     * 鍒濆鍖朧iewPager
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
                        break;
                    case 1:
                        rbValuation.setChecked(true);
                        break;
                    case 2:
                        rbDiscover.setChecked(true);
                        break;
                    case 3:
                        rbMe.setChecked(true);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();
    }

    private class MyPageAdapter extends PagerAdapter {

        private List<View> list;

        private MyPageAdapter(List<View> list) {
            this.list = list;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object arg2) {
            ViewPager pViewPager = ((ViewPager) view);
            pViewPager.removeView(list.get(position));
        }

        @Override
        public void finishUpdate(View arg0) {}

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            ViewPager pViewPager = ((ViewPager) view);
            pViewPager.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {}

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {}

    }

    @OnClick({R.id.rb_homepage, R.id.rb_valuation, R.id.rb_discover, R.id.rb_me})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_homepage:
                pager.setCurrentItem(0);
                break;
            case R.id.rb_valuation:
                pager.setCurrentItem(1);
                break;
            case R.id.rb_discover:
                pager.setCurrentItem(2);
                break;
            case R.id.rb_me:
                pager.setCurrentItem(3);
                break;
        }
    }
}
