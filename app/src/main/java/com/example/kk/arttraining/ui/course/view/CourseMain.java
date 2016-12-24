package com.example.kk.arttraining.ui.course.view;

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
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.course.adapter.CourseListAdapter;
import com.example.kk.arttraining.ui.course.adapter.ListDropDownAdapter;
import com.example.kk.arttraining.ui.course.bean.CourseBean;
import com.example.kk.arttraining.ui.course.presenter.CourseListData;
import com.example.kk.arttraining.ui.course.function.DropDownMenu;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullToRefreshLayout;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullableGridView;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kanghuicong on 2016/12/16.
 * QQ邮箱:515849594@qq.com
 */
public class CourseMain extends Fragment implements ICourseMainView, PullToRefreshLayout.OnRefreshListener {

    Activity activity;
    View view_course;

    private String headers[] = {"类别", "水平等级"};
    private String sort[] = {"不限", "音乐"};
    private String level[] = {"不限", "初级", "中级", "高级"};
//    private String style[] = {"不限", "200"};

    private List<View> popupViews = new ArrayList<View>();
    ListDropDownAdapter sortAdapter;
    ListDropDownAdapter levelAdapter;
//    ListDropDownAdapter styleAdapter;

    DropDownMenu mDropDownMenu;
    CourseListAdapter courseListAdapter;

    PullToRefreshLayout refreshView;
    PullableGridView gvDrop;

    CourseListData courseListData;
    List<CourseBean> courseList = new ArrayList<CourseBean>();

    String Key = "";
    static String Sort = "";
    static String Level = "";

    boolean Flag = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        if (view_course == null) {
            view_course = View.inflate(activity, R.layout.homepage_course_main, null);
            initFindViewById();
            initData();
        }
        ViewGroup parent = (ViewGroup) view_course.getParent();
        if (parent != null) {
            parent.removeView(view_course);
        }
        return view_course;
    }

    private void initData() {
        courseListData = new CourseListData(this);
        courseListData.getCourseListData(Key, Sort, 0, Level);
    }

    private void initFindViewById() {
        mDropDownMenu = (DropDownMenu) view_course.findViewById(R.id.dropDownMenu);

        //实例化筛选列表
        sortAdapter = new ListDropDownAdapter(activity, Arrays.asList(sort));
        ListView sortView = getAdapter(sortAdapter);
        levelAdapter = new ListDropDownAdapter(activity, Arrays.asList(level));
        ListView levelView = getAdapter(levelAdapter);
//        styleAdapter = new ListDropDownAdapter(activity,Arrays.asList(style));
//        ListView styleView = getAdapter(styleAdapter);

        sortView.setOnItemClickListener(new SortItemClick());
        levelView.setOnItemClickListener(new LevelItemClick());
//        styleView.setOnItemClickListener(new StyleItemClick());

        //添加上下拉刷新
        refreshView = new PullToRefreshLayout(activity);
        refreshView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        refreshView.setOnRefreshListener(this);

        View view_refresh_head = View.inflate(activity, R.layout.homepage_refresh_head, null);
        refreshView.addView(view_refresh_head, 0);

        gvDrop = new PullableGridView(activity);
        gvDrop.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        gvDrop.setNumColumns(2);
        gvDrop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                CourseBean courseBean = (CourseBean) parent.getItemAtPosition(position);
//                Intent intent = new Intent(activity, CourseDetailActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("courseBean", courseBean);
//                intent.putExtras(bundle);
//                activity.startActivity(intent);

                CourseBean courseBean = (CourseBean) parent.getItemAtPosition(position);
                Intent intent = new Intent(activity, ArtCourseActivity.class);
                intent.putExtra("course_id",courseBean.getCourse_id());
                intent.putExtra("course_name",courseBean.getCourse_name());
                startActivity(intent);
            }
        });
        refreshView.addView(gvDrop, 1);

        View view_refresh_load = View.inflate(activity, R.layout.homepage_refresh_load, null);
        refreshView.addView(view_refresh_load, 2);

        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, refreshView);

        mDropDownMenu.SearchCourseClick(new DropDownMenu.ISearchCourse() {
            @Override
            public void searchContent(String tv) {
                Key = tv;
            }

            @Override
            public void searchCourseClick() {
                courseListData.getCourseListData(Key, Sort, 0, Level);
            }
        });
    }

    @Override
    public void getCourseList(List<CourseBean> course_list) {
        Flag = true;
        if (courseList == null || courseList.size() == 0) {
            courseList.addAll(course_list);
            courseListAdapter = new CourseListAdapter(activity, courseList);
            gvDrop.setAdapter(courseListAdapter);
        } else {
            courseList.clear();
            courseList.addAll(course_list);
            courseListAdapter.changeCount(courseList.size());
            courseListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnCourseFailure() {
        UIUtil.ToastshowShort(activity, "网络连接失败");
    }


    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        courseListData.getCourseListData(Key, Sort, 0, Level);
        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        if (Flag) {
            courseListData.loadCourseListData("", Sort, courseListAdapter.getSelfId(), Level);
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
    public void loadCourseList(List<CourseBean> course_list) {
        courseList.addAll(course_list);
        courseListAdapter.changeCount(courseList.size());
        courseListAdapter.notifyDataSetChanged();
        refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void OnLoadCourseListFailure(int code) {
        if (code == 404) {
            UIUtil.ToastshowShort(activity, "网络连接失败！");
        }

        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                refreshView.loadmoreFinish(PullToRefreshLayout.FAIL);
            }
        }.sendEmptyMessageDelayed(0, 1000);
    }


    private class SortItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            sortAdapter.setCheckItem(position);
            Sort = (position == 0 ? headers[0] : sort[position]);
            mDropDownMenu.setTabText(position == 0 ? headers[0] : sort[position]);
            courseListData.getCourseListData(Key, Sort, 0, Level);
            mDropDownMenu.closeMenu();
        }
    }

    private class LevelItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            levelAdapter.setCheckItem(position);
            Level = (position == 0 ? headers[1] : level[position]);
            mDropDownMenu.setTabText(position == 0 ? headers[1] : level[position]);
            courseListData.getCourseListData(Key, Sort, 0, Level);
            mDropDownMenu.closeMenu();
        }
    }

//    private class StyleItemClick implements AdapterView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            styleAdapter.setCheckItem(position);
//            mDropDownMenu.setTabText(position == 0 ? headers[2] : style[position]);
//            mDropDownMenu.closeMenu();
//        }
//    }

    public ListView getAdapter(ListDropDownAdapter listDropDownAdapter) {
        final ListView view = new ListView(activity);
        view.setDividerHeight(0);
        view.setAdapter(listDropDownAdapter);
        popupViews.add(view);
        return view;
    }
}
