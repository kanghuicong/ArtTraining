package com.example.kk.arttraining.ui.live.presenter;

import com.example.kk.arttraining.prot.rxjava_retrofit.RxApiManager;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxHelper;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxSubscribe;
import com.example.kk.arttraining.ui.live.view.ILiveBuyCourseView;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.HashMap;

import rx.Subscription;

/**
 * 作者：wschenyongyin on 2017/3/4 10:53
 * 说明:购买直播课程
 */
public class LiveBuyCoursePresenter {
    private ILiveBuyCourseView iLiveBuyCourseView;
    private Subscription queryCloudSub;
    private Subscription paySub;

    public LiveBuyCoursePresenter(ILiveBuyCourseView iLiveBuyCourseView) {
        this.iLiveBuyCourseView = iLiveBuyCourseView;
    }


    //查询当前云币
    public void QueryCloud(HashMap<String, Object> map) {
        queryCloudSub = HttpRequest.getCloudApi().queryCloud(map).compose(RxHelper.<Double>handleResult()).subscribe(new RxSubscribe<Double>() {
            @Override
            protected void _onNext(Double aDouble) {
                iLiveBuyCourseView.SuccessQueryCloud(aDouble);
            }

            @Override
            protected void _onError(String error_code, String error_msg) {
                iLiveBuyCourseView.FailureQueryCloud(error_code, error_msg);
            }

            @Override
            public void onCompleted() {

            }
        });
        RxApiManager.get().add("queryICloudSub", queryCloudSub);
    }

    //用云币支付

    public void pay(HashMap<String, Object> map) {
        paySub = HttpRequest.getLiveApi().buyChapter(map).compose(RxHelper.<String>handleResult()).subscribe(new RxSubscribe<String>() {
            @Override
            protected void _onNext(String s) {
                iLiveBuyCourseView.SuccessPay();
            }

            @Override
            protected void _onError(String error_code, String error_msg) {
                iLiveBuyCourseView.FailurePay(error_code, error_msg);
            }

            @Override
            public void onCompleted() {

            }
        });
        RxApiManager.get().add("paySub", paySub);

    }


    //取消订阅
    public void cancelSubscription() {
        RxApiManager.get().unsubscribe("queryICloudSub");
        RxApiManager.get().unsubscribe("paySub");
    }

}
