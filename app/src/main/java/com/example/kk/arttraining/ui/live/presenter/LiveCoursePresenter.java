package com.example.kk.arttraining.ui.live.presenter;

import com.example.kk.arttraining.ui.live.bean.ChapterBean;
import com.example.kk.arttraining.ui.live.bean.ParseTimeTableBean;
import com.example.kk.arttraining.ui.live.bean.TimeTableBean;
import com.example.kk.arttraining.ui.live.view.ILiveCourseView;
import com.example.kk.arttraining.utils.ErrorMsgUtils;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2017/1/14 08:58
 * 说明:设置直播课表
 */
public class LiveCoursePresenter {
    ILiveCourseView iLiveCourseView;

    public LiveCoursePresenter(ILiveCourseView iLiveCourseView) {
        this.iLiveCourseView = iLiveCourseView;
    }


    public void getTimeTable(Map<String, Object> map) {
        Callback<ParseTimeTableBean> callback = new Callback<ParseTimeTableBean>() {
            @Override
            public void onResponse(Call<ParseTimeTableBean> call, Response<ParseTimeTableBean> response) {

                ParseTimeTableBean parseTimeTableBean = response.body();
                if (parseTimeTableBean != null) {
                    UIUtil.showLog("parseTimeTableBean-------->",parseTimeTableBean.toString()+"");
                    if (parseTimeTableBean.getError_code().equals("0")) {
                        ProcessData(parseTimeTableBean.getTimetable_list());
                    } else {
                        iLiveCourseView.Failure(parseTimeTableBean.getError_code(), parseTimeTableBean.getError_msg());
                    }
                } else {
                    iLiveCourseView.Failure(response.code() + "", ErrorMsgUtils.LIVE_TIMETABLE_ERROR_MSG);
                }
            }

            @Override
            public void onFailure(Call<ParseTimeTableBean> call, Throwable t) {
                iLiveCourseView.Failure(ErrorMsgUtils.NETWORK_ERROR_CODE, ErrorMsgUtils.NETWORK_ERROR_MSG);
            }
        };

        Call<ParseTimeTableBean> call = HttpRequest.getLiveApi().getTableTable(map);
        call.enqueue(callback);
    }


    //处理数据
    void ProcessData(List<TimeTableBean> timeTableBeanList) {
        Map<String, List<ChapterBean>> map = new HashMap<String, List<ChapterBean>>();
        TimeTableBean tableBean;
        for (int i = 0; i < timeTableBeanList.size(); i++) {
            tableBean = timeTableBeanList.get(i);
            map.put(tableBean.getName(), tableBean.getChapter_list());
        }
        iLiveCourseView.Success(timeTableBeanList, map);
    }


}
