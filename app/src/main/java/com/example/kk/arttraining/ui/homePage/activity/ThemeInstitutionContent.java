package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.parsebean.OrgShowBean;
import com.example.kk.arttraining.custom.view.InnerView;
import com.example.kk.arttraining.custom.view.MyGridView;
import com.example.kk.arttraining.ui.homePage.adapter.InstitutionTeacherAdapter;
import com.example.kk.arttraining.ui.homePage.function.homepage.Shuffling;
import com.example.kk.arttraining.ui.homePage.prot.IInstitutionContent;
import com.example.kk.arttraining.utils.TitleBack;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_institution_content);
        ButterKnife.inject(this);

        TitleBack.TitleBackActivity(this, getIntent().getStringExtra("name"));
        Shuffling.initShuffling(vpInstitution, this);//轮播
        //详情数据
//        OrgShowBean orgShowBean = InstitutionContentDate.getInstitutionContentDate(Integer.valueOf(getIntent().getStringExtra("org_id")));

        //老师列表
        InstitutionTeacherAdapter teacherAdapter = new InstitutionTeacherAdapter(this);
        gvInstitutionTeacher.setAdapter(teacherAdapter);

        homepage_institution_student_item
    }

    @Override
    public void getInstitutionContent(OrgShowBean orgShowBean) {

    }

    @Override
    public void OnFailure(String error_code) {

    }
}
