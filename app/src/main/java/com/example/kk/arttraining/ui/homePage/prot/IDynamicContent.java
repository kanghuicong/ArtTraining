package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.bean.modelbean.StatusesDetailBean;
import com.example.kk.arttraining.bean.parsebean.CommentsBean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/5.
 * QQ邮箱:515849594@qq.com
 */
public interface IDynamicContent {

    //获取帖子详情数据
    void getDynamicData(StatusesDetailBean statusesDetailBean);
    //获取数据失败
    void OnFailure(String error_code);

    void getCreateComment(String result);

    void getWorkData(StatusesDetailBean statusesDetailBean);

    //上拉加载数据
    void loadDynamic(List<CommentsBean> commentsBeanList);

    //上拉加载失败
    void OnLoadDynamicFailure(int result);

    void NoWifi();

}
