package com.example.kk.arttraining.prot.apiversion1;

import com.example.kk.arttraining.pay.bean.WeChatBean;
import com.example.kk.arttraining.prot.rxjava_retrofit.BaseModel;
import com.example.kk.arttraining.ui.me.bean.ScoreBean;
import com.example.kk.arttraining.utils.Config;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 作者：wschenyongyin on 2017/2/28 15:35
 * 说明:积分接口
 */
public interface ScoreRequestApi {
    //查询用户当前积分
    @POST(Config.API_SCORE_QUERY)
    @FormUrlEncoded
    Observable<BaseModel<Integer>> queryScore(@FieldMap Map<String, Object> map);

    //消费积分送礼物
    @POST(Config.API_SCORE_CONSUME)
    @FormUrlEncoded
    Observable<BaseModel<String>> consumeScore(@FieldMap Map<String, Object> map);

    //查询积分详情列表接口
    @POST(Config.API_SCORE_DETAIL)
    @FormUrlEncoded
    Observable<BaseModel<List<ScoreBean>>> QueryDetailScore(@FieldMap Map<String, Object> map);

    //获取微信支付的必要信息
    @POST(Config.API_RECHARFE_PAY)
    @FormUrlEncoded
    Observable<BaseModel<WeChatBean>> getWxPartnerId(@FieldMap Map<String, Object> map);
}
