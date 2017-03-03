package com.example.kk.arttraining.ui.me.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.me.bean.ScoreBean;
import com.example.kk.arttraining.ui.me.presenter.ScorePresenter;
import com.example.kk.arttraining.utils.Config;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：wschenyongyin on 2017/2/28 15:34
 * 说明:我的积分类
 */
public class ScoreActivity extends BaseActivity implements IScoreView {

    //请求参数封装
    private HashMap<String, Object> scoreMap;
    private HashMap<String, Object> scoreDetailMap;

    ScorePresenter presenter;
    //分页id
    private int self_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void init() {
        presenter = new ScorePresenter(this);
        QueryScore();
        QueryScoreDatail();

    }

    @Override
    public void onClick(View v) {

    }

    //查询总积分
    @Override
    public void QueryScore() {
        if (scoreMap == null)
            scoreMap = new HashMap<String, Object>();
        scoreMap.put("access_token", Config.ACCESS_TOKEN);
        scoreMap.put("uid", Config.UID);
        scoreMap.put("utype", Config.USER_TYPE);
        presenter.QueryScore(scoreMap);

    }

    //查询积分成功
    @Override
    public void SuccessQuery(int score) {
        Log.e("SuccessQuery-->","--->"+score);
    }

    //查询积分失败
    @Override
    public void FailureQuery(String error_code, String error_msg) {
        Log.e("FailureQuery-->",error_code+"--->"+error_msg);
    }

    //查询积分详情
    @Override
    public void QueryScoreDatail() {
        if (scoreDetailMap == null)
            scoreDetailMap = new HashMap<String, Object>();
        scoreDetailMap.put("access_token", Config.ACCESS_TOKEN);
        scoreDetailMap.put("uid", Config.UID);
        scoreDetailMap.put("utype", Config.USER_TYPE);
        if (self_id != 0)
            scoreDetailMap.put("self", self_id);
        presenter.QueryScoreDetail(scoreDetailMap);

    }

    //查询积分详情成功
    @Override
    public void SuccessQueryDetail(List<ScoreBean> scoreBeanList) {
        Log.e("scoreBeanList----->",scoreBeanList.toString());

    }

    //查询积分详情失败
    @Override
    public void FailureQueryDetail(String error_code, String error_msg) {
        Log.e("FailureQueryDetail-->",error_code+"--->"+error_msg);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.cancelSubscription();
    }
}
