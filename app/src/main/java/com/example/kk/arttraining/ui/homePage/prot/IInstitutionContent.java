package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.bean.parsebean.OrgShowBean;

/**
 * Created by kanghuicong on 2016/11/6.
 * QQ邮箱:515849594@qq.com
 */
public interface IInstitutionContent {
    //获取头条数据
    void getInstitutionContent(OrgShowBean orgShowBean);

    //获取数据失败
    void OnFailure(String error_code);
}
