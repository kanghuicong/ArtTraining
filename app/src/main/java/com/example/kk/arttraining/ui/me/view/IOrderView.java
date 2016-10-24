package com.example.kk.arttraining.ui.me.view;

import com.example.kk.arttraining.bean.OrderInfoBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/22 20:47
 * 说明:我的订单activity接口
 */
public interface IOrderView {
    //获取全部订单数据
    void getAllOrder(List<OrderInfoBean> allOrderList);

    //获取待付款订单数据
    void unPayOrder(List<OrderInfoBean> unPayOrderList);

    //获取已付款订单数据
    void AlreadyPaid(List<OrderInfoBean> payOrderList);

    //跳转到付款页面
    void ToPayActivity();

    //跳转到订单详情页面
    void toOrderDetail(OrderInfoBean orderInfoBean);

    //显示加载dialog
    void showLoging();

    //隐藏加载dialog
    void hideLoading();
}
