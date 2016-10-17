package com.example.kk.arttraining.ui.me.presenter;

import android.os.Handler;


import com.example.kk.arttraining.bean.ResponseObject;
import com.example.kk.arttraining.bean.UserInfoBean;
import com.example.kk.arttraining.ui.me.view.IUserLoginView;
import com.example.kk.arttraining.utils.HttpRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/10/15 17:04
 * 说明:
 */
public class UserLoginPresenter {
    private IUserLoginView iUserLoginView;
    private Handler handler = new Handler();

    public UserLoginPresenter(IUserLoginView iUserLoginView) {
        this.iUserLoginView = iUserLoginView;

    }

    public void login() {
        Callback<ResponseObject> callback = new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {

            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {

            }
        };


        Call<ResponseObject> call = HttpRequest.getApiService().Login("userId", "password");
        call.enqueue(callback);
    }


}
