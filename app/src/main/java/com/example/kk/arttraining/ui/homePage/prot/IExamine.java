package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.ui.homePage.bean.ExamineListBean;

import java.util.List;

/**
 * Created by kanghuicong on 2017/3/13.
 * QQ邮箱:515849594@qq.com
 */
public interface IExamine {
     void getExamine(List<ExamineListBean> examineList);

    void onExamineFailure(String msg);
}
