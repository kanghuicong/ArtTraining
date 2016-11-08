package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.bean.parsebean.OrgShowBean;
import com.example.kk.arttraining.bean.parsebean.ParseLocationBean;

/**
 * Created by kanghuicong on 2016/11/6.
 * QQ邮箱:515849594@qq.com
 */
public interface IProvince {
    //获取头条数据
    void getProvince(ParseLocationBean parseLocationBean);

    //获取数据失败
    void OnFailure(String error_code);
}
