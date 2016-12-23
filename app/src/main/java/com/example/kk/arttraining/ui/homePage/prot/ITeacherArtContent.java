package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.bean.parsebean.TecherShow;
import com.example.kk.arttraining.ui.course.bean.ArtTeacherContentBean;

/**
 * Created by kanghuicong on 2016/12/23.
 * QQ邮箱:515849594@qq.com
 */
public interface ITeacherArtContent {
    void getTeacherContent(ArtTeacherContentBean artTeacher);

    void OnTeacherContentFailure();

    void NoWifi();
}
