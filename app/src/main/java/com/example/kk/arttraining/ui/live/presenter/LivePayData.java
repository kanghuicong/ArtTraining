package com.example.kk.arttraining.ui.live.presenter;

import android.content.Context;

import com.example.kk.arttraining.prot.rxjava_retrofit.RxApiManager;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxHelper;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxSubscribe;
import com.example.kk.arttraining.ui.valuation.bean.CommitOrderBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.HashMap;
import java.util.Map;

import rx.Subscription;

/**
 * Created by kanghuicong on 2017/3/22.
 * QQ邮箱:515849594@qq.com
 */
public class LivePayData {
    Context context;
    IPayOther iPayOther;

    private Subscription payOtherSub;


    public LivePayData(Context context, IPayOther iPayOther) {
        this.context = context;
        this.iPayOther = iPayOther;
    }


    public void getPayOther(int room_id,int chapter_id,String buy_type,String is_check){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("room_id", room_id);
        map.put("chapter_id", chapter_id);
        map.put("buy_type", buy_type);
        map.put("is_check", is_check);

        payOtherSub = HttpRequest.getLiveApi().payOther(map).compose(RxHelper.<CommitOrderBean>handleResult()).subscribe(new RxSubscribe<CommitOrderBean>() {
            @Override
            protected void _onNext(CommitOrderBean commitOrderBean) {
                iPayOther.getPayOther(commitOrderBean);
            }
            @Override
            protected void _onError(String error_code, String error_msg) {
                iPayOther.onPayOtherFailure(error_msg);
            }
            @Override
            public void onCompleted() {}
        });
        RxApiManager.get().add("payOtherSub", payOtherSub);
    }


    public void cancelSubscription() {
        RxApiManager.get().unsubscribe("payOtherSub");
    }

    public interface IPayOther{
        void getPayOther(CommitOrderBean commitOrderBean);

        void onPayOtherFailure(String msg);
    }


}
