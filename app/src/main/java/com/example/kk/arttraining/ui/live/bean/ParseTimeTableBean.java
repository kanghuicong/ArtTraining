package com.example.kk.arttraining.ui.live.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

import java.util.List;


/**
 * 作者：wschenyongyin on 2017/1/21 15:52
 * 说明:解析课表
 */
public class ParseTimeTableBean extends NoDataResponseBean{

    List<TimeTableBean> timetable_list;

    public List<TimeTableBean> getTimetable_list() {
        return timetable_list;
    }

    public void setTimetable_list(List<TimeTableBean> timetable_list) {
        this.timetable_list = timetable_list;
    }

    @Override
    public String toString() {
        return "ParseTimeTableBean{" +
                "timetable_list=" + timetable_list +
                '}';
    }
}
