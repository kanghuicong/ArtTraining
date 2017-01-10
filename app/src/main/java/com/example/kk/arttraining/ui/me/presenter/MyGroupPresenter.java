package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.bean.parsebean.GroupListBean;
import com.example.kk.arttraining.ui.me.view.IMyGroupActivity;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/10 19:47
 * 说明:
 */
public class MyGroupPresenter {
    private IMyGroupActivity iMyGroupActivity;

    public MyGroupPresenter(IMyGroupActivity iMyGroupActivity) {
        this.iMyGroupActivity = iMyGroupActivity;
    }

    //下拉刷新
    public void getRefreshData(Map<String, Object> map) {
        Callback<GroupListBean> callback = new Callback<GroupListBean>() {
            @Override
            public void onResponse(Call<GroupListBean> call, Response<GroupListBean> response) {
                GroupListBean groupListBean = response.body();
                if (groupListBean != null) {

                    if (groupListBean.getError_code().equals("0")) {
                        iMyGroupActivity.SuccessRefresh(groupListBean.getGroups());
                    } else {
                        iMyGroupActivity.FailureRefresh(groupListBean.getError_code());
                    }
                } else {
                    iMyGroupActivity.FailureRefresh("failure");
                }
            }

            @Override
            public void onFailure(Call<GroupListBean> call, Throwable t) {
                iMyGroupActivity.FailureRefresh("failure");
            }
        };
        Call<GroupListBean> call = HttpRequest.getGroupApi().groupMyList(map);
    }

    //上拉加载
    public  void getLoadData(Map<String, Object> map) {
        Callback<GroupListBean> callback = new Callback<GroupListBean>() {
            @Override
            public void onResponse(Call<GroupListBean> call, Response<GroupListBean> response) {
                GroupListBean groupListBean = response.body();
                if (groupListBean != null) {

                    if (groupListBean.getError_code().equals("")) {
                        iMyGroupActivity.SuccessLoad(groupListBean.getGroups());
                    } else {
                        iMyGroupActivity.FailureLoad(groupListBean.getError_code());
                    }
                } else {
                    iMyGroupActivity.FailureLoad("failure");
                }
            }

            @Override
            public void onFailure(Call<GroupListBean> call, Throwable t) {
                iMyGroupActivity.FailureLoad("failure");
            }
        };
        Call<GroupListBean> call = HttpRequest.getGroupApi().groupMyList(map);
    }
}
