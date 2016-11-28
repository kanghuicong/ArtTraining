package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.bean.OrgBean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/27.
 * QQ邮箱:515849594@qq.com
 */
public interface IInstitutionList {

    void getInstitutionList(List<OrgBean> orgBeanList) ;

    void OnInstitutionListFailure(String result);

    void loadInstitutionList(List<OrgBean> orgBeanList);

    void OnLoadInstitutionListFailure(int result);
}
