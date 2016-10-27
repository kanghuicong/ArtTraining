package com.example.kk.arttraining.utils;


import com.example.kk.arttraining.prot.CommonRequestApi;
import com.example.kk.arttraining.prot.GroupRequestApi;
import com.example.kk.arttraining.prot.SchoolRequestApi;
import com.example.kk.arttraining.prot.StatusesRequestApi;
import com.example.kk.arttraining.prot.UserRequestApi;
import com.example.kk.arttraining.prot.RetrofitClient;

/**
 * 作者：wschenyongyin on 2016/9/20 16:42
 * 说明:封装retrofit网络请求框架
 */
public class HttpRequest {


    private static UserRequestApi userApiService;
    private static StatusesRequestApi statusesApiService;
    private static GroupRequestApi groupRequestApi;
    private static CommonRequestApi commonRequestApi;
    private static SchoolRequestApi schoolRequestApi;

    public static UserRequestApi getUserApi() {
        userApiService = RetrofitClient.getRetrofit().create(UserRequestApi.class);
        return userApiService;
    }

    public static StatusesRequestApi getStatusesApi() {
        statusesApiService = RetrofitClient.getRetrofit().create(StatusesRequestApi.class);
        return statusesApiService;
    }

    public static GroupRequestApi getGroupApi() {
        groupRequestApi = RetrofitClient.getRetrofit().create(GroupRequestApi.class);
        return groupRequestApi;
    }

    public static CommonRequestApi getCommonApi() {
        commonRequestApi = RetrofitClient.getRetrofit().create(CommonRequestApi.class);
        return commonRequestApi;
    }

    public static SchoolRequestApi getSchoolApi() {
        schoolRequestApi = RetrofitClient.getRetrofit().create(SchoolRequestApi.class);
        return schoolRequestApi;
    }
}
