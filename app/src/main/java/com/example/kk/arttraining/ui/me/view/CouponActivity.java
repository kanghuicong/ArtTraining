package com.example.kk.arttraining.ui.me.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
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
public class CouponActivity extends BaseActivity implements ICouponActivity, AdapterView.OnItemClickListener {

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
    private CouponPresenter couponPresenter;
    private Dialog loadingDialog;
    private String error_code;
    private CouponAdapter couponAdapter;
    private String fromIntent;

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

//        couponAdapter = new CouponAdapter(this);
//        meCouponLv.setAdapter(couponAdapter);

        if (fromIntent.equals("ValuationActivity")) {
            meCouponLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CouponBean couponBean = (CouponBean) parent.getItemAtPosition(position);
                    UIUtil.showLog("优惠券信息:", couponBean.toString());
                    //表示测评券
                    if (couponBean.getCoupon_type() == 0) {

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

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        couponPresenter.getData(map);

    }

    @Override
    public void onClick(View v) {

    }

    //获取数据
    @Override
    public void getDatas(List<CouponBean> couponBeanList) {
        this.couponPresenter = couponPresenter;
        couponAdapter = new CouponAdapter(this, couponBeanList);
        meCouponLv.setAdapter(couponAdapter);

    }

    //显示加载dialog
    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    //隐藏加载dialog
    @Override
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    //请求失败
    @Override
    public void onFailure(String error_code) {
        this.error_code = error_code;
        handler.sendEmptyMessage(0);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (error_code) {
                case "500":
                    break;
            }
        }
    };

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
}