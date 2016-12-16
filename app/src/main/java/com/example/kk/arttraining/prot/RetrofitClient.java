package com.example.kk.arttraining.prot;

import com.example.kk.arttraining.prot.OKHttpFactory;
import com.example.kk.arttraining.utils.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 作者：wschenyongyin on 2016/9/20 16:22
 * 说明:
 */
public class RetrofitClient {
    RetrofitClient() {

    }

    //普通
    public static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(OKHttpFactory.getOkHttpClient())
                //baseUrl
                .baseUrl(Config.BASE_URL)
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())

                .build();
        return retrofit;
    }
    //课程
    public static Retrofit getRetrofitCourse() {
        Retrofit retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(OKHttpFactory.getOkHttpClient())
                //baseUrl
                .baseUrl(Config.BASE_URL_COURSE)
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())

                .build();
        return retrofit;
    }
}
