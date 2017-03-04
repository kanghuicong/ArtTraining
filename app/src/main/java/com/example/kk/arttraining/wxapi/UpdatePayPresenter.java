package com.example.kk.arttraining.wxapi;

import com.example.kk.arttraining.bean.modelbean.GeneralBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/28 09:50
 * 说明:更新订单为支付成功
 */
public class UpdatePayPresenter {

    UpdateOrderPaySuccess updateOrderPaySuccess;

    public UpdatePayPresenter(UpdateOrderPaySuccess updateOrderPaySuccess) {
        this.updateOrderPaySuccess = updateOrderPaySuccess;

    }

    //更新订单状态为支付成功
    public void updateOrder(Map<String, Object> map) {

        final Callback<GeneralBean> callback = new Callback<GeneralBean>() {
            @Override
            public void onResponse(Call<GeneralBean> call, Response<GeneralBean> response) {
                UIUtil.showLog("updateOrder", "onResponse--------->" + response.code() + "---->" + response.message());
                GeneralBean generalBean = response.body();

                if (generalBean != null) {
                    if (generalBean.getError_code().equals("0")) {
                        updateOrderPaySuccess.Success();
                    } else {
                        updateOrderPaySuccess.Failure(generalBean.getError_code(), generalBean.getError_msg());
                    }

                } else {
                    updateOrderPaySuccess.Failure(response.code() + "", Config.Connection_ERROR_TOAST);
                }
            }

            @Override
            public void onFailure(Call<GeneralBean> call, Throwable t) {
                UIUtil.showLog("updateOrder", "onFailure--------->" + t.getMessage() + "------>" + t.getCause());
                updateOrderPaySuccess.Failure(Config.Connection_Failure, Config.Connection_ERROR_TOAST);
                //更新失败，重新请求
                call.request();
            }
        };
        Call<GeneralBean> call = HttpRequest.getPayApi().UpdateOrder(map);
        call.enqueue(callback);

    }


}
