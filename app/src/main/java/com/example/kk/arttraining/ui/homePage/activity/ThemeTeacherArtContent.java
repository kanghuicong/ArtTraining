package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.JustifyText;
import com.example.kk.arttraining.ui.course.bean.ArtTeacherContentBean;
import com.example.kk.arttraining.ui.homePage.function.teacher.TeacherArtContentData;
import com.example.kk.arttraining.ui.homePage.prot.ITeacherArtContent;
import com.example.kk.arttraining.custom.view.GlideCircleTransform;
import com.example.kk.arttraining.utils.UIUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/10/31.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeTeacherArtContent extends Activity implements ITeacherArtContent {

    @InjectView(R.id.iv_teacher_header)
    ImageView ivTeacherHeader;
    @InjectView(R.id.tv_teacher_name)
    TextView tvTeacherName;
    @InjectView(R.id.tv_teacher_title)
    TextView tvTeacherTitle;

    @InjectView(R.id.tv_teacher_introduction)
    JustifyText tvTeacherIntroduction;
    @InjectView(R.id.fl_teacher_content)
    FrameLayout flTeacherContent;
    @InjectView(R.id.no_wifi)
    TextView noWifi;
    @InjectView(R.id.teacher_bg)
    ImageView teacherBg;
    @InjectView(R.id.tv_title_bar)
    TextView tvTitleBar;

    TeacherArtContentData teacherArtContentData;
    ArtTeacherContentBean artTeacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_teacher_art_content);
        ButterKnife.inject(this);

        teacherArtContentData = new TeacherArtContentData(this);
        teacherArtContentData.getTeacherArtContentData(Integer.valueOf(getIntent().getStringExtra("tec_id")));
    }

    @OnClick({R.id.iv_teacher_header, R.id.iv_title_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.iv_teacher_header:
                Intent intent_header = new Intent(this, ShareDynamicImage.class);
                intent_header.putExtra("image_path", artTeacher.getIcon_url());
                int[] location = new int[2];
                ivTeacherHeader.getLocationOnScreen(location);
                intent_header.putExtra("locationX", location[0]);
                intent_header.putExtra("locationY", location[1]);
                intent_header.putExtra("width", ivTeacherHeader.getWidth());
                intent_header.putExtra("height", ivTeacherHeader.getHeight());
                startActivity(intent_header);
                overridePendingTransition(0, 0);
                break;
        }
    }

    @Override
    public void getTeacherContent(ArtTeacherContentBean artTeacher) {
        this.artTeacher = artTeacher;

        tvTitleBar.setText(artTeacher.getName());
        Glide.with(getApplicationContext()).load(artTeacher.getIcon_url()).transform(new GlideCircleTransform(this)).error(R.mipmap.default_user_header).into(ivTeacherHeader);
        tvTeacherName.setText(artTeacher.getName());
        if (artTeacher.getArt_type() == null || artTeacher.getArt_type().equals("")) {
            tvTeacherTitle.setVisibility(View.INVISIBLE);
        } else {
            tvTeacherTitle.setText(artTeacher.getArt_type());
        }

        Glide.with(getApplicationContext()).load(artTeacher.getImage_url()).error(R.mipmap.default_teacher_bg).into(teacherBg);
        tvTeacherIntroduction.setText(artTeacher.getProfile());
    }

    @Override
    public void OnTeacherContentFailure() {
        UIUtil.ToastshowLong(this,"数据加载失败");
    }

    @Override
    public void NoWifi() {
        flTeacherContent.setVisibility(View.GONE);
        noWifi.setVisibility(View.VISIBLE);
    }
}
