package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.bean.parsebean.OrgShowBean;
import com.example.kk.arttraining.ui.homePage.bean.Course;
import com.example.kk.arttraining.ui.homePage.bean.Teachers;
import com.example.kk.arttraining.ui.homePage.bean.Trainees;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/6.
 * QQ邮箱:515849594@qq.com
 */
public interface IInstitutionContent {
    //获取机构所有数据
    void getInstitutionContent(OrgShowBean orgShowBean);

    //获取机构标签数据
    void getInstitutionTags(List<OrgShowBean.Tags> tags);

    //获取机构老师数据
    void getInstitutionTeacher(List<Teachers> teachers);

    //获取机构课程数据
    void getInstitutionCourse(List<Course> course);

    //获取机构学生数据
    void getInstitutionStudent(List<Trainees> trainees);

    //获取数据失败
    void OnFailure(String error_code);
}
