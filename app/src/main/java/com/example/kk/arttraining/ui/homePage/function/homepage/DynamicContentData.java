package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.app.Activity;

import com.example.kk.arttraining.bean.GeneralBean;
import com.example.kk.arttraining.bean.StatusesDetailBean;
import com.example.kk.arttraining.bean.parsebean.CommentsListBean;
import com.example.kk.arttraining.ui.homePage.prot.IDynamic;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

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

    public DynamicContentData(IDynamic iDynamic, String stus_type) {
        this.iDynamic = iDynamic;
        this.stus_type = stus_type;
    }

    //获取详情数据
    public void getDynamicContentData(final Activity activity, int status_id) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("status_id", status_id + "");
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);

        switch (stus_type) {
            case "status":
                Callback<StatusesDetailBean> statusCallback = new Callback<StatusesDetailBean>() {
                    @Override
                    public void onResponse(Call<StatusesDetailBean> call, Response<StatusesDetailBean> response) {
                        StatusesDetailBean statusesDetailBean = response.body();
                        if (response.body() != null) {
                            if (statusesDetailBean.getError_code().equals("0")) {
                                iDynamic.getDynamicData(statusesDetailBean);
                            } else {
                                iDynamic.OnFailure(statusesDetailBean.getError_code());
                            }
                        } else {
                            iDynamic.OnFailure("onFailure");
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusesDetailBean> call, Throwable t) {
                        UIUtil.showLog("DateUtils", "123999" + t.toString());
                        iDynamic.OnFailure("onFailure");
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
                            } else {
                                iDynamic.OnFailure(statusesDetailBean.getError_code());
                            }
                        } else {
                            iDynamic.OnFailure("onFailure");
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusesDetailBean> call, Throwable t) {
                        iDynamic.OnFailure("onFailure");
                    }
                };
                UIUtil.showLog("DateUtils", "12399");
                Call<StatusesDetailBean> workCall = HttpRequest.getStatusesApi().statusesUserWorkDetail(map);
                workCall.enqueue(workCallback);
                break;
        }
    }

    //发布评论
    public void getCreateComment(int status_id,String content) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("status_id", status_id);
        map.put("content", content);
        UIUtil.showLog("iDynamic", "iDynamic");

        Callback<GeneralBean> callback = new Callback<GeneralBean>() {
            @Override
            public void onResponse(Call<GeneralBean> call, Response<GeneralBean> response) {
                GeneralBean generalBean = response.body();
                if (response.body() != null) {
                    if (generalBean.getError_code().equals("0")) {
                        UIUtil.showLog("iDynamic", "Error_code1");
                        iDynamic.getCreateComment(generalBean.getError_msg());
                    } else {
                        UIUtil.showLog("iDynamic", "Error_code2");
                        iDynamic.OnFailure(generalBean.getError_code());
                    }
                }
            }

            @Override
            public void onFailure(Call<GeneralBean> call, Throwable t) {
                UIUtil.showLog("iDynamic", "onFailure");
                iDynamic.OnFailure("onFailure");
            }
        };
        if (stus_type.equals("status")) {
            Call<GeneralBean> call = HttpRequest.getStatusesApi().statusesCommentsCreateBBS(map);
            call.enqueue(callback);
        } else if (stus_type.equals("work")) {
            Call<GeneralBean> call = HttpRequest.getStatusesApi().statusesCommentsCreateWork(map);
            call.enqueue(callback);
        }
    }

    //上拉加载
    public void loadComment(int status_id,int self) {

        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("status_id", status_id);
        map.put("self", self);


        Callback<CommentsListBean> callback = new Callback<CommentsListBean>() {
            @Override
            public void onResponse(Call<CommentsListBean> call, Response<CommentsListBean> response) {
                CommentsListBean commentsListBean = response.body();
                if (response.body() != null) {
                    if (commentsListBean.getError_code().equals("0")) {
                        iDynamic.loadDynamic(commentsListBean.getComments());
                    } else {
                        iDynamic.OnLoadDynamicFailure(commentsListBean.getError_msg());
                    }
                }
            }
            @Override
            public void onFailure(Call<CommentsListBean> call, Throwable t) {
                iDynamic.OnFailure("OnFailure");
            }
        };
        if (stus_type.equals("status")) {
            Call<CommentsListBean> call = HttpRequest.getStatusesApi().statusesCommentsListBBS(map);
            call.enqueue(callback);
        } else if (stus_type.equals("work")) {
            Call<CommentsListBean> call = HttpRequest.getStatusesApi().statusesCommentsListWork(map);
            call.enqueue(callback);
        }
    }
}
