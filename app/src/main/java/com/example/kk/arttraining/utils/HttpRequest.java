package com.example.kk.arttraining.utils;


import com.example.kk.arttraining.prot.RequestServesApi;
import com.example.kk.arttraining.prot.RetrofitClient;

/**
 * 作者：wschenyongyin on 2016/9/20 16:42
 * 说明:封装retrofit网络请求框架
 */
public class HttpRequest {


    private  static RequestServesApi apiService;
    public static RequestServesApi getApiService() {
        apiService= RetrofitClient.getRetrofit().create(RequestServesApi.class);
        return apiService;
    }
}
