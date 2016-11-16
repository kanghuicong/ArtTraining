package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.MajorBean;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.ui.homePage.adapter.TeacherMajorLeftAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.TeacherMajorRightAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.TeacherSchoolLeftAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.TeacherSchoolRightAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.ThemeTeacherAdapter;
import com.example.kk.arttraining.ui.homePage.function.teacher.TeacherSearchData;
import com.example.kk.arttraining.ui.homePage.function.teacher.TeacherThemeData;
import com.example.kk.arttraining.ui.homePage.prot.ITeacher;
import com.example.kk.arttraining.ui.homePage.prot.ITeacherSearch;
import com.example.kk.arttraining.ui.school.bean.ProvinceBean;
import com.example.kk.arttraining.ui.school.bean.SchoolBean;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/10/18.
 * QQ邮箱:515849594@qq.com
 */

public class ThemeTeacher extends Activity implements ITeacherSearch, ITeacher {

    TeacherThemeData teacherThemeData;
    TeacherSearchData teacherSearchData;

    List<TecInfoBean> tecInfoBeanList = new ArrayList<TecInfoBean>();
    ThemeTeacherAdapter teacherListViewAdapter;

    List<MajorBean> majorBeanLeftList = new ArrayList<MajorBean>();
    List<MajorBean> majorBeanRightList = new ArrayList<MajorBean>();
    TeacherMajorLeftAdapter majorLeftAdapter;
    TeacherMajorRightAdapter majorRightAdapter;

    List<ProvinceBean> provinceBeanLeftList = new ArrayList<ProvinceBean>();
    List<SchoolBean> schoolBeanRightList = new ArrayList<SchoolBean>();
    TeacherSchoolLeftAdapter schoolLeftAdapter;
    TeacherSchoolRightAdapter schoolRightAdapter;

    Boolean state_profession = false;
    Boolean state_school = false;
    int num_profession = 0;
    int num_school = 0;

    @InjectView(R.id.lv_teacher)
    ListView lvTeacher;
    @InjectView(R.id.ll_school_theme)
    LinearLayout llSchoolTheme;
    @InjectView(R.id.ll_profession_theme)
    LinearLayout llProfessionTheme;
    @InjectView(R.id.lv_teacher_school_left)
    ListView lvTeacherSchoolLeft;
    @InjectView(R.id.lv_teacher_school_right)
    ListView lvTeacherSchoolRight;
    @InjectView(R.id.lv_teacher_major_left)
    ListView lvTeacherMajorLeft;
    @InjectView(R.id.gv_teacher_major_right)
    GridView gvTeacherMajorRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_teacher);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this, "名师");

        //刚进来显示的老师列表
        teacherThemeData = new TeacherThemeData(this);

        teacherSearchData = new TeacherSearchData(this);
        teacherSearchData.getTeacherListData();
    }

    @OnClick({R.id.rb_teacher_profession, R.id.rb_teacher_school})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_teacher_profession:
                initSate("major", num_profession, state_profession);
                num_profession++;
                break;
            case R.id.rb_teacher_school:
                initSate("school", num_school, state_school);
                num_school++;
                break;
        }
    }

    private void initThemeListView(String state, int num) {
        switch (state) {
            case "major":
                if (num == 0) {
                    //获取专业所以数据
                    teacherThemeData.getTeacherMajorData();
                }
                break;
            case "school":
                if (num == 0) {
                    //学校left
                    teacherThemeData.getTeacherSchoolLeftData();
                }
                break;
        }
    }


    //获取默认显示的老师列表
    @Override
    public void getTeacher(List<TecInfoBean> tecInfoBeanList) {
        this.tecInfoBeanList = tecInfoBeanList;
        //名师列表
        teacherListViewAdapter = new ThemeTeacherAdapter(this, tecInfoBeanList);
        lvTeacher.setAdapter(teacherListViewAdapter);
        lvTeacher.setOnItemClickListener(new TeacherListItemClick());
    }

    //更新老师列表
    @Override
    public void updateTeacher(List<TecInfoBean> tecInfoBeanList) {
        this.tecInfoBeanList = tecInfoBeanList;
        teacherListViewAdapter.notifyDataSetChanged();
    }

    //名师列表点击事件
    private class TeacherListItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(ThemeTeacher.this, ThemeTeacherContent.class);
            intent.putExtra("tec_id", tecInfoBeanList.get(position).getTec_id() + "");
            startActivity(intent);
        }
    }

    //专业列表
    @Override
    public void getMajor(List<MajorBean> majorBean) {
        this.majorBeanLeftList = majorBean;
        majorLeftAdapter = new TeacherMajorLeftAdapter(this, majorBeanLeftList);
        lvTeacherMajorLeft.setAdapter(majorLeftAdapter);
        lvTeacherMajorLeft.setOnItemClickListener(new MajorLeftClick());

        //专业right默认列表
        majorBeanRightList.addAll(majorBeanLeftList.get(0).getSon_majors());
        majorRightAdapter = new TeacherMajorRightAdapter(this, majorBeanRightList);
        gvTeacherMajorRight.setAdapter(majorRightAdapter);
        gvTeacherMajorRight.setOnItemClickListener(new MajorRightClick());
    }

    //专业左边点击事件，更新右边专业
    private class MajorLeftClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            majorBeanRightList.clear();
            majorBeanRightList.addAll(majorBeanLeftList.get(position).getSon_majors());
            majorRightAdapter.changeCount(majorBeanRightList.size());
            majorRightAdapter.notifyDataSetChanged();
        }
    }

    //专业右边点击事件，更新老师列表
    private class MajorRightClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            initVisibility("teacher");
            yesState("teacher");
            teacherSearchData.getTeacherSearchData("spec", majorBeanRightList.get(position).getMajor_name());
        }
    }

    //院校默认列表
    @Override
    public void getSchoolLeft(List<ProvinceBean> provinceBeanLeftList1) {
        provinceBeanLeftList.addAll(provinceBeanLeftList1);
        schoolLeftAdapter = new TeacherSchoolLeftAdapter(this, provinceBeanLeftList);
        lvTeacherSchoolLeft.setAdapter(schoolLeftAdapter);
        lvTeacherSchoolLeft.setOnItemClickListener(new SchoolLeftClick());
        //院校Right默认列表
        teacherThemeData.getTeacherSchoolRightData(0, provinceBeanLeftList.get(0).getName());
    }

    //院校右边学院默认列表
    @Override
    public void getSchoolRight(List<SchoolBean> schoolBeanRightList1) {
        schoolBeanRightList.addAll(schoolBeanRightList1);
        schoolRightAdapter = new TeacherSchoolRightAdapter(this, schoolBeanRightList);
        lvTeacherSchoolRight.setAdapter(schoolRightAdapter);
        lvTeacherSchoolRight.setOnItemClickListener(new SchoolRightClick());
    }

    //更新院校右边学院列表
    @Override
    public void getUpdateSchoolRight(List<SchoolBean> schoolUpdateRightList) {
        schoolBeanRightList.clear();
        schoolBeanRightList.addAll(schoolUpdateRightList);
        schoolRightAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnFailure(String error_code) {
    }

    //院校左边点击事件
    private class SchoolLeftClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            teacherThemeData.getTeacherSchoolRightData(1, provinceBeanLeftList.get(position).getName());
        }
    }

    private class SchoolRightClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            teacherSearchData.getTeacherSearchData("college",schoolBeanRightList.get(position).get,1);
            yesState("school");
            initVisibility("teacher");
        }
    }

    public void yesState(String state) {
        switch (state) {
            case "major":
                state_profession = true;
                state_school = false;
                break;
            case "school":
                state_profession = false;
                state_school = true;
                break;
            case "teacher":
                state_profession = false;
                state_school = false;
                break;
        }
    }

    public void initVisibility(String state) {
        switch (state) {
            case "major":
                lvTeacher.setVisibility(View.GONE);
                llProfessionTheme.setVisibility(View.VISIBLE);
                llSchoolTheme.setVisibility(View.GONE);
                break;
            case "school":
                lvTeacher.setVisibility(View.GONE);
                llProfessionTheme.setVisibility(View.GONE);
                llSchoolTheme.setVisibility(View.VISIBLE);
                break;
            case "teacher":
                lvTeacher.setVisibility(View.VISIBLE);
                llProfessionTheme.setVisibility(View.GONE);
                llSchoolTheme.setVisibility(View.GONE);
                break;
        }
    }

    public void initSate(String state, int num, Boolean isClickState) {
        if (!isClickState) {
            yesState(state);
            initVisibility(state);
            initThemeListView(state, num);
        } else if (isClickState) {
            yesState("teacher");
            initVisibility("teacher");
        }
    }

}