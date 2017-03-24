package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.prot.rxjava_retrofit.RxApiManager;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxHelper;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxSubscribe;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.HashMap;

import rx.Subscription;

/**
 * Created by kanghuicong on 2017/3/15.
 * QQ邮箱:515849594@qq.com
 */
public class CloudData {
    private Subscription myCloudSub;
    ICloud iCloud;

    public CloudData(ICloud iCloud) {
        this.iCloud = iCloud;
    }

    public void getCloud() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);

        myCloudSub = HttpRequest.getCloudApi().queryCloud(map).compose(RxHelper.<Double>handleResult()).subscribe(new RxSubscribe<Double>() {
            @Override
            protected void _onNext(Double aDouble) {
                iCloud.getCloud(aDouble);
            }
            @Override
            protected void _onError(String error_code, String error_msg) {
                iCloud.onFailure(error_code, error_msg);
            }
            @Override
            public void onCompleted() {}
        });
        RxApiManager.get().add("myCloudSub", myCloudSub);
    }


    public interface ICloud{
        void getCloud(Double aDouble);

        void onFailure(String code,String msg);
    }

    public void cancelSubscription() {
        RxApiManager.get().unsubscribe("myCloudSub");
    }

}
