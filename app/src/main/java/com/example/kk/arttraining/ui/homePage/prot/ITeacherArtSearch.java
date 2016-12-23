package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.ui.course.bean.ArtTeacherBean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/12/23.
 * QQ邮箱:515849594@qq.com
 */
public interface ITeacherArtSearch {
    //获取artSchool老师列表
    void getArtTeacher(List<ArtTeacherBean> artTeacherBeanList);

    void OnTeacherFailure(String result);

    void loadArtTeacher(List<ArtTeacherBean> artTeacherBeanList);

    void OnLoadTeacherFailure(String result);
}
