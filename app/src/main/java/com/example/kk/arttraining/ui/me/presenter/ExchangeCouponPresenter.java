package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.bean.GeneralBean;
import com.example.kk.arttraining.bean.NoDataResponseBean;
import com.example.kk.arttraining.ui.me.view.IExchageCouponView;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/28 14:16
 * 说明:
 */
public class ExchangeCouponPresenter {
    IExchageCouponView iExchageCouponView;

    public ExchangeCouponPresenter(IExchageCouponView iExchageCouponView) {
        this.iExchageCouponView = iExchageCouponView;

    }

    public void exchangeCoupon(Map<String, Object> map) {
        Callback<NoDataResponseBean> callback = new Callback<NoDataResponseBean>() {
            @Override
            public void onResponse(Call<NoDataResponseBean> call, Response<NoDataResponseBean> response) {
                NoDataResponseBean responseBean = response.body();
                if (responseBean != null) {
                    if (responseBean.getError_code().equals("0")) {
                        iExchageCouponView.Success();
                    } else {
                        iExchageCouponView.Failure(responseBean.getError_code(), responseBean.getError_msg());
                    }
                } else {
                    iExchageCouponView.Failure(response.code() + "", Config.Connection_ERROR_TOAST);
                }
            }

            @Override
            public void onFailure(Call<NoDataResponseBean> call, Throwable t) {
                iExchageCouponView.Failure(Config.Connection_Failure, Config.Connection_ERROR_TOAST);
            }
        };
        Call<NoDataResponseBean> call = HttpRequest.getUserApi().exchangeCoupon(map);
        call.enqueue(callback);
    }

}
