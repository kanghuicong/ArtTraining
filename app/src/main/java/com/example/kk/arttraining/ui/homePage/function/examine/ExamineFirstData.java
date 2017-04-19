package com.example.kk.arttraining.ui.homePage.function.examine;

import com.example.kk.arttraining.prot.rxjava_retrofit.RxApiManager;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxHelper;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxSubscribe;
import com.example.kk.arttraining.ui.homePage.bean.ExamineCategoryBean;
import com.example.kk.arttraining.ui.homePage.bean.ExamineProvinceBean;
import com.example.kk.arttraining.ui.live.bean.LiveHistoryTypeBean;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * Created by kanghuicong on 2017/4/17.
 * QQ邮箱:515849594@qq.com
 */
public class ExamineFirstData {
    Subscription examineProvinceSub;
    Subscription examineCategorySub;
    IExamineFirst iExamineFirst;
    Map<String, Object> provinceMap = new HashMap<String, Object>();
    Map<String, Object> categoryMap = new HashMap<String, Object>();;

    public ExamineFirstData(IExamineFirst iExamineFirst) {
        this.iExamineFirst = iExamineFirst;
    }

    //省份
    public void getExamineProvince() {
        examineProvinceSub = HttpRequest.getExamineApi().entrance_province(provinceMap).compose(RxHelper.<List<ExamineProvinceBean>>handleResult()).subscribe(new RxSubscribe<List<ExamineProvinceBean>>() {
            @Override
            public void onCompleted() {}

            @Override
            protected void _onNext(List<ExamineProvinceBean> examineProvinceList) {
                iExamineFirst.successExamineProvince(examineProvinceList);
            }

            @Override
            protected void _onError(String error_code, String error_msg) {
                iExamineFirst.failureExamineFirst(error_code,error_msg);
            }
        });
        RxApiManager.get().add("examineProvinceSub", examineProvinceSub);
    }

    //类别
    public void getExamineCategory() {

        examineCategorySub = HttpRequest.getExamineApi().entrance_category(categoryMap).compose(RxHelper.<List<ExamineCategoryBean>>handleResult()).subscribe(new RxSubscribe<List<ExamineCategoryBean>>() {
            @Override
            public void onCompleted() {}

            @Override
            protected void _onNext(List<ExamineCategoryBean> examineCategoryList) {
                iExamineFirst.successExamineCategory(examineCategoryList);
            }

            @Override
            protected void _onError(String error_code, String error_msg) {
                iExamineFirst.failureExamineFirst(error_code,error_msg);
            }
        });
        RxApiManager.get().add("examineCategorySub", examineCategorySub);
    }



    public interface IExamineFirst{
        void successExamineProvince(List<ExamineProvinceBean> examineProvinceList);

        void successExamineCategory(List<ExamineCategoryBean> examineCategoryList);

        void failureExamineFirst(String error_code, String error_msg);

    }

    public void cancelSubscription() {
        RxApiManager.get().unsubscribe("examineProvinceSub");
        RxApiManager.get().unsubscribe("examineCategorySub");
    }
}
