package com.example.kk.arttraining.ui.live.presenter;

import com.example.kk.arttraining.ui.live.bean.RoomBean;
import com.example.kk.arttraining.ui.live.view.IPLVideoView;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.ErrorMsgUtils;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2017/1/7 17:54
 * 说明:
 */
public class PLVideoViewPresenter {
    IPLVideoView iplVideoView;

    public PLVideoViewPresenter(IPLVideoView iplVideoView) {
        this.iplVideoView = iplVideoView;
    }


    //获取房间信息
    public void getRoomData(Map<String, Object> map) {

        Callback<RoomBean> callback = new Callback<RoomBean>() {
            @Override
            public void onResponse(Call<RoomBean> call, Response<RoomBean> response) {
                RoomBean roomBean = response.body();
                if (roomBean != null) {
                    if (roomBean.getError_code().equals("0")) {
                        iplVideoView.SuccessRoom(roomBean);
                    } else {
                        iplVideoView.FailureRoom(roomBean.getError_code(),roomBean.getError_msg());
                    }
                } else {
                    iplVideoView.FailureRoom(response.code()+"", ErrorMsgUtils.ERROR_LIVE_ROOM);
                }
            }

            @Override
            public void onFailure(Call<RoomBean> call, Throwable t) {
                UIUtil.showLog("onFailure----------->",t.getMessage()+"--->"+t.getCause());
                iplVideoView.FailureRoom(Config.Connection_Failure+"", Config.REQUEST_FAILURE);
            }
        };
        Call<RoomBean> call = HttpRequest.getLiveApi().joinLiveRoom(map);
        call.enqueue(callback);
    }
}
