package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.bean.MajorBean;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.bean.parsebean.ParseLocationBean;
import com.example.kk.arttraining.ui.school.bean.ParseSchoolListBean;
import com.example.kk.arttraining.ui.school.bean.ProvinceBean;
import com.example.kk.arttraining.ui.school.bean.SchoolBean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/7.
 * QQ邮箱:515849594@qq.com
 */
public interface ITeacher {

    void getMajor(List<MajorBean> majorBeanLeftList);

//    void getMajorRight(List<MajorBean> majorBeanRightList);
//
//    void getUpdateMajorRight(List<MajorBean> majorUpdateRightList);

    void getSchoolLeft(List<ProvinceBean> provinceBeanLeftList);

    void getSchoolRight(List<SchoolBean> schoolBeanRightList);

    void getUpdateSchoolRight(List<SchoolBean> schoolUpdateRightList);


    //获取数据失败
    void OnFailure(String error_code);
}
