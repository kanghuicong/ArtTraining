package com.example.kk.arttraining.ui.live.view;

import com.example.kk.arttraining.ui.live.bean.ChapterBean;
import com.example.kk.arttraining.ui.live.bean.TimeTableBean;

import java.util.List;
import java.util.Map;


/**
 * 作者：wschenyongyin on 2017/1/14 08:58
 * 说明:课表
 */
public interface ILiveCourseView {


    //获取课表
    void getTimeTable();
    //获取成功
    void Success(List<TimeTableBean> timeTableBeanList, Map<String, List<ChapterBean>> mapChapterBeanList);
    //获取失败
    void Failure(String error_code, String error_msg);
}
