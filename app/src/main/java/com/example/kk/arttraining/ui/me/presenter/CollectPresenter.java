package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.ui.me.bean.ParseCollectBean;
import com.example.kk.arttraining.ui.me.view.ICollectActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/9 17:23
 * 说明:收藏处理类
 */
public class CollectPresenter {
    ICollectActivity iCollectActivity;

    public CollectPresenter(ICollectActivity iCollectActivity) {
        this.iCollectActivity = iCollectActivity;
    }

    public void getCollectData(Map<String, Object> map, final int type) {


        Callback<ParseCollectBean> callback = new Callback<ParseCollectBean>() {
            @Override
            public void onResponse(Call<ParseCollectBean> call, Response<ParseCollectBean> response) {
                ParseCollectBean parseCollectBean = response.body();
                if (parseCollectBean != null) {
                    if (parseCollectBean.getError_code().equals("0")) {
                        if (type == 0) {
                            iCollectActivity.Success(parseCollectBean.getFavorites());
                        } else {
                            iCollectActivity.SuccessLoadData(parseCollectBean.getFavorites());
                        }

                    } else {
                        iCollectActivity.Failure(parseCollectBean.getError_code(),parseCollectBean.getError_msg());
                    }

                } else {
                    iCollectActivity.Failure(Config.Connection_Failure,Config.Connection_ERROR_TOAST);
                }
            }

            @Override
            public void onFailure(Call<ParseCollectBean> call, Throwable t) {
                UIUtil.showLog("response------>",t.getMessage()+"--------->"+t.getCause());
                iCollectActivity.Failure(Config.Connection_Failure,Config.Connection_ERROR_TOAST);
            }
        };

        Call<ParseCollectBean> call = HttpRequest.getStatusesApi().statusesFavoritesList(map);
        call.enqueue(callback);

    }
}
