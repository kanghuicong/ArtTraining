package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.bean.HeadNews;
import com.example.kk.arttraining.bean.StatusesDetailBean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/5.
 * QQ邮箱:515849594@qq.com
 */
public interface ITeacherComment {
    //获取头条数据
    void getTeacherComment(StatusesDetailBean statusesDetailBean);
    //获取数据失败
    void OnFailure(String error_code);
}
