package com.example.kk.arttraining.ui.course.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/12/23 17:04
 * 说明:
 */
public class IArtFindTabAdapter extends FragmentPagerAdapter {

    private List<Fragment> list_fragment;                         //fragment列表
    private List<String> list_Title;                              //tab名的列表
    private String  course_id;
    private String course_name;


    public IArtFindTabAdapter(FragmentManager fm, List<Fragment> list_fragment, List<String> list_Title, String course_id) {
        super(fm);
        this.list_fragment = list_fragment;
        this.list_Title = list_Title;
        this.course_id = course_id;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("course_id", course_id);
        Fragment fragment = list_fragment.get(position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return list_Title.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {

        return list_Title.get(position % list_Title.size());
    }
}