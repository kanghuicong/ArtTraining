package com.example.kk.arttraining.ui.homePage.function.search;

import com.example.kk.arttraining.bean.parsebean.SearchBean;
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
    ISearch iSearch;

    public DoSearchData(ISearch iSearch) {
        this.iSearch = iSearch;
    }

    //综合搜索
    public void getSearchData(String key) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("key",key);

        Callback<SearchHomepagerBean> callback = new Callback<SearchHomepagerBean>() {
            @Override
            public void onResponse(Call<SearchHomepagerBean> call, Response<SearchHomepagerBean> response) {
                SearchHomepagerBean statusesBean = response.body();
                if (response.body() != null) {
                    if (statusesBean.getError_code().equals("0")) {
//                        iSearch.getDoSearchData(statusesBean);
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

    //搜索机构
    public void getInstitutionSearchData(String key) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("key",key);

        Callback<SearchBean> callback = new Callback<SearchBean>() {
            @Override
            public void onResponse(Call<SearchBean> call, Response<SearchBean> response) {
                SearchBean searchBean = response.body();
                if (response.body() != null) {
                    if (searchBean.getError_code().equals("0")) {
                        iSearch.getInstitutionSearch(searchBean.getOrg());
                    } else {
                        iSearch.OnSearchEmpty(searchBean.getError_code());
                    }
                } else {
                    iSearch.OnSearchEmpty("OnFailure");
                }
            }
            @Override
            public void onFailure(Call<SearchBean> call, Throwable t) {
                iSearch.OnSearchFailure("OnFailure");
            }
        };
        Call<SearchBean> call = HttpRequest.getCommonApi().searchOrg(map);
        call.enqueue(callback);
    }

    //院校搜索
    public void getSchoolSearchData(String key) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("key",key);

        Callback<SearchBean> callback = new Callback<SearchBean>() {
            @Override
            public void onResponse(Call<SearchBean> call, Response<SearchBean> response) {
                SearchBean searchBean = response.body();
                if (response.body() != null) {
                    if (searchBean.getError_code().equals("0")) {

                    } else {

                    }
                } else {

                }
            }
            @Override
            public void onFailure(Call<SearchBean> call, Throwable t) {

            }
        };
        Call<SearchBean> call = HttpRequest.getCommonApi().searchOrg(map);
        call.enqueue(callback);
    }

    //老师搜索
    public void getTeacherSearchData(String key) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("key",key);

        Callback<SearchBean> callback = new Callback<SearchBean>() {
            @Override
            public void onResponse(Call<SearchBean> call, Response<SearchBean> response) {
                SearchBean searchBean = response.body();
                if (response.body() != null) {
                    if (searchBean.getError_code().equals("0")) {
                        iSearch.getTeacherSearch(searchBean.getTec());
                    } else {
                        iSearch.OnSearchEmpty(searchBean.getError_msg());
                    }
                } else {
                    iSearch.OnSearchEmpty(searchBean.getError_msg());
                }
            }
            @Override
            public void onFailure(Call<SearchBean> call, Throwable t) {
                iSearch.OnSearchFailure("OnFailure");
            }
        };
        Call<SearchBean> call = HttpRequest.getCommonApi().searchTec(map);
        call.enqueue(callback);
    }
}
