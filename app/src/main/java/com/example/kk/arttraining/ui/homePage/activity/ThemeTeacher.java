package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.custom.view.MyGridView;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.homePage.adapter.TeacherGridViewAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.TeacherListViewAdapter;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/10/18.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeTeacher extends Activity {

    TeacherListViewAdapter teacherListViewAdapter;
    SimpleAdapter simpleAdapter;
    List<Map<String, String>> mList = new ArrayList<Map<String, String>>();
    List<Boolean> isClick = new LinkedList<Boolean>();
    int isClickNum = 0;
    Boolean state_profession = false;
    Boolean state_school = false;
    Boolean state_regional = false;
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    TeacherGridViewAdapter teacherGridViewAdapter;

    @InjectView(R.id.lv_teacher)
    MyListView lvTeacher;
    @InjectView(R.id.lv_teacher_theme)
    MyListView lvTeacherTheme;
    @InjectView(R.id.ll_teacher_pay)
    LinearLayout llTeacherPay;
    @InjectView(R.id.gv_teacher)
    MyGridView gvTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_teacher);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this, "名师");

        List<TecInfoBean> tecInfoBeanList = new ArrayList<TecInfoBean>();
        for (int i=0;i<10;i++) {
            TecInfoBean tecInfoBean = new TecInfoBean();
            tecInfoBean.setName("小灰灰"+i);
            tecInfoBean.setTec_id(i);
            tecInfoBeanList.add(tecInfoBean);
        }

        for (int i = 0; i < 15; i++) {
            isClick.add(i, false);
        }

        teacherListViewAdapter = new TeacherListViewAdapter(this, tecInfoBeanList, isClick,isClickNum,new TeacherListViewAdapter.CallBack() {
            @Override
            public void callbackAdd(int position,int misClickNum,int id, String name) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("position", position + "");
                map.put("id", id + "");
                map.put("name", name);
                list.add(map);
                isClickNum = misClickNum;
                if (list.size()==1){
                    teacherGridViewAdapter = new TeacherGridViewAdapter(ThemeTeacher.this, list);
                    gvTeacher.setAdapter(teacherGridViewAdapter);
                }else {
                    teacherGridViewAdapter.notifyDataSetChanged();
                }
                isClick.set(position, true);
            }

            @Override
            public void callbackSub(int position,int misClickNum,int id, String name) {
                isClickNum = misClickNum;
                for (int i=0;i<list.size();i++) {
                    if(list.get(i).get("name").equals(name)){
                        list.remove(i);
                        break;
                    }
                }
                teacherGridViewAdapter.notifyDataSetChanged();
                isClick.set(position, false);
            }

        });

        gvTeacher.setOnItemClickListener(new TeacherGridItemClick());
        lvTeacher.setAdapter(teacherListViewAdapter);
        lvTeacher.setOnItemClickListener(new TeacherListItemClick());
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
            llTeacherPay.setVisibility(View.GONE);
            initThemeListView(state);
        } else if (!state_profession && (state_regional || state_school)) {
            yesState(state);
            getDate(state);
            simpleAdapter.notifyDataSetChanged();
        } else if (state_profession) {
            noState();
            lvTeacherTheme.setVisibility(View.GONE);
            lvTeacher.setVisibility(View.VISIBLE);
            llTeacherPay.setVisibility(View.VISIBLE);
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
            lvTeacherTheme.setVisibility(View.GONE);
            lvTeacher.setVisibility(View.VISIBLE);
            llTeacherPay.setVisibility(View.VISIBLE);
        }
    }

    private class TeacherGridItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            isClick.set(Integer.valueOf(list.get(position).get("position")), false);
            TeacherListViewAdapter.Count(isClickNum-1);
            list.remove(position);
            teacherGridViewAdapter.notifyDataSetChanged();
            teacherListViewAdapter.notifyDataSetChanged();
            isClickNum--;
        }
    }
}