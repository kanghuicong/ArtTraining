package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.prot.rxjava_retrofit.RxApiManager;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxHelper;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxSubscribe;
import com.example.kk.arttraining.ui.me.bean.MyCourseBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import rx.Subscription;

/**
 * Created by kanghuicong on 2017/3/22.
 * QQ邮箱:515849594@qq.com
 */
public class MyCourseData {
    ICourse iCourse;
    Subscription myCourseRefreshSub;
    Subscription myCourseLoadSub;

    public MyCourseData(ICourse iCourse) {
        this.iCourse = iCourse;
    }

    public void refreshData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);

        myCourseRefreshSub = HttpRequest.getLiveApi().myCourse(map).compose(RxHelper.<List<MyCourseBean>>handleResult()).subscribe(new RxSubscribe<List<MyCourseBean>>() {
            @Override
            protected void _onNext(List<MyCourseBean> courseBeanList) {
                iCourse.refreshSuccess(courseBeanList);
            }

            @Override
            protected void _onError(String error_code, String error_msg) {
                iCourse.onRefreshFailure(error_code,error_msg);
            }

            @Override
            public void onCompleted() {}
        });
        RxApiManager.get().add("myCourseRefreshSub", myCourseRefreshSub);
    }

    public void loadData(int self) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("self", self);

        myCourseLoadSub = HttpRequest.getLiveApi().myCourse(map).compose(RxHelper.<List<MyCourseBean>>handleResult()).subscribe(new RxSubscribe<List<MyCourseBean>>() {
            @Override
            protected void _onNext(List<MyCourseBean> courseBeanList) {
                iCourse.loadSuccess(courseBeanList);
            }

            @Override
            protected void _onError(String error_code, String error_msg) {
                iCourse.onLoadFailure(error_code,error_msg);
            }

            @Override
            public void onCompleted() {}
        });
        RxApiManager.get().add("myCourseLoadSub", myCourseLoadSub);
    }

    public interface ICourse{
        void refreshSuccess(List<MyCourseBean> courseBeanList);

        void loadSuccess(List<MyCourseBean> courseBeanList);


        void onRefreshFailure(String code,String msg);

        void onLoadFailure(String code,String msg);
    }

    public void cancelSubscription() {
        RxApiManager.get().unsubscribe("myCourseLoadSub");
        RxApiManager.get().unsubscribe("myCourseRefreshSub");
    }

}
