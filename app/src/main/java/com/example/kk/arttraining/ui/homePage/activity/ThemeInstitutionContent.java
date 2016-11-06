package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.parsebean.OrgShowBean;
import com.example.kk.arttraining.custom.view.InnerView;
import com.example.kk.arttraining.ui.homePage.function.homepage.Shuffling;
import com.example.kk.arttraining.ui.homePage.function.institution.InstitutionContentDate;
import com.example.kk.arttraining.utils.TitleBack;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/11/2.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeInstitutionContent extends Activity {
    @InjectView(R.id.vp_institution)
    InnerView vpInstitution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_institution_content);
        ButterKnife.inject(this);

        TitleBack.TitleBackActivity(this,getIntent().getStringExtra("name"));
        Shuffling.initShuffling(vpInstitution,this);//轮播
        //详情数据
//        OrgShowBean orgShowBean = InstitutionContentDate.getInstitutionContentDate(Integer.valueOf(getIntent().getStringExtra("org_id")));

    }
}
