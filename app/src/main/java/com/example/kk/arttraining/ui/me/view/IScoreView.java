package com.example.kk.arttraining.ui.me.view;

import com.example.kk.arttraining.ui.me.bean.ScoreBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2017/2/28 15:48
 * 说明:积分
 */
public interface IScoreView {
    //查询总积分
    void QueryScore();

    //查询积分成功
    void SuccessQuery(int score);

    //查询积分失败
    void FailureQuery(String error_code, String error_msg);

    //查询积分详情
    void QueryScoreDatail();

    //查询积分详情成功
    void SuccessQueryDetail(List<ScoreBean> scoreBeanList);

    //查询积分详情失败
    void FailureQueryDetail(String error_code, String error_msg);


}
