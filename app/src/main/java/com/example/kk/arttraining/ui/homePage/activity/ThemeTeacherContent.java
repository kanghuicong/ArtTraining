package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.parsebean.TecherShow;
import com.example.kk.arttraining.utils.TitleBack;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/10/31.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeTeacherContent extends Activity {

    TecherShow techerShow;

    @InjectView(R.id.iv_teacher_header)
    ImageView ivTeacherHeader;
    @InjectView(R.id.tv_teacher_name)
    TextView tvTeacherName;
    @InjectView(R.id.tv_teacher_address)
    TextView tvTeacherAddress;
    @InjectView(R.id.tv_teacher_school)
    TextView tvTeacherSchool;
    @InjectView(R.id.tv_teacher_specialty)
    TextView tvTeacherSpecialty;
    @InjectView(R.id.tv_teacher_like)
    TextView tvTeacherLike;
    @InjectView(R.id.tv_teacher_fans)
    TextView tvTeacherFans;
    @InjectView(R.id.tv_teacher_group)
    TextView tvTeacherGroup;
    @InjectView(R.id.tv_teacher_focus)
    TextView tvTeacherFocus;
    @InjectView(R.id.tv_teacher_introduction)
    TextView tvTeacherIntroduction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_teacher_content);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this, "名师详情");

//        String headerPath = techerShow.getPic();
//        Glide.with(this).load(headerPath).transform(new GlideCircleTransform(this)).error(R.mipmap.ic_launcher).into(ivTeacherHeader);
//        tvTeacherName.setText(techerShow.getName());
//        tvTeacherAddress.setText(techerShow.getCity());
//        tvTeacherSchool.setText(techerShow.getCollege());
//        tvTeacherSpecialty.setText(techerShow.getSpecialty());
//        tvTeacherLike.setText(techerShow.getLike_num());
//        tvTeacherFans.setText(techerShow.getFans_num());
//        tvTeacherGroup.setText(techerShow.get);
//        tvTeacherFocus.setText();
//        tvTeacherIntroduction.setText(techerShow.getIntroduction());


    }

    @OnClick({R.id.tv_teacher_focus_on, R.id.bt_teacher_measurement, R.id.bt_teacher_teaching})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_teacher_focus_on:
                break;
            case R.id.bt_teacher_measurement:
                break;
            case R.id.bt_teacher_teaching:
                break;
        }
    }
}
