package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.parsebean.OrgShowBean;
import com.example.kk.arttraining.custom.view.InnerView;
import com.example.kk.arttraining.custom.view.MyGridView;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.homePage.adapter.InstitutionCourseAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.InstitutionStudentAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.InstitutionTagsAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.InstitutionTeacherAdapter;
import com.example.kk.arttraining.ui.homePage.bean.Course;
import com.example.kk.arttraining.ui.homePage.bean.Teachers;
import com.example.kk.arttraining.ui.homePage.bean.Trainees;
import com.example.kk.arttraining.ui.homePage.function.institution.InstitutionContentDate;
import com.example.kk.arttraining.ui.homePage.prot.IInstitutionContent;
import com.example.kk.arttraining.utils.TitleBack;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/11/2.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeInstitutionContent extends Activity implements IInstitutionContent {
    InstitutionTagsAdapter institutionTagsAdapter;
    InstitutionTeacherAdapter institutionTeacherAdapter;
    InstitutionStudentAdapter institutionStudentAdapter;
    InstitutionCourseAdapter institutionCourseAdapter;

    @InjectView(R.id.vp_institution)
    InnerView vpInstitution;
    @InjectView(R.id.gv_institution_teacher)
    MyGridView gvInstitutionTeacher;
    @InjectView(R.id.lv_institution_student)
    MyListView lvInstitutionStudent;
    @InjectView(R.id.tv_institution_content_comment)
    TextView tvInstitutionContentComment;
    @InjectView(R.id.tv_institution_content_fans)
    TextView tvInstitutionContentFans;
    @InjectView(R.id.tv_institution_content_skill)
    TextView tvInstitutionContentSkill;
    @InjectView(R.id.tv_institution_content_name)
    TextView tvInstitutionContentName;
    @InjectView(R.id.iv_institution_content_auth)
    ImageView ivInstitutionContentAuth;
    @InjectView(R.id.tv_institution_content_remarks)
    TextView tvInstitutionContentRemarks;
    @InjectView(R.id.gv_institution_tags)
    MyGridView gvInstitutionTags;
    @InjectView(R.id.lv_institution_course)
    MyListView lvInstitutionCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_institution_content);
        ButterKnife.inject(this);

        TitleBack.TitleBackActivity(this, getIntent().getStringExtra("name"));

//        Shuffling.initShuffling(vpInstitution, this);//轮播

        InstitutionContentDate institutionContentDate = new InstitutionContentDate(this);
        institutionContentDate.getInstitutionContentDate(Integer.valueOf(getIntent().getStringExtra("name")));


    }

    @Override
    public void getInstitutionContent(OrgShowBean orgShowBean) {
        tvInstitutionContentComment.setText("评论:"+orgShowBean.getComment());
        tvInstitutionContentFans.setText("粉丝:"+orgShowBean.getFans_num());
        tvInstitutionContentSkill.setText("专长:"+orgShowBean.getSkill());
        tvInstitutionContentName.setText(orgShowBean.getName());
        tvInstitutionContentRemarks.setText(orgShowBean.getIntroduction());
    }

    @Override
    public void getInstitutionTags(List<OrgShowBean.Tags> tags) {
        //标签
        institutionTagsAdapter = new InstitutionTagsAdapter(this, tags);
        gvInstitutionTags.setAdapter(institutionStudentAdapter);
    }

    @Override
    public void getInstitutionTeacher(List<Teachers> teachers) {
        //老师列表
        institutionTeacherAdapter = new InstitutionTeacherAdapter(this, teachers);
        gvInstitutionTeacher.setAdapter(institutionStudentAdapter);
    }

    @Override
    public void getInstitutionCourse(List<Course> course) {
        //课程列表
        institutionCourseAdapter = new InstitutionCourseAdapter(this,course);
        lvInstitutionCourse.setAdapter(institutionStudentAdapter);
    }

    @Override
    public void getInstitutionStudent(List<Trainees> trainees) {
        //学生列表
        institutionStudentAdapter = new InstitutionStudentAdapter(this, trainees);
        lvInstitutionStudent.setAdapter(institutionStudentAdapter);
    }

    @Override
    public void OnFailure(String error_code) {

    }
}
