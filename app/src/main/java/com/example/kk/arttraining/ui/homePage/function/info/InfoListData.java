package com.example.kk.arttraining.ui.homePage.function.info;

import com.example.kk.arttraining.bean.parsebean.OrgShowBean;
import com.example.kk.arttraining.ui.homePage.bean.InfoListBean;
import com.example.kk.arttraining.ui.homePage.prot.IInfo;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2017/1/6.
 * QQ邮箱:515849594@qq.com
 */
public class InfoListData {
    IInfo iInfo;

    public InfoListData(IInfo iInfo) {
        this.iInfo = iInfo;
    }

    public void getInfoListData(String type) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);

        Callback<InfoListBean> callback = new Callback<InfoListBean>() {
            @Override
            public void onResponse(Call<InfoListBean> call, Response<InfoListBean> response) {
                InfoListBean infoListData = response.body();
                if (response.body() != null) {
                    if (infoListData.getError_code().equals("0")) {
                        iInfo.getInfoList(infoListData.getInformations());
                    }else {
                        iInfo.OnInfoListFailure();
                    }
                }else {
                    iInfo.OnInfoListFailure();
                }
            }

            @Override
            public void onFailure(Call<InfoListBean> call, Throwable t) {
                iInfo.OnInfoListFailure();
            }
        };

        Call<InfoListBean> call = HttpRequest.getCommonApi().infoListData(map);
        call.enqueue(callback);
    }


    public void loadInfoListData(int self,String type) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("self", self);
        map.put("type", type);

        Callback<InfoListBean> callback = new Callback<InfoListBean>() {
            @Override
            public void onResponse(Call<InfoListBean> call, Response<InfoListBean> response) {
                InfoListBean infoListData = response.body();
                if (response.body() != null) {
                    if (infoListData.getError_code().equals("0")) {
                        iInfo.loadInfoList(infoListData.getInformations());
                    }else {
                        iInfo.loadInfoListFailure(0);
                    }
                }else {
                    iInfo.loadInfoListFailure(1);
                }
            }

            @Override
            public void onFailure(Call<InfoListBean> call, Throwable t) {
                iInfo.loadInfoListFailure(2);
            }
        };

        Call<InfoListBean> call = HttpRequest.getCommonApi().infoListData(map);
        call.enqueue(callback);
    }

}
