package com.example.kk.arttraining.ui.homePage.function.live;

import com.example.kk.arttraining.bean.parsebean.ParseLocationBean;
import com.example.kk.arttraining.ui.homePage.bean.LiveList;
import com.example.kk.arttraining.ui.homePage.prot.ILiveList;
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

    public LiveListData(ILiveList iLiveList) {
        this.iLiveList = iLiveList;
    }

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
        Call<LiveList> call = HttpRequest.getLiveApi().liveList(map);
        call.enqueue(callback);
    }

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
}
