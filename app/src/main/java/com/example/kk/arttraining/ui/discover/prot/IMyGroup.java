package com.example.kk.arttraining.ui.discover.prot;

import com.example.kk.arttraining.bean.GroupBean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/13.
 * QQ邮箱:515849594@qq.com
 */
public interface IMyGroup {

    void getMyGroupData(List<GroupBean> myGroupBeanList);

    void OnMyGroupFailure(String error_code);
}
