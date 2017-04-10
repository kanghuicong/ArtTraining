package com.example.kk.arttraining.ui.homePage.function.refresh;

import com.example.kk.arttraining.prot.rxjava_retrofit.BaseModel;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxApiManager;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxHelper;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxSubscribe;
import com.example.kk.arttraining.ui.me.bean.CloudContentBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;

/**
 * Created by kanghuicong on 2017/3/27.
 * QQ邮箱:515849594@qq.com
 */
public class RefreshData {

    Subscription refreshSub;
    Subscription loadSub;
    IRefresh iRefresh;

    public RefreshData(IRefresh iRefresh) {
        this.iRefresh = iRefresh;
    }

    public void refreshData(Observable observable) {

        refreshSub = observable.compose(RxHelper.<List<BaseModel>>handleResult()).subscribe(new RxSubscribe<List<BaseModel>>() {
            @Override
            protected void _onNext(List<BaseModel> baseModel) {
                iRefresh.refreshSuccess(baseModel);
            }
            @Override
            protected void _onError(String error_code, String error_msg) {
                iRefresh.onFailure(error_code, error_msg);
            }
            @Override
            public void onCompleted() {}
        });
        RxApiManager.get().add("refreshSub", refreshSub);
    }

    public void loadData(Observable observable) {

        loadSub = observable.compose(RxHelper.<List<BaseModel>>handleResult()).subscribe(new RxSubscribe<List<BaseModel>>() {
            @Override
            protected void _onNext(List<BaseModel> baseModel) {
                iRefresh.loadSuccess(baseModel);
            }
            @Override
            protected void _onError(String error_code, String error_msg) {
                iRefresh.onFailure(error_code, error_msg);
            }
            @Override
            public void onCompleted() {
            }
        });
        RxApiManager.get().add("loadSub", loadSub);
    }


    public void cancelSubscription() {
        RxApiManager.get().unsubscribe("refreshSub");
        RxApiManager.get().unsubscribe("loadSub");
    }

}
