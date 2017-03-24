package com.example.kk.arttraining.ui.live.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.live.adapter.CourseAdapter;
import com.example.kk.arttraining.ui.live.bean.ChapterBean;
import com.example.kk.arttraining.ui.live.bean.TimeTableBean;
import com.example.kk.arttraining.ui.live.presenter.LiveCoursePresenter;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2017/1/21 15:24
 * 说明:课程列表
 */
public class LiveCourseActivity extends BaseActivity implements ILiveCourseView {

    @InjectView(R.id.course_elv)
    ExpandableListView courseElv;

    LiveCoursePresenter liveCoursePresenter;
    private CourseAdapter courseAdapter;
    private int room_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_course_activity);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this, "课程列表");
        init();
    }

    @Override
    public void init() {
        room_id = getIntent().getIntExtra("room_id", 0);
        liveCoursePresenter = new LiveCoursePresenter(this);
        getTimeTable();
    }

    @Override
    public void onClick(View v) {
    }

    //获取课程列表
    @Override
    public void getTimeTable() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("room_id", room_id);
        liveCoursePresenter.getTimeTable(map);
    }

    //获取成功
    @Override
    public void Success(List<TimeTableBean> timeTableBeanList, Map<String, List<ChapterBean>> mapChapterBeanList) {
        courseAdapter = new CourseAdapter(this, timeTableBeanList, mapChapterBeanList,room_id);
        courseElv.setAdapter(courseAdapter);
        courseElv.setGroupIndicator(null);
    }

    //获取失败
    @Override
    public void Failure(String error_code, String error_msg) {
        if (error_code.equals("20028")) {
            startActivity(new Intent(this, UserLoginActivity.class));
            UIUtil.ToastshowShort(this, error_msg);
        } else {
            UIUtil.ToastshowShort(this, error_msg);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTimeTable();
    }
}
