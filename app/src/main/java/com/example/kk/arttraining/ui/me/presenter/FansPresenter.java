package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.ui.homePage.bean.FollowList;
import com.example.kk.arttraining.ui.me.view.IFansActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/9 11:05
 * 说明:
 */
public class FansPresenter {
    IFansActivity iFansActivity;

    public FansPresenter(IFansActivity iFansActivity) {
        this.iFansActivity = iFansActivity;
    }

    //获取粉丝数据
    public void getFansData(Map<String, Object> map,final String type) {

        Callback<FollowList> callback = new Callback<FollowList>() {
            @Override
            public void onResponse(Call<FollowList> call, Response<FollowList> response) {
                FollowList followList = response.body();
                if (followList != null) {
                    if (followList.getError_code().equals("0")) {
                        if(type.equals("refresh")){
                            iFansActivity.SuccessRefresh(followList.getFollows());
                        }else {
                            iFansActivity.SuccessLoad(followList.getFollows());
                        }
                    } else {
                        if(type.equals("refresh")){
                            iFansActivity.Failure(followList.getError_code(),followList.getError_msg());
                        }else {
                            iFansActivity.LoadFailure(followList.getError_code(),followList.getError_msg());
                        }
                    }
                } else {
                    if(type.equals("refresh")){
                        iFansActivity.Failure(Config.Connection_Failure,Config.Connection_ERROR_TOAST);
                    }else {
                        iFansActivity.LoadFailure(Config.Connection_Failure,Config.Connection_ERROR_TOAST);
                    }
                }

            }

            @Override
            public void onFailure(Call<FollowList> call, Throwable t) {
                if(type.equals("refresh")){
                    iFansActivity.Failure(Config.Connection_Failure,Config.Connection_ERROR_TOAST);
                }else {
                    iFansActivity.LoadFailure(Config.Connection_Failure,Config.Connection_ERROR_TOAST);
                }
            }
        };
        Call<FollowList> call = HttpRequest.getStatusesApi().follow_fans_list(map);
        call.enqueue(callback);
    }

    //获取关注信息
    public void getFocusData(Map<String, Object> map, final String type) {
        Callback<FollowList> callback = new Callback<FollowList>() {
            @Override
            public void onResponse(Call<FollowList> call, Response<FollowList> response) {
                FollowList followList = response.body();
                if (followList != null) {
                    if (followList.getError_code().equals("0")) {
                        if(type.equals("refresh")){
                            iFansActivity.SuccessRefresh(followList.getFollows());
                        }else {
                            iFansActivity.SuccessLoad(followList.getFollows());
                        }
                    } else {
                        if(type.equals("refresh")){
                            iFansActivity.Failure(followList.getError_code(),followList.getError_msg());
                        }else {
                            iFansActivity.LoadFailure(followList.getError_code(),followList.getError_msg());
                        }

                    }
                } else {
                    if(type.equals("refresh")){
                        iFansActivity.Failure(Config.Connection_Failure,Config.Connection_ERROR_TOAST);
                    }else {
                        iFansActivity.LoadFailure(Config.Connection_Failure,Config.Connection_ERROR_TOAST);
                    }

                }
            }

            @Override
            public void onFailure(Call<FollowList> call, Throwable t) {
                if(type.equals("refresh")){
                    iFansActivity.Failure(Config.Connection_Failure,Config.Connection_ERROR_TOAST);
                }else {
                    iFansActivity.LoadFailure(Config.Connection_Failure,Config.Connection_ERROR_TOAST);
                }
            }
        };
        Call<FollowList> call = HttpRequest.getStatusesApi().follow_follow_list(map);
        call.enqueue(callback);
    }

}
