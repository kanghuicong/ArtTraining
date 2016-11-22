package com.example.kk.arttraining.ui.me.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.BottomPullSwipeRefreshLayout;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.me.adapter.CouponAdapter;
import com.example.kk.arttraining.ui.me.bean.CouponBean;
import com.example.kk.arttraining.ui.me.presenter.CouponPresenter;
import com.example.kk.arttraining.ui.valuation.view.ValuationMain;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;
import com.jaeger.library.StatusBarUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/9/23 11:18
 * 说明:优惠券
 */
public class CouponActivity extends BaseActivity implements ICouponActivity, AdapterView.OnItemClickListener, BottomPullSwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @InjectView(R.id.tv_title_bar)
    TextView tvTitleBar;
    @InjectView(R.id.iv_title_image)
    ImageView ivTitleImage;
    @InjectView(R.id.rl_title)
    RelativeLayout rlTitle;
    @InjectView(R.id.me_coupon_lv)
    ListView meCouponLv;
    @InjectView(R.id.tv_failure_hint_)
    TextView tvFailureHint;
    @InjectView(R.id.failure_hint_layout)
    LinearLayout failureHintLayout;
    private CouponPresenter couponPresenter;
    private Dialog loadingDialog;
    private String error_code;
    private CouponAdapter couponAdapter;
    private String fromIntent;
    BottomPullSwipeRefreshLayout swipeRefreshLayout;
    private boolean REFRESH_FIRST_FLAG = true;
    List<CouponBean> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_coupon_activity);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.blue_overlay));
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this, "优惠券");

        init();

    }

    @Override
    public void init() {
        Intent intent = getIntent();
        fromIntent = intent.getStringExtra("from");
        loadingDialog = DialogUtils.createLoadingDialog(CouponActivity.this, "正在加载...");
        couponPresenter = new CouponPresenter(this);

        swipeRefreshLayout = new BottomPullSwipeRefreshLayout(this);
        swipeRefreshLayout = (BottomPullSwipeRefreshLayout) findViewById(R.id.idcoupon_swipe);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#87CEFA"));
        swipeRefreshLayout.setOnRefreshListener(this);
        //自动刷新
        swipeRefreshLayout.autoRefresh();

        if (fromIntent.equals("ValuationActivity")) {
            meCouponLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CouponBean couponBean = (CouponBean) parent.getItemAtPosition(position);
                    UIUtil.showLog("优惠券信息:", couponBean.toString());
                    //表示测评券
                    if (couponBean.getCoupon_type() == 0 || couponBean.getCoupon_type() == 2) {

                        Intent intent = new Intent();
                        intent.putExtra("values", couponBean.getFace_value());
                        setResult(ValuationMain.CHOSE_COUPON, intent);
                        finish();
                    } else {
                        UIUtil.ToastshowShort(CouponActivity.this, getResources().getString(R.string.coupon_no_use));
                    }
                }
            });
        } else {
        }


    }

    @Override
    public void onClick(View v) {

    }

    //获取数据
    @Override
    public void getDatas(List<CouponBean> couponBeanList) {
        listData = couponBeanList;
        swipeRefreshLayout.setRefreshing(false);
        failureHintLayout.setVisibility(View.GONE);
        if (REFRESH_FIRST_FLAG) {
            couponAdapter = new CouponAdapter(this, listData);
            meCouponLv.setAdapter(couponAdapter);
        } else {
            couponAdapter.notifyDataSetChanged();
        }

    }


    //请求失败
    @Override
    public void onFailure(String error_code, String error_msg) {
        swipeRefreshLayout.setRefreshing(false);
        failureHintLayout.setVisibility(View.VISIBLE);
        if (error_code.equals(Config.TOKEN_INVALID)) {
            startActivity(new Intent(this, UserLoginActivity.class));
            UIUtil.ToastshowShort(getApplicationContext(), getResources().getString(R.string.toast_user_login));
        } else {
            UIUtil.ToastshowShort(getApplicationContext(), error_msg);
            tvFailureHint.setText(error_msg);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CouponBean couponBean = (CouponBean) parent.getItemAtPosition(position);
//        CouponBean couponBean = new CouponBean();
//        couponBean.setCoupon_type(0);
//        couponBean.setFace_value("10");
        //表示测评券
        if (couponBean.getCoupon_type() == 0) {

            Intent intent = new Intent();
            intent.putExtra("values", couponBean.getFace_value());
//            intent.putExtra("values","10");
            setResult(ValuationMain.CHOSE_COUPON, intent);
            finish();
        } else {
            UIUtil.ToastshowShort(CouponActivity.this, getResources().getString(R.string.coupon_no_use));
        }
    }

    @Override
    public void onRefresh() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        couponPresenter.getData(map);
    }
}