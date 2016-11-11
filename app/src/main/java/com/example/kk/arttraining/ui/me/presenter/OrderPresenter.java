package com.example.kk.arttraining.ui.me.presenter;

import android.content.Context;

import com.example.kk.arttraining.bean.parsebean.ParseOrderListBean;
import com.example.kk.arttraining.ui.me.view.IOrderView;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/10/23 11:42
 * 说明:订单处理类
 */
public class OrderPresenter {
    private IOrderView iOrderView;
    private ParseOrderListBean OrderListBean;
    private Context context;

    //构造函数
    public OrderPresenter(IOrderView iOrderView, Context context) {
        this.iOrderView = iOrderView;
        this.context = context;

    }

    //获取全部订单信息
    public void getAllOrderData() {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);

        Callback<ParseOrderListBean> callback = new Callback<ParseOrderListBean>() {
            @Override
            public void onResponse(Call<ParseOrderListBean> call, Response<ParseOrderListBean> response) {
                if (response.body() != null) {
                    OrderListBean = response.body();
                    if (OrderListBean.getError_code().equals("0")) {
                        iOrderView.getAllOrder(OrderListBean);
                    } else {
                        iOrderView.showFailedError(OrderListBean.getError_code(), OrderListBean.getError_msg());

                    }
                } else {
                    iOrderView.showFailedError("failure", Config.REQUEST_FAILURE);
                }

            }

            @Override
            public void onFailure(Call<ParseOrderListBean> call, Throwable t) {
                iOrderView.showFailedError("failure", Config.REQUEST_FAILURE);
            }
        };
        Call<ParseOrderListBean> call = HttpRequest.getUserApi().getOrderList(map);

    }


    //获取未付款订单
    public void getUnPayOrderData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("status", "no_pay");

        Callback<ParseOrderListBean> callback = new Callback<ParseOrderListBean>() {
            @Override
            public void onResponse(Call<ParseOrderListBean> call, Response<ParseOrderListBean> response) {
                if (response.body() != null) {
                    OrderListBean = response.body();
                    if (OrderListBean.getError_code().equals("0")) {
                        iOrderView.getAllOrder(OrderListBean);
                    } else {
                        iOrderView.showFailedError(OrderListBean.getError_code(), OrderListBean.getError_msg());

                    }
                } else {
                    iOrderView.showFailedError("failure", Config.REQUEST_FAILURE);
                }
            }

            @Override
            public void onFailure(Call<ParseOrderListBean> call, Throwable t) {
                iOrderView.showFailedError("failure", Config.REQUEST_FAILURE);
            }
        };
        Call<ParseOrderListBean> call = HttpRequest.getUserApi().getOrderList(map);
    }


    //获取已付款的订单
    public void getAlreadyPayOrderData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("status", "pay");
        Callback<ParseOrderListBean> callback = new Callback<ParseOrderListBean>() {
            @Override
            public void onResponse(Call<ParseOrderListBean> call, Response<ParseOrderListBean> response) {
                if (response.body() != null) {
                    OrderListBean = response.body();
                    if (OrderListBean.getError_code().equals("0")) {
                        iOrderView.getAllOrder(OrderListBean);
                    } else {
                        iOrderView.showFailedError(OrderListBean.getError_code(), OrderListBean.getError_msg());

                    }
                } else {
                    iOrderView.showFailedError("failure", Config.REQUEST_FAILURE);
                }
            }

            @Override
            public void onFailure(Call<ParseOrderListBean> call, Throwable t) {
                iOrderView.showFailedError("failure", Config.REQUEST_FAILURE);
            }
        };
        Call<ParseOrderListBean> call = HttpRequest.getUserApi().getOrderList(map);
    }


}
