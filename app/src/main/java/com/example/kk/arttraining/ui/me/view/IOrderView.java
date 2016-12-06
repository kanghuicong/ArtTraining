package com.example.kk.arttraining.ui.me.view;

import com.example.kk.arttraining.bean.OrderBean;
import com.example.kk.arttraining.bean.parsebean.ParseOrderListBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/22 20:47
 * 说明:我的订单activity接口
 */
public interface IOrderView {
    //获取全部订单数据
    void getAllOrder(String type);

    //获取待付款订单数据
    void unPayOrder(String type);

    //获取已付款订单数据
    void AlreadyPaid(String type);

    //刷新成功
    void SuccessRefresh(List<OrderBean> payOrderList);

    void SuccessLoad(List<OrderBean> payOrderList);




    //请求数据失败
    void showFailedError(String error_code, String errorMsg);
}
