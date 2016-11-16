package com.example.kk.arttraining.ui.homePage.function.province;

import com.example.kk.arttraining.bean.parsebean.ParseLocationBean;
import com.example.kk.arttraining.ui.homePage.adapter.ChoseProvinceAdapter;
import com.example.kk.arttraining.ui.homePage.prot.IProvince;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/11/6.
 * QQ邮箱:515849594@qq.com
 */
public class ProvinceData {

    IProvince iProvince;

    public ProvinceData(IProvince iProvince) {
        this.iProvince = iProvince;
    }

    public void getProvinceData() {

        HashMap<String, String> province_map = new HashMap<String, String>();
        province_map.put("access_token", Config.ACCESS_TOKEN);

        UIUtil.showLog("ParseLocationBean", "1");
        Callback<ParseLocationBean> callback = new Callback<ParseLocationBean>() {
            @Override
            public void onResponse(Call<ParseLocationBean> call, Response<ParseLocationBean> response) {
                ParseLocationBean parseLocationBean = response.body();
                UIUtil.showLog("ParseLocationBean", "2");
                if (response.body() != null) {
                    if (parseLocationBean.getError_code().equals("0")) {

                        iProvince.getProvince(parseLocationBean);
                    } else {
                        iProvince.OnFailure(parseLocationBean.getError_code());
                    }
                }else {
                    iProvince.OnFailure("failure");
                }
            }

            @Override
            public void onFailure(Call<ParseLocationBean> call, Throwable t) {
                iProvince.OnFailure("failure");
            }
        };

        Call<ParseLocationBean> call = HttpRequest.getCommonApi().locationCity(province_map);
        call.enqueue(callback);

    }
}
