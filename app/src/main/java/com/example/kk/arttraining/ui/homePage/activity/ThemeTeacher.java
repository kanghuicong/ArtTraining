package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.homePage.function.homepage.TeacherSearchData;
import com.example.kk.arttraining.ui.homePage.prot.ITeacherSearch;
import com.example.kk.arttraining.ui.valuation.adapter.ValuationListViewAdapter;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/10/18.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeTeacher extends Activity implements ITeacherSearch {

    ValuationListViewAdapter teacherListViewAdapter;
    SimpleAdapter simpleAdapter;
    List<Map<String, String>> mList = new ArrayList<Map<String, String>>();
    List<Boolean> isClick = new LinkedList<Boolean>();
    int isClickNum = 0;
    Boolean state_profession = false;
    Boolean state_school = false;
    Boolean state_regional = false;

    List<TecInfoBean> tecInfoBeanList = new ArrayList<TecInfoBean>();
    TeacherSearchData teacherSearchData;
    @InjectView(R.id.lv_teacher)
    MyListView lvTeacher;
    @InjectView(R.id.lv_teacher_theme)
    MyListView lvTeacherTheme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_teacher);
        ButterKnife.inject(this);

        TitleBack.TitleBackActivity(this, "名师");

        teacherSearchData = new TeacherSearchData(this);
        teacherSearchData.getTeacherSearchData("key");

    }

    @OnClick({R.id.rb_teacher_profession, R.id.rb_teacher_school, R.id.rb_teacher_regional})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_teacher_profession:
                initSate("profession", state_profession, state_school, state_regional);
                break;
            case R.id.rb_teacher_school:
                initSate("school", state_school, state_profession, state_regional);
                break;
            case R.id.rb_teacher_regional:
                initSate("regional", state_regional, state_school, state_profession);
                break;
        }
    }


    @Override
    public void getTeacher(List<TecInfoBean> tecInfoBeanList) {
        this.tecInfoBeanList = tecInfoBeanList;
        //名师列表
        teacherListViewAdapter = new ValuationListViewAdapter(this, tecInfoBeanList,isClickNum, "teacher", new ValuationListViewAdapter.CallBack() {
            @Override
            public void callbackAdd(int misClickNum, TecInfoBean tecInfoBean) {
            }
            @Override
            public void callbackSub(int misClickNum, TecInfoBean tecInfoBean) {
            }
        });

        lvTeacher.setAdapter(teacherListViewAdapter);
        lvTeacher.setOnItemClickListener(new TeacherListItemClick());
    }

    @Override
    public void updateTeacher(List<TecInfoBean> tecInfoBeanList) {
        this.tecInfoBeanList = tecInfoBeanList;
        teacherListViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnFailure(String error_code) {

    }

    //名师列表点击事件
    private class TeacherListItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(ThemeTeacher.this, ThemeTeacherContent.class);
            startActivity(intent);
        }
    }

    public void initSate(String state, Boolean state_profession, Boolean state_school, Boolean state_regional) {
        if (!state_profession && !state_regional && !state_school) {
            yesState(state);
            lvTeacherTheme.setVisibility(View.VISIBLE);
            lvTeacher.setVisibility(View.GONE);
            initThemeListView(state);
        } else if (!state_profession && (state_regional || state_school)) {
            yesState(state);
            getDate(state);
            simpleAdapter.notifyDataSetChanged();
        } else if (state_profession) {
            noState();
            lvTeacherTheme.setVisibility(View.GONE);
            lvTeacher.setVisibility(View.VISIBLE);
        }
    }

    private void initThemeListView(String state) {
        getDate(state);
        simpleAdapter = new SimpleAdapter(this, mList,
                R.layout.homepage_search_history_listview, new String[]{"content"},
                new int[]{R.id.tv_search_history});
        lvTeacherTheme.setAdapter(simpleAdapter);
        lvTeacherTheme.setOnItemClickListener(new ThemeItemClick());
    }

    public void noState() {
        state_profession = false;
        state_regional = false;
        state_school = false;
    }

    public void yesState(String state) {
        switch (state) {
            case "profession":
                state_profession = true;
                state_regional = false;
                state_school = false;
                break;
            case "school":
                state_profession = false;
                state_regional = false;
                state_school = true;
                break;
            case "regional":
                state_profession = false;
                state_regional = true;
                state_school = false;
                break;
        }
    }

    public void getDate(String state) {
        mList.clear();
        switch (state) {
            case "profession":
                for (int i = 0; i < 30; i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("content", i + "");
                    mList.add(map);
                }
                break;
            case "school":
                for (int i = 0; i < 30; i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("content", i + 10 + "");
                    mList.add(map);
                }
                break;
            case "regional":
                for (int i = 0; i < 30; i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("content", i + 100 + "");
                    mList.add(map);
                }
                break;
        }
    }

    private class ThemeItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            UIUtil.ToastshowShort(ThemeTeacher.this, position + "");
            noState();
            mList.clear();
            teacherSearchData.getTeacherSearchData("key");
            lvTeacherTheme.setVisibility(View.GONE);
            lvTeacher.setVisibility(View.VISIBLE);
        }
    }
}