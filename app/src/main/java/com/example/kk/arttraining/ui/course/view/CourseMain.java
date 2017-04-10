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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.MyGridView;
import com.example.kk.arttraining.ui.course.adapter.CourseListAdapter;
import com.example.kk.arttraining.ui.course.adapter.ListDropDownAdapter;
import com.example.kk.arttraining.ui.course.bean.ArtTypeBean;
import com.example.kk.arttraining.ui.course.bean.CourseBean;
import com.example.kk.arttraining.ui.course.presenter.CourseListData;
import com.example.kk.arttraining.ui.course.function.DropDownMenu;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullToRefreshLayout;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullableGridView;
import com.example.kk.arttraining.utils.UIUtil;
import com.mingle.widget.ShapeLoadingDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kanghuicong on 2016/12/16.
 * QQ邮箱:515849594@qq.com
 */
public class CourseMain extends Fragment implements ICourseMainView, PullToRefreshLayout.OnRefreshListener{

    Activity activity;
    View view_course;

    private String headers[] = {"音乐类别", "水平等级"};
    //    private String sort[] = {"不限", "音乐"};
    List<String> sort = new ArrayList<String>();
    List<Integer> sortFlag = new ArrayList<>();
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
    static int Sort = 0;
    static String Level = "";

    boolean Flag = false;
    ShapeLoadingDialog shapeLoadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        if (view_course == null) {
            view_course = View.inflate(activity, R.layout.homepage_course_main, null);
            shapeLoadingDialog = new ShapeLoadingDialog(activity);
            shapeLoadingDialog.setLoadingText("加载中...");
            shapeLoadingDialog.show();
            shapeLoadingDialog.setCanceledOnTouchOutside(false);
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
        courseListData.getArtType();
    }

    private void initFindViewById() {
        mDropDownMenu = (DropDownMenu) view_course.findViewById(R.id.dropDownMenu);

        //实例化筛选列表
        sortAdapter = new ListDropDownAdapter(activity, sort);
        ListView sortView = getAdapter(sortAdapter);
        levelAdapter = new ListDropDownAdapter(activity, Arrays.asList(level));
        ListView levelView = getAdapter(levelAdapter);

        sortView.setOnItemClickListener(new SortItemClick());
        levelView.setOnItemClickListener(new LevelItemClick());
        
        //添加上下拉刷新
        refreshView = new PullToRefreshLayout(activity);
        refreshView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        refreshView.setOnRefreshListener(this);

        View view_refresh_head = View.inflate(activity, R.layout.homepage_refresh_head, null);
        refreshView.addView(view_refresh_head, 0);

        gvDrop = new PullableGridView(activity);
        gvDrop.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        gvDrop.setNumColumns(2);
        gvDrop.setFastScrollEnabled(false);
        gvDrop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews,refreshView);
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
    public void getArtType(List<ArtTypeBean> type_list) {
        if (sort.size() != 0) {
            sort.clear();
        }
        sort.add("不限");
        sortFlag.add(0);

        for (int i = 0 ;i<type_list.size();i++) {
            sort.add(type_list.get(i).getName());
            sortFlag.add(type_list.get(i).getType_id());
            UIUtil.showLog("ArtTypeBean",type_list.get(i).getName()+"-----"+type_list.get(i).getType_id());
        }
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
        shapeLoadingDialog.dismiss();
    }

    @Override
    public void OnCourseFailure() {
        UIUtil.ToastshowShort(activity, "网络连接失败");
        shapeLoadingDialog.dismiss();
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        courseListData.getCourseListData(Key, Sort, 0, Level);
        courseListData.getArtType();
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

//    /**
//     * 监听listview滑动状态  如果是滑动状态停止加载图片
//     * @param view
//     * @param scrollState
//     */
//    @Override
//    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        switch (scrollState) {
//            case SCROLL_STATE_FLING:
//                Glide.with(activity.getApplicationContext()).pauseRequests();
//                //刷新
//                break;
//            case SCROLL_STATE_IDLE:
//                Glide.with(activity.getApplicationContext()).resumeRequests();
//                break;
//            case SCROLL_STATE_TOUCH_SCROLL:
//                Glide.with(activity.getApplicationContext()).resumeRequests();
//                break;
//        }
//    }
//    @Override
//    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//    }

    private class SortItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            sortAdapter.setCheckItem(position);
            Sort = sortFlag.get(position);
            mDropDownMenu.setTabText(position == 0 ? headers[0] : sort.get(position));
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

    public ListView getAdapter(ListDropDownAdapter listDropDownAdapter) {
        final ListView view = new ListView(activity);
        view.setDividerHeight(0);
        view.setAdapter(listDropDownAdapter);
        popupViews.add(view);
        return view;
    }

}
