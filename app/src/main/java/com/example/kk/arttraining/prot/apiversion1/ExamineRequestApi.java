package com.example.kk.arttraining.prot.apiversion1;

import com.example.kk.arttraining.prot.rxjava_retrofit.BaseModel;
import com.example.kk.arttraining.ui.homePage.bean.ExamineCategoryBean;
import com.example.kk.arttraining.ui.homePage.bean.ExamineCollegeBean;
import com.example.kk.arttraining.ui.homePage.bean.ExamineProvinceBean;
import com.example.kk.arttraining.ui.valuation.bean.CommitOrderBean;
import com.example.kk.arttraining.utils.Config;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by kanghuicong on 2017/4/17.
 * QQ邮箱:515849594@qq.com
 */
public interface ExamineRequestApi {

    //报考省份
    @POST(Config.API_ENTRANCE_PROVINCE)
    @FormUrlEncoded
    Observable<BaseModel<List<ExamineProvinceBean>>> entrance_province(@FieldMap Map<String, Object> map);

    //报考类别
    @POST(Config.API_ENTRANCE_CATEGORY)
    @FormUrlEncoded
    Observable<BaseModel<List<ExamineCategoryBean>>> entrance_category(@FieldMap Map<String, Object> map);

    //报考学院
    @POST(Config.API_ENTRANCE_COLLEGE)
    @FormUrlEncoded
    Observable<BaseModel<List<ExamineCollegeBean>>> entrance_college(@FieldMap Map<String, Object> map);

}
