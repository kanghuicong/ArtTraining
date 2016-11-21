package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.bean.OrgBean;

import java.util.List;
import java.util.Map;

/**
 * Created by kanghuicong on 2016/11/11.
 * QQ邮箱:515849594@qq.com
 */
public interface ISearch {

    void getDoSearchData(List<Map<String, Object>> mapList);

    void OnFailure(String error_code);

    //机构搜索
    void getInstitutionSearch(List<OrgBean> orgBeanList);

    //搜索机构返回为空
    void OnInstitutionSearchEmpty(String result);

    void OnInstitutionSearchFailure(String result);

}
