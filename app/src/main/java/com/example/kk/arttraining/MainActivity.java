package com.example.kk.arttraining;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kk.arttraining.ui.discover.view.DiscoverMain;
import com.example.kk.arttraining.ui.homePage.activity.HomePageMain;
import com.example.kk.arttraining.ui.homePage.function.homepage.MainRadioButton;
import com.example.kk.arttraining.ui.me.view.MeMainActivity;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.ui.school.view.SchoolMain;
import com.example.kk.arttraining.ui.valuation.view.ValuationMain;
import com.example.kk.arttraining.utils.Config;
import com.jaeger.library.StatusBarUtil;

/**
 * Created by kanghuicong on 2016/9/19.
 * QQ邮箱:515849594@qq.com
 */

public class MainActivity extends FragmentActivity implements OnClickListener {
    public static RadioGroup rgMain;
    private static boolean isExit = false;// 定义一个变量，来标识是否退出
    private RadioButton rb_homepage, rb_discover, rb_valuation, rb_school, rb_me;
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
    private ConnectivityManager connectivityManager;


    private TextView tv_valuation_music;
    private TextView tv_valuation_dance;
    private TextView tv_valuation_perform;
    private ImageView iv_valuation_colse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        setContentView(R.layout.activity_main);

//        StatusBarUtil.setTransparent(MainActivity.this);
        StatusBarUtil.setColor(MainActivity.this, getResources().getColor(R.color.blue_overlay));
//        StatusBarUtil.setTranslucent(MainActivity.this,255);
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
        getImage();
    }

    private void getTextColor() {
        MainRadioButton.initColor(this, rb_homepage);
        MainRadioButton.initColor(this, rb_school);
        MainRadioButton.initColor(this, rb_discover);
        MainRadioButton.initColor(this, rb_me);
    }

    private void getImage() {
        MainRadioButton.initImage(this, rb_homepage, R.mipmap.rb_homepage_checked, R.mipmap.rb_homepage_normal);
        MainRadioButton.initImage(this, rb_school, R.mipmap.rb_school_checked, R.mipmap.rb_school_normal);
        MainRadioButton.initImage(this, rb_discover, R.mipmap.rb_discover_checked, R.mipmap.rb_discover_normal);
        MainRadioButton.initImage(this, rb_me, R.mipmap.rb_me_checked, R.mipmap.rb_me_normal);
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
                getImage();
                transaction.commit();
                break;
            case R.id.rb_school:
                if (schoolFragment == null) {
                    schoolFragment = new SchoolMain();
                    transaction.add(R.id.flMain, schoolFragment);
                } else {
                    transaction.show(schoolFragment);
                }
                getTextColor();
                getImage();
                transaction.commit();
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
                getImage();
                transaction.commit();
                break;
            case R.id.rb_me:
                if (Config.ACCESS_TOKEN != null&&!Config.ACCESS_TOKEN.equals("")) {
                    if (meFragment == null) {
                        meFragment = new MeMainActivity();
                        transaction.add(R.id.flMain, meFragment);
                    } else {
                        transaction.show(meFragment);
                    }
                    getTextColor();
                    getImage();
                    transaction.commit();
                } else {
                    if (meFragment == null) {
                        meFragment = new MeMainActivity();
                        transaction.add(R.id.flMain, meFragment);
                    } else {
                        transaction.show(meFragment);
                    }
                    getTextColor();
                    getImage();
                    transaction.commit();
                    Intent intent = new Intent(MainActivity.this, UserLoginActivity.class);
                    startActivity(intent);

                }


                break;

            case R.id.popwindow_valuation_music:
                window.dismiss();
                Intent musicIntent = new Intent(MainActivity.this, ValuationMain.class);
                musicIntent.putExtra("type", "音乐");
                startActivity(musicIntent);
                break;

            case R.id.popwindow_valuation_dance:
                window.dismiss();
                Intent danceIntent = new Intent(MainActivity.this, ValuationMain.class);
                danceIntent.putExtra("type", "舞蹈");
                startActivity(danceIntent);
                break;
            case R.id.popwindow_valuation_perform:
                window.dismiss();
                Intent performIntent = new Intent(MainActivity.this, ValuationMain.class);
                performIntent.putExtra("type", "表演");
                startActivity(performIntent);
                break;

            case R.id.popwindow_valuation_colse:
                window.dismiss();
                break;
            default:
                break;
        }
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
//        View view = inflater.inflate(R.layout.popwindow_valuation, null);
        View view = View.inflate(MainActivity.this, R.layout.popwindow_valuation, null);
        tv_valuation_music = (TextView) view.findViewById(R.id.popwindow_valuation_music);
        tv_valuation_dance = (TextView) view.findViewById(R.id.popwindow_valuation_dance);
        tv_valuation_perform = (TextView) view.findViewById(R.id.popwindow_valuation_perform);
        iv_valuation_colse = (ImageView) view.findViewById(R.id.popwindow_valuation_colse);

        tv_valuation_music.setOnClickListener(this);
        tv_valuation_dance.setOnClickListener(this);
        tv_valuation_perform.setOnClickListener(this);
        iv_valuation_colse.setOnClickListener(this);

        // 得到宽度和高度 getWindow().getDecorView().getWidth()

        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
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

    long waitTime = 2000;
    long touchTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - touchTime) >= waitTime) {
                //让Toast的显示时间和等待时间相同
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                touchTime = currentTime;
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}