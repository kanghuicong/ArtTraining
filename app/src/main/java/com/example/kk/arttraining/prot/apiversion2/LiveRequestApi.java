package com.example.kk.arttraining.prot.apiversion2;

import com.example.kk.arttraining.bean.NoDataResponseBean;
import com.example.kk.arttraining.ui.homePage.bean.LiveFinishBean;
import com.example.kk.arttraining.ui.homePage.bean.LiveWaitBean;
import com.example.kk.arttraining.ui.homePage.bean.LiveList;
import com.example.kk.arttraining.ui.homePage.bean.LiveListBean;
import com.example.kk.arttraining.ui.live.bean.ParseCommentListBean;
import com.example.kk.arttraining.ui.live.bean.ParseMemerListBean;
import com.example.kk.arttraining.ui.live.bean.LiveBeingBean;
import com.example.kk.arttraining.ui.live.bean.ParseTimeTableBean;
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


    //直播封面列表
    @POST(Config.API_LIVE_LIST)
    @FormUrlEncoded
    Call<LiveList> liveList(@FieldMap Map<String,Object> map);

    //直播状态
    @POST(Config.API_LIVE_ENTER)
    @FormUrlEncoded
    Call<LiveListBean> liveType(@FieldMap Map<String,Object> map);

    //直播未开始
    @POST(Config.API_WAIT_LIVE)
    @FormUrlEncoded
    Call<LiveWaitBean> liveWait(@FieldMap Map<String,Object> map);


    //直播结束
    @POST(Config.API_FINISH_LIVE)
    @FormUrlEncoded
    Call<LiveFinishBean> liveFinish(@FieldMap Map<String,Object> map);


    //进入直播房间
    @POST(Config.API_CLASS_LIVE_BEING)
    @FormUrlEncoded
    Call<LiveBeingBean> joinLiveRoom(@FieldMap Map<String,Object> map);

    //退出直播间
    @POST(Config.API_CLASS_LIVE_EXIT)
    @FormUrlEncoded
    Call<NoDataResponseBean> exitLiveRoom(@FieldMap Map<String,Object> map);

    //获取播放的url
    @POST(Config.API_CLASS_LIVE_PLAY_URL)
    @FormUrlEncoded
    Call<NoDataResponseBean> getLivePlayUrl(@FieldMap Map<String,Object> map);

    //评论
    @POST(Config.API_LIVE_CREATE_COMMENT)
    @FormUrlEncoded
    Call<NoDataResponseBean> liveCreateComment(@FieldMap Map<String,Object> map);


    //获取直播间评论列表
    @POST(Config.API_LIVE_COMMENT_LIST)
    @FormUrlEncoded
    Call<ParseCommentListBean> getCommentList(@FieldMap Map<String,Object> map);

    //获取成员列表
    @POST(Config.API_LIVE_MEMBER_LIST)
    @FormUrlEncoded
    Call<ParseMemerListBean> getMemberList(@FieldMap Map<String,Object> map);


    //对直播点赞
    @POST(Config.API_LIVE_CREATE_LIKE)
    @FormUrlEncoded
    Call<NoDataResponseBean> createLike(@FieldMap Map<String,Object> map);


    //获取直播课程
    @POST(Config.API_LIVE_COURSELIST)
    @FormUrlEncoded
    Call<ParseTimeTableBean> getTableTable(@FieldMap Map<String,Object> map);
}
