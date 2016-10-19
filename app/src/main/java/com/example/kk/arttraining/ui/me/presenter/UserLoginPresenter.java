package com.example.kk.arttraining.ui.me.presenter;

import android.os.Handler;


import com.example.kk.arttraining.bean.ResponseObject;
import com.example.kk.arttraining.bean.UserInfoBean;
import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.dao.UserDao;
import com.example.kk.arttraining.dao.UserDaoImpl;
import com.example.kk.arttraining.ui.me.view.IUserLoginView;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.GsonTools;
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
        iUserLoginView.showLoading();
        Callback<UserLoginBean> callback = new Callback<UserLoginBean>() {
            @Override
            public void onResponse(Call<UserLoginBean> call, Response<UserLoginBean> response) {
                if (response.body() != null) {
                    UserLoginBean userBean = response.body();

                    // TODO: 2016/10/17 将用户信息存到本地数据库
                    iUserLoginView.SaveUserInfo(userBean);
                    iUserLoginView.ToMainActivity(userBean);
                    //登陆成功后将access_token赋值到全局变量
                    Config.ACCESS_TOKEN = userBean.getAccess_token();
                } else {
                    iUserLoginView.showFailedError();
                }
                iUserLoginView.hideLoading();
            }

            @Override
            public void onFailure(Call<UserLoginBean> call, Throwable t) {
                iUserLoginView.showFailedError();
                iUserLoginView.hideLoading();
            }
        };


        Call<UserLoginBean> call = HttpRequest.getApiService().Login(iUserLoginView.getUserName(), iUserLoginView.getPassword());
        call.enqueue(callback);

    }


}
