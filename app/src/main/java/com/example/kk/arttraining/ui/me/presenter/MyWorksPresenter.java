package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.bean.parsebean.StatusesBean;
import com.example.kk.arttraining.ui.me.view.IMyBBS;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.JsonTools;
import com.google.gson.Gson;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/22 11:08
 * 说明:我的作品处理类
 */
public class MyWorksPresenter {
    private IMyBBS iMyBBS;

    public MyWorksPresenter(IMyBBS iMyBBS) {
        this.iMyBBS = iMyBBS;
    }

    public void getMyWorks(Map<String, Object> map, final String type) {
        Callback<StatusesBean> callbackw = new Callback<StatusesBean>() {
            @Override
            public void onResponse(Call<StatusesBean> call, Response<StatusesBean> response) {
                StatusesBean statusesBean = response.body();
                if (statusesBean != null) {
                    if (statusesBean.getError_code().equals("0")) {
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(statusesBean.getStatuses());
                        if (type.equals("refresh")) {
                            iMyBBS.SuccessRefresh(JsonTools.ParseStatuses(jsonString));
                        } else if (type.equals("load")) {
                            iMyBBS.SuccessLoad(JsonTools.ParseStatuses(jsonString));
                        }

                    } else {
                        if (type.equals("refresh")) {
                            iMyBBS.OnFailure(statusesBean.getError_code(), statusesBean.getError_msg());
                        } else if (type.equals("load")) {
                            iMyBBS.OnFailureLoad(statusesBean.getError_code(), statusesBean.getError_msg());
                        }

                    }
                } else {
                    if (type.equals("refresh")) {
                        iMyBBS.OnFailure(response.code()+"", Config.Connection_ERROR_TOAST);
                    } else if (type.equals("load")) {
                        iMyBBS.OnFailureLoad(response.code()+"", Config.Connection_ERROR_TOAST);
                    }

                }

            }

            @Override
            public void onFailure(Call<StatusesBean> call, Throwable t) {
                if (type.equals("refresh")) {
                    iMyBBS.OnFailure(Config.Connection_Failure, Config.Connection_ERROR_TOAST);
                } else if (type.equals("load")) {
                    iMyBBS.OnFailureLoad(Config.Connection_Failure, Config.Connection_ERROR_TOAST);
                }

            }
        };

        Call<StatusesBean> call= HttpRequest.getStatusesApi().statusesUserWorkList(map);
        call.enqueue(callbackw);

    }
}
