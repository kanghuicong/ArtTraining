package com.example.kk.arttraining.ui.me.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullToRefreshLayout;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullableListView;
import com.example.kk.arttraining.ui.homePage.function.refresh.RefreshUtil;
import com.example.kk.arttraining.ui.live.view.LiveAllActivity;
import com.example.kk.arttraining.ui.me.adapter.MyCourseListAdapter;
import com.example.kk.arttraining.ui.me.bean.MyCourseBean;
import com.example.kk.arttraining.ui.me.presenter.MyCourseData;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2017/3/22.
 * QQ邮箱:515849594@qq.com
 */
public class MyCourse extends Activity implements PullToRefreshLayout.OnRefreshListener, MyCourseData.ICourse, RefreshUtil.IRefreshUtil {
    @InjectView(R.id.lv_course)
    PullableListView lvCourse;
    @InjectView(R.id.refresh_view)
    PullToRefreshLayout refreshView;

    MyCourseData myCourseData;
    RefreshUtil refreshUtil;
    List<MyCourseBean> courseList = new ArrayList<MyCourseBean>();
    MyCourseListAdapter courseListAdapter;
    @InjectView(R.id.bt_find_course)
    TextView btFindCourse;
    @InjectView(R.id.ll_no_course)
    LinearLayout llNoCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_mycourse);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this, "我的课程");

        refreshUtil = new RefreshUtil(this, lvCourse, refreshView, courseList, this);
        refreshView.setOnRefreshListener(this);

        myCourseData = new MyCourseData(this);
        myCourseData.refreshData();

    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        refreshUtil.onRefresh();
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        refreshUtil.onLoadMore();
    }

    @Override
    public void refreshSuccess(List<MyCourseBean> courseBeanList) {
        UIUtil.showLog("mycourse",courseBeanList.size()+"-----1");
        refreshUtil.refreshSuccess(courseBeanList);
    }

    @Override
    public void loadSuccess(List<MyCourseBean> courseBeanList) {
        refreshUtil.loadSuccess(courseBeanList);
    }

    @Override
    public void onRefreshFailure(String code, String msg) {
        if (code.equals("20007")) {
            llNoCourse.setVisibility(View.VISIBLE);
        }
        if (refreshView.isRefresh()) {
            refreshView.refreshFinish(PullToRefreshLayout.FAIL);
        }
        UIUtil.ToastshowShort(this, msg);
    }

    @Override
    public void onLoadFailure(String code, String msg) {
        refreshUtil.onFailure(code, msg);
    }

    @Override
    public void refreshData() {
        myCourseData.refreshData();
    }

    @Override
    public void loadData() {
        myCourseData.loadData(courseListAdapter.getSelf());
    }

    @Override
    public void newAdapter() {
        UIUtil.showLog("mycourse",courseList.size()+"-----3");
        courseListAdapter = new MyCourseListAdapter(this, courseList);
        lvCourse.setAdapter(courseListAdapter);
    }

    @Override
    public void notifyAdapter() {
        courseListAdapter.changeCount(courseList.size());
        courseListAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.bt_find_course)
    public void onClick() {
        startActivity(new Intent(this, LiveAllActivity.class));
        finish();
    }
}
