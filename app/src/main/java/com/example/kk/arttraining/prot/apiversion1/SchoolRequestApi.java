package com.example.kk.arttraining.prot.apiversion1;

import com.example.kk.arttraining.bean.ConditionListBean;
import com.example.kk.arttraining.bean.parsebean.ParseMajorBean;
import com.example.kk.arttraining.bean.testBean;
import com.example.kk.arttraining.ui.homePage.bean.ParseSchoolListBean;
import com.example.kk.arttraining.utils.Config;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 作者：wschenyongyin on 2016/10/26 14:43
 * 说明:
 */
public interface SchoolRequestApi {
//    @POST(Config.URL_TEST)
//    @FormUrlEncoded
//    Call<ParseProvinceListBean> provinceList(@FieldMap Map<String, String> map);

    @POST(Config.URL_INSTITUTIONS_CONDITIONS)
    @FormUrlEncoded
    Call<ConditionListBean> conditionList(@FieldMap Map<String, Object> map);

    //获取学校列表
    @POST(Config.URL_INSTITUTIONS_LIST)
    @FormUrlEncoded
    Call<ParseSchoolListBean> schoolList(@FieldMap Map<String, Object> map);

    //获取学校详细信息
    @POST(Config.URL_INSTITUTIONS_SHOW)
    @FormUrlEncoded
    Call<testBean> schoolDetail(@FieldMap Map<String, String> map);

    //获取专业列表
    @POST(Config.URL_MAJOR_LIST)
    @FormUrlEncoded
    Call<ParseMajorBean> majorList(@FieldMap Map<String, String> map);
    //获取一级专业列表
    @POST(Config.URL_MAJOR_LIST_LEVEL_ONE)
    @FormUrlEncoded
    Call<ParseMajorBean> majorListLevelOne(@FieldMap Map<String, String> map);
}
