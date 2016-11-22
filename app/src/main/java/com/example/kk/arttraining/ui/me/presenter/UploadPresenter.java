package com.example.kk.arttraining.ui.me.presenter;

import android.content.Context;

import com.example.kk.arttraining.bean.GeneralBean;
import com.example.kk.arttraining.sqlite.bean.UploadBean;
import com.example.kk.arttraining.sqlite.dao.UploadDao;
import com.example.kk.arttraining.ui.me.view.IUploadFragment;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/7 08:36
 * 说明:上传列表处理类
 */
public class UploadPresenter {
    public IUploadFragment iUploadFragment;

    public UploadPresenter(IUploadFragment iUploadFragment) {
        this.iUploadFragment = iUploadFragment;
    }

    //从本地数据库查询上传列表信息
    public void getLocalUploadData(Context context, String type) {
        UploadDao uploadDao = new UploadDao(context);
        List<UploadBean> uploadBeanList = uploadDao.query(type);
        UIUtil.showLog("UploadPresenter-->", "true");
        iUploadFragment.onSuccess(uploadBeanList);
    }

    public void updateOrder(Map<String, Object> map) {
        Callback<GeneralBean> callback = new Callback<GeneralBean>() {
            @Override
            public void onResponse(Call<GeneralBean> call, Response<GeneralBean> response) {
                UIUtil.showLog("updateOrder", "onResponse--------->" + response.code() + "---->" + response.message());
                GeneralBean generalBean = response.body();

                if (generalBean != null) {
                    if (generalBean.getError_code().equals("0")) {
                        iUploadFragment.UpdateOrderSuccess();
                    } else {
                        iUploadFragment.UpdateOrderFailure(generalBean.getError_code(), generalBean.getError_msg());
                    }

                } else {
                    iUploadFragment.UpdateOrderFailure(response.code() + "", Config.Connection_ERROR_TOAST);
                }
            }

            @Override
            public void onFailure(Call<GeneralBean> call, Throwable t) {
                UIUtil.showLog("updateOrder", "onFailure--------->" + t.getMessage() + "------>" + t.getCause());
                iUploadFragment.UpdateOrderFailure(Config.Connection_Failure, Config.Connection_ERROR_TOAST);
            }
        };
        Call<GeneralBean> call = HttpRequest.getPayApi().UpdateOrder(map);
        call.enqueue(callback);

    }
}
