package com.example.kk.arttraining.ui.discover.function.circle;

import com.example.kk.arttraining.bean.GroupBean;
import com.example.kk.arttraining.bean.parsebean.GroupListBean;
import com.example.kk.arttraining.bean.parsebean.GroupListMyBean;
import com.example.kk.arttraining.bean.parsebean.StatusesBean;
import com.example.kk.arttraining.ui.discover.prot.IGroup;
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
 * Created by kanghuicong on 2016/11/10.
 * QQ邮箱:515849594@qq.com
 */
public class CircleGroupData {
    IGroup iGroup;
    public CircleGroupData(IGroup iGroup) {
        this.iGroup = iGroup;
    }

    public void getMyCircleGroupData() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", "");
        map.put("uid", Config.UID);

        Callback<GroupListBean> callback = new Callback<GroupListBean>() {
            @Override
            public void onResponse(Call<GroupListBean> call, Response<GroupListBean> response) {
                GroupListBean groupListBean = response.body();
                if (response.body() != null) {
                    if (groupListBean.getError_code().equals("0")) {
                        List<GroupBean> tecInfoBeanList = groupListBean.getGroups();
                        iGroup.getMyGroupData(tecInfoBeanList);
                    } else {
                        iGroup.OnFailure(groupListBean.getError_code());
                    }
                } else {
                    iGroup.OnFailure("failure");
                }
            }
            @Override
            public void onFailure(Call<GroupListBean> call, Throwable t) {
                iGroup.OnFailure("failure");
            }
        };
        Call<GroupListBean> call = HttpRequest.getGroupApi().groupMyList(map);
        call.enqueue(callback);
    }


    public void getRecommendCircleGroupData() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", "");

        Callback<GroupListBean> callback = new Callback<GroupListBean>() {
            @Override
            public void onResponse(Call<GroupListBean> call, Response<GroupListBean> response) {
                GroupListBean groupListBean = response.body();
                if (response.body() != null) {
                    if (groupListBean.getError_code().equals("0")) {
                        List<GroupBean> tecInfoBeanList = groupListBean.getGroups();
                        iGroup.getRecommendCircleGroupData(tecInfoBeanList);
                    } else {
                        iGroup.OnFailure(groupListBean.getError_code());
                    }
                } else {
                    iGroup.OnFailure("failure");
                }
            }
            @Override
            public void onFailure(Call<GroupListBean> call, Throwable t) {
                iGroup.OnFailure("failure");
            }
        };
        Call<GroupListBean> call = HttpRequest.getGroupApi().groupList(map);
        call.enqueue(callback);
    }

    public void getGroupDynamicData() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", "");
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
                        iGroup.getGroupDynamic(mapList);
                    } else {
                        iGroup.OnFailure(statusesBean.getError_code());
                    }
                } else {
                    iGroup.OnFailure("failure");
                }
            }

            @Override
            public void onFailure(Call<StatusesBean> call, Throwable t) {
                iGroup.OnFailure("failure");
            }
        };
        Call<StatusesBean> call = HttpRequest.getStatusesApi().statusesGoodListGroup(map);
        call.enqueue(callback);
    }
}
