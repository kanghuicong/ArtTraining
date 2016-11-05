package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.bean.HeadNews;
import com.example.kk.arttraining.bean.TecInfoBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/3 14:44
 * 说明:
 */
public interface IHomePageMain {

    //获取头条数据
    void getHeadNews(List<HeadNews> headNewsList);

    //获取数据失败
    void OnFailure(String error_code);

}
