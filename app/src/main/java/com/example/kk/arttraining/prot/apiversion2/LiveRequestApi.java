package com.example.kk.arttraining.prot.apiversion2;

import com.example.kk.arttraining.bean.parsebean.AssessmentsListBean;
import com.example.kk.arttraining.ui.live.bean.RoomBean;
import com.example.kk.arttraining.utils.Config;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 作者：wschenyongyin on 2017/1/7 17:16
 * 说明:直播接口
 */
public interface LiveRequestApi {

    //进入直播房间

    @POST(Config.API_LIVE_JOIN_ROOM)
    @FormUrlEncoded
    Call<RoomBean> joinLiveRoom(@FieldMap Map<String,Object> map);
}
