package com.example.kk.arttraining.customview;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by kanghuicong on 2016/9/22.
 * QQ邮箱:515849594@qq.com
 */
public class MyPageAdapter extends PagerAdapter {

    private List<View> list;
    public MyPageAdapter(List<View> list) {
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
