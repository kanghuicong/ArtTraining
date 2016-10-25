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
    void getAllOrder(ParseOrderListBean allOrderList);

    //获取待付款订单数据
    void unPayOrder(ParseOrderListBean unPayOrderList);

    //获取已付款订单数据
    void AlreadyPaid(ParseOrderListBean payOrderList);

    //跳转到付款页面
    void ToPayActivity();

    //跳转到订单详情页面
    void toOrderDetail(OrderBean orderInfoBean);

    //显示加载dialog
    void showLoging();

    //隐藏加载dialog
    void hideLoading();

    //请求数据失败
    void showFailedError(String error_code,String errorMsg);
}
