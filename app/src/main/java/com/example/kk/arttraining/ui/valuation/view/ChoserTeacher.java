package com.example.kk.arttraining.ui.valuation.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.ui.school.bean.SchoolBean;
import com.example.kk.arttraining.ui.valuation.adapter.FirstAdapter;
import com.example.kk.arttraining.ui.valuation.adapter.TeacherAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/10/29 09:50
 * 说明:
 */
public class ChoserTeacher extends Activity implements IChoseTeacher {

    @InjectView(R.id.valuation_lv_first)
    ListView lv_first;
    @InjectView(R.id.valuation_lv_school)
    ListView lv_school;

    private FirstAdapter firstAdapter;
    private TeacherAdapter teacherAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valuation_activity_choseteacher);
        initView();
    }

    void initView() {
        ButterKnife.inject(this);
        firstAdapter = new FirstAdapter(ChoserTeacher.this);
        teacherAdapter = new TeacherAdapter(ChoserTeacher.this);
        lv_first.setAdapter(firstAdapter);
        lv_school.setAdapter(teacherAdapter);

    }

    @Override
    public void getTeacherData(List<TecInfoBean> tecInfoBeanList) {

    }

    @Override
    public void getSchoolData(List<SchoolBean> schoolBeanList) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onFailure() {

    }
}
