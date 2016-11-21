package com.example.kk.arttraining.ui.me.view;

import com.example.kk.arttraining.bean.GroupBean;

import java.util.List;
import java.util.Map;

/**
 * 作者：wschenyongyin on 2016/11/14 14:46
 * 说明:我的帖子
 */
public interface IMyBBS {


    public void RefreshData();

    public void LoadData();

    public void SuccessRefresh(List<Map<String, Object>> mapList);

    public void SuccessLoad(List<Map<String, Object>> mapList);

    void OnFailure(String error_code);
    void OnFailureLoad(String error_code);
}
