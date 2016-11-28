package com.example.kk.arttraining.ui.homePage.function.institution;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.kk.arttraining.bean.OrgBean;
import com.example.kk.arttraining.bean.parsebean.OrgListBean;
import com.example.kk.arttraining.ui.homePage.activity.ThemeInstitutionContent;
import com.example.kk.arttraining.ui.homePage.adapter.InstitutionFragmentAdapter;
import com.example.kk.arttraining.ui.homePage.prot.IInstitutionList;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/11/27.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeInstitutionListData {
    IInstitutionList iInstitutionList;

    public ThemeInstitutionListData(IInstitutionList iInstitutionList) {
        this.iInstitutionList = iInstitutionList;
    }

    public void getThemeInstitutionListData(String province) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", "");
        if (!province.equals("")) {
            map.put("province", province);
        }

        UIUtil.showLog("orgBeanList","orgBeanList"+"-----");
        Callback<OrgListBean> callback = new Callback<OrgListBean>() {
            @Override
            public void onResponse(Call<OrgListBean> call, Response<OrgListBean> response) {
                OrgListBean orgListBean = response.body();
                if (response.body() != null) {
                    if (orgListBean.getError_code().equals("0")) {
                        final List<OrgBean> orgBeanList = orgListBean.getOrg();
                        iInstitutionList.getInstitutionList(orgBeanList);
                    }else {
                        iInstitutionList.OnInstitutionListFailure(orgListBean.getError_msg());
                    }
                }else {
                    iInstitutionList.OnInstitutionListFailure(orgListBean.getError_msg());
                }
            }
            @Override
            public void onFailure(Call<OrgListBean> call, Throwable t) {
                iInstitutionList.OnInstitutionListFailure("网络连接失败~");
            }
        };
        Call<OrgListBean> call = HttpRequest.getCommonApi().orgList(map);
        call.enqueue(callback);
    }


    public void loadTeacherListAllData(int self,String province) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", "");
        map.put("self", self);
        if (!province.equals("")) {
            map.put("province", province);
        }

        Callback<OrgListBean> callback = new Callback<OrgListBean>() {
            @Override
            public void onResponse(Call<OrgListBean> call, Response<OrgListBean> response) {
                OrgListBean orgListBean = response.body();
                UIUtil.showLog("onLoadMore","456");
                if (response.body() != null) {
                    if (orgListBean.getError_code().equals("0")) {
                        UIUtil.showLog("onLoadMore","345");
                        iInstitutionList.loadInstitutionList(orgListBean.getOrg());
                    }else {
                        iInstitutionList.OnLoadInstitutionListFailure(0);
                    }
                }else {
                    iInstitutionList.OnLoadInstitutionListFailure(1);
                }
            }
            @Override
            public void onFailure(Call<OrgListBean> call, Throwable t) {
                iInstitutionList.OnLoadInstitutionListFailure(2);
            }
        };
        Call<OrgListBean> call = HttpRequest.getCommonApi().orgList(map);
        call.enqueue(callback);
    }
}
