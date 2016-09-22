package com.example.kk.arttraining.prot;


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
 * 说明:
 */
public interface RequestServesApi {
    @POST(Config.URL_LOGIN)
    Call<String> Login(@Query("user_id") String loginname,
                           @Query("user_pwd") String nloginpwd);

    @POST("servlet/LeRunServlet")
    @FormUrlEncoded
    Call<String> userinfo(@FieldMap HashMap<String, String> map);


    @Multipart
    @POST("upload")
    Call<ResponseBody> upload(@Part("description") RequestBody description,
                              @Part MultipartBody.Part file);
}
