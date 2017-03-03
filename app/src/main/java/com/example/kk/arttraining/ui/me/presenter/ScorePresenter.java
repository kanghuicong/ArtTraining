package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.prot.rxjava_retrofit.RxApiManager;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxHelper;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxSubscribe;
import com.example.kk.arttraining.ui.me.bean.ScoreBean;
import com.example.kk.arttraining.ui.me.view.IScoreView;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.List;

import rx.Subscription;

/**
 * 作者：wschenyongyin on 2017/2/28 15:48
 * 说明:积分
 */
public class ScorePresenter {
    private IScoreView iScoreView;
    Subscription queryScoreSub;
    Subscription queryScoreDetailSub;

    public ScorePresenter(IScoreView iScoreView) {
        this.iScoreView = iScoreView;
    }

    //查询积分
    public void QueryScore(HashMap<String, Object> map) {

        queryScoreSub = HttpRequest.getScoreApi().queryScore(map).compose(RxHelper.<Integer>handleResult()).subscribe(new RxSubscribe<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            protected void _onNext(Integer integer) {
                iScoreView.SuccessQuery(integer.intValue());
            }

            @Override
            protected void _onError(String error_code, String error_msg) {
                iScoreView.FailureQuery(error_code, error_msg);
            }
        });
        RxApiManager.get().add("queryScoreSub", queryScoreSub);

    }


    //查询积分详情列表
    public void QueryScoreDetail(HashMap<String, Object> map) {
        queryScoreDetailSub = HttpRequest.getScoreApi().QueryDetailScore(map).compose(RxHelper.<List<ScoreBean>>handleResult()).subscribe(new RxSubscribe<List<ScoreBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            protected void _onNext(List<ScoreBean> scoreBeanList) {
                iScoreView.SuccessQueryDetail(scoreBeanList);

            }

            @Override
            protected void _onError(String error_code, String error_msg) {
                iScoreView.FailureQueryDetail(error_code, error_msg);
            }
        });
        RxApiManager.get().add("queryScoreDetailSub", queryScoreDetailSub);
    }

    //取消订阅
    public void cancelSubscription() {
        RxApiManager.get().cancel("queryScoreSub");
        RxApiManager.get().cancel("queryScoreDetailSub");
    }
}
