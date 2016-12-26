package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.ui.course.bean.ArtTeacherBean;
import com.example.kk.arttraining.ui.course.bean.ArtTypeBean;
import com.example.kk.arttraining.ui.homePage.adapter.ThemeArtMajorAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.ThemeArtTeacherAdapter;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullToRefreshLayout;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullableGridView;
import com.example.kk.arttraining.ui.homePage.function.teacher.TeacherArtSearchData;
import com.example.kk.arttraining.ui.homePage.prot.ITeacherArtSearch;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/12/23.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeTeacherArtFragment extends Fragment implements ITeacherArtSearch, PullToRefreshLayout.OnRefreshListener {

    String identity;
    String major;
    int art_type_id = 0;
    Activity activity;
    View view;

    TeacherArtSearchData teacherSearchData;

    List<ArtTeacherBean> artTeacherBeanList = new ArrayList<ArtTeacherBean>();
    ThemeArtTeacherAdapter themeArtTeacherAdapter;
    int teacher_num = 0;
    boolean Flag = false;
    int teacherPosition = 0;
    List<ArtTypeBean> typeList = new ArrayList<ArtTypeBean>();


    @InjectView(R.id.gv_teacher)
    PullableGridView gvTeacher;
    @InjectView(R.id.refresh_view)
    PullToRefreshLayout refreshView;
    @InjectView(R.id.tv_default_teacher)
    TextView tvDefaultTeacher;

    LoadingDialog progressDialog;
    @InjectView(R.id.lv_art_type)
    ListView lvArtType;

    ThemeArtMajorAdapter themeArtMajorAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();

        if (view == null) {
            view = View.inflate(activity, R.layout.homepage_teacher_fragment, null);
            ButterKnife.inject(this, view);
            identity = getArguments().getString("type");
            major = getArguments().getString("major");

            progressDialog = new LoadingDialog(activity);
            progressDialog.setTitle("数据加载中");
            progressDialog.show();


            teacherSearchData = new TeacherArtSearchData(this);
            teacherSearchData.getArtSchoolData(major,art_type_id);
            teacherSearchData.getArtType();

            refreshView.setOnRefreshListener(this);
        }

        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

        ButterKnife.inject(this, view);
        return view;
    }


    @Override
    public void getArtType(List<ArtTypeBean> type_list) {
        UIUtil.showLog("type_list", type_list.size() + "---1");
        ArtTypeBean artTypeBean = new ArtTypeBean();
        artTypeBean.setName("全部");
        artTypeBean.setType_id(0);
        typeList.add(artTypeBean);
        typeList.addAll(type_list);
        UIUtil.showLog("type_list", typeList.size() + "---2");

        themeArtMajorAdapter = new ThemeArtMajorAdapter(activity, typeList);
        lvArtType.setAdapter(themeArtMajorAdapter);
        lvArtType.setOnItemClickListener(new ArtMajorClick());
    }

    @Override
    public void getArtTeacher(List<ArtTeacherBean> artTeacherBeanList1) {
        Flag = true;

        if (artTeacherBeanList1.size() == 0) {
            artTeacherBeanList.clear();
            tvDefaultTeacher.setVisibility(View.VISIBLE);
            themeArtTeacherAdapter.ChangeCount(artTeacherBeanList1.size());
            themeArtTeacherAdapter.notifyDataSetChanged();
            teacher_num = artTeacherBeanList.size();
        }else {
            tvDefaultTeacher.setVisibility(View.GONE);
            if (teacherPosition == 0) {
                teacher_num = artTeacherBeanList1.size();
                artTeacherBeanList.addAll(artTeacherBeanList1);
                themeArtTeacherAdapter = new ThemeArtTeacherAdapter(activity.getApplicationContext(), artTeacherBeanList);
                gvTeacher.setAdapter(themeArtTeacherAdapter);
                gvTeacher.setOnItemClickListener(new TeacherListItemClick());
                teacherPosition++;
            } else {
                artTeacherBeanList.clear();
                artTeacherBeanList.addAll(artTeacherBeanList1);
                themeArtTeacherAdapter.ChangeCount(artTeacherBeanList1.size());
                themeArtTeacherAdapter.notifyDataSetChanged();
                teacher_num = artTeacherBeanList.size();
            }
        }

        progressDialog.dismiss();

    }

    @Override
    public void OnTeacherFailure(String result) {
        progressDialog.dismiss();
        UIUtil.ToastshowShort(activity, result);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

        teacherSearchData.getArtSchoolData(major,art_type_id);
        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        if (Flag) {
            teacherSearchData.loadTeacherListData(major, teacher_num,art_type_id);
        } else {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    refreshView.loadmoreFinish(PullToRefreshLayout.FAIL);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }
    }

    @Override
    public void loadArtTeacher(List<ArtTeacherBean> artTeacherBeanList1) {
        if (artTeacherBeanList1 != null && artTeacherBeanList1.size() != 0) {


            if (artTeacherBeanList.size() == 0 || artTeacherBeanList == null) {
                getArtTeacher(artTeacherBeanList1);
            } else {
                artTeacherBeanList.addAll(artTeacherBeanList1);
                teacher_num = teacher_num + artTeacherBeanList1.size();

                themeArtTeacherAdapter.ChangeCount(teacher_num);
                themeArtTeacherAdapter.notifyDataSetChanged();
            }
            refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        } else {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    refreshView.loadmoreFinish(PullToRefreshLayout.EMPTY);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }
    }

    @Override
    public void OnLoadTeacherFailure(String result) {
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                refreshView.loadmoreFinish(PullToRefreshLayout.FAIL);
            }
        }.sendEmptyMessageDelayed(0, 1000);
    }

    private class TeacherListItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(activity, ThemeTeacherArtContent.class);
            intent.putExtra("tec_id", artTeacherBeanList.get(position).getTeacher_id() + "");
            startActivity(intent);
        }
    }

    private class ArtMajorClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {
                art_type_id = 0;
            }else {
                art_type_id = typeList.get(position).getType_id();
            }
            themeArtMajorAdapter.selectPosition(position);
            themeArtMajorAdapter.notifyDataSetChanged();

            teacherSearchData.getArtSchoolData(major,art_type_id);
        }
    }
}

