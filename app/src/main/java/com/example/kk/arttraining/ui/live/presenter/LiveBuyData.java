package com.example.kk.arttraining.ui.live.presenter;

import com.example.kk.arttraining.prot.rxjava_retrofit.RxApiManager;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxHelper;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxSubscribe;
import com.example.kk.arttraining.ui.live.view.ILiveBuy;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.HashMap;

import rx.Subscription;

/**
 * Created by kanghuicong on 2017/3/14.
 * QQ邮箱:515849594@qq.com
 */
public class LiveBuyData {
    ILiveBuy iLiveBuy;
    private Subscription cloudSub;
    private Subscription paySub;

    public LiveBuyData(ILiveBuy iLiveBuy) {
        this.iLiveBuy = iLiveBuy;
    }

    //查询云币
    public void getCouldData(int position) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);

        cloudSub = HttpRequest.getCloudApi().queryCloud(map).compose(RxHelper.<Double>handleResult()).subscribe(new RxSubscribe<Double>() {
            @Override
            protected void _onNext(Double aDouble) {
                iLiveBuy.getCloud(aDouble,position);
            }

            @Override
            protected void _onError(String error_code, String error_msg) {
                iLiveBuy.onFailure(error_code, error_msg);
            }

            @Override
            public void onCompleted() {

            }
        });
        RxApiManager.get().add("cloudSub", cloudSub);
    }

    //云币购买
    public void getPayCould(int room_id, int chapter_id, String buy_type) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("utype", Config.USER_TYPE);
        map.put("uid", Config.UID);
        map.put("room_id", room_id);
        map.put("chapter_id", chapter_id);
        map.put("buy_type", buy_type);

        paySub = HttpRequest.getLiveApi().payClound(map).compose(RxHelper.<String>handleResult()).subscribe(new RxSubscribe<String>() {
            @Override
            protected void _onNext(String s) {
                iLiveBuy.getPayCloud();
            }

            @Override
            protected void _onError(String error_code, String error_msg) {
                iLiveBuy.onFailure(error_code, error_msg);
            }

            @Override
            public void onCompleted() {
            }
        });
        RxApiManager.get().add("paySub", paySub);
    }


    public void cancelSubscription() {
        RxApiManager.get().unsubscribe("cloudSub");
        RxApiManager.get().unsubscribe("paySub");
    }
}
