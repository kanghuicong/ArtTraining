package com.example.kk.arttraining;

import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.example.kk.arttraining.custom.view.MyPageAdapter;
import com.example.kk.arttraining.custom.view.NoScrollViewPager;
import com.example.kk.arttraining.ui.discover.view.DiscoverMain;
import com.example.kk.arttraining.ui.homePage.activity.HomePageMain;
import com.example.kk.arttraining.ui.me.MeMainActivity;
import com.example.kk.arttraining.ui.school.view.SchoolMain;
import com.example.kk.arttraining.ui.valuation.activity.ValuationMain;
import com.yixia.camera.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/9/19.
 * QQ邮箱:515849594@qq.com
 */

public class MainActivity extends TabActivity {

    @InjectView(android.R.id.tabs)
    TabWidget tabs;
    @InjectView(R.id.rb_homepage)
    RadioButton rbHomepage;
    @InjectView(R.id.rb_valuation)
    RadioButton rbValuation;
    @InjectView(R.id.rb_discover)
    RadioButton rbDiscover;
    @InjectView(R.id.rb_me)
    RadioButton rbMe;
    @InjectView(R.id.radioGroup)
    RadioGroup radioGroup;
    @InjectView(android.R.id.tabcontent)
    FrameLayout tabContent;
    @InjectView(R.id.rb_school)
    RadioButton rbSchool;
    @InjectView(android.R.id.tabhost)
    TabHost tabhost;


    List<View> listViews;
    Context context;
    LocalActivityManager manager;
    TabHost tabHost;
    NoScrollViewPager pager;

    private PopupWindow window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
//        StatusBarUtil.setTransparent(this);
//        StatusBarUtil.setColor(this,this.getResources().getColor(R.color.blue_overlay));
//        StatusBarCompat.compat(this,this.getResources().getColor(R.color.blue_overlay));
        ButterKnife.inject(this);
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);
//        manager.dispatchResume();
//        manager.dispatchPause(true);
        listViews = new ArrayList<View>();
        initTabHost();
        initPage();
        getTextColor();
    }

    private void getTextColor() {
        initColor(rbHomepage);
        initColor(rbSchool);
        initColor(rbDiscover);
        initColor(rbMe);
    }

    private void initColor(RadioButton rb) {
        if (rb.isChecked()) {
            rb.setTextColor(this.getResources().getColor(R.color.title_color));
        } else {
            rb.setTextColor(this.getResources().getColor(R.color.rb_text));
        }
    }

    private void initTabHost() {

        tabHost = getTabHost();
        tabHost.setup(manager);
        context = MainActivity.this;

        Intent i1 = new Intent(context, HomePageMain.class);
        listViews.add(getView("T1Activity", i1));
        Intent i2 = new Intent(context, ValuationMain.class);
        listViews.add(getView("T2Activity", i2));
        Intent i3 = new Intent(context, SchoolMain.class);
        listViews.add(getView("T3Activity", i3));
        Intent i4 = new Intent(context, DiscoverMain.class);
        listViews.add(getView("T4Activity", i4));
        Intent i5 = new Intent(context, MeMainActivity.class);
        listViews.add(getView("T5Activity", i5));


        tabHost.addTab(tabHost.newTabSpec("A").setIndicator("A").setContent(i1));
        tabHost.addTab(tabHost.newTabSpec("B").setIndicator("B").setContent(i2));
        tabHost.addTab(tabHost.newTabSpec("C").setIndicator("C").setContent(i3));
        tabHost.addTab(tabHost.newTabSpec("D").setIndicator("D").setContent(i4));
        tabHost.addTab(tabHost.newTabSpec("E").setIndicator("E").setContent(i5));
    }

    /**
     *
     */
    private void initPage() {
        pager = (NoScrollViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(new MyPageAdapter(listViews));
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rbHomepage.setChecked(true);
                        getTextColor();
                        break;
                    case 1:
                        rbValuation.setChecked(true);
                        getTextColor();
                        break;
                    case 2:
                        rbSchool.setChecked(true);
                        getTextColor();
                        break;
                    case 3:
                        rbDiscover.setChecked(true);
                        getTextColor();
                        break;
                    case 4:
                        rbMe.setChecked(true);
                        getTextColor();
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

    @OnClick({R.id.rb_homepage, R.id.rb_valuation, R.id.rb_school, R.id.rb_discover, R.id.rb_me})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_homepage:
                getTextColor();
                pager.setCurrentItem(0);
                break;
            case R.id.rb_valuation:
//                getTextColor();
//                pager.setCurrentItem(1);
                showPopwindow();
                break;
            case R.id.rb_school:
                getTextColor();
                pager.setCurrentItem(2);
                break;
            case R.id.rb_discover:
                getTextColor();
                pager.setCurrentItem(3);
                break;
            case R.id.rb_me:
                getTextColor();
                pager.setCurrentItem(4);
                break;
        }
    }

    private void showPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) MainActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popwindow_evaluation, null);

        // 得到宽度和高度 getWindow().getDecorView().getWidth()

        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0xa0000000);
//        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(
                MainActivity.this.findViewById(R.id.ll_main),
                Gravity.BOTTOM, 0, 0);


        // 这里检验popWindow里的button是否可以点击
        // popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                System.out.println("popWindow消失");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.dispatchResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.dispatchPause(isFinishing());
    }
}
