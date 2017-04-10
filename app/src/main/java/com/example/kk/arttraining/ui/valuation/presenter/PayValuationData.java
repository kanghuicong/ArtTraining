package com.example.kk.arttraining.ui.valuation.presenter;

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
 * Created by kanghuicong on 2017/3/24.
 * QQ邮箱:515849594@qq.com
 */
public class PayValuationData {
    IOderNumber iOderNumber;
    Subscription payValuationSub;

    public PayValuationData(IOderNumber iOderNumber) {
        this.iOderNumber = iOderNumber;
    }

    public void getOrderNumber(String is_check,String order_id,String order_number) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("is_check", is_check);
        map.put("order_id", order_id);
        map.put("order_number", order_number);

        payValuationSub = HttpRequest.getPayApi().payOther(map).compose(RxHelper.<CommitOrderBean>handleResult()).subscribe(new RxSubscribe<CommitOrderBean>() {
            @Override
            protected void _onNext(CommitOrderBean commitOrderBean) {
                iOderNumber.getPayOther(commitOrderBean);
            }
            @Override
            protected void _onError(String error_code, String error_msg) {
                iOderNumber.onPayOtherFailure(error_msg);
            }
            @Override
            public void onCompleted() {}
        });
        RxApiManager.get().add("payValuationSub", payValuationSub);
    }

    public interface IOderNumber{
        void getPayOther(CommitOrderBean commitOrderBean);

        void onPayOtherFailure(String error_msg);
    }

    public void cancelSubscription() {
        RxApiManager.get().unsubscribe("payValuationSub");
    }
}
