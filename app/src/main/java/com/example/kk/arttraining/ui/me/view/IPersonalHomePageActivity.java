package com.example.kk.arttraining.ui.me.view;

import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.ui.me.bean.CollectBean;
import com.example.kk.arttraining.ui.me.bean.UserCountBean;

import java.util.List;
import java.util.Map;

/**
 * 作者：wschenyongyin on 2016/11/9 20:22
 * 说明:个人主页activity
 */
public interface IPersonalHomePageActivity {
    //获取用户信息
    void getUserInfo();

    //获取用户统计
    void getUserCount();

    //获取用户动态
    void getUserStatuses();

    //获取用户作品
    void getUserWorks();

    //下拉刷新
    void RefreshData();

    void LoadData();

    //获取用户信息成功
    void SuccessUserInfo(UserLoginBean userLoginBean);

    //获取用户统计信息成功
    void SuccessCount(UserCountBean userCountBean);

    //获取用户动态信息成功
    void SuccessStatuses(List<Map<String, Object>> mapList);

    //获取用户动态作品成功
    void SuccessWorks(List<Map<String, Object>> mapList);

    //刷新成功
    void SuccessRefresh(List<Map<String, Object>> mapList);

    //加载试成功
    void SuccessLoad(List<Map<String, Object>> mapList);

    //获取用户信息失败
    void FailureUserInfo(String error_code);

    //获取用户统计信息失败
    void FailureCount(String error_code);

    //获取用户动态信息失败
    void FailureStatuses(String error_code);

    //获取用户动态信息失败
    void FailureWoreks(String error_code);

    void OnFailure(String error_msg);

    /**
     * 显示loadingDialog
     */
    void showLoading();

    /**
     * 隐藏loadingDialog
     */
    void hideLoading();
}
