package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.function.teacher.ThemeTeacherAll;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.NetUtils;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/11/22.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeTeacher extends Activity {

    List<View> list_Views = new ArrayList<View>();
    @InjectView(R.id.tv_no_wifi)
    TextView tvNoWifi;
    private List<String> mTitleList = new ArrayList<>();
    LocalActivityManager manager;
    @InjectView(R.id.vp_institution_list)
    ViewPager vpInstitutionList;
    @InjectView(R.id.tabs)
    TabLayout mTabLayout;
    boolean positionValue[]={false,false,false,false,false,false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_teacher_major);

        ButterKnife.inject(this);
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);

        if (NetUtils.isConnected(this)) {
            tvNoWifi.setVisibility(View.GONE);
        } else {
            tvNoWifi.setVisibility(View.VISIBLE);
        }

        initPager();
    }

    private void initPager() {
        Intent i  = new Intent(ThemeTeacher.this, ThemeTeacherAll.class);
        i.putExtra("major", "");
        list_Views.add(getView("TActivity", i));
        Intent i1 = new Intent(ThemeTeacher.this, ThemeTeacherAll.class);
        i1.putExtra("major", "声乐");
        list_Views.add(getView("T1Activity", i1));
        Intent i2 = new Intent(ThemeTeacher.this, ThemeTeacherAll.class);
        i2.putExtra("major", "器乐");
        list_Views.add(getView("T2Activity", i2));
        Intent i3 = new Intent(ThemeTeacher.this, ThemeTeacherAll.class);
        i3.putExtra("major", "舞蹈");
        list_Views.add(getView("T3Activity", i3));
        Intent i4 = new Intent(ThemeTeacher.this, ThemeTeacherAll.class);
        i4.putExtra("major", "表演");
        list_Views.add(getView("T4Activity", i4));
        Intent i5 = new Intent(ThemeTeacher.this, ThemeTeacherAll.class);
        i5.putExtra("major", "编导");
        list_Views.add(getView("T5Activity", i5));
        Intent i6 = new Intent(ThemeTeacher.this, ThemeTeacherAll.class);
        i6.putExtra("major", "书画");
        list_Views.add(getView("T6Activity", i6));

        mTitleList.add("全部");
        mTitleList.add("声乐");
        mTitleList.add("器乐");
        mTitleList.add("舞蹈");
        mTitleList.add("表演");
        mTitleList.add("编导");
        mTitleList.add("书画");

        for (int n = 0; n < mTitleList.size(); n++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(n)));

        }

        MyPagerAdapter mAdapter = new MyPagerAdapter(list_Views);

        vpInstitutionList.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(vpInstitutionList);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
    }

    @OnClick({R.id.iv_teacher_back, R.id.rb_experts, R.id.rb_teacher, R.id.rb_intelligent, R.id.iv_teacher_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_teacher_back:
                finish();
                break;
            case R.id.iv_teacher_search:
                Intent intent_school = new Intent(this, SearchMain.class);
                intent_school.putExtra("type", "teacher");
                startActivity(intent_school);
                break;

            case R.id.rb_experts:

                vpInstitutionList.setCurrentItem(0);
                break;
            case R.id.rb_teacher:

                vpInstitutionList.setCurrentItem(0);
                break;
            case R.id.rb_intelligent:

                vpInstitutionList.setCurrentItem(0);
                break;
        }
    }


    //ViewPager适配器
    class MyPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        public MyPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();//页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方推荐写法
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
        }

    }

    private View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();
    }

}