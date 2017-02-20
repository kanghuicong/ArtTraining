package com.example.kk.arttraining.ui.homePage.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.utils.TitleBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2017/1/12.
 * QQ邮箱:515849594@qq.com
 */
public class InfoAll extends FragmentActivity {

    @InjectView(R.id.tabs)
    TabLayout tabs;
    @InjectView(R.id.viewPager)
    ViewPager viewPager;
    private List<String> mTitleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_info_list_all);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this, "更多资讯");
        initView();
    }

    private void initView() {
        mTitleList.add("综合资讯");
        mTitleList.add("招生简章");
        mTitleList.add("校考");
        mTitleList.add("统考");
        mTitleList.add("政策");
        mTitleList.add("文化");
        mTitleList.add("留学");
        mTitleList.add("艺术");

        for (int n = 0; n < mTitleList.size(); n++) {
            tabs.addTab(tabs.newTab().setText(mTitleList.get(n)));
        }

        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), mTitleList);
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
            fragment = new InfoFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type", mTitleList.get(position));
            fragment.setArguments(bundle);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            finish();
        }
        return true;
    }
}
