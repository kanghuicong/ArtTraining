package com.example.kk.arttraining.ui.homePage.function.examine;

import com.example.kk.arttraining.prot.rxjava_retrofit.RxApiManager;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxHelper;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxSubscribe;
import com.example.kk.arttraining.ui.homePage.bean.ExamineBean;
import com.example.kk.arttraining.ui.homePage.bean.ExamineCollegeBean;
import com.example.kk.arttraining.ui.homePage.bean.ExamineListBean;
import com.example.kk.arttraining.ui.homePage.bean.ExamineProvinceBean;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;

/**
 * Created by kanghuicong on 2017/3/13.
 * QQ邮箱:515849594@qq.com
 */
public class ExamineSecondData {
    Subscription examineSecondSub;
    IExamineSecond iExamineSecond;
    public ExamineSecondData(IExamineSecond iExamineSecond) {
        this.iExamineSecond = iExamineSecond;
    }

    public void getExamineData(double majorScore,double cultureScore,String examineProvince,String examineCategory,String examineSubject) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("major_score", majorScore);
        map.put("culture_score", cultureScore);
        map.put("province", examineProvince);
        map.put("category", examineCategory);
//        map.put("subject", examineProvince);

        examineSecondSub = HttpRequest.getExamineApi().entrance_college(map).compose(RxHelper.<List<ExamineCollegeBean>>handleResult()).subscribe(new RxSubscribe<List<ExamineCollegeBean>>() {
            @Override
            public void onCompleted() {}

            @Override
            protected void _onNext(List<ExamineCollegeBean> examineCollegeList) {
                UIUtil.showLog("ThemeExamineSecondActivity","_onNext"+"----"+examineCollegeList.size());
                iExamineSecond.successExamineSecond(examineCollegeList);
            }

            @Override
            protected void _onError(String error_code, String error_msg) {
                iExamineSecond.failureExamineSecond(error_code,error_msg);
            }
        });
        RxApiManager.get().add("examineSecondSub", examineSecondSub);
    }


    public interface IExamineSecond {
        void successExamineSecond(List<ExamineCollegeBean> examineCollegeList);

        void failureExamineSecond(String error_code,String msg);
    }

    public void cancelSubscription() {
        RxApiManager.get().unsubscribe("examineSecondSub");
    }

}
