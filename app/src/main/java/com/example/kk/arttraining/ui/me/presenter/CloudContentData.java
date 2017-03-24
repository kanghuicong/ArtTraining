package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.pay.wxapi.HttpUtils;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxApiManager;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxHelper;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxSubscribe;
import com.example.kk.arttraining.ui.me.bean.CloudContentBean;
import com.example.kk.arttraining.ui.me.bean.ScoreBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.List;

import rx.Subscription;

/**
 * Created by kanghuicong on 2017/3/15.
 * QQ邮箱:515849594@qq.com
 */
public class CloudContentData {
    ICloudContent iCloudContent;
    private Subscription cloudContentSub;

    public CloudContentData(ICloudContent iCloudContent) {
        this.iCloudContent = iCloudContent;
    }

    public void refreshData() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);

        cloudContentSub = HttpRequest.getCloudApi().QueryDetailCloud(map).compose(RxHelper.<List<CloudContentBean>>handleResult()).subscribe(new RxSubscribe<List<CloudContentBean>>() {
            @Override
            protected void _onNext(List<CloudContentBean> cloudContentBeen) {
                iCloudContent.refreshSuccess(cloudContentBeen);
            }
            @Override
            protected void _onError(String error_code, String error_msg) {
                UIUtil.showLog("iCloudContent", error_code + "----" + error_msg);
                iCloudContent.onFailure(error_code, error_msg);
            }
            @Override
            public void onCompleted() {
            }
        });
        RxApiManager.get().add("cloudContentSub", cloudContentSub);
    }

    public void loadData(int self) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("self", self);

        cloudContentSub = HttpRequest.getCloudApi().QueryDetailCloud(map).compose(RxHelper.<List<CloudContentBean>>handleResult()).subscribe(new RxSubscribe<List<CloudContentBean>>() {
            @Override
            protected void _onNext(List<CloudContentBean> cloudContentBeen) {
                iCloudContent.loadSuccess(cloudContentBeen);
            }
            @Override
            protected void _onError(String error_code, String error_msg) {
                iCloudContent.onFailure(error_code, error_msg);
            }
            @Override
            public void onCompleted() {
            }
        });
        RxApiManager.get().add("cloudContentSub", cloudContentSub);
    }


    public interface ICloudContent{
        void refreshSuccess(List<CloudContentBean> contentBeanList);

        void loadSuccess(List<CloudContentBean> contentBeanList);

        void onFailure(String error_code, String error_msg);

    }

    public void cancelSubscription() {
        RxApiManager.get().unsubscribe("cloudContentSub");
    }

}
