package com.example.kk.arttraining.ui.course.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.course.adapter.CourseListAdapter;
import com.example.kk.arttraining.ui.course.adapter.ListDropDownAdapter;
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
public class CourseMain extends Fragment {

    Activity activity;
    View view_course;

    private String headers[] = {"类别", "水平等级", "风格"};
    private String sort[] = { "不限", "2", "3", "4", "5", "6"};
    private String level[] = {"不限", "20", "30", "40", "50"};
    private String style[] = {"不限", "200"};

    private List<View> popupViews = new ArrayList<View>();
    ListDropDownAdapter sortAdapter;
    ListDropDownAdapter levelAdapter;
    ListDropDownAdapter styleAdapter;

    DropDownMenu mDropDownMenu;
    CourseListAdapter courseListAdapter;

    PullToRefreshLayout refreshView;
    PullableGridView gvDrop;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        activity = getActivity();
        if (view_course == null) {
            view_course = View.inflate(activity, R.layout.homepage_course_main, null);
            initFindViewById();
        }
        ViewGroup parent = (ViewGroup) view_course.getParent();
        if (parent != null) {
            parent.removeView(view_course);
        }
        return view_course;
    }

    private void initFindViewById() {
        mDropDownMenu = (DropDownMenu)view_course.findViewById(R.id.dropDownMenu);

        //实例化筛选列表
        sortAdapter = new ListDropDownAdapter(activity, Arrays.asList(sort));
        ListView sortView = getAdapter(sortAdapter);
        levelAdapter = new ListDropDownAdapter(activity,Arrays.asList(level));
        ListView levelView = getAdapter(levelAdapter);
        styleAdapter = new ListDropDownAdapter(activity,Arrays.asList(style));
        ListView styleView = getAdapter(styleAdapter);

        sortView.setOnItemClickListener(new SortItemClick());
        levelView.setOnItemClickListener(new LevelItemClick());
        styleView.setOnItemClickListener(new StyleItemClick());

        //添加上下拉刷新
        refreshView = new PullToRefreshLayout(activity);
        refreshView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        View view_refresh_head = View.inflate(activity, R.layout.homepage_refresh_head, null);
        refreshView.addView(view_refresh_head, 0);

        gvDrop = new PullableGridView(activity);
        gvDrop.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        gvDrop.setNumColumns(2);

        courseListAdapter = new CourseListAdapter(activity);
        gvDrop.setAdapter(courseListAdapter);
        refreshView.addView(gvDrop, 1);

        View view_refresh_load = View.inflate(activity, R.layout.homepage_refresh_load, null);
        refreshView.addView(view_refresh_load, 2);

        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, refreshView);

    }

    private class SortItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            sortAdapter.setCheckItem(position);
            mDropDownMenu.setTabText(position == 0 ? headers[0] : sort[position]);
            UIUtil.showLog("mDropDownMenu",position == 0 ? headers[0] : sort[position]);
            mDropDownMenu.closeMenu();
        }
    }

    private class LevelItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            levelAdapter.setCheckItem(position);
            mDropDownMenu.setTabText(position == 0 ? headers[1] : level[position]);
            mDropDownMenu.closeMenu();
        }
    }

    private class StyleItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            styleAdapter.setCheckItem(position);
            mDropDownMenu.setTabText(position == 0 ? headers[2] : style[position]);
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
