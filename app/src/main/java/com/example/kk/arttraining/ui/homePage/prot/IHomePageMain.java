package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.bean.HeadNews;
import com.example.kk.arttraining.bean.TecInfoBean;

import java.util.List;
import java.util.Map;

/**
 * 作者：wschenyongyin on 2016/11/3 14:44
 * 说明:
 */
public interface IHomePageMain {

    //获取动态列表
    void getDynamicListData(List<Map<String, Object>> mapList);

    void loadDynamicListData(List<Map<String, Object>> mapList);

    void OnDynamicFailure(String error_code);

    //获取头条数据
    void getHeadNews(List<HeadNews> headNewsList);

    void OnHeadNewsFailure(String error_code);

    //获取数据失败
    void OnFailure(String error_code);

}
