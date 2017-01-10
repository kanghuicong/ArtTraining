package com.example.kk.arttraining.utils;


import com.example.kk.arttraining.prot.apiversion1.CommonRequestApi;
import com.example.kk.arttraining.prot.apiversion1.CourseRequestApi;
import com.example.kk.arttraining.prot.apiversion1.GroupRequestApi;
import com.example.kk.arttraining.prot.apiversion1.PayRequestApi;
import com.example.kk.arttraining.prot.apiversion1.SchoolRequestApi;
import com.example.kk.arttraining.prot.apiversion1.StatusesRequestApi;
import com.example.kk.arttraining.prot.apiversion1.UserRequestApi;
import com.example.kk.arttraining.prot.RetrofitClient;
import com.example.kk.arttraining.prot.apiversion2.LiveRequestApi;

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
    private static PayRequestApi payRequestApi;
    private static CourseRequestApi courseRequestApi;

    private static LiveRequestApi liveRequestApi;

    //用户
    public static UserRequestApi getUserApi() {
        userApiService = RetrofitClient.getRetrofit().create(UserRequestApi.class);
        return userApiService;
    }

    //动态
    public static StatusesRequestApi getStatusesApi() {
        statusesApiService = RetrofitClient.getRetrofit().create(StatusesRequestApi.class);
        return statusesApiService;
    }

    //小组
    public static GroupRequestApi getGroupApi() {
        groupRequestApi = RetrofitClient.getRetrofit().create(GroupRequestApi.class);
        return groupRequestApi;
    }

    //通用
    public static CommonRequestApi getCommonApi() {
        commonRequestApi = RetrofitClient.getRetrofit().create(CommonRequestApi.class);
        return commonRequestApi;
    }

    //院校
    public static SchoolRequestApi getSchoolApi() {
        schoolRequestApi = RetrofitClient.getRetrofit().create(SchoolRequestApi.class);
        return schoolRequestApi;
    }

    //支付
    public static PayRequestApi getPayApi() {
        payRequestApi = RetrofitClient.getRetrofit().create(PayRequestApi.class);
        return payRequestApi;
    }

    //课程
    public static CourseRequestApi getCourseApi() {
        courseRequestApi = RetrofitClient.getRetrofitCourse().create(CourseRequestApi.class);
        return courseRequestApi;
    }

    //直播
    public static LiveRequestApi getLiveApi() {
        liveRequestApi = RetrofitClient.getRetrofit().create(LiveRequestApi.class);
        return liveRequestApi;
    }
}
