package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.ScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/10/18.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeTest extends Activity {
    List<View> listViews;
    ViewPager pager;
    LocalActivityManager manager;
    @InjectView(R.id.rb_test_guide)
    RadioButton rbTestGuide;
    @InjectView(R.id.rb_test_question)
    RadioButton rbTestQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_test);
        ButterKnife.inject(this);
        listViews  = new ArrayList<View>();;
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);
        initPager();
    }


    @OnClick({R.id.rb_test_guide, R.id.rb_test_question})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_test_guide:
                pager.setCurrentItem(0);
                break;
            case R.id.rb_test_question:
                pager.setCurrentItem(1);
                break;
        }
    }

    private void initPager() {
        Intent i1 = new Intent(ThemeTest.this, ThemeTestGuide.class);
        listViews.add(getView("T1Activity", i1));
        Intent i2 = new Intent(ThemeTest.this, ThemeTestQuestion.class);
        listViews.add(getView("T2Activity", i2));

        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(new MyPageAdapter(listViews));
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rbTestGuide.setChecked(true);
                        break;
                    case 1:
                        rbTestQuestion.setChecked(true);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}

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
        public void finishUpdate(View arg0) {
        }

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
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }
    }
}
