package com.example.kk.arttraining.ui.homePage.function.homepage;

import com.example.kk.arttraining.bean.modelbean.NoDataResponseBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/12/14.
 * QQ邮箱:515849594@qq.com
 *偷听偷看
 */
public class ReadTecComment {

    public static void getReadTecComment(int comm_id, int host_id, String host_type) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("comm_id", comm_id);
        map.put("host_id", host_id);
        map.put("host_type", "tec");


        Callback<NoDataResponseBean> callback = new Callback<NoDataResponseBean>() {
            @Override
            public void onResponse(Call<NoDataResponseBean> call, Response<NoDataResponseBean> response) {

            }

            @Override
            public void onFailure(Call<NoDataResponseBean> call, Throwable t) {

            }
        };

        Call<NoDataResponseBean> call = HttpRequest.getStatusesApi().teccommentRead(map);
        call.enqueue(callback);
    }
}
