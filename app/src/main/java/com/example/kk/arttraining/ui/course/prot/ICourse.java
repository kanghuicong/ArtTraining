package com.example.kk.arttraining.ui.course.prot;

import com.example.kk.arttraining.ui.course.bean.CourseBean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/12/17.
 * QQ邮箱:515849594@qq.com
 */
public interface ICourse {
    void getCourseList(List<CourseBean> course_list);

    void OnCourseFailure();

    void loadCourseList(List<CourseBean> course_list);

    void OnLoadCourseListFailure(int code);

}
