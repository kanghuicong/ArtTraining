package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.app.Activity;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.parsebean.StatusesBean;
import com.example.kk.arttraining.Media.playvideo.activity.VideoListLayout;
import com.example.kk.arttraining.ui.homePage.prot.IHomePageMain;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicAdapter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.JsonTools;
import com.example.kk.arttraining.utils.UIUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/10/28.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicData {
    IHomePageMain iHomePageMain;

    public DynamicData(IHomePageMain iHomePageMain) {
        this.iHomePageMain = iHomePageMain;
    }

    public void getDynamicData() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);

        Callback<StatusesBean> callback = new Callback<StatusesBean>() {
            @Override
            public void onResponse(Call<StatusesBean> call, Response<StatusesBean> response) {
                StatusesBean statusesBean = response.body();
                UIUtil.showLog("statusesBean",statusesBean+"=="+response.code());

                if (response.body() != null) {
                    if (statusesBean.getError_code().equals("0")) {
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(statusesBean.getStatuses());
                        List<Map<String, Object>> mapList = JsonTools.ParseStatuses(jsonString);
                        iHomePageMain.getDynamicListData(mapList);
                    } else {
                        iHomePageMain.OnFailure(statusesBean.getError_code());
                    }
                } else {
                    iHomePageMain.OnFailure("failure");
                }
            }
            @Override
            public void onFailure(Call<StatusesBean> call, Throwable t) {
                UIUtil.showLog("statusesBean","failure"+t.toString());
                iHomePageMain.OnFailure("failure");
            }
        };
        Call<StatusesBean> call = HttpRequest.getStatusesApi().statusesGoodList(map);
        call.enqueue(callback);
    }

    public void loadDynamicData() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);

        Callback<StatusesBean> callback = new Callback<StatusesBean>() {
            @Override
            public void onResponse(Call<StatusesBean> call, Response<StatusesBean> response) {
                StatusesBean statusesBean = response.body();
                UIUtil.showLog("statusesBean",statusesBean+"=="+response.code());

                if (response.body() != null) {
                    if (statusesBean.getError_code().equals("0")) {
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(statusesBean.getStatuses());
                        List<Map<String, Object>> mapList = JsonTools.ParseStatuses(jsonString);
                        iHomePageMain.loadDynamicListData(mapList);
                    } else {
                        iHomePageMain.OnFailure(statusesBean.getError_code());
                    }
                } else {
                    iHomePageMain.OnFailure("failure");
                }
            }
            @Override
            public void onFailure(Call<StatusesBean> call, Throwable t) {
                UIUtil.showLog("statusesBean","failure"+t.toString());
                iHomePageMain.OnFailure("failure");
            }
        };
        Call<StatusesBean> call = HttpRequest.getStatusesApi().statusesGoodList(map);
        call.enqueue(callback);
    }
}
