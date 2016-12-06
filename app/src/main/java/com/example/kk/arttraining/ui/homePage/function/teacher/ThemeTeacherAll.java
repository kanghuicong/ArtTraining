package com.example.kk.arttraining.ui.homePage.function.teacher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.ui.homePage.activity.ThemeTeacher;
import com.example.kk.arttraining.ui.homePage.activity.ThemeTeacherContent;
import com.example.kk.arttraining.ui.homePage.adapter.ThemeTeacherAdapter;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullToRefreshLayout;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullableListView;
import com.example.kk.arttraining.ui.homePage.prot.ITeacherSearch;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.UIUtil;
import com.mingle.widget.ShapeLoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/11/22.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeTeacherAll extends Activity implements ITeacherSearch, PullToRefreshLayout.OnRefreshListener {
    TeacherSearchData teacherSearchData;
    ThemeTeacherAdapter teacherListViewAdapter;
    List<TecInfoBean> tecInfoBeanList = new ArrayList<TecInfoBean>();
    int teacher_num = 0;
    boolean Flag = false;
    int teacherPosition = 0;
    int refreshResult = PullToRefreshLayout.FAIL;
//    ShapeLoadingDialog shapeLoadingDialog;
    String major ;
    @InjectView(R.id.refresh_view)
    PullToRefreshLayout refreshView;
    @InjectView(R.id.lv_teacher)
    PullableListView lvTeacher;
    @InjectView(R.id.tv_default_teacher)
    TextView tvDefaultTeacher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_teacher_major_fragment);
        ButterKnife.inject(this);



//        shapeLoadingDialog = new ShapeLoadingDialog(this);
//        shapeLoadingDialog.show();
//        shapeLoadingDialog.setLoadingText("加载中...");

        major = getIntent().getStringExtra("major");
        UIUtil.showLog("major", major);
        teacherSearchData = new TeacherSearchData(this);
        teacherSearchData.getTeacherListData(major);
        refreshView.setOnRefreshListener(this);
    }

    @Override
    public void getTeacher(List<TecInfoBean> tecInfoBeanList1) {
        Flag = true;
        teacher_num = tecInfoBeanList1.size();
        tvDefaultTeacher.setVisibility(View.GONE);
        //名师列表
        if (teacherPosition == 0) {
            tecInfoBeanList.addAll(tecInfoBeanList1);
            teacherListViewAdapter = new ThemeTeacherAdapter(getApplicationContext(), tecInfoBeanList);
            lvTeacher.setAdapter(teacherListViewAdapter);
            lvTeacher.setOnItemClickListener(new TeacherListItemClick());
            teacherPosition++;
        } else {
            tecInfoBeanList.clear();
            tecInfoBeanList.addAll(tecInfoBeanList1);
            teacherListViewAdapter.ChangeCount(tecInfoBeanList1.size());
            teacherListViewAdapter.notifyDataSetChanged();
            teacher_num = tecInfoBeanList1.size();
        }
//        shapeLoadingDialog.dismiss();
    }

    //名师列表点击事件
    private class TeacherListItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(ThemeTeacherAll.this, ThemeTeacherContent.class);
            intent.putExtra("tec_id", tecInfoBeanList.get(position).getTec_id() + "");
            startActivity(intent);
        }
    }

    @Override
    public void loadTeacher(List<TecInfoBean> tecInfoBeanList1) {
        tecInfoBeanList.addAll(tecInfoBeanList1);
        UIUtil.showLog("loadTeacher", tecInfoBeanList.size() + "");
        UIUtil.showLog("loadTeacher_num1", teacher_num + "");
        teacher_num = teacher_num + tecInfoBeanList1.size();
        UIUtil.showLog("loadTeacher_num2", teacher_num + "");
        teacherListViewAdapter.ChangeCount(teacher_num);
        teacherListViewAdapter.notifyDataSetChanged();
        refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void OnLoadTeacherFailure(int result) {
        switch (result) {
            case 0:
                refreshResult = PullToRefreshLayout.EMPTY;
                break;
            case 1:
                refreshResult = PullToRefreshLayout.FAIL;
                break;
            case 2:
                refreshResult = PullToRefreshLayout.FAIL;
                UIUtil.ToastshowShort(this, "网络连接失败！");
                break;
        }
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                refreshView.loadmoreFinish(refreshResult);
            }
        }.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public void OnTeacherFailure(String result) {
//        shapeLoadingDialog.dismiss();
        UIUtil.ToastshowShort(this,result);
    }

    @Override
    public void updateTeacher(List<TecInfoBean> tecInfoBeanList) {
    }

    @Override
    public void OnFailure(String error_code) {
        tvDefaultTeacher.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        teacherSearchData.getTeacherListData(major);
        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        if (Flag) {
            UIUtil.showLog("loadTeacher_Self", teacherListViewAdapter.getSelfId() + "");
            teacherSearchData.loadTeacherListData(teacherListViewAdapter.getSelfId(),major);
        }
    }
}
