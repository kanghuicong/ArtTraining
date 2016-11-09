package com.example.kk.arttraining.ui.me.presenter;

import android.content.Context;

import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.sqlite.dao.UserDao;
import com.example.kk.arttraining.sqlite.dao.UserDaoImpl;
import com.example.kk.arttraining.ui.me.bean.UserCountBean;
import com.example.kk.arttraining.ui.me.view.IMeMain;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.PreferencesUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/9 09:34
 * 说明:我的页面处理类
 */
public class MeMainPresenter {
    IMeMain iMeMain;
    private UserLoginBean userInfoBean;
    UserLoginBean serverUserBean = null;
    private Context context;

    public MeMainPresenter(IMeMain iMeMain) {
        this.iMeMain = iMeMain;
    }

    //获取用户信息
    public  void getUserInfoData(Context context) {
        this.context=context;
        UserLoginBean userInfoBean = getLocalUserInfo(context);
        if (userInfoBean == null) {
            //从服务器获取用户信息成功
            UserLoginBean serverUserBean = getServerUserinfo();
            if (serverUserBean != null) {
                iMeMain.getUserInfoSuccess(serverUserBean);
            } else {
                // TODO: 2016/11/9  获取用户信息失败
            }
        }
        //从本地获取用户信息成功
        else {
            iMeMain.getUserInfoSuccess(userInfoBean);
        }

    }

    //从本地数据库获取用户信息
    public  UserLoginBean getLocalUserInfo(Context context) {
        UserDao userDao = new UserDaoImpl(context);
        userInfoBean = userDao.QueryAll(Config.UID);
        return userInfoBean;
    }

    //从服务器获取用户信息
    public  UserLoginBean getServerUserinfo() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);

        Callback<UserLoginBean> callback = new Callback<UserLoginBean>() {
            @Override
            public void onResponse(Call<UserLoginBean> call, Response<UserLoginBean> response) {
                if (response.body() != null) {
                    serverUserBean = response.body();
                } else {
                }
            }

            @Override
            public void onFailure(Call<UserLoginBean> call, Throwable t) {
                serverUserBean = null;
            }
        };

        Call<UserLoginBean> call = HttpRequest.getUserApi().userinfo(map);
        call.enqueue(callback);
        return serverUserBean;
    }

    //获取用户统计数量
    public void getUserCount() {
        Callback<UserCountBean> callback = new Callback<UserCountBean>() {
            @Override
            public void onResponse(Call<UserCountBean> call, Response<UserCountBean> response) {
                UserCountBean userCountBean = response.body();
                if (userCountBean != null) {
                    if (userCountBean.getError_code().equals("0")) {
                        iMeMain.getUserCountSuccess(userCountBean);
                        UserDao userDao = new UserDaoImpl(context);
                        userDao.Insert(userInfoBean);
                        Config.userBean = userInfoBean;
                    } else {
                        iMeMain.getUserCountFailure(userCountBean.getError_code());
                    }

                } else {
                    iMeMain.getUserCountFailure("failure");
                }

            }

            @Override
            public void onFailure(Call<UserCountBean> call, Throwable t) {
                iMeMain.getUserCountFailure("failure");
            }
        };
//        Call<UserCountBean > call=HttpRequest.getUserApi().userinfo()

    }

}
