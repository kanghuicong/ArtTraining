package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.MajorBean;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.ui.homePage.adapter.TeacherMajorLeftAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.TeacherMajorRightAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.TeacherSchoolLeftAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.TeacherSchoolRightAdapter;
import com.example.kk.arttraining.ui.homePage.function.teacher.TeacherSearchData;
import com.example.kk.arttraining.ui.homePage.function.teacher.TeacherThemeData;
import com.example.kk.arttraining.ui.homePage.prot.ITeacher;
import com.example.kk.arttraining.ui.homePage.prot.ITeacherSearch;
import com.example.kk.arttraining.ui.school.bean.ProvinceBean;
import com.example.kk.arttraining.ui.school.bean.SchoolBean;
import com.example.kk.arttraining.ui.valuation.adapter.ValuationListViewAdapter;
import com.example.kk.arttraining.utils.TitleBack;

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
    ValuationListViewAdapter teacherListViewAdapter;

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
    @InjectView(R.id.lv_teacher_major_right)
    ListView lvTeacherMajorRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_teacher);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this, "名师");

        //刚进来显示的老师列表
        teacherThemeData = new TeacherThemeData(this);
        teacherSearchData = new TeacherSearchData(this);
        teacherSearchData.getTeacherSearchData("key", "key", 0);
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

    private void initThemeListView(String state, int num) {
        switch (state) {
            case "major":
                if (num == 0) {
                    //专业left
                    teacherThemeData.getTeacherMajorLeftData();
                    majorLeftAdapter = new TeacherMajorLeftAdapter(this, majorBeanLeftList);
                    lvTeacherMajorLeft.setAdapter(majorLeftAdapter);
                    lvTeacherMajorLeft.setOnItemClickListener(new MajorLeftClick());
                    //专业right
                    teacherThemeData.getTeacherMajorRightData(0);
                    majorRightAdapter = new TeacherMajorRightAdapter(this, majorBeanRightList);
                    lvTeacherMajorRight.setAdapter(majorRightAdapter);
                    lvTeacherMajorRight.setOnItemClickListener(new MajorRightClick());
                }
                break;
            case "school":
                if (num == 0) {
                    //学校left
                    teacherThemeData.getTeacherSchoolLeftData();
                    schoolLeftAdapter = new TeacherSchoolLeftAdapter(this, provinceBeanLeftList);
                    lvTeacherSchoolLeft.setAdapter(schoolLeftAdapter);
                    lvTeacherSchoolLeft.setOnItemClickListener(new SchoolLeftClick());
                    //学校Right
                    teacherThemeData.getTeacherSchoolRightData("江西", 0);
                    schoolRightAdapter = new TeacherSchoolRightAdapter(this, schoolBeanRightList);
                    lvTeacherSchoolRight.setAdapter(schoolRightAdapter);
                    lvTeacherSchoolRight.setOnItemClickListener(new SchoolRightClick());
                }
                break;
        }
    }

    //名师列表点击事件
    private class TeacherListItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(ThemeTeacher.this, ThemeTeacherContent.class);
            startActivity(intent);
        }
    }

    //获取首次进入显示的老师列表
    @Override
    public void getTeacher(List<TecInfoBean> tecInfoBeanList) {
        this.tecInfoBeanList = tecInfoBeanList;
        //名师列表
        teacherListViewAdapter = new ValuationListViewAdapter(this, tecInfoBeanList, 0, "teacher", new ValuationListViewAdapter.CallBack() {
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

    //更新老师列表
    @Override
    public void updateTeacher(List<TecInfoBean> tecInfoBeanList) {
        this.tecInfoBeanList = tecInfoBeanList;
        teacherListViewAdapter.notifyDataSetChanged();
    }

    //专业左边一级列表
    @Override
    public void getMajorLeft(List<MajorBean> majorBeanLeftList) {
        this.majorBeanLeftList = majorBeanLeftList;
    }

    //专业边二级级列表
    @Override
    public void getMajorRight(List<MajorBean> majorBeanRightList) {
        this.majorBeanRightList = majorBeanRightList;
    }

    //更新专业边二级级列表
    @Override
    public void getUpdateMajorRight(List<MajorBean> majorBeanRightList) {
        this.majorBeanRightList = majorBeanRightList;
        majorRightAdapter.notifyDataSetChanged();
    }

    //院校左边省级列表
    @Override
    public void getSchoolLeft(List<ProvinceBean> provinceBeanLeftList) {
        this.provinceBeanLeftList = provinceBeanLeftList;
    }

    //院校右边学院列表
    @Override
    public void getSchoolRight(List<SchoolBean> schoolBeanRightList) {
        this.schoolBeanRightList = schoolBeanRightList;
    }

    //更新院校右边学院列表
    @Override
    public void getUpdateSchoolRight(List<SchoolBean> schoolUpdateRightList) {
        this.schoolBeanRightList = schoolUpdateRightList;
        schoolRightAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnFailure(String error_code) {
    }

    private class MajorRightClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            initVisibility("teacher");
            state_profession = false;
//            teacherSearchData.getTeacherSearchData("spec",majorBeanRightList.get(position).getMajor_name(),1);
        }
    }

    private class MajorLeftClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            teacherThemeData.getTeacherMajorRightData(1);
        }
    }

    private class SchoolLeftClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            teacherThemeData.getTeacherSchoolRightData(provinceBeanLeftList.get(position).getName(),1);
        }
    }

    private class SchoolRightClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            teacherSearchData.getTeacherSearchData("college",schoolBeanRightList.get(position).get,1);
            state_school = false;
            initVisibility("teacher");
        }
    }
}