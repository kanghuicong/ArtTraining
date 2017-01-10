package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.content.Context;
import android.content.Intent;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.NoDataResponseBean;
import com.example.kk.arttraining.ui.homePage.activity.DynamicContentTeacherVideo;
import com.example.kk.arttraining.ui.homePage.prot.ITokenVerfy;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/12/15.
 * QQ邮箱:515849594@qq.com
 */
public class TokenVerfy {
    ITokenVerfy iTokenVerfy;

    public TokenVerfy(ITokenVerfy iTokenVerfy) {
        this.iTokenVerfy = iTokenVerfy;
    }

    public void getTokenVerfy() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);

        Callback<NoDataResponseBean> callback = new Callback<NoDataResponseBean>() {
            @Override
            public void onResponse(Call<NoDataResponseBean> call, Response<NoDataResponseBean> response) {
                NoDataResponseBean noDataResponseBean = response.body();
                if (response.body() != null) {
                    if (noDataResponseBean.getError_code().equals("0")) {
                        iTokenVerfy.TokenSuccess();
                    }else {
                        iTokenVerfy.TokenFailure(0);
                    }
                }else {
                    iTokenVerfy.TokenFailure(1);
                }
            }
            @Override
            public void onFailure(Call<NoDataResponseBean> call, Throwable t) {
                iTokenVerfy.TokenFailure(1);
            }
        };
        Call<NoDataResponseBean> call = HttpRequest.getCommonApi().tokenVerfy(map);
        call.enqueue(callback);
    }

    public static void Login(Context context,int flag) {
        if (flag == 0) {
            UIUtil.ToastshowShort(context, context.getResources().getString(R.string.toast_token_nvalid));
            context.startActivity(new Intent(context, UserLoginActivity.class));
        }else if(flag == 1){
            UIUtil.ToastshowShort(context, "网络连接失败");
        }else {
            UIUtil.ToastshowShort(context, context.getResources().getString(R.string.toast_user_login));
            context.startActivity(new Intent(context, UserLoginActivity.class));
        }
    }
}
