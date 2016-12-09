package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.NoPreloadViewPager;
import com.example.kk.arttraining.ui.homePage.function.institution.ThemeInstitutionAll;
import com.example.kk.arttraining.ui.homePage.function.teacher.ThemeTeacherFragment;
import com.example.kk.arttraining.utils.TitleBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/10/18.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeInstitution extends FragmentActivity {


    LocalActivityManager manager;

    @InjectView(R.id.iv_advertisement)
    ImageView ivAdvertisement;
    @InjectView(R.id.view_splitter3)
    View viewSplitter3;
    @InjectView(R.id.rb_institution_all)
    RadioButton rbInstitutionAll;
    @InjectView(R.id.rb_institution_1)
    RadioButton rbInstitution1;
    @InjectView(R.id.rb_institution_2)
    RadioButton rbInstitution2;
    @InjectView(R.id.rb_institution_3)
    RadioButton rbInstitution3;
    @InjectView(R.id.rb_institution_4)
    RadioButton rbInstitution4;
    @InjectView(R.id.rb_institution_more)
    RadioButton rbInstitutionMore;
    @InjectView(R.id.vp_institution_list)
    NoPreloadViewPager vpInstitutionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_institution);
        ButterKnife.inject(this);
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);

        TitleBack.SearchBackActivity(this,"机构",R.mipmap.icon_search_white,"institution");

        initPager();
    }

    @OnClick({R.id.rb_institution_all, R.id.rb_institution_1, R.id.rb_institution_2, R.id.rb_institution_3, R.id.rb_institution_4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_institution_all:
                vpInstitutionList.setCurrentItem(0);
                break;
            case R.id.rb_institution_1:
                vpInstitutionList.setCurrentItem(1);
                break;
            case R.id.rb_institution_2:
                vpInstitutionList.setCurrentItem(2);
                break;
            case R.id.rb_institution_3:
                vpInstitutionList.setCurrentItem(3);
                break;
            case R.id.rb_institution_4:
                vpInstitutionList.setCurrentItem(4);
                break;
        }
    }

    private void initPager() {

        vpInstitutionList.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
        vpInstitutionList.setOnPageChangeListener(new NoPreloadViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rbInstitutionAll.setChecked(true);
                        break;
                    case 1:
                        rbInstitution1.setChecked(true);
                        break;
                    case 2:
                        rbInstitution2.setChecked(true);
                        break;
                    case 3:
                        rbInstitution3.setChecked(true);
                        break;
                    case 4:
                        rbInstitution4.setChecked(true);
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

    private class MyPageAdapter extends FragmentPagerAdapter {

        private MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new ThemeInstitutionAll();
                    Bundle bundle0 = new Bundle();
                    bundle0.putString("type", "");
                    fragment.setArguments(bundle0);
                    break;
                case 1:
                    fragment = new ThemeInstitutionAll();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("type", "江西");
                    fragment.setArguments(bundle1);
                    break;
                case 2:
                    fragment = new ThemeInstitutionAll();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("type", "湖北");
                    fragment.setArguments(bundle2);
                    break;
                case 3:
                    fragment = new ThemeInstitutionAll();
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("type", "广东");
                    fragment.setArguments(bundle3);
                    break;
                case 4:
                    fragment = new ThemeInstitutionAll();
                    Bundle bundle4 = new Bundle();
                    bundle4.putString("type", "湖南");
                    fragment.setArguments(bundle4);
                    break;
            }
            return fragment;
        }


        @Override
        public int getCount() {
            return 5;
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
