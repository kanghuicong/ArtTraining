package com.example.kk.arttraining.ui.homePage.function.institution;

import com.example.kk.arttraining.bean.parsebean.OrgListBean;
import com.example.kk.arttraining.bean.parsebean.OrgShowBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.yixia.camera.util.Log;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/11/3.
 * QQ邮箱:515849594@qq.com
 */
public class InstitutionContentDate {
    public static OrgShowBean orgShowBean;
    public static OrgShowBean getInstitutionContentDate(int org_id) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", "");
        map.put("org_id", org_id + "");

        Callback<OrgShowBean> callback = new Callback<OrgShowBean>() {
            @Override
            public void onResponse(Call<OrgShowBean> call, Response<OrgShowBean> response) {
                orgShowBean = response.body();
                if (response.body() != null) {
                }
            }

            @Override
            public void onFailure(Call<OrgShowBean> call, Throwable t) {

            }
        };

        Call<OrgShowBean> call = HttpRequest.getCommonApi().orgDetail(map);
        call.enqueue(callback);
        return orgShowBean;
    }
}