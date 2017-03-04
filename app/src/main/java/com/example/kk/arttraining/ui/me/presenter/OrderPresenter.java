package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.bean.modelbean.GeneralBean;
import com.example.kk.arttraining.bean.parsebean.ParseOrderListBean;
import com.example.kk.arttraining.prot.apiversion1.GeneralResultListener;
import com.example.kk.arttraining.ui.me.view.IOrderView;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

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
    GeneralResultListener resultListener;
    //构造函数
    public OrderPresenter(IOrderView iOrderView) {
        this.iOrderView = iOrderView;

    }
    public OrderPresenter(GeneralResultListener resultListener) {
        UIUtil.showLog("GeneralResultListener--->","true");
        this.resultListener = resultListener;

    }

    //获取全部订单信息
    public void getAllOrderData(Map<String, Object> map, final String type) {



        Callback<ParseOrderListBean> callback = new Callback<ParseOrderListBean>() {
            @Override
            public void onResponse(Call<ParseOrderListBean> call, Response<ParseOrderListBean> response) {
                if (response.body() != null) {
                    OrderListBean = response.body();
                    UIUtil.showLog("订单信息----》onResponse",OrderListBean.toString());
                    if (OrderListBean.getError_code().equals("0")) {
                        if(type.equals("refresh")){
                            iOrderView.SuccessRefresh(OrderListBean.getOrders());
                        }else if(type.equals("load")){
                            iOrderView.SuccessLoad(OrderListBean.getOrders());
                        }


                    } else {
                        iOrderView.showFailedError(OrderListBean.getError_code(), OrderListBean.getError_msg());

                    }
                } else {
                    UIUtil.showLog("订单信息",response.code()+"------>"+response.message());
                    iOrderView.showFailedError("failure", Config.REQUEST_FAILURE);
                }

            }

            @Override
            public void onFailure(Call<ParseOrderListBean> call, Throwable t) {
                UIUtil.showLog("订单信息---->onFailure",t.getMessage()+"------>"+t.getCause());
                iOrderView.showFailedError("failure", Config.REQUEST_FAILURE);
            }
        };
        Call<ParseOrderListBean> call = HttpRequest.getUserApi().getOrderList(map);
        call.enqueue(callback);

    }


    //获取未付款订单
    public void getUnPayOrderData( Map<String, Object> map,final String type) {


        Callback<ParseOrderListBean> callback = new Callback<ParseOrderListBean>() {
            @Override
            public void onResponse(Call<ParseOrderListBean> call, Response<ParseOrderListBean> response) {
                if (response.body() != null) {
                    OrderListBean = response.body();
                    if (OrderListBean.getError_code().equals("0")) {
                        if(type.equals("refresh")){
                            iOrderView.SuccessRefresh(OrderListBean.getOrders());
                        }else if(type.equals("load")){
                            iOrderView.SuccessLoad(OrderListBean.getOrders());
                        }
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
        call.enqueue(callback);
    }


    //获取已付款的订单
    public void getAlreadyPayOrderData(Map<String, Object> map,final String type) {

        Callback<ParseOrderListBean> callback = new Callback<ParseOrderListBean>() {
            @Override
            public void onResponse(Call<ParseOrderListBean> call, Response<ParseOrderListBean> response) {
                if (response.body() != null) {
                    OrderListBean = response.body();
                    if (OrderListBean.getError_code().equals("0")) {
                        if(type.equals("refresh")){
                            iOrderView.SuccessRefresh(OrderListBean.getOrders());
                        }else if(type.equals("load")){
                            iOrderView.SuccessLoad(OrderListBean.getOrders());
                        }
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
        call.enqueue(callback);
    }

    //取消订单
    public void cancelOrder(Map<String, Object> map) {

        Callback<GeneralBean> callback = new Callback<GeneralBean>() {
            @Override
            public void onResponse(Call<GeneralBean> call, Response<GeneralBean> response) {
                GeneralBean generalBean = response.body();
                UIUtil.showLog("paypresenter--->onresponse:", response.code() + "--->" + response.message());
                if (generalBean != null) {
                    if (generalBean.getError_code().equals("0")) {
                        resultListener.GeneralSuccess();
                    } else {
                        resultListener.GeneralFailure(generalBean.getError_code(), generalBean.getError_msg());
                    }
                } else {
                    resultListener.GeneralFailure(response.code() + "", "请求失败");
                }
            }

            @Override
            public void onFailure(Call<GeneralBean> call, Throwable t) {
                UIUtil.showLog("paypresenter--->onFailure", t.getMessage() + "--->" + t.getCause());
                resultListener.GeneralFailure(Config.Connection_Failure, Config.Connection_ERROR_TOAST);
            }
        };
        Call<GeneralBean> call = HttpRequest.getPayApi().CancelOrder(map);
        call.enqueue(callback);
    }

}
