package com.example.kk.arttraining.ui.homePage.function.live;

import com.example.kk.arttraining.ui.homePage.bean.LiveFinishBean;
import com.example.kk.arttraining.ui.homePage.prot.ILiveFinish;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2017/1/18.
 * QQ邮箱:515849594@qq.com
 */
public class LiveFinishData {
    ILiveFinish iLiveAfter;

    public LiveFinishData(ILiveFinish iLiveAfter) {
        this.iLiveAfter = iLiveAfter;
    }

    public void getLiveAfterData(int room_id) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("room_id", room_id);

        Callback<LiveFinishBean> callback = new Callback<LiveFinishBean>() {
            @Override
            public void onResponse(Call<LiveFinishBean> call, Response<LiveFinishBean> response) {
                LiveFinishBean liveAfterBean = response.body();
                if (response.body() != null) {
                    if (liveAfterBean.getError_code().equals("0")) {
                        iLiveAfter.getLiveFinish(liveAfterBean);
                    } else {
                        iLiveAfter.OnLiveFinishFailure(liveAfterBean.getError_msg());
                    }
                }else {
                    iLiveAfter.OnLiveFinishFailure("网络连接失败");
                }
            }
            @Override
            public void onFailure(Call<LiveFinishBean> call, Throwable t) {
                iLiveAfter.OnLiveFinishFailure("网络连接失败");
            }
        };
        Call<LiveFinishBean> call = HttpRequest.getLiveApi().liveFinish(map);
        call.enqueue(callback);
    }
}
