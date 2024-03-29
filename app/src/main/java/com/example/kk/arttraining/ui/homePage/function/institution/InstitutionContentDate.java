package com.example.kk.arttraining.ui.homePage.function.institution;

import com.example.kk.arttraining.bean.parsebean.OrgListBean;
import com.example.kk.arttraining.bean.parsebean.OrgShowBean;
import com.example.kk.arttraining.ui.homePage.prot.IInstitutionContent;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/11/3.
 * QQ邮箱:515849594@qq.com
 */
public class InstitutionContentDate {
    IInstitutionContent iInstitutionContent;

    public InstitutionContentDate(IInstitutionContent iInstitutionContent) {
        this.iInstitutionContent = iInstitutionContent;
    }

    public void getInstitutionContentDate(int org_id) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("org_id", org_id);
        map.put("login_id", Config.UID);
        map.put("login_type", Config.USER_TYPE);

        Callback<OrgShowBean> callback = new Callback<OrgShowBean>() {
            @Override
            public void onResponse(Call<OrgShowBean> call, Response<OrgShowBean> response) {
                OrgShowBean orgShowBean = response.body();
                if (response.body() != null) {
                    if (orgShowBean.getError_code().equals("0")) {
                        iInstitutionContent.getInstitutionContent(orgShowBean);
//                    iInstitutionContent.getInstitutionTags(orgShowBean.getTags());
//                    iInstitutionContent.getInstitutionTeacher(orgShowBean.getTeachers());
//                    iInstitutionContent.getInstitutionCourse(orgShowBean.getCourse());
//                    iInstitutionContent.getInstitutionStudent(orgShowBean.getTrainees());
                    }else {
                        iInstitutionContent.OnFailure(orgShowBean.getError_msg());
                    }
                }else {
                    iInstitutionContent.OnFailure("onFailure");
                }
            }

            @Override
            public void onFailure(Call<OrgShowBean> call, Throwable t) {
                iInstitutionContent.OnFailure("onFailure");
            }
        };

        Call<OrgShowBean> call = HttpRequest.getCommonApi().orgDetail(map);
        call.enqueue(callback);
    }
}