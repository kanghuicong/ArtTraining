package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.bean.parsebean.StatusesBean;
import com.example.kk.arttraining.ui.me.view.IMyBBS;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.JsonTools;
import com.example.kk.arttraining.utils.UIUtil;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/14 14:56
 * 说明:
 */
public class MyBBSPresenter {
    IMyBBS iMyBBS;


    public MyBBSPresenter(IMyBBS iMyBBS) {
        this.iMyBBS = iMyBBS;
    }

    public void RefreshData(Map<String, Object> map) {
        Callback<StatusesBean> callback = new Callback<StatusesBean>() {
            @Override
            public void onResponse(Call<StatusesBean> call, Response<StatusesBean> response) {
                StatusesBean statusesBean = response.body();
                UIUtil.showLog("statusesBean", statusesBean + "==" + response.code());

                if (response.body() != null) {
                    if (statusesBean.getError_code().equals("0")) {
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(statusesBean.getStatuses());
                        List<Map<String, Object>> mapList = JsonTools.ParseStatuses(jsonString);
                        iMyBBS.SuccessRefresh(mapList);
                    } else {
                        iMyBBS.OnFailure(statusesBean.getError_code());
                    }
                } else {
                    iMyBBS.OnFailure(Config.Connection_Failure);
                }
            }

            @Override
            public void onFailure(Call<StatusesBean> call, Throwable t) {
                UIUtil.showLog("statusesBean", "failure" + t.toString());
                iMyBBS.OnFailure("failure");
            }
        };
        Call<StatusesBean> call = HttpRequest.getStatusesApi().statusesUserList(map);
        call.enqueue(callback);
    }


    public void LoadData(Map<String, Object> map) {
        Callback<StatusesBean> callback = new Callback<StatusesBean>() {
            @Override
            public void onResponse(Call<StatusesBean> call, Response<StatusesBean> response) {
                StatusesBean statusesBean = response.body();
                UIUtil.showLog("statusesBean", statusesBean + "==" + response.code());

                if (response.body() != null) {
                    if (statusesBean.getError_code().equals("0")) {
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(statusesBean.getStatuses());
                        List<Map<String, Object>> mapList = JsonTools.ParseStatuses(jsonString);
                        iMyBBS.SuccessLoad(mapList);
                    } else {
                        iMyBBS.OnFailureLoad(statusesBean.getError_msg());
                    }
                } else {
                    iMyBBS.OnFailureLoad(Config.Connection_ERROR_TOAST);
                }
            }

            @Override
            public void onFailure(Call<StatusesBean> call, Throwable t) {
                UIUtil.showLog("statusesBean", "failure" + t.toString());
                iMyBBS.OnFailureLoad(Config.Connection_ERROR_TOAST);
            }
        };
        Call<StatusesBean> call = HttpRequest.getStatusesApi().statusesUserList(map);
        call.enqueue(callback);
    }
}
