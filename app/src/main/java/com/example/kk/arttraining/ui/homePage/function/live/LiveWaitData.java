package com.example.kk.arttraining.ui.homePage.function.live;

import com.example.kk.arttraining.ui.homePage.bean.LiveWaitBean;
import com.example.kk.arttraining.ui.homePage.prot.ILiveWait;
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
public class LiveWaitData {
    ILiveWait iLiveWait;
    public LiveWaitData(ILiveWait iLiveWait) {
        this.iLiveWait = iLiveWait;
    }

    public void getLiveWaitData(int room_id,int chapter_id) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("room_id", room_id);
        map.put("chapter_id", chapter_id);

        Callback<LiveWaitBean> callback = new Callback<LiveWaitBean>() {
            @Override
            public void onResponse(Call<LiveWaitBean> call, Response<LiveWaitBean> response) {
                LiveWaitBean liveBeforeBean = response.body();
                if (response.body() != null) {
                    if (liveBeforeBean.getError_code().equals("0")) {
                        iLiveWait.getLiveWait(liveBeforeBean);
                    } else {
                        iLiveWait.OnLiveWaitFailure(liveBeforeBean.getError_msg());
                    }
                }else {
                    iLiveWait.OnLiveWaitFailure("网络连接失败");
                }
            }
            @Override
            public void onFailure(Call<LiveWaitBean> call, Throwable t) {
                iLiveWait.OnLiveWaitFailure("网络连接失败");
            }
        };
        Call<LiveWaitBean> call = HttpRequest.getLiveApi().liveWait(map);
        call.enqueue(callback);
    }
}
