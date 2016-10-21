package com.example.kk.arttraining.utils;


import com.example.kk.arttraining.prot.UserRequestApi;
import com.example.kk.arttraining.prot.RetrofitClient;

/**
 * 作者：wschenyongyin on 2016/9/20 16:42
 * 说明:封装retrofit网络请求框架
 */
public class HttpRequest {


    private static UserRequestApi apiService;

    public static UserRequestApi getUserApi() {
        apiService = RetrofitClient.getRetrofit().create(UserRequestApi.class);
        return apiService;
    }
}
