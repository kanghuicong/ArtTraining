package com.example.kk.arttraining.ui.me.presenter;

import android.content.Context;
import android.os.Handler;


import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.ui.me.view.IUserLoginView;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

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
    private Context context;

    public UserLoginPresenter(IUserLoginView iUserLoginView) {
        this.iUserLoginView = iUserLoginView;

    }

    //请求后台验证
    public void loginRequest(String name, String pwd) {
        iUserLoginView.showLoading();

        Map<String, String> map = new HashMap<String, String>();
        map.put("name", name);
        map.put("pwd", pwd);

        Callback<UserLoginBean> callback = new Callback<UserLoginBean>() {
            @Override
            public void onResponse(Call<UserLoginBean> call, Response<UserLoginBean> response) {
                if (response.body() != null) {
                    UserLoginBean userBean = response.body();

                    if (userBean.getError_code().equals("0")) {
                        // TODO: 2016/10/17 将用户信息存到本地数据库
                        iUserLoginView.SaveUserInfo(userBean);
                        iUserLoginView.ToMainActivity(userBean);
                        //登陆成功后将access_token赋值到全局变量
                        Config.ACCESS_TOKEN = userBean.getAccess_token();
                    } else {
                        iUserLoginView.showFailedError(userBean.getError_code());
                    }
                } else {
                    iUserLoginView.showFailedError(Config.Connection_Failure);
                }
                iUserLoginView.hideLoading();
            }

            @Override
            public void onFailure(Call<UserLoginBean> call, Throwable t) {
                iUserLoginView.showFailedError(Config.Connection_Failure);
                iUserLoginView.hideLoading();
            }
        };


        Call<UserLoginBean> call = HttpRequest.getUserApi().Login(map);
        call.enqueue(callback);

    }

    //登陆
    public void Login() {
        String name = iUserLoginView.getUserName();
        String pwd = iUserLoginView.getPassword();
        if (name.equals("")) {
            iUserLoginView.showFailedError("101");
        } else if (StringUtils.isPhone(name)) {
            iUserLoginView.showFailedError("103");
        } else if (pwd.equals("")) {
            iUserLoginView.showFailedError("102");
        } else {
            loginRequest(name, pwd);
        }
    }


}
