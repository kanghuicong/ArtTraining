package com.example.kk.arttraining.ui.homePage.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.kk.arttraining.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/12/6.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeTeacher extends FragmentActivity {

    @InjectView(R.id.tabs)
    TabLayout tabs;
    @InjectView(R.id.viewPager)
    ViewPager viewPager;
    private List<String> mTitleList = new ArrayList<>();
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_teacher);
        ButterKnife.inject(this);

        type = getIntent().getStringExtra("type");
        initView();
    }

    private void initView() {

        if (!type.equals("art")) {
            mTitleList.add("全部");
            mTitleList.add("声乐");
            mTitleList.add("器乐");
            mTitleList.add("舞蹈");
            mTitleList.add("表演");
            mTitleList.add("编导");
            mTitleList.add("书画");
        }else {
            mTitleList.add("全部");
            mTitleList.add("国内名师");
            mTitleList.add("海外华人艺术家");
            mTitleList.add("国际名师");
        }


        for (int n = 0; n < mTitleList.size(); n++) {
            tabs.addTab(tabs.newTab().setText(mTitleList.get(n)));
        }

        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(),mTitleList);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来,viewpager滑动与table一起切换
        tabs.setTabsFromPagerAdapter(adapter);
    }


    public class MainPagerAdapter extends FragmentPagerAdapter {
        private List<String> mTitleList;

        public MainPagerAdapter(FragmentManager fm, List<String> mTitleList) {
            super(fm);
            this.mTitleList = mTitleList;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            if (position==0) {
                fragment = new ThemeTeacherFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type", type);
                bundle.putString("major", "");
                fragment.setArguments(bundle);
            }else {
                fragment = new ThemeTeacherFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type", type);
                bundle.putString("major", mTitleList.get(position));
                fragment.setArguments(bundle);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return mTitleList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }

    }
}
