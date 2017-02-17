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
    public void loadLiveListData(int self) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("self", self);

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
    public void getLiveTypeData(final Context context, final int room_id, final int chapter_id) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("room_id", room_id);

        Callback<LiveListBean> callback = new Callback<LiveListBean>() {
            @Override
            public void onResponse(Call<LiveListBean> call, Response<LiveListBean> response) {
                LiveListBean liveBean = response.body();
                if (response.body() != null) {
                    if (liveBean.getError_code().equals("0")) {
                        iLiveList.getLiveType(liveBean.getLive_status(),room_id,chapter_id);
                    } else {
                        iLiveList.OnLiveTypeFailure(liveBean.getError_msg());
                        if ("".equals(liveBean.getError_msg()) || "".equals(liveBean.getError_msg())) {
                            context.startActivity(new Intent(context, UserLoginActivity.class));
                        }
                    }
                }else {
                    iLiveList.OnLiveTypeFailure("网络连接失败");
                }
            }
            @Override
            public void onFailure(Call<LiveListBean> call, Throwable t) {
                iLiveList.OnLiveTypeFailure("网络连接失败");
            }
        };
        Call<LiveListBean> call = HttpRequest.getLiveApi().liveType(map);
        call.enqueue(callback);
    }


}
