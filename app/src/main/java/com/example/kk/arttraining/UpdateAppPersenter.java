package com.example.kk.arttraining;

import com.example.kk.arttraining.IUpdateApp;
import com.example.kk.arttraining.bean.AppInfoBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/30 17:34
 * 说明:
 */
public class UpdateAppPersenter {
    IUpdateApp iMainActivity;

    public UpdateAppPersenter(IUpdateApp iMainActivity) {
        this.iMainActivity = iMainActivity;
    }

    public void updateApp(Map<String, Object> map) {

        Callback<AppInfoBean> callback = new Callback<AppInfoBean>() {
            @Override
            public void onResponse(Call<AppInfoBean> call, Response<AppInfoBean> response) {

                AppInfoBean appInfoBean = response.body();
//                UIUtil.showLog("appinfo----->",appInfoBean.toString());
                if (appInfoBean != null) {
                    if (appInfoBean.getError_code().equals("0")) {
                        iMainActivity.SuccessAppVersion(appInfoBean);
                    } else {
                        iMainActivity.FailureAppVersion(appInfoBean.getError_code(),appInfoBean.getError_msg());
                    }
                } else {
                    iMainActivity.FailureAppVersion(Config.Connection_NO_CONTENT,"已经是最新版本！");
                }
            }

            @Override
            public void onFailure(Call<AppInfoBean> call, Throwable t) {
                iMainActivity.FailureAppVersion(Config.Connection_NO_CONTENT,"已经是最新版本！");
            }
        };
        UIUtil.showLog("1111-------------->","true");
        Call<AppInfoBean> call = HttpRequest.getCommonApi().updateApp(map);
        UIUtil.showLog("222-------------->","true");
        call.enqueue(callback);
        UIUtil.showLog("333-------------->","true");

    }

}
