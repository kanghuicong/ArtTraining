package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.bean.parsebean.TecherShow;

/**
 * Created by kanghuicong on 2016/11/15.
 * QQ邮箱:515849594@qq.com
 */
public interface ITeacherContent {

    void getTeacherContent(TecherShow techerShow);

    void OnTeacherContentFailure(String error_code);

    void NoWifi();
}
