package com.example.kk.arttraining.ui.course.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.course.adapter.IArtFindTabAdapter;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 作者：wschenyongyin on 2016/12/23 15:43
 * 说明:I艺术课程详情页面
 */
public class ArtCourseActivity extends FragmentActivity {

    @InjectView(R.id.tab_FindFragment_title)
    TabLayout tabFindFragmentTitle;
    @InjectView(R.id.vp_FindFragment_pager)
    ViewPager vpFindFragmentPager;
    @InjectView(R.id.course_videoplayer_standard)
    JCVideoPlayerStandard courseVideoplayerStandard;
    @InjectView(R.id.course_video)
    FrameLayout courseVideo;

    private ArtCourseVideoFragment videoFragment;
    private ArtIntroductionFragment introductionFragment;
    private IArtFindTabAdapter iArtFindTabAdapter;

    private List<Fragment> fragmentList;
    private List<String> titleList;

    private int course_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_iart_activity);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        Intent intent=getIntent();
        course_id=intent.getIntExtra("course_id",0);
//        UIUtil.s

        videoFragment=new ArtCourseVideoFragment();
        introductionFragment=new ArtIntroductionFragment();
        fragmentList=new ArrayList<Fragment>();

        fragmentList.add(introductionFragment);
        fragmentList.add(videoFragment);
        titleList=new ArrayList<String>();
        titleList.add("简介");
        titleList.add("课程");

        tabFindFragmentTitle.setTabMode(TabLayout.MODE_FIXED);
        tabFindFragmentTitle.addTab(tabFindFragmentTitle.newTab().setText(titleList.get(0)));
        tabFindFragmentTitle.addTab(tabFindFragmentTitle.newTab().setText(titleList.get(1)));
        iArtFindTabAdapter = new IArtFindTabAdapter(getSupportFragmentManager(),fragmentList,titleList,course_id);

        //viewpager加载adapter
        vpFindFragmentPager.setAdapter(iArtFindTabAdapter);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        tabFindFragmentTitle.setupWithViewPager(vpFindFragmentPager);

        courseVideoplayerStandard.backButton.setVisibility(View.GONE);
//        courseVideoplayerStandard.setUp()
        courseVideoplayerStandard.coverImageView.setImageResource(R.mipmap.me_bg);
    }




}
