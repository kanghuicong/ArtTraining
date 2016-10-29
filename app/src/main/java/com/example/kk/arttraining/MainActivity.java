package com.example.kk.arttraining;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.kk.arttraining.ui.discover.view.DiscoverMain;
import com.example.kk.arttraining.ui.homePage.activity.HomePageMain;
import com.example.kk.arttraining.ui.me.MeMainActivity;
import com.example.kk.arttraining.ui.school.view.SchoolMain;

/**
 * Created by kanghuicong on 2016/9/19.
 * QQ邮箱:515849594@qq.com
 */

public class MainActivity extends FragmentActivity implements OnClickListener {
    public static RadioGroup rgMain;
    private static boolean isExit = false;// 定义一个变量，来标识是否退出
    private RadioButton rb_homepage, rb_discover, rb_valuation, rb_school,rb_me;
    private FragmentManager fm;
    PopupWindow window;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    private HomePageMain homepageFragment;
    private SchoolMain schoolFragment;
    private DiscoverMain discoverFragment;
    private MeMainActivity meFragment;
    private long mExitTime;
    private ConnectivityManager connectivityManager;
    private Fragment fg;    // fg记录当前的Fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        initFragment();
        rgMain = (RadioGroup) findViewById(R.id.radioGroup);
        rgMain.check(R.id.rb_homepage);
        // 进入主页面，初始页面pager为乐跑
        rb_homepage = (RadioButton) findViewById(R.id.rb_homepage);
        rb_school = (RadioButton) findViewById(R.id.rb_school);
        rb_discover = (RadioButton) findViewById(R.id.rb_discover);
        rb_valuation = (RadioButton) findViewById(R.id.rb_valuation);
        rb_me = (RadioButton) findViewById(R.id.rb_me);

        rb_homepage.setOnClickListener(this);
        rb_school.setOnClickListener(this);
        rb_discover.setOnClickListener(this);
        rb_valuation.setOnClickListener(this);
        rb_me.setOnClickListener(this);

        getTextColor();
    }

    private void getTextColor() {
        initColor(rb_homepage);
        initColor(rb_school);
        initColor(rb_discover);
        initColor(rb_me);
    }

    private void initColor(RadioButton rb) {
        if (rb.isChecked()) {
            rb.setTextColor(this.getResources().getColor(R.color.title_color));
        } else {
            rb.setTextColor(this.getResources().getColor(R.color.rb_text));
        }
    }

    private void initFragment() {
        //一开始先初始到lerunFragment
        fm = getSupportFragmentManager();
        homepageFragment = new HomePageMain();
        fm.beginTransaction().replace(R.id.flMain, homepageFragment).addToBackStack(null).commit();

    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = fm.beginTransaction();
        hideAllFragment(transaction);
        switch (v.getId()) {
            case R.id.rb_homepage:
                if (homepageFragment == null) {
                    homepageFragment = new HomePageMain();
                    transaction.add(R.id.flMain, homepageFragment);
                } else {
                    transaction.show(homepageFragment);
                }
                getTextColor();
                break;
            case R.id.rb_school:
                if (schoolFragment == null) {
                    schoolFragment = new SchoolMain();
                    transaction.add(R.id.flMain, schoolFragment);
                } else {
                    transaction.show(schoolFragment);
                }
                getTextColor();
                break;
            case R.id.rb_valuation:
                showPopwindow();
                break;
            case R.id.rb_discover:
                if (discoverFragment == null) {
                    discoverFragment = new DiscoverMain();
                    transaction.add(R.id.flMain, discoverFragment);
                } else {
                    transaction.show(discoverFragment);
                }
                getTextColor();
                break;
            case R.id.rb_me:
                if (meFragment == null) {
                    meFragment = new MeMainActivity();
                    transaction.add(R.id.flMain, meFragment);
                } else {
                    transaction.show(meFragment);
                }
                getTextColor();
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (homepageFragment != null) {
            transaction.hide(homepageFragment);
        }
        if (schoolFragment != null) {
            transaction.hide(schoolFragment);
        }
        if (discoverFragment != null) {
            transaction.hide(discoverFragment);
        }
        if (meFragment != null) {
            transaction.hide(meFragment);
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
}