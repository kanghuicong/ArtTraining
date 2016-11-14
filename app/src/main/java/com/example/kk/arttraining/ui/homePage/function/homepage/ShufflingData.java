package com.example.kk.arttraining.ui.homePage.function.homepage;

import com.example.kk.arttraining.bean.BannerBean;
import com.example.kk.arttraining.bean.parsebean.HeadNewsListBean;
import com.example.kk.arttraining.bean.parsebean.ParseBannerBean;
import com.example.kk.arttraining.ui.homePage.prot.IShuffling;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/11/8.
 * QQ邮箱:515849594@qq.com
 */
public class ShufflingData {

    IShuffling iShuffling;

    public ShufflingData(IShuffling iShuffling) {
        this.iShuffling = iShuffling;
    }

    public void getShufflingData() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", "");

        Callback<ParseBannerBean> callback = new Callback<ParseBannerBean>() {
            @Override
            public void onResponse(Call<ParseBannerBean> call, Response<ParseBannerBean> response) {
                ParseBannerBean parseBannerBean = response.body();
                if (response.body() != null) {
                    if (parseBannerBean.getError_code().equals("0")) {
                        iShuffling.getShuffling(parseBannerBean.getBanners());
                    } else {
                        iShuffling.OnShufflingFailure(parseBannerBean.getError_code());
                    }
                }
            }
            @Override
            public void onFailure(Call<ParseBannerBean> call, Throwable t) {
                iShuffling.OnShufflingFailure("onFailure");
            }
        };

        Call<ParseBannerBean> call = HttpRequest.getStatusesApi().bannerList(map);
        call.enqueue(callback);
    }
}
