package com.example.kk.arttraining.ui.valuation.presenter;

import com.example.kk.arttraining.bean.NoDataResponseBean;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.ui.valuation.view.IValuationMain;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/10/31 09:52
 * 说明:
 */
public class ValuationMainPresenter {
    private IValuationMain iValuationMain;

    public ValuationMainPresenter(IValuationMain iValuationMain) {
        this.iValuationMain = iValuationMain;
    }

    //提交订单
    public void CommitOrder(Map<String, String> map) {
        if (CheckOrderData()) {
            iValuationMain.showLoading();
            Callback<NoDataResponseBean> callback = new Callback<NoDataResponseBean>() {
                @Override
                public void onResponse(Call<NoDataResponseBean> call, Response<NoDataResponseBean> response) {
                    iValuationMain.hideLoading();
                    if (response.body() != null) {
                        NoDataResponseBean responseBean = response.body();
                        if (responseBean.getError_code().equals("0")) {
                            iValuationMain.CommitOrder();
                        } else {
                            iValuationMain.OnFailure(responseBean.getError_code());
                        }
                    } else {
                        iValuationMain.OnFailure("500");
                    }
                }

                @Override
                public void onFailure(Call<NoDataResponseBean> call, Throwable t) {
                    iValuationMain.hideLoading();
                    iValuationMain.OnFailure("500");

                }
            };

            Call<NoDataResponseBean> call = HttpRequest.getPayApi().commitOrder(map);
            call.enqueue(callback);
        } else {
            iValuationMain.OnFailure("501");
        }


    }

    //检查订单信息是否填写完整
    Boolean CheckOrderData() {
        String production_describe = iValuationMain.getProductionDescribe();
        String production_name = iValuationMain.getProductionName();
        String production_path = iValuationMain.getProductionPath();
        TecInfoBean tecInfoBean = iValuationMain.getTeacherInfo();

        if (production_describe != null && production_name != null && production_path != null && tecInfoBean != null) {
            return true;
        } else {
            return false;
        }
    }
}
