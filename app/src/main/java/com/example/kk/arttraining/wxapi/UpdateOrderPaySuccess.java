package com.example.kk.arttraining.wxapi;

/**
 * 作者：wschenyongyin on 2016/11/28 09:44
 * 说明:更新支付状态
 */
public interface UpdateOrderPaySuccess {

    void updateOrder();

    void Success();

    void Failure(String error_code,String error_msg);


}
