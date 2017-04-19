package com.example.kk.arttraining.ui.live.presenter;

import com.example.kk.arttraining.prot.rxjava_retrofit.BaseModel;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxApiManager;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxHelper;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxSubscribe;
import com.example.kk.arttraining.ui.homePage.bean.LiveHistoryBean;
import com.example.kk.arttraining.ui.live.bean.LiveHistoryTypeBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * Created by kanghuicong on 2017/4/14.
 * QQ邮箱:515849594@qq.com
 */
public class LiveHistoryTypeData {

    Subscription historyTypeSub;
    Map<String, Object> clickMap = new HashMap<String, Object>();
    ILiveHistory iLiveHistory;

    public LiveHistoryTypeData(ILiveHistory iLiveHistory) {
        this.iLiveHistory = iLiveHistory;
    }

    public void getLiveHistoryType(int chapter_id, LiveHistoryBean bean) {
        clickMap.put("access_token", Config.ACCESS_TOKEN);
        clickMap.put("uid", Config.UID);
        clickMap.put("utype", Config.USER_TYPE);
        clickMap.put("chapter_id", chapter_id);

        historyTypeSub = HttpRequest.getLiveApi().liveHistoryType(clickMap).compose(RxHelper.<LiveHistoryTypeBean>handleResult()).subscribe(new RxSubscribe<LiveHistoryTypeBean>() {
            @Override
            public void onCompleted() {}
            @Override
            protected void _onNext(LiveHistoryTypeBean liveHistoryTypeBean) {
                iLiveHistory.successHistory(liveHistoryTypeBean,bean);
            }

            @Override
            protected void _onError(String error_code, String error_msg) {
                iLiveHistory.failureHistory(error_code, error_msg);
            }
        });
        RxApiManager.get().add("historyTypeSub", historyTypeSub);
    }

    public void cancelSubscription() {
        RxApiManager.get().unsubscribe("historyTypeSub");
    }

    public interface ILiveHistory{
        void successHistory(LiveHistoryTypeBean liveHistoryTypeBean,LiveHistoryBean liveHistoryBean);

        void failureHistory(String error_code, String error_msg);
    }

}
