package com.example.kk.arttraining.ui.live.presenter;

import com.example.kk.arttraining.ui.live.bean.ParseMemerListBean;
import com.example.kk.arttraining.ui.live.view.IMemberListView;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2017/1/10 16:09
 * 说明:
 */
public class MemberPresenter {
    IMemberListView iMemberView;

    public MemberPresenter(IMemberListView iMemberView) {
        this.iMemberView = iMemberView;
    }


    public void RefreshData(Map<String, Object> map, final String request_type) {

        Callback<ParseMemerListBean> callback = new Callback<ParseMemerListBean>() {
            @Override
            public void onResponse(Call<ParseMemerListBean> call, Response<ParseMemerListBean> response) {
                ParseMemerListBean memerListBean = response.body();
                if (memerListBean != null) {
                    if (memerListBean.getError_code().equals("0")) {
                        if (request_type.equals("search")) {
                            iMemberView.SuccessSearchData(memerListBean.getMember_list());
                        } else {
                            iMemberView.SuccessRefresh(memerListBean.getMember_list());
                        }
                    } else {
                        iMemberView.FailureRefresh(memerListBean.getError_code(),memerListBean.getError_msg());
                    }
                } else {
                    iMemberView.FailureRefresh(response.code()+"","请求失败");
                }
            }

            @Override
            public void onFailure(Call<ParseMemerListBean> call, Throwable t) {
                iMemberView.FailureRefresh(Config.Connection_Failure,"请求失败");
            }
        };
        Call<ParseMemerListBean> call = HttpRequest.getLiveApi().getMemberList(map);
        call.enqueue(callback);

    }

    public void LoadData(Map<String, Object> map) {

        Callback<ParseMemerListBean> callback = new Callback<ParseMemerListBean>() {
            @Override
            public void onResponse(Call<ParseMemerListBean> call, Response<ParseMemerListBean> response) {
                ParseMemerListBean memerListBean = response.body();
                if (memerListBean != null) {
                    if (memerListBean.getError_code().equals("0")) {
                            iMemberView.SuccessLoadData(memerListBean.getMember_list());
                    } else {
                        iMemberView.FailureLoadData(memerListBean.getError_code(),memerListBean.getError_msg());
                    }
                } else {
                    iMemberView.FailureLoadData(response.code()+"","加载失败");
                }
            }

            @Override
            public void onFailure(Call<ParseMemerListBean> call, Throwable t) {
                iMemberView.FailureRefresh(Config.Connection_Failure,"加载失败");
            }
        };
        Call<ParseMemerListBean> call = HttpRequest.getLiveApi().getMemberList(map);
        call.enqueue(callback);

    }

}
