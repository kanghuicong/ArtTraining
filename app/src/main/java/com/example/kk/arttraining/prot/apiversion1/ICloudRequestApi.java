package com.example.kk.arttraining.prot.apiversion1;

import com.example.kk.arttraining.bean.modelbean.NoDataResponseBean;
import com.example.kk.arttraining.pay.bean.RechargeBean;
import com.example.kk.arttraining.pay.bean.RechargeOrderBean;
import com.example.kk.arttraining.prot.rxjava_retrofit.BaseModel;
import com.example.kk.arttraining.ui.me.bean.CloudContentBean;
import com.example.kk.arttraining.ui.me.bean.ScoreBean;
import com.example.kk.arttraining.utils.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 作者：wschenyongyin on 2017/3/2 16:44
 * 说明:云币接口
 */
public interface ICloudRequestApi {

    //创建充值订单
    @POST(Config.API_RECHARGE_ICLOUD_CREATE)
    @FormUrlEncoded
    Observable<BaseModel<RechargeOrderBean>> createICloudOrder(@FieldMap HashMap<String, Object> map);

    //获取云币充值列表
    @POST(Config.API_RECHARGE_ICLOUD_LIST)
    @FormUrlEncoded
    Observable<BaseModel<List<RechargeBean>>> getRechargeList(@FieldMap HashMap<String, Object> map);

    //更新充值订单状态
    @POST(Config.API_CLOUD_UPDATE)
    @FormUrlEncoded
    Observable<BaseModel<Double>> updateOrder(@FieldMap HashMap<String, Object> map);

    //查询用户当前云币
    @POST(Config.API_CLOUD_QUERY)
    @FormUrlEncoded
    Observable<BaseModel<Double>> queryCloud(@FieldMap Map<String, Object> map);

    //消费云币送礼物
    @POST(Config.API_CLOUD_CONSUME)
    @FormUrlEncoded
    Observable<BaseModel<String>> consumeCloud(@FieldMap Map<String, Object> map);

    //查询云币详情列表接口
    @POST(Config.API_CLOUD_DETAIL)
    @FormUrlEncoded
    Observable<BaseModel<List<CloudContentBean>>> QueryDetailCloud(@FieldMap Map<String, Object> map);

}
