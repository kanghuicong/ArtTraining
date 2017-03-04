package com.example.kk.arttraining.ui.live.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.kk.arttraining.pay.view.RechargeICloudActivity;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxBus;
import com.example.kk.arttraining.ui.live.bean.eventbean.EventBuyCourse;
import com.example.kk.arttraining.ui.live.presenter.LiveBuyCoursePresenter;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import rx.Observable;
import rx.functions.Action1;

/**
 * 作者：wschenyongyin on 2017/3/4 10:51
 * 说明:购买直播课程页面
 */
public class LiveBuyCourseView extends BaseActivity implements ILiveBuyCourseView {
    private LiveBuyCoursePresenter liveBuyCoursePresenter;
    //用户当前云币数量
    private double cloud;
    //
    private Observable<EventBuyCourse> chapterData;
    //直播间ID
    private int room_id;
    //课时id
    private int chapter_id;
    //购买类型直播还是重播(live/record)
    private String buy_type;
    //课程价格
    private double chapter_price;
    //判断从哪个页面进来的
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    //初始化
    @Override
    public void init() {
        liveBuyCoursePresenter = new LiveBuyCoursePresenter(this);

        chapterData = RxBus.get().register("chapterData", EventBuyCourse.class);
        chapterData.subscribe(new Action1<EventBuyCourse>() {
            @Override
            public void call(EventBuyCourse eventBuyCourse) {
                room_id = eventBuyCourse.getRoom_id();
                chapter_id = eventBuyCourse.getChapter_id();
                buy_type = eventBuyCourse.getBuy_type();
                chapter_price = eventBuyCourse.getChapter_price();
                from = eventBuyCourse.getFrom();
            }
        });
    }

    @Override
    public void onClick(View v) {
        //判断用户的云币是否够支付课程
        if (cloud >= chapter_price) {
            Pay();
        } else {
            skipRechargeCloudActivity();
        }
    }

    //查询用户当前云币
    @Override
    public void queryCloud() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        liveBuyCoursePresenter.QueryCloud(map);
    }

    //查询成功
    @Override
    public void SuccessQueryCloud(double cloud) {
        this.cloud = cloud;
    }

    //查询失败
    @Override
    public void FailureQueryCloud(String error_code, String error_msg) {

    }

    //购买直播课程
    @Override
    public void Pay() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("utype", Config.USER_TYPE);
        map.put("uid", Config.UID);
        map.put("room_id", room_id);
        map.put("chapter_id", chapter_id);
        map.put("buy_type", buy_type);
        liveBuyCoursePresenter.pay(map);
    }

    //购买成功
    @Override
    public void SuccessPay() {
        UIUtil.ToastshowShort(this, "购买成功");
        switch (from) {
            //从直播页面过来
            case "liveWaitActivity":
                // TODO: 2017/3/4
                break;

            //从老师往期课程页面返回
            case "liveCourseActivity":
                // TODO: 2017/3/4
                break;
        }

    }

    //购买失败
    @Override
    public void FailurePay(String error_code, String error_msg) {
        if (error_code.equals("20097")) {
            skipRechargeCloudActivity();
        } else if (error_code.equals("20023")) {
            startActivity(new Intent(this, UserLoginActivity.class));
        } else {
            UIUtil.ToastshowShort(this, error_msg);
        }
    }

    //跳转到云币充值
    @Override
    public void skipRechargeCloudActivity() {
        startActivity(new Intent(this, RechargeICloudActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销
        RxBus.get().unregister("chapterData", chapterData);
        //取消订阅
        liveBuyCoursePresenter.cancelSubscription();
    }
}
