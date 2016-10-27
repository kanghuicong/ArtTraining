package com.example.kk.arttraining.prot;

import com.example.kk.arttraining.bean.testBean;
import com.example.kk.arttraining.ui.school.bean.ParseProvinceListBean;
import com.example.kk.arttraining.ui.school.bean.ParseSchoolListBean;
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
    //获取省份列表
    @POST(Config.URL_TEST)
    @FormUrlEncoded
    Call<ParseProvinceListBean> provinceList(@FieldMap Map<String, String> map);

    //获取学校列表
    @POST(Config.URL_TEST)
    @FormUrlEncoded
    Call<ParseSchoolListBean> schoolList(@FieldMap Map<String, String> map);

    //获取学校详细信息
    @POST(Config.URL_TEST)
    @FormUrlEncoded
    Call<testBean> schoolDetail(@FieldMap Map<String, String> map);

}
