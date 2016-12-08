package com.example.kk.arttraining.ui.discover.function.dynamic;

import com.example.kk.arttraining.bean.parsebean.StatusesBean;
import com.example.kk.arttraining.ui.discover.prot.IDiscover;

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
 * Created by kanghuicong on 2016/12/8.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicData {
    IDiscover iDiscover;

    public DynamicData(IDiscover iDiscover) {
        this.iDiscover = iDiscover;
    }

    public void getDynamicData() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);

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
                        iDiscover.getDynamicListData(mapList);
                    } else {
                        iDiscover.OnDynamicFailure(statusesBean.getError_msg());
                    }
                } else {
                    iDiscover.OnDynamicFailure("OnFailure");
                }
            }
            @Override
            public void onFailure(Call<StatusesBean> call, Throwable t) {
                iDiscover.OnDynamicFailure("网络连接失败！");
            }
        };
        Call<StatusesBean> call = HttpRequest.getStatusesApi().statusesGoodList(map);
        call.enqueue(callback);
    }

    public void loadDynamicData(int self) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("self",self);
        UIUtil.showLog("loadDynamicListDataself",self+"==");
        Callback<StatusesBean> callback = new Callback<StatusesBean>() {
            @Override
            public void onResponse(Call<StatusesBean> call, Response<StatusesBean> response) {
                StatusesBean statusesBean = response.body();
                response.message();
                if (response.body() != null) {
                    if (statusesBean.getError_code().equals("0")) {
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(statusesBean.getStatuses());
                        List<Map<String, Object>> mapList = JsonTools.ParseStatuses(jsonString);
                        iDiscover.loadDynamicListData(mapList);
                    } else {
                        iDiscover.OnLoadDynamicFailure(0);
                    }
                } else {
                    iDiscover.OnLoadDynamicFailure(1);
                }
            }
            @Override
            public void onFailure(Call<StatusesBean> call, Throwable t) {
                iDiscover.OnLoadDynamicFailure(2);
            }
        };
        Call<StatusesBean> call = HttpRequest.getStatusesApi().statusesGoodList(map);
        call.enqueue(callback);
    }
}
