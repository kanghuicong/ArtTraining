package com.example.kk.arttraining.ui.course.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.JustifyText;
import com.example.kk.arttraining.custom.view.MyGridView;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.course.bean.CourseBean;
import com.example.kk.arttraining.utils.TitleBack;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/12/17 15:05
 * 说明:课程首页点击课程后进入到课程详情页面
 */
public class CourseDetailActivity extends BaseActivity {

    //课程背景图
    @InjectView(R.id.iv_course_cover_pic)
    ImageView ivCoursePic;
    //课程教师名称
    @InjectView(R.id.tv_course_tecName)
    TextView tvCourseTecName;
    //课程教师名称
    @InjectView(R.id.tv_course_name)
    TextView tvCourseTitle;
    //视频数量
    @InjectView(R.id.tv_course_videoNum)
    TextView tvCourseVideoNum;
    //众筹金额
    @InjectView(R.id.tv_coure_raise_money)
    TextView tvCoureRaiseMoney;
    //众筹人数
    @InjectView(R.id.tv_course_raise_num)
    TextView tvCourseRaiseNum;
    //众筹已经付款
    @InjectView(R.id.tv_course_raise_yet)
    TextView tvCourseRaiseYet;
    //众筹进度条
    @InjectView(R.id.tv_course_progress)
    ProgressBar tvCourseProgress;
    //众筹进度
    @InjectView(R.id.tv_course_progress_num)
    TextView tvCourseProgressNum;
    //学习人数
    @InjectView(R.id.tv_course_study_num)
    TextView tvCourseStudyNum;
    //点击展开章节列表
    @InjectView(R.id.iv_unfold_chapter)
    ImageView ivUnfoldChapter;
    //章节列表
    @InjectView(R.id.lv_course_chapter)
    MyListView lvCourseChapter;
    //推荐其他大师课
    @InjectView(R.id.gv_course_recommend_tec)
    MyGridView gvCourseRecommendTec;
    //咨询
    @InjectView(R.id.tv_course_consult)
    TextView tvCourseConsult;
    //vip免费听
    @InjectView(R.id.tv_course_vip)
    TextView tvCourseVip;
    //分享
    @InjectView(R.id.tv_course_share)
    TextView tvCourseShare;
    //立即购买
    @InjectView(R.id.btn_course_buy)
    Button btnCourseBuy;
    //参与众筹
    @InjectView(R.id.btn_course_raise)
    Button btnCourseRaise;
    //参与众筹
    @InjectView(R.id.tv_course_describe)
    JustifyText btnCourseDescribe;

    //课程信息
    CourseBean courseBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_info_activity);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        courseBean= (CourseBean) bundle.getSerializable("courseBean");
        TitleBack.TitleBackActivity(this, courseBean.getCourse_name());
        tvCourseTitle.setText(courseBean.getCourse_name());
        tvCourseTecName.setText(courseBean.getTeacher_name());
        btnCourseDescribe.setText(courseBean.getCourse_profile());
        Glide.with(getApplicationContext()).load(courseBean.getIcon_url()).into(ivCoursePic);
    }

    @OnClick({R.id.btn_course_buy, R.id.tv_course_share, R.id.tv_course_vip, R.id.tv_course_consult, R.id.iv_unfold_chapter, R.id.btn_course_raise})
    public void onClick(View v) {
        switch (v.getId()) {
            //立即购买
            case R.id.btn_course_buy:
                break;
            //分享
            case R.id.tv_course_share:
                break;
            //vip免费听
            case R.id.tv_course_vip:
                break;
            //咨询
            case R.id.tv_course_consult:
                break;
            //点击展开列表
            case R.id.iv_unfold_chapter:
                break;
            //参与众筹
            case R.id.btn_course_raise:
                break;
        }
    }
}
