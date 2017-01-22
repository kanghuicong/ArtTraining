package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.bean.OrgBean;
import com.example.kk.arttraining.ui.homePage.bean.LiveListBean;

import java.util.List;

/**
 * Created by kanghuicong on 2017/1/17.
 * QQ邮箱:515849594@qq.com
 */
public interface ILiveList {
    void getLiveListData(List<LiveListBean> liveListBeanList);

    void OnLiveListFailure(String result);

    void loadLiveList(List<LiveListBean> liveListBeanList);

    void OnLoadLiveListFailure(int result);

    void getLiveType(int type,int room_id,int chapter_id);

    void OnLiveTypeFailure(String result);
}
