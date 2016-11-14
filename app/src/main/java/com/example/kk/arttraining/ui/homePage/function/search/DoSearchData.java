package com.example.kk.arttraining.ui.homePage.function.search;

import com.example.kk.arttraining.bean.parsebean.StatusesBean;
import com.example.kk.arttraining.ui.homePage.bean.SearchHomepagerBean;
import com.example.kk.arttraining.ui.homePage.prot.ISearch;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.JsonTools;
import com.example.kk.arttraining.utils.UIUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/11/11.
 * QQ邮箱:515849594@qq.com
 */

public class DoSearchData {
    static ISearch iSearch;

    public DoSearchData(ISearch iSearch) {
        this.iSearch = iSearch;
    }

    public void getDynamicData() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", "lll");
        map.put("uid", Config.UID);

        Callback<SearchHomepagerBean> callback = new Callback<SearchHomepagerBean>() {
            @Override
            public void onResponse(Call<SearchHomepagerBean> call, Response<SearchHomepagerBean> response) {
                SearchHomepagerBean statusesBean = response.body();
                if (response.body() != null) {
                    if (statusesBean.getError_code().equals("0")) {
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(statusesBean.getSearch());
                        List<Map<String, Object>> mapList = JsonTools.SearchStatuses(jsonString);
                        iSearch.getDoSearchData(mapList);
                    } else {
                        iSearch.OnFailure(statusesBean.getError_code());
                    }
                } else {
                    iSearch.OnFailure("failure");
                }
            }
            @Override
            public void onFailure(Call<SearchHomepagerBean> call, Throwable t) {
                iSearch.OnFailure("failure");
            }
        };
        Call<SearchHomepagerBean> call = HttpRequest.getCommonApi().searchPublic(map);
        call.enqueue(callback);
    }
}
