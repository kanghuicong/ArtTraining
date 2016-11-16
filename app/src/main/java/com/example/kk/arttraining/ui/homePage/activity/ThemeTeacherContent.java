package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.bean.parsebean.TecherShow;
import com.example.kk.arttraining.ui.homePage.function.teacher.TeacherContentData;
import com.example.kk.arttraining.ui.homePage.prot.ITeacherContent;
import com.example.kk.arttraining.ui.valuation.view.ValuationMain;
import com.example.kk.arttraining.utils.GlideCircleTransform;
import com.example.kk.arttraining.utils.TitleBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/10/31.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeTeacherContent extends Activity implements ITeacherContent{

    TecherShow techerShow;
    TeacherContentData teacherContentData;

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

        teacherContentData = new TeacherContentData(this);
        teacherContentData.getTeacherContentData(Integer.valueOf(getIntent().getStringExtra("tec_id")));

    }

    @OnClick({R.id.iv_teacher_focus_on, R.id.bt_teacher_measurement, R.id.bt_teacher_teaching})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_teacher_focus_on:
                break;
            case R.id.bt_teacher_measurement:
                List<TecInfoBean> list = new ArrayList<TecInfoBean>();
                TecInfoBean tecInfoBean = new TecInfoBean();
                tecInfoBean.setName(techerShow.getName());
                tecInfoBean.setTec_id(techerShow.getTec_id());
                tecInfoBean.setAss_pay(techerShow.getAss_pay());
                list.add(tecInfoBean);
                Intent intent = new Intent(this, ValuationMain.class);
                intent.putExtra("type", techerShow.getSpecialty());
                intent.putStringArrayListExtra("tec", (ArrayList)list);
                startActivity(intent);
                break;
            case R.id.bt_teacher_teaching:
                break;
        }
    }

    @Override
    public void getTeacherContent(TecherShow techerShow) {
        this.techerShow = techerShow;
        Glide.with(this).load(techerShow.getPic()).transform(new GlideCircleTransform(this)).error(R.mipmap.default_user_header).into(ivTeacherHeader);
        tvTeacherName.setText(techerShow.getName());
        tvTeacherAddress.setText(techerShow.getCity());
        tvTeacherSchool.setText(techerShow.getCollege());
        tvTeacherSpecialty.setText(techerShow.getSpecialty());
        tvTeacherLike.setText("点赞数:"+techerShow.getLike_num());
        tvTeacherFans.setText("粉丝数:"+techerShow.getFans_num());
//        tvTeacherGroup.setText(techerShow.);
//        tvTeacherFocus.setText(techerShow.get);
        tvTeacherIntroduction.setText(techerShow.getIntroduction());
    }

    @Override
    public void OnTeacherContentFailure(String error_code) {

    }
}
