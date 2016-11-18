package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.bean.parsebean.StatusesBean;
import com.example.kk.arttraining.ui.me.bean.ParseCollectBean;
import com.example.kk.arttraining.ui.me.bean.UserCountBean;
import com.example.kk.arttraining.ui.me.view.IPersonalHomePageActivity;
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
 * 作者：wschenyongyin on 2016/11/9 20:21
 * 说明:
 */
public class PersonalHomePagePresenter {
    IPersonalHomePageActivity iPersonalHomePageActivity;

    public PersonalHomePagePresenter(IPersonalHomePageActivity iPersonalHomePageActivity) {
        this.iPersonalHomePageActivity = iPersonalHomePageActivity;
    }

    //获取用户信息
    public void getUserInfoData(Map<String, Object> map) {
        Callback<UserLoginBean> callback = new Callback<UserLoginBean>() {
            @Override
            public void onResponse(Call<UserLoginBean> call, Response<UserLoginBean> response) {
                UIUtil.showLog("PersonalHomePagePresenter.class", "getUserInfoData_onResponse" + response.code() + "----->" + response.message());
                UserLoginBean userLoginBean = response.body();
                UIUtil.showLog("PersonalHomePagePresenter.class", "用户信息" + "-->" + userLoginBean.toString());
                if (userLoginBean != null) {
                    if (userLoginBean.getError_code().equals("0")) {
                        iPersonalHomePageActivity.SuccessUserInfo(userLoginBean);
                    } else {
                        iPersonalHomePageActivity.FailureUserInfo(userLoginBean.getError_code());
                    }
                } else {
                    iPersonalHomePageActivity.FailureUserInfo("failure");
                }
            }

            @Override
            public void onFailure(Call<UserLoginBean> call, Throwable t) {
                UIUtil.showLog("PersonalHomePagePresenter.class", "getUserInfoData_onFailure" + t.getMessage() + "----->" + t.getCause());
                iPersonalHomePageActivity.FailureUserInfo("failure");
            }
        };

        Call<UserLoginBean> call = HttpRequest.getUserApi().userinfo(map);
        call.enqueue(callback);
    }

    //获取用户统计数量
    public void getUserCount(Map<String, Object> map) {
        Callback<UserCountBean> callback = new Callback<UserCountBean>() {
            @Override
            public void onResponse(Call<UserCountBean> call, Response<UserCountBean> response) {
                UIUtil.showLog("PersonalHomePagePresenter.class", "getUserCount_onResponse" + response.code() + "----->" + response.message());

                UserCountBean userCountBean = response.body();
                if (userCountBean != null) {
                    if (userCountBean.getError_code().equals("0")) {
                        iPersonalHomePageActivity.SuccessCount(userCountBean);
                    } else {
                        iPersonalHomePageActivity.FailureCount(userCountBean.getError_code());
                    }
                } else {
                    iPersonalHomePageActivity.FailureCount("failure");
                }
            }

            @Override
            public void onFailure(Call<UserCountBean> call, Throwable t) {
                iPersonalHomePageActivity.FailureCount("failure");
            }
        };
//        Call<UserCountBean> call = HttpRequest.getUserApi().userinfo(map);
//        call.enqueue(callback);


    }

    //获取用户帖子
    public void getUserStatuses(Map<String, Object> map) {

        Callback<StatusesBean> callback = new Callback<StatusesBean>() {
            @Override
            public void onResponse(Call<StatusesBean> call, Response<StatusesBean> response) {
                UIUtil.showLog("PersonalHomePagePresenter.class", "getUserStatuses_onResponse" + response.code() + "----->" + response.message());

                StatusesBean statusesBean = response.body();
                UIUtil.showLog("PersonalHomePagePresenter.class", "statusesBean"  + "----->" +statusesBean.getError_code());

                if (statusesBean != null) {
                    if (statusesBean.getError_code().equals("0")) {
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(statusesBean.getStatuses());
                        List<Map<String, Object>> mapList = JsonTools.ParseStatuses(jsonString);
                        iPersonalHomePageActivity.SuccessRefresh(mapList);

                    } else {
                        iPersonalHomePageActivity.FailureStatuses(statusesBean.getError_code());
                    }

                } else {
                    iPersonalHomePageActivity.FailureStatuses("failure");
                }
            }

            @Override
            public void onFailure(Call<StatusesBean> call, Throwable t) {
                UIUtil.showLog("PersonalHomePagePresenter.class", "getUserStatuses_onFailure" + t.getMessage() + "----->" + t.getCause());
                iPersonalHomePageActivity.FailureStatuses("failure");
            }
        };
        Call<StatusesBean> call = HttpRequest.getStatusesApi().statusesUserList(map);
        call.enqueue(callback);

    }


    //获取用户作品
    public void geWorkStatuses(Map<String, String> map) {

        Callback<StatusesBean> callback = new Callback<StatusesBean>() {
            @Override
            public void onResponse(Call<StatusesBean> call, Response<StatusesBean> response) {
                StatusesBean statusesBean = response.body();
                if (statusesBean != null) {
                    if (statusesBean.getError_code().equals("0")) {
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(statusesBean.getStatuses());
                        List<Map<String, Object>> mapList = JsonTools.ParseStatuses(jsonString);
                        iPersonalHomePageActivity.SuccessWorks(mapList);

                    } else {
                        iPersonalHomePageActivity.FailureWoreks(statusesBean.getError_code());
                    }

                } else {
                    iPersonalHomePageActivity.FailureWoreks("failure");
                }
            }

            @Override
            public void onFailure(Call<StatusesBean> call, Throwable t) {
                UIUtil.showLog("PersonalHomePagePresenter.class", "LoadData_onFailure" + t.getMessage() + "----->" + t.getCause());
                iPersonalHomePageActivity.FailureWoreks("failure");
            }
        };
        Call<StatusesBean> call = HttpRequest.getStatusesApi().statusesUserWorkList(map);
        call.enqueue(callback);

    }


    public void LoadData(Map<String, Object> map) {
        Callback<StatusesBean> callback = new Callback<StatusesBean>() {
            @Override
            public void onResponse(Call<StatusesBean> call, Response<StatusesBean> response) {
                UIUtil.showLog("PersonalHomePagePresenter.class", "LoadData_onResponse" + response.code() + "----->" + response.message());

                StatusesBean statusesBean = response.body();
                UIUtil.showLog("statusesBean", statusesBean.getError_code() + "==" + response.code());

                if (response.body() != null) {
                    if (statusesBean.getError_code().equals("0")) {
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(statusesBean.getStatuses());
                        List<Map<String, Object>> mapList = JsonTools.ParseStatuses(jsonString);
                        iPersonalHomePageActivity.SuccessLoad(mapList);
                    } else {
                        iPersonalHomePageActivity.OnFailure(statusesBean.getError_code());
                    }
                } else {
                    iPersonalHomePageActivity.OnFailure(Config.Connection_Failure);
                }
            }

            @Override
            public void onFailure(Call<StatusesBean> call, Throwable t) {
                UIUtil.showLog("statusesBean", "failure" + t.toString());
                UIUtil.showLog("PersonalHomePagePresenter.class", "LoadData_onFailure" + t.getMessage() + "----->" + t.getCause());

                iPersonalHomePageActivity.OnFailure(Config.Connection_Failure);
            }
        };
        Call<StatusesBean> call = HttpRequest.getStatusesApi().statusesGoodList(map);
        call.enqueue(callback);
    }
}
