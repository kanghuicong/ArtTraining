package com.example.kk.arttraining.ui.live.view;

import com.example.kk.arttraining.ui.live.bean.MemberBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2017/1/10 15:51
 * 说明:
 */
public interface IMemberListView {


    //获取成员列表成功
    void SuccessRefresh(List<MemberBean> memberBeanList);

    //获取成员列表失败
    void FailureRefresh(String error_code, String error_msg);


    //上拉加载成功
    void SuccessLoadData(List<MemberBean> memberBeanList);

    //上拉加载失败
    void FailureLoadData(String error_code, String error_msg);

    //搜索成员
    void SearchData();

    //搜索成员成功
    void SuccessSearchData(List<MemberBean> memberBeanList);

    //搜索成员失败
    void FailureSearchData();

}
