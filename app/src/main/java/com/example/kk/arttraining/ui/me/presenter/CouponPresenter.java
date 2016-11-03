package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.ui.me.bean.ParseCouponBean;
import com.example.kk.arttraining.ui.me.view.ICouponActivity;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/1 19:16
 * 说明:
 */
public class CouponPresenter {
    private ICouponActivity iCouponActivity;

    public CouponPresenter(ICouponActivity iCouponActivity) {
        this.iCouponActivity = iCouponActivity;

    }

    //加载数据
    public void getData(Map<String, String> map) {
        Callback<ParseCouponBean> callback = new Callback<ParseCouponBean>() {
            @Override
            public void onResponse(Call<ParseCouponBean> call, Response<ParseCouponBean> response) {

                if (response.body() != null) {
                    ParseCouponBean parseCouponBean=response.body();
                    if(parseCouponBean.getError_code().equals("0")){
                        iCouponActivity.getDatas(parseCouponBean.getCoupons());
                    }else{

                        iCouponActivity.onFailure(parseCouponBean.getError_code());
                    }

                }else{
                    iCouponActivity.onFailure("500");
                }
            }

            @Override
            public void onFailure(Call<ParseCouponBean> call, Throwable t) {
                iCouponActivity.onFailure("500");
            }
        };
        Call<ParseCouponBean> call = HttpRequest.getUserApi().getCouponList(map);
        call.enqueue(callback);


    }
}
