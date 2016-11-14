package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.app.Activity;

import com.example.kk.arttraining.bean.GeneralBean;
import com.example.kk.arttraining.bean.StatusesDetailBean;
import com.example.kk.arttraining.ui.homePage.prot.IDynamic;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/11/5.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicContentData {

    IDynamic iDynamic;
    String stus_type;

    public DynamicContentData(IDynamic iDynamic, String stus_type){
        this.iDynamic = iDynamic;
        this.stus_type = stus_type;
    }

    public void getDynamicContentTeacher(final Activity activity,int status_id) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", "");
        map.put("status_id", status_id+"");

        switch (stus_type){
            case "status":
                Callback<StatusesDetailBean> statusCallback = new Callback<StatusesDetailBean>() {
                    @Override
                    public void onResponse(Call<StatusesDetailBean> call, Response<StatusesDetailBean> response) {
                        StatusesDetailBean statusesDetailBean = response.body();
                        if (response.body() != null) {
                            if (statusesDetailBean.getError_code().equals("0")) {
                                iDynamic.getDynamicData(statusesDetailBean);
                            }else {
                                iDynamic.OnFailure(statusesDetailBean.getError_code());
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<StatusesDetailBean> call, Throwable t) {
                        iDynamic.OnFailure("onfailure");
                    }
                };
                Call<StatusesDetailBean> statusCall = HttpRequest.getStatusesApi().statusesDetail(map);
                statusCall.enqueue(statusCallback);
                break;

            case "work":
                Callback<StatusesDetailBean> workCallback = new Callback<StatusesDetailBean>() {
                    @Override
                    public void onResponse(Call<StatusesDetailBean> call, Response<StatusesDetailBean> response) {
                        StatusesDetailBean statusesDetailBean = response.body();
                        if (response.body() != null) {
                            if (statusesDetailBean.getError_code().equals("0")) {
                                iDynamic.getWorkData(statusesDetailBean);
                            }else {
                                iDynamic.OnFailure(statusesDetailBean.getError_code());
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<StatusesDetailBean> call, Throwable t) {
                        iDynamic.OnFailure("onFailure");
                    }
                };
                Call<StatusesDetailBean> workCall = HttpRequest.getStatusesApi().statusesUserWorkDetail(map);
                workCall.enqueue(workCallback);
                break;
        }


    }


    public void getCreateComment() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", "");
        map.put("uid", "");
        map.put("status_id", "");
        map.put("stus_type", "");
        map.put("content", "");
        map.put("score", "");

        Callback<GeneralBean> callback = new Callback<GeneralBean>() {
            @Override
            public void onResponse(Call<GeneralBean> call, Response<GeneralBean> response) {
                GeneralBean generalBean = response.body();
                if (response.body() != null) {
                    if (generalBean.getError_code().equals("0")) {
                        iDynamic.getCreateComment(generalBean.getError_msg());
                    }else {
                        iDynamic.OnFailure(generalBean.getError_code());
                    }
                }
            }
            @Override
            public void onFailure(Call<GeneralBean> call, Throwable t) {
                iDynamic.OnFailure("onfailure");
            }
        };
        Call<GeneralBean> call = HttpRequest.getStatusesApi().statusesCommentsCreateBBS(map);
        call.enqueue(callback);



    }
}
