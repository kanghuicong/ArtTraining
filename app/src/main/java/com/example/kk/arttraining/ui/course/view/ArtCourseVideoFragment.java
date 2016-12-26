package com.example.kk.arttraining.ui.course.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.course.adapter.ArtCourseVideoListAdapter;
import com.example.kk.arttraining.ui.course.bean.ChapterBean;
import com.example.kk.arttraining.ui.course.bean.LessonBean;
import com.example.kk.arttraining.ui.course.presenter.ArtCourseVideoPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/12/23 17:09
 * 说明:章节列表fragment
 */
public class ArtCourseVideoFragment extends Fragment implements IArtCousrseVideoFragmentView, ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener {

    @InjectView(R.id.tv_video_cousr_name)
    TextView tvVideoCousrName;
    @InjectView(R.id.course_chapter_num)
    TextView courseChapterNum;
    @InjectView(R.id.course_elv)
    ExpandableListView courseElv;
    private Context context;
    private View view;

    private String course_id;
    private ArtCourseVideoPresenter artCourseVideoPresenter;
    private ArtCourseVideoListAdapter courseVideoListAdapter;

    private String course_name;
    private String chapter_id;
    private String chapter_name;
    private int groupPosition;
    Map<String, List<LessonBean>> map;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        if (view == null) {
            view = View.inflate(context, R.layout.course_iart_video_fragment, null);
            ButterKnife.inject(this, view);
            init();
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        ButterKnife.inject(this, view);
        courseElv.setOnChildClickListener(this);
        courseElv.setOnGroupClickListener(this);
        return view;
    }

    void init() {
        Bundle bundle = getArguments();
        course_id = bundle.getString("course_id");
        UIUtil.showLog("course_id-------->", course_id + "");
        artCourseVideoPresenter = new ArtCourseVideoPresenter(this);
        getChapterData();
    }

    //测试  模式数据
    void test(List<ChapterBean> chapterBeanList) {
        List<String> parentData = new ArrayList<String>();
        List<String> chapterIdList = new ArrayList<String>();


        map = new HashMap<String, List<LessonBean>>();
        course_name = chapterBeanList.get(0).getCourse_name();
        tvVideoCousrName.setText(course_name + "");
        for (int i = 0; i < chapterBeanList.size(); i++) {
            List<LessonBean> lessonBeanList = new ArrayList<LessonBean>();
            ChapterBean chapterBean = chapterBeanList.get(i);
            chapterIdList.add(chapterBean.getChapter_id());
            parentData.add(i + 1 + "." + chapterBean.getName());
            for (int j = 0; j < 5; j++) {
                LessonBean lessonBean = new LessonBean();
                lessonBean.setName("课程" + j);
                lessonBeanList.add(lessonBean);
            }
            map.put(i + 1 + "." + chapterBean.getName(), lessonBeanList);
        }
        courseVideoListAdapter = new ArtCourseVideoListAdapter(context, parentData, chapterBeanList, map);
        courseElv.setAdapter(courseVideoListAdapter);
        courseElv.setGroupIndicator(null);
    }

    //获取章节列表
    @Override
    public void getChapterData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user_name", Config.ArtName);
        map.put("course_id", course_id);
        artCourseVideoPresenter.getChapterData(map);

    }

    //获取课程列表
    @Override
    public void getLessonData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user_name", Config.ArtName);
        map.put("chapter_id", chapter_id);
        artCourseVideoPresenter.getLessonData(map);
    }

    //获取章节列表成功
    @Override
    public void SuccessChapter(List<ChapterBean> chapterBeanList, int total_num) {
        test(chapterBeanList);
    }

    //获取课程列表成功
    @Override
    public void SuccessLesson(List<LessonBean> lessonBeanList) {
        map.put(groupPosition + 1 + "." + chapter_name, lessonBeanList);
        courseVideoListAdapter.notifyDataSetChanged();

    }

    //获取章节列表失败
    @Override
    public void FailureChapter(int error_code, String error_msg) {

    }

    //获取课程列表失败
    @Override
    public void FailureLesson(int error_code, String error_msg) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    //章节点击时事件
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        UIUtil.showLog("childPosition___position----->", childPosition + "");
        return false;
    }

    //课堂点击事件
    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

//        ChapterBean chapterBean= (ChapterBean) parent.getItemAtPosition(groupPosition);
//        chapter_id= chapterBean.getChapter_id();
//        chapter_name=chapterBean.getName();
//        UIUtil.showLog("chapter_id------->",chapter_id);
//        getLessonData();
//        UIUtil.showLog("parent___position----->",groupPosition+"");
        return false;
    }
}
