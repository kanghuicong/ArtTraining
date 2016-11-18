package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.function.institution.ThemeInstitution1;
import com.example.kk.arttraining.ui.homePage.function.institution.ThemeInstitution2;
import com.example.kk.arttraining.ui.homePage.function.institution.ThemeInstitution3;
import com.example.kk.arttraining.ui.homePage.function.institution.ThemeInstitution4;
import com.example.kk.arttraining.ui.homePage.function.institution.ThemeInstitutionAll;
import com.example.kk.arttraining.utils.DialogUtils;
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
public class ThemeInstitution extends Activity {

    List<View> list_Views = new ArrayList<View>();
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
    ViewPager vpInstitutionList;


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
        Intent i = new Intent(ThemeInstitution.this, ThemeInstitutionAll.class);
        list_Views.add(getView("TActivity", i));
        Intent i1 = new Intent(ThemeInstitution.this, ThemeInstitution1.class);
        list_Views.add(getView("T1Activity", i1));
        Intent i2 = new Intent(ThemeInstitution.this, ThemeInstitution2.class);
        list_Views.add(getView("T2Activity", i2));
        Intent i3 = new Intent(ThemeInstitution.this, ThemeInstitution3.class);
        list_Views.add(getView("T3Activity", i3));
        Intent i4 = new Intent(ThemeInstitution.this, ThemeInstitution4.class);
        list_Views.add(getView("T4Activity", i4));

        vpInstitutionList.setAdapter(new MyPageAdapter(list_Views));
        vpInstitutionList.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            finish();
        }
        return true;
    }
}
