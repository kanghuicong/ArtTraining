package com.example.kk.arttraining.ui.live.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.utils.UIUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2017/4/14.
 * QQ邮箱:515849594@qq.com
 */
public class LiveAllActivity extends FragmentActivity {
    @InjectView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @InjectView(R.id.tv_choose_live)
    TextView tvChooseLive;
    @InjectView(R.id.tv_choose_history)
    TextView tvChooseHistory;
    private FragmentManager fm;
    LiveMainFragment liveMainFragment ;
    LiveHistoryFragment liveHistoryFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.live_all);
        ButterKnife.inject(this);

        initFragment();
    }

    private void initFragment() {
        fm = getSupportFragmentManager();
        liveMainFragment = new LiveMainFragment();
        fm.beginTransaction().replace(R.id.fl_main, liveMainFragment).addToBackStack(null).commit();
    }

    @OnClick({R.id.iv_title_back, R.id.tv_choose_live, R.id.tv_choose_history})
    public void onClick(View view) {
        FragmentTransaction transaction = fm.beginTransaction();
        hideAllFragment(transaction);
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_choose_live:
                if (liveMainFragment == null) {
                    UIUtil.showLog("historyLive", "3");
                    liveMainFragment = new LiveMainFragment();
                    transaction.add(R.id.fl_main, liveMainFragment);
                } else {
                    UIUtil.showLog("historyLive", "4");
                    transaction.show(liveMainFragment);
                }
                tvChooseLive.setBackgroundResource(R.drawable.shape_chose_school_left_focus);
                tvChooseHistory.setBackgroundResource(R.drawable.shape_chose_school_right_unfocus);
                tvChooseLive.setTextColor(getResources().getColor(R.color.blue_overlay));
                tvChooseHistory.setTextColor(getResources().getColor(R.color.white));
                transaction.commit();
                break;
            case R.id.tv_choose_history:
                if (liveHistoryFragment == null) {
                    UIUtil.showLog("historyLive", "1");
                    liveHistoryFragment = new LiveHistoryFragment();
                    transaction.add(R.id.fl_main, liveHistoryFragment);
                } else {
                    UIUtil.showLog("historyLive", "2");
                    transaction.show(liveHistoryFragment);
                }
                tvChooseHistory.setBackgroundResource(R.drawable.shape_chose_school_right_focus);
                tvChooseLive.setBackgroundResource(R.drawable.shape_chose_school_left_unfocus);
                tvChooseHistory.setTextColor(getResources().getColor(R.color.blue_overlay));
                tvChooseLive.setTextColor(getResources().getColor(R.color.white));
                transaction.commit();
                break;
        }
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (liveMainFragment != null) {
            transaction.hide(liveMainFragment);
        }
        if (liveHistoryFragment != null) {
            transaction.hide(liveHistoryFragment);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
