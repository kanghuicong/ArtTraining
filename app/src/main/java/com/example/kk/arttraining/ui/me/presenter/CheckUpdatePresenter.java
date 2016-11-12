package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.utils.Config;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/10/28 16:25
 * 说明:
 */
public class CheckUpdatePresenter {

    public void checkUpdate(){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid",Config.UID);
        map.put("","");

        Callback callback=new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        };


    }
}
