package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.bean.UpdateBean;
import com.example.kk.arttraining.ui.me.view.IUpdateUserInfo;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/15 20:30
 * 说明:用于更新用户信息
 */
public class UpdatePresenter {
    IUpdateUserInfo iUpdateUserInfo;

    public UpdatePresenter(IUpdateUserInfo iUpdateUserInfo) {
        this.iUpdateUserInfo = iUpdateUserInfo;
    }

    public void updateUserInfo(Map<String, Object> map) {
        Callback<UpdateBean> callback = new Callback<UpdateBean>() {
            @Override
            public void onResponse(Call<UpdateBean> call, Response<UpdateBean> response) {
                UpdateBean updateBean = response.body();
                if (updateBean != null) {
                    if (updateBean.getError_code().equals("0")) {
                        iUpdateUserInfo.SuccessUpdate(updateBean);
                    } else {
                        iUpdateUserInfo.FailureUpdate(updateBean.getError_code(), updateBean.getError_msg());
                    }
                } else {
                    iUpdateUserInfo.FailureUpdate(response.code() + "", Config.Connection_ERROR_TOAST);
                }
            }

            @Override
            public void onFailure(Call<UpdateBean> call, Throwable t) {
                iUpdateUserInfo.FailureUpdate(t.getMessage() + "", Config.Connection_ERROR_TOAST);
            }
        };
        Call<UpdateBean> call = HttpRequest.getUserApi().setUserInfo(map);
        call.enqueue(callback);
    }


}
