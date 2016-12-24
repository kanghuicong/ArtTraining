package com.example.kk.arttraining.ui.course.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.course.adapter.IArtFindTabAdapter;
import com.example.kk.arttraining.ui.course.bean.CourseBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 作者：wschenyongyin on 2016/12/23 15:43
 * 说明:I艺术课程详情页面
 */
public class ArtCourseActivity extends FragmentActivity implements View.OnClickListener {

    @InjectView(R.id.tab_FindFragment_title)
    TabLayout tabFindFragmentTitle;
    @InjectView(R.id.vp_FindFragment_pager)
    ViewPager vpFindFragmentPager;
    @InjectView(R.id.course_videoplayer_standard)
    JCVideoPlayerStandard courseVideoplayerStandard;
    @InjectView(R.id.course_video)
    FrameLayout courseVideo;
    @InjectView(R.id.course_bg)
    ImageView courseBg;
    @InjectView(R.id.course_btn_back)
    ImageView courseBtnBack;

    private ArtCourseVideoFragment videoFragment;
    private ArtIntroductionFragment introductionFragment;
    private IArtFindTabAdapter iArtFindTabAdapter;
    private List<Fragment> fragmentList;
    private List<String> titleList;
    private String course_id;
    private Bitmap thumbImage;

    private String course_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_iart_activity);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        course_id = intent.getStringExtra("course_id");
        course_name=intent.getStringExtra("course_name");

        videoFragment = new ArtCourseVideoFragment();
        introductionFragment = new ArtIntroductionFragment();
        fragmentList = new ArrayList<Fragment>();

        fragmentList.add(introductionFragment);
        fragmentList.add(videoFragment);
        titleList = new ArrayList<String>();
        titleList.add("简介");
        titleList.add("课程");

        tabFindFragmentTitle.setTabMode(TabLayout.MODE_FIXED);
        tabFindFragmentTitle.addTab(tabFindFragmentTitle.newTab().setText(titleList.get(0)));
        tabFindFragmentTitle.addTab(tabFindFragmentTitle.newTab().setText(titleList.get(1)));
        iArtFindTabAdapter = new IArtFindTabAdapter(getSupportFragmentManager(), fragmentList, titleList, course_id);

        //viewpager加载adapter
        vpFindFragmentPager.setAdapter(iArtFindTabAdapter);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        tabFindFragmentTitle.setupWithViewPager(vpFindFragmentPager);
        courseVideoplayerStandard.backButton.setVisibility(View.GONE);
//        courseVideoplayerStandard.setUp()
    }

    public void SuccessGetCourseInfo(final CourseBean courseBean) {
        Glide.with(getApplicationContext()).load(courseBean.getImage_url()).thumbnail(0.5f).error(R.mipmap.default_teacher_bg).into(courseBg);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                FileUtil.getFileName(courseBean.getImage_url());
//                try {
//                    String str = URLEncoder.encode(courseBean.getImage_url(), "utf-8");
//                    thumbImage = FileUtil.returnBitmap(courseBean.getImage_url());
//                    handler.sendEmptyMessage(0);
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();
//        Uri uri = Uri.parse((String) (courseBean.getImage_url()));
//        UIUtil.showLog("getImage_url------>",courseBean.getImage_url()+"");
//        UIUtil.showLog("uri------>",uri+"");
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            courseVideoplayerStandard.thumbImageView.setImageBitmap(thumbImage);
        }
    };


    @OnClick(R.id.course_btn_back)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.course_btn_back:
                finish();
                break;
        }
    }
}
