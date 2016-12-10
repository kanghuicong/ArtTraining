package com.example.kk.arttraining.ui.valuation.presenter;

import com.example.kk.arttraining.bean.parsebean.SearchBean;
import com.example.kk.arttraining.bean.parsebean.TecherList;
import com.example.kk.arttraining.ui.valuation.view.IValuationChooseTeacher;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/10/29 09:56
 * 说明:
 */
public class ChoserTeacherPresenter {
    IValuationChooseTeacher iValuationChooseTeacher;

    public ChoserTeacherPresenter(IValuationChooseTeacher iValuationChooseTeacher) {
        this.iValuationChooseTeacher = iValuationChooseTeacher;
    }

    //刷新获取数据
    public void RefreshData(String spec,String search_key) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("spec", spec);
        if (!search_key .equals("")) {
            map.put("key", search_key);
        }

        Callback<TecherList> callback = new Callback<TecherList>() {
            @Override
            public void onResponse(Call<TecherList> call, Response<TecherList> response) {
                UIUtil.showLog("ChoserTeacherPresenter.class","RefreshData_onResponse--->"+response.code()+"---->"+response.message());
                TecherList techerList = response.body();
                UIUtil.showLog("ChoserTeacherPresenter.class","techerList--->"+techerList.getError_code()+"");

                if (techerList != null) {
                    if (techerList.getError_code().equals("0")) {
                        iValuationChooseTeacher.SuccessRefresh(techerList.getTec());

                    } else {
                        iValuationChooseTeacher.FailureRefresh(techerList.getError_msg());
                    }
                } else {
                    iValuationChooseTeacher.FailureRefresh("网络连接超时");
                }
            }

            @Override
            public void onFailure(Call<TecherList> call, Throwable t) {
                iValuationChooseTeacher.FailureRefresh("网络连接超时");
            }
        };
        Call<TecherList> call = HttpRequest.getCommonApi().techerList(map);
        call.enqueue(callback);


    }

    //上拉加载获取数据
    public void LoadData(int self_id,String spec,String search_key) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("self", self_id);
        map.put("spec", spec);
        if (!search_key.equals("")) {
            map.put("key", search_key);
        }
        Callback<TecherList> callback = new Callback<TecherList>() {
            @Override
            public void onResponse(Call<TecherList> call, Response<TecherList> response) {
                UIUtil.showLog("ChoserTeacherPresenter.class","loadData_onResponse--->"+response.code()+"---->"+response.message());
                TecherList techerList = response.body();
                if (techerList != null) {
                    if (techerList.getError_code().equals("0")) {
                        iValuationChooseTeacher.SuccessLoad(techerList.getTec());
                    } else {
                        iValuationChooseTeacher.FailureLoad(0);
                    }
                } else {
                    iValuationChooseTeacher.FailureLoad(1);
                }
            }

            @Override
            public void onFailure(Call<TecherList> call, Throwable t) {
                iValuationChooseTeacher.FailureLoad(2);
            }
        };
        Call<TecherList> call = HttpRequest.getCommonApi().techerList(map);
        call.enqueue(callback);
    }

    //搜索老师
    public void SearchTeacher(String spec,String search_key) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("spec", spec);
        map.put("key", search_key);

        Callback<SearchBean> callback = new Callback<SearchBean>() {
            @Override
            public void onResponse(Call<SearchBean> call, Response<SearchBean> response) {
                UIUtil.showLog("ChoserTeacherPresenter.class","searchData_onResponse--->"+response.code()+"---->"+response.message());

                SearchBean techerList = response.body();
                UIUtil.showLog("ChoserTeacherPresenter.class","techerList--->"+techerList.toString());

                if (techerList != null) {
                    if (techerList.getError_code().equals("0")) {
                        iValuationChooseTeacher.SuccessSearch(techerList.getTec());
                    } else {
                        iValuationChooseTeacher.FailureSearch(techerList.getError_msg());
                    }
                } else {
                    iValuationChooseTeacher.FailureSearch("网络连接超时");
                }
            }

            @Override
            public void onFailure(Call<SearchBean> call, Throwable t) {
                UIUtil.showLog("ChoserTeacherPresenter.class","RefreshData_onFailure--->"+t.getMessage()+"---->"+t.getCause());

                iValuationChooseTeacher.FailureSearch("网络连接超时");
            }
        };
        Call<SearchBean> call = HttpRequest.getCommonApi().searchTec(map);
        call.enqueue(callback);
    }
}
