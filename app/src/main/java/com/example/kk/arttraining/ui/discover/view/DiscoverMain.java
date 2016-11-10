package com.example.kk.arttraining.ui.discover.view;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.activity.ThemeTestGuide;
import com.example.kk.arttraining.ui.homePage.activity.ThemeTestQuestion;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/9/19.
 * QQ邮箱:515849594@qq.com
 */
public class DiscoverMain extends Fragment {
    Activity activity;
    Context context;
    View view_discover;
    List<View> listViews;
    LocalActivityManager manager;
    @InjectView(R.id.vp_discover)
    ViewPager vpDiscover;
    @InjectView(R.id.rb_discover_circle)
    RadioButton rbDiscoverCircle;
    @InjectView(R.id.rb_discover_course)
    RadioButton rbDiscoverCourse;
    @InjectView(R.id.rb_discover_activity)
    RadioButton rbDiscoverActivity;
    @InjectView(R.id.rb_discover_shopping)
    RadioButton rbDiscoverShopping;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        activity = getActivity();
        context = getActivity();
        if (view_discover == null) {
            view_discover = View.inflate(activity, R.layout.discover_main, null);
            ButterKnife.inject(this, view_discover);
            listViews = new ArrayList<>();
            manager = new LocalActivityManager(activity, true);
            manager.dispatchCreate(savedInstanceState);
            initPager();
        }
        ViewGroup parent = (ViewGroup) view_discover.getParent();
        if (parent != null) {
            parent.removeView(view_discover);
        }
        ButterKnife.inject(this, view_discover);
        return view_discover;
    }

    private void initPager() {
        Intent i1 = new Intent(activity, DiscoverCircle.class);
        listViews.add(getView("T1Activity", i1));
        Intent i2 = new Intent(activity, DiscoverCourse.class);
        listViews.add(getView("T2Activity", i2));
        Intent i3 = new Intent(activity, DiscoverActivity.class);
        listViews.add(getView("T3Activity", i3));
        Intent i4 = new Intent(activity, DiscoverShopping.class);
        listViews.add(getView("T4Activity", i4));

        vpDiscover.setAdapter(new MyPageAdapter(listViews));
        vpDiscover.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rbDiscoverCircle.setChecked(true);
                        break;
                    case 1:
                        rbDiscoverCourse.setChecked(true);
                        break;
                    case 2:
                        rbDiscoverActivity.setChecked(true);
                        break;
                    case 3:
                        rbDiscoverShopping.setChecked(true);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
