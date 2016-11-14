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
import com.example.kk.arttraining.ui.homePage.adapter.InstitutionStudentAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_institution_content);
        ButterKnife.inject(this);

        TitleBack.TitleBackActivity(this, getIntent().getStringExtra("name"));
//        Shuffling.initShuffling(vpInstitution, this);//轮播

        InstitutionContentDate institutionContentDate = new InstitutionContentDate(this);
        institutionContentDate.getInstitutionContentDate(Integer.valueOf(getIntent().getStringExtra("name")));
        //老师列表
        InstitutionTeacherAdapter teacherAdapter = new InstitutionTeacherAdapter(this);
        gvInstitutionTeacher.setAdapter(teacherAdapter);

        //学生列表
        InstitutionStudentAdapter studentAdapter = new InstitutionStudentAdapter(this);
        lvInstitutionStudent.setAdapter(studentAdapter);


    }

    @Override
    public void getInstitutionContent(OrgShowBean orgShowBean) {
        tvInstitutionContentComment.setText(orgShowBean.getComment() + "");
        tvInstitutionContentFans.setText(orgShowBean.getFans_num() + "");
        tvInstitutionContentSkill.setText(orgShowBean.getSkill());
        tvInstitutionContentName.setText(orgShowBean.getName());
        tvInstitutionContentRemarks.setText(orgShowBean.getIntroduction());
    }

    @Override
    public void getInstitutionTags(List<OrgShowBean.Tags> tags) {
        for (int i=0;i<tags.size();i++) {

        }
    }

    @Override
    public void getInstitutionTeacher(List<Teachers> teachers) {

    }

    @Override
    public void getInstitutionCourse(List<Course> course) {

    }

    @Override
    public void getInstitutionStudent(List<Trainees> trainees) {

    }

    @Override
    public void OnFailure(String error_code) {

    }
}
