package com.example.kk.arttraining.ui.homePage.function.examine;

import com.example.kk.arttraining.ui.homePage.bean.ExamineBean;
import com.example.kk.arttraining.ui.homePage.bean.InfoListBean;
import com.example.kk.arttraining.ui.homePage.prot.IExamine;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2017/3/13.
 * QQ邮箱:515849594@qq.com
 */
public class ExamineData {
    IExamine iExamine;
    public ExamineData(IExamine iExamine) {
        this.iExamine = iExamine;
    }

    public void getExamineData(String majorScore,String cultureScore,String examineProvince) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("", majorScore);
        map.put("", cultureScore);
        map.put("", examineProvince);

        Callback<ExamineBean> callback = new Callback<ExamineBean>() {
            @Override
            public void onResponse(Call<ExamineBean> call, Response<ExamineBean> response) {
                ExamineBean examineBean = response.body();
                if (response.body() != null) {
                    if (examineBean.getError_code().equals("0")) {
                        iExamine.getExamine(examineBean.getList());
                    }else {
                        iExamine.onExamineFailure(examineBean.getError_msg());
                    }
                }else {
                    iExamine.onExamineFailure("网络连接失败！");
                }
            }

            @Override
            public void onFailure(Call<ExamineBean> call, Throwable t) {
                iExamine.onExamineFailure("网络连接失败！");
            }
        };

        Call<ExamineBean> call = HttpRequest.getCommonApi().getExamine(map);
        call.enqueue(callback);
    }

}
