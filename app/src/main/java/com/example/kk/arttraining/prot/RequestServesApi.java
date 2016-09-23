package com.example.kk.arttraining.prot;


import com.example.kk.arttraining.bean.ResponseObject;
import com.example.kk.arttraining.utils.Config;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * 作者：wschenyongyin on 2016/8/30 16:13
 * 说明:网络调用接口
 */
public interface RequestServesApi {
    @POST(Config.URL_LOGIN)
    Call<ResponseObject> Login(@Query("user_id") String loginname,
                           @Query("user_pwd") String nloginpwd);


    //获取用户信息
    @POST("servlet/LeRunServlet")
    @FormUrlEncoded
    Call<ResponseObject> userinfo(@FieldMap HashMap<String, String> map);


    //反馈
    @POST("servlet/LeRunServlet")
    @FormUrlEncoded
    Call<ResponseObject> feedback(@FieldMap HashMap<String, String> map);


    //获取商家信息 以便付款
    @POST("servlet/LeRunServlet")
    @FormUrlEncoded
    Call<ResponseObject> getPayCount(@FieldMap HashMap<String, String> map);

    //付款后 同步付款结果
    @POST("servlet/LeRunServlet")
    @FormUrlEncoded
    Call<ResponseObject> syncPayInfo(@FieldMap HashMap<String, String> map);


    @Multipart
    @POST("upload")
    Call<ResponseBody> upload(@Part("description") RequestBody description,
                              @Part MultipartBody.Part file);
}
