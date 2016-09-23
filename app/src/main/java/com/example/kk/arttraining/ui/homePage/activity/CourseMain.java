package com.example.kk.arttraining.ui.homePage.activity;

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
import android.widget.RadioButton;
import android.widget.TabHost;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.customview.MyPageAdapter;
import com.example.kk.arttraining.ui.discover.activity.DiscoverMain;
import com.example.kk.arttraining.ui.valuation.activity.ValuationMian;
import com.example.kk.arttraining.utils.TitleBack;

import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/9/22.
 * QQ邮箱:515849594@qq.com
 */
public class CourseMain extends TabActivity {

    List<View> mlist;
    Context context;
    LocalActivityManager manager;
    TabHost tabHost;
    ViewPager pager;
    @InjectView(R.id.rb_course_content)
    RadioButton rbCourseContent;
    @InjectView(R.id.rb_course_directory)
    RadioButton rbCourseDirectory;
    @InjectView(R.id.rb_course_relate)
    RadioButton rbCourseRelate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.homepage_course_into);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this,"课程详情");
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);
        mlist = new ArrayList<View>();
        mlist = new ArrayList<View>();

        initTabHost();
        initPage();
    }

    private void initTabHost() {

        tabHost = getTabHost();
        tabHost.setup(manager);
        context = CourseMain.this;

        Intent i1 = new Intent(context, CourseContent.class);
        mlist.add(getView("T1Activity", i1));
        Intent i2 = new Intent(context, ValuationMian.class);
        mlist.add(getView("T2Activity", i2));
        Intent i3 = new Intent(context, DiscoverMain.class);
        mlist.add(getView("T3Activity", i3));

        tabHost.addTab(tabHost.newTabSpec("A").setIndicator("A").setContent(i1));
        tabHost.addTab(tabHost.newTabSpec("B").setIndicator("B").setContent(i2));
        tabHost.addTab(tabHost.newTabSpec("C").setIndicator("C").setContent(i3));
    }

    private void initPage() {
        pager = (ViewPager) findViewById(R.id.course_viewpager);
        pager.setAdapter(new MyPageAdapter(mlist));
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rbCourseContent.setChecked(true);
                        break;
                    case 1:
                        rbCourseDirectory.setChecked(true);
                        break;
                    case 2:
                        rbCourseRelate.setChecked(true);
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}
            @Override
            public void onPageScrollStateChanged(int arg0) {}
        });
    }

    private View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();
    }

    @OnClick({R.id.rb_course_content, R.id.rb_course_directory, R.id.rb_course_relate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_course_content:
                pager.setCurrentItem(0);
                break;
            case R.id.rb_course_directory:
                pager.setCurrentItem(1);
                break;
            case R.id.rb_course_relate:
                pager.setCurrentItem(2);
                break;
        }
    }
}

