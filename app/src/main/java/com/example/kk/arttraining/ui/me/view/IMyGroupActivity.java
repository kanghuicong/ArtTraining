package com.example.kk.arttraining.ui.me.view;

import com.example.kk.arttraining.bean.GroupBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/10 19:05
 * 说明:我的小组接口
 */
public interface IMyGroupActivity {
    //初始化
    void init();

    //下拉刷新获取数据
    void RefreshData();

    //上拉加载数据
    void LoadData();

    void SuccessRefresh(List<GroupBean> groupBeanList);

    void SuccessLoad(List<GroupBean> groupBeanList);

    void FailureRefresh(String error_code);

    void FailureLoad(String error_code);

    void ShowLoading();

    void HideLoading();

}
