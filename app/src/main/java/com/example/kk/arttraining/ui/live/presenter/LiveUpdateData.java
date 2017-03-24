package com.example.kk.arttraining.ui.live.presenter;

import com.example.kk.arttraining.prot.rxjava_retrofit.RxApiManager;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxHelper;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxSubscribe;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.HashMap;
import java.util.Map;

import rx.Subscription;

/**
 * Created by kanghuicong on 2017/3/22.
 * QQ邮箱:515849594@qq.com
 */
public class LiveUpdateData {
    IPayUpdate iPayUpdate;
    private Subscription payUpdateSub;

    public LiveUpdateData(IPayUpdate iPayUpdate) {
        this.iPayUpdate = iPayUpdate;
    }

    public void getPayUpdate(String order_number, String pay_type) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("order_number", order_number);
        map.put("pay_type", pay_type);

        payUpdateSub = HttpRequest.getLiveApi().payUpdate(map).compose(RxHelper.<String>handleResult()).subscribe(new RxSubscribe<String>() {
            @Override
            protected void _onNext(String data) {
                iPayUpdate.getPayUpdate();
            }
            @Override
            protected void _onError(String error_code, String error_msg) {
                iPayUpdate.onPayUpdateFailure(error_msg);
            }
            @Override
            public void onCompleted() {}
        });
        RxApiManager.get().add("payUpdateSub", payUpdateSub);
    }

    public void cancelSubscription() {
        RxApiManager.get().unsubscribe("payUpdateSub");
    }

    public interface IPayUpdate {
        void getPayUpdate();

        void onPayUpdateFailure(String msg);
    }
}
