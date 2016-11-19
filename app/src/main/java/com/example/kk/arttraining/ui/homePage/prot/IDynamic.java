package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.bean.HeadNews;
import com.example.kk.arttraining.bean.StatusesDetailBean;

import java.util.List;
import java.util.Map;

/**
 * Created by kanghuicong on 2016/11/5.
 * QQ邮箱:515849594@qq.com
 */
public interface IDynamic {

    //获取帖子详情数据
    void getDynamicData(StatusesDetailBean statusesDetailBean);
    //获取数据失败
    void OnFailure(String error_code);

    void getCreateComment(String result);

    void getWorkData(StatusesDetailBean statusesDetailBean);

}
