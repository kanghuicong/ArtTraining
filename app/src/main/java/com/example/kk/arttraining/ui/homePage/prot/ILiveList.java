package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.ui.homePage.bean.LiveList;
import com.example.kk.arttraining.ui.homePage.bean.LiveListBean;

import java.util.List;

/**
 * Created by kanghuicong on 2017/1/17.
 * QQ邮箱:515849594@qq.com
 */
public interface ILiveList {
    void getLiveListData(LiveList liveList);

    void OnLiveListFailure(String result);

    void loadLiveList(LiveList liveList);

    void OnLoadLiveListFailure(int result);

    void getLiveType(int type,int room_id,int chapter_id);

    void OnLiveTypeFailure(String error_code,String error_msg);
}
