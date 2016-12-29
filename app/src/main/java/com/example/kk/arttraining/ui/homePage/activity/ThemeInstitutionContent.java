package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.parsebean.OrgShowBean;
import com.example.kk.arttraining.custom.view.InnerView;
import com.example.kk.arttraining.custom.view.JustifyText;
import com.example.kk.arttraining.custom.view.MyGridView;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.homePage.adapter.InstitutionCourseAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.InstitutionStudentAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.InstitutionTagsAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.InstitutionTeacherAdapter;
import com.example.kk.arttraining.ui.homePage.bean.Course;
import com.example.kk.arttraining.ui.homePage.bean.Teachers;
import com.example.kk.arttraining.ui.homePage.bean.Trainees;
import com.example.kk.arttraining.ui.homePage.function.homepage.FollowCreate;
import com.example.kk.arttraining.ui.homePage.function.institution.InstitutionContentDate;
import com.example.kk.arttraining.ui.homePage.prot.IFollow;
import com.example.kk.arttraining.ui.homePage.prot.IInstitutionContent;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.LruCacheUtils;
import com.example.kk.arttraining.utils.PhotoLoader;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/11/2.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeInstitutionContent extends Activity implements IInstitutionContent, IFollow {
    InstitutionTagsAdapter institutionTagsAdapter;
    InstitutionTeacherAdapter institutionTeacherAdapter;
    InstitutionStudentAdapter institutionStudentAdapter;
    InstitutionCourseAdapter institutionCourseAdapter;
    View teacher_view, course_view, student_view;
    OrgShowBean orgShowBean = new OrgShowBean();
    String FollowType;
    FollowCreate followCreate;
    InstitutionContentDate institutionContentDate;
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
    JustifyText tvInstitutionContentRemarks;
    @InjectView(R.id.gv_institution_tags)
    MyGridView gvInstitutionTags;
    @InjectView(R.id.lv_institution_course)
    MyListView lvInstitutionCourse;
    @InjectView(R.id.iv_institution_focus)
    TextView ivInstitutionFocus;
    @InjectView(R.id.iv_institution_remark)
    ImageView ivInstitutionRemark;
    @InjectView(R.id.tv_title_bar)
    TextView tvTitleBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_institution_content);
        ButterKnife.inject(this);

//        TitleBack.TitleBackActivity(this, getIntent().getStringExtra("name"));

        tvTitleBar.setText(getIntent().getStringExtra("name"));

        institutionContentDate = new InstitutionContentDate(this);
        institutionContentDate.getInstitutionContentDate(Integer.valueOf(getIntent().getStringExtra("org_id")));

//        teacher_view = (View) findViewById(R.id.ll_institution_teacher);
//        course_view = (View) findViewById(R.id.ll_institution_course);
//        student_view = (View) findViewById(R.id.ll_institution_student);

//        FindTitle.findTitle(teacher_view,this,"优秀师资",R.mipmap.arrow_right_topic,"institution_teacher");
//        FindTitle.findTitle(course_view,this,"课程介绍",R.mipmap.arrow_right_topic,"institution_course");
//        FindTitle.findTitle(student_view,this,"优秀考生",R.mipmap.arrow_right_topic,"institution_student");

    }

    @Override
    public void getInstitutionContent(OrgShowBean orgShowBean1) {
        orgShowBean = orgShowBean1;

        UIUtil.showLog("getRemarks", orgShowBean.getRemarks());
//        Glide.with(this).load(orgShowBean.getRemarks()).error(R.mipmap.default_advertisement).diskCacheStrategy( DiskCacheStrategy.SOURCE ).into(ivInstitutionRemark);
        Bitmap bitmap = LruCacheUtils.getInstance().getBitmapFromMemCache(orgShowBean.getRemarks());
        if (bitmap != null) {
            ivInstitutionRemark.setImageBitmap(bitmap);
        } else {
            PhotoLoader.displayImageTarget(ivInstitutionRemark, orgShowBean.getRemarks(), PhotoLoader.getTarget(ivInstitutionRemark,
                    orgShowBean.getRemarks(), 0),R.mipmap.me_bg);
        }

        tvInstitutionContentComment.setText("评论:" + orgShowBean.getComment());
        tvInstitutionContentFans.setText("粉丝:" + orgShowBean.getFans_num());
        if (orgShowBean.getSkill() != null && !orgShowBean.getSkill().equals("")) {
            tvInstitutionContentSkill.setText("专长:" + orgShowBean.getSkill());
        } else {
            tvInstitutionContentSkill.setVisibility(View.GONE);
        }

        tvInstitutionContentName.setText(orgShowBean.getName());

        String tv1 = orgShowBean.getIntroduction().replace("\\n", "\n\n");
        String tv2 = tv1.replace("\\u3000", "\u3000");
        orgShowBean.setIntroduction(tv2);
        tvInstitutionContentRemarks.setText(orgShowBean.getIntroduction());

        FollowType = orgShowBean.getIs_follow();
        UIUtil.showLog("FollowType",FollowType);
        if (orgShowBean.getIs_follow().equals("no")) {
            ivInstitutionFocus.setText("+ 关注");
        } else if (orgShowBean.getIs_follow().equals("yes")) {
            ivInstitutionFocus.setText("已关注");
        }
//        getInstitutionTags(orgShowBean.getTags());
//        getInstitutionTeacher(orgShowBean.getTeachers());
//        getInstitutionCourse(orgShowBean.getCourse());
//        getInstitutionStudent(orgShowBean.getTrainees());
    }

    public void getInstitutionTags(List<OrgShowBean.Tags> tags) {
        //标签
        institutionTagsAdapter = new InstitutionTagsAdapter(this, tags);
        gvInstitutionTags.setAdapter(institutionTagsAdapter);
    }

    public void getInstitutionTeacher(List<Teachers> teachers) {
        //老师列表
        institutionTeacherAdapter = new InstitutionTeacherAdapter(this, teachers);
        gvInstitutionTeacher.setAdapter(institutionTeacherAdapter);
    }

    public void getInstitutionCourse(List<Course> course) {
        //课程列表
        institutionCourseAdapter = new InstitutionCourseAdapter(this, course);
        lvInstitutionCourse.setAdapter(institutionCourseAdapter);
    }

    public void getInstitutionStudent(List<Trainees> trainees) {
        //学生列表
        institutionStudentAdapter = new InstitutionStudentAdapter(this, trainees);
        lvInstitutionStudent.setAdapter(institutionStudentAdapter);
    }

    public void OnFailure(String error_code) {

    }

    @OnClick({R.id.iv_title_back,R.id.iv_institution_focus})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.iv_institution_focus:
                if (Config.ACCESS_TOKEN != null && !Config.ACCESS_TOKEN.equals("")) {
                    if (FollowType != null) {
                        if (FollowType.equals("no")) {
                            followCreate = new FollowCreate(this);
                            followCreate.getFocus(this, "org", orgShowBean.getOrg_id());
                        } else {
                            UIUtil.ToastshowShort(this, "已经关注了");
                        }
                    }

                } else {
                    UIUtil.ToastshowShort(this, getResources().getString(R.string.toast_user_login));
                    startActivity(new Intent(this, UserLoginActivity.class));
                }
                break;
        }
    }

    @Override
    public void getCreateFollow() {
        ivInstitutionFocus.setText("已关注");
        FollowType = "yes";
        UIUtil.ToastshowShort(this, "关注成功！");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        institutionContentDate.getInstitutionContentDate(Integer.valueOf(getIntent().getStringExtra("org_id")));
    }
}
