package com.example.kk.arttraining.ui.homePage.function.homepage;

import com.example.kk.arttraining.bean.GeneralBean;
import com.example.kk.arttraining.ui.homePage.prot.IFollow;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/11/18.
 * QQ邮箱:515849594@qq.com
 */
public class FollowCreate {
    IFollow iFollow;
    public FollowCreate(IFollow iFollow) {
        this.iFollow = iFollow;
    }

    public void getFocus(String type, int follow_id) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("type", type);
        map.put("follow_id", follow_id);

        Callback<GeneralBean> callback = new Callback<GeneralBean>() {
            @Override
            public void onResponse(Call<GeneralBean> call, Response<GeneralBean> response) {
                GeneralBean generalBean = response.body();
                UIUtil.showLog("getFocus2", response.body()+"");
                if (response.body() != null) {
                    if (generalBean.getError_code().equals("0")) {
                        iFollow.getCreateFollow();
                    } else {
                        iFollow.getOnFollowFailure(generalBean.getError_msg());
                    }
                }
            }

            @Override
            public void onFailure(Call<GeneralBean> call, Throwable t) {
                iFollow.getOnFollowFailure("onFailure");
            }
        };
        Call<GeneralBean> call = HttpRequest.getStatusesApi().follow_create(map);
        call.enqueue(callback);
    }
}
