package com.example.kk.arttraining.ui.homePage.function.live;

import android.content.Context;
import android.content.Intent;

import com.example.kk.arttraining.bean.parsebean.ParseLocationBean;
import com.example.kk.arttraining.ui.homePage.bean.LiveList;
import com.example.kk.arttraining.ui.homePage.bean.LiveListBean;
import com.example.kk.arttraining.ui.homePage.prot.ILiveList;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2017/1/17.
 * QQ邮箱:515849594@qq.com
 */
public class LiveListData {

    ILiveList iLiveList;
    String type;

    public LiveListData(ILiveList iLiveList,String type) {
        this.iLiveList = iLiveList;
        this.type = type;
    }

    //直播封面List
    public void getLiveListData() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("token", Config.ACCESS_TOKEN);

        Callback<LiveList> callback = new Callback<LiveList>() {
            @Override
            public void onResponse(Call<LiveList> call, Response<LiveList> response) {
                LiveList liveList = response.body();
                if (response.body() != null) {
                    if (liveList.getError_code().equals("0")) {
                        iLiveList.getLiveListData(liveList.getOpenclass_list());
                    } else {
                        iLiveList.OnLiveListFailure(liveList.getError_msg());
                    }
                }else {
                    iLiveList.OnLiveListFailure("网络连接失败");
                }
            }
            @Override
            public void onFailure(Call<LiveList> call, Throwable t) {
                iLiveList.OnLiveListFailure("网络连接失败");
            }
        };

        if (("home".equals(type))) {
            Call<LiveList> call = HttpRequest.getLiveApi().liveHome(map);
            call.enqueue(callback);
        }else {
            Call<LiveList> call = HttpRequest.getLiveApi().liveList(map);
            call.enqueue(callback);
        }


    }

    //直播封面上拉
    public void loadLiveListData(int self,int live_status) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("self", self);
        map.put("live_status",live_status);

        Callback<LiveList> callback = new Callback<LiveList>() {
            @Override
            public void onResponse(Call<LiveList> call, Response<LiveList> response) {
                LiveList liveList = response.body();
                if (response.body() != null) {
                    if (liveList.getError_code().equals("0")) {
                        iLiveList.loadLiveList(liveList.getOpenclass_list());
                    } else {
                        iLiveList.OnLoadLiveListFailure(0);
                    }
                }else {
                    iLiveList.OnLoadLiveListFailure(1);
                }
            }
            @Override
            public void onFailure(Call<LiveList> call, Throwable t) {
                iLiveList.OnLoadLiveListFailure(2);
            }
        };
        Call<LiveList> call = HttpRequest.getLiveApi().liveList(map);
        call.enqueue(callback);
    }

    //直播状态
    public void getLiveTypeData(Map<String,Object> map) {

        Callback<LiveListBean> callback = new Callback<LiveListBean>() {
            @Override
            public void onResponse(Call<LiveListBean> call, Response<LiveListBean> response) {
                LiveListBean liveBean = response.body();
                if (response.body() != null) {
                    if (liveBean.getError_code().equals("0")) {
                        iLiveList.getLiveType(liveBean.getLive_status(),(int)map.get("room_id"),liveBean.getChapter_id());
                    } else {
                        iLiveList.OnLiveTypeFailure(liveBean.getError_code(),liveBean.getError_msg());

                    }
                }else {
                    iLiveList.OnLiveTypeFailure(response.code()+"","网络连接失败");
                }
            }
            @Override
            public void onFailure(Call<LiveListBean> call, Throwable t) {
                iLiveList.OnLiveTypeFailure(Config.Connection_Failure,"网络连接失败");
            }
        };
        Call<LiveListBean> call = HttpRequest.getLiveApi().liveType(map);
        call.enqueue(callback);
    }


}
