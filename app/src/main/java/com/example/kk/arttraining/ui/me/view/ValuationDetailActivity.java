package com.example.kk.arttraining.ui.me.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.Media.recodevideo.PlayAudioListenter;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.AttachmentBean;
import com.example.kk.arttraining.bean.StatusesDetailBean;
import com.example.kk.arttraining.bean.parsebean.ParseCommentDetail;
import com.example.kk.arttraining.custom.view.AutoSwipeRefreshLayout;
import com.example.kk.arttraining.custom.view.EmptyGridView;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.custom.view.VipTextView;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.homePage.activity.DynamicContent;
import com.example.kk.arttraining.ui.homePage.activity.ThemeTeacherContent;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicContentTeacherAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicContentTeacherCommentAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicImageAdapter;
import com.example.kk.arttraining.ui.homePage.function.homepage.LikeAnimatorSet;
import com.example.kk.arttraining.ui.homePage.function.teacher.TeacherContentData;
import com.example.kk.arttraining.ui.me.presenter.ValuationDetailPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DateUtils;
import com.example.kk.arttraining.utils.FileUtil;
import com.example.kk.arttraining.utils.GlideCircleTransform;
import com.example.kk.arttraining.utils.PlayAudioUtil;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 作者：wschenyongyin on 2016/11/23 09:53
 * 说明:测评详情
 */
public class ValuationDetailActivity extends BaseActivity implements IValuationDetailActivity, SwipeRefreshLayout.OnRefreshListener, PlayAudioListenter {

    @InjectView(R.id.iv_title_image)
    ImageView ivTitleImage;
    @InjectView(R.id.tv_title_subtitle)
    TextView tvTitleSubtitle;
    @InjectView(R.id.rl_title)
    RelativeLayout rlTitle;
    @InjectView(R.id.iv_dynamic_content_header)
    ImageView ivDynamicContentHeader;
    @InjectView(R.id.tv_dynamic_content_vip_name)
    VipTextView tvDynamicContentVipName;
    @InjectView(R.id.tv_dynamic_content_ordinary_name)
    TextView tvDynamicContentOrdinaryName;
    @InjectView(R.id.tv_dynamic_content_time)
    TextView tvDynamicContentTime;
    @InjectView(R.id.tv_dynamic_content_address)
    TextView tvDynamicContentAddress;
    @InjectView(R.id.tv_dynamic_content_identity)
    TextView tvDynamicContentIdentity;
    @InjectView(R.id.tv_dynamic_content_focus)
    TextView tvDynamicContentFocus;
    @InjectView(R.id.ll_user_title)
    LinearLayout llUserTitle;
    @InjectView(R.id.iv_music)
    ImageView ivMusic;
    @InjectView(R.id.tv_dynamic_music_time)
    TextView tvDynamicMusicTime;
    @InjectView(R.id.ll_dynamic_content_music)
    LinearLayout llDynamicContentMusic;
    @InjectView(R.id.custom_videoplayer_standard)
    JCVideoPlayerStandard customVideoplayerStandard;
    @InjectView(R.id.dynameic_video)
    FrameLayout dynameicVideo;
    @InjectView(R.id.tv_dynamic_content_text)
    TextView tvDynamicContentText;
    @InjectView(R.id.tv_dynamic_content_browse)
    TextView tvDynamicContentBrowse;
    @InjectView(R.id.tv_dynamic_content_like)
    TextView tvDynamicContentLike;
    @InjectView(R.id.valuation_title)
    TextView valuation_title;

    @InjectView(R.id.tv_dynamic_content_comment)
    TextView tvDynamicContentComment;
    @InjectView(R.id.tv_homepage_dynamic_content_share)
    TextView tvHomepageDynamicContentShare;
    @InjectView(R.id.ll_count)
    LinearLayout llCount;
    @InjectView(R.id.tv_dynamic_content_teacher_num)
    TextView tvDynamicContentTeacherNum;
    @InjectView(R.id.iv_dynamic_content_teacher_no)
    TextView ivDynamicContentTeacherNo;
    @InjectView(R.id.iv_dynamic_teacher)
    MyListView ivDynamicTeacher;
    @InjectView(R.id.gv_dynamic_content_img)
    EmptyGridView gv_dynamic_content_img;

    @InjectView(R.id.ll_dynamic_teacher_comment)
    LinearLayout llDynamicTeacherComment;

    AutoSwipeRefreshLayout swipeRefreshLayout;
    //作品id
    private int work_id;
    ValuationDetailPresenter valuationDetailPresenter;
    private Bitmap thumbnail_pic;
    JCVideoPlayerStandard jcVideoPlayerStandard;
    //视频附件地址
    private String void_url;
    //音频附件地址
    private String audio_url;
    //判断音频播放的标记
    private int music_position = 0;
    //播放音频
    private PlayAudioUtil playAudioUtil;
    //音乐动画
    private AnimationDrawable musicAnimation;
    //老师评论adapter
    private DynamicContentTeacherAdapter teacherContentAdapter;

    private StatusesDetailBean statusesDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_valuation_detail_activity);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        work_id = intent.getIntExtra("work_id", 1);
        UIUtil.showLog("work_id", work_id + "");
        TitleBack.TitleBackActivity(this, "测评详情");
        valuationDetailPresenter = new ValuationDetailPresenter(this);
        swipeRefreshLayout = new AutoSwipeRefreshLayout(getApplicationContext());
        swipeRefreshLayout = (AutoSwipeRefreshLayout) findViewById(R.id.detail_swipe);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#87CEFA"));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.autoRefresh();
        playAudioUtil = new PlayAudioUtil(this);
    }

    @Override
    public void onRefresh() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("status_id", work_id);
        map.put("utype", Config.USER_TYPE);
        valuationDetailPresenter.getData(map);
        ivMusic.setBackgroundResource(R.drawable.music_anim);
        musicAnimation = (AnimationDrawable) ivMusic.getBackground();

    }

    @OnClick(R.id.ll_dynamic_content_music)
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_dynamic_content_music:
                if (music_position == 0) {
                    playAudioUtil.playUrl(audio_url);
                    musicAnimation.start();
                    music_position = 2;
                } else if (music_position == 2) {
                    playAudioUtil.pause();
                    musicAnimation.stop();
                    music_position--;
                } else if (music_position == 1) {
                    playAudioUtil.play();
                    musicAnimation.start();
                    music_position++;

                } else {
                    playAudioUtil.stop(0);
                    musicAnimation.stop();
                    music_position = 0;
                }
                break;

            case R.id.iv_dynamic_content_header:
//                Intent intentTec = new Intent(this, ThemeTeacherContent.class);
//                intentTec.putExtra("type", "tec");
//                intentTec.putExtra("tec_id", statusesDetailBean.getUid() + "");
//                startActivity(intentTec);
        }
    }


    //设置作品基本信息
    @Override
    public void setWorkInfo(StatusesDetailBean statusesDetailBean) {
        swipeRefreshLayout.setRefreshing(false);
        this.statusesDetailBean = statusesDetailBean;
        Glide.with(getApplicationContext()).load(statusesDetailBean.getOwner_head_pic()).transform(new GlideCircleTransform(this)).error(R.mipmap.default_user_header).into(ivDynamicContentHeader);
        tvDynamicContentOrdinaryName.setText(statusesDetailBean.getOwner_name());
        tvDynamicContentAddress.setText(statusesDetailBean.getCity());
        tvDynamicContentIdentity.setText(statusesDetailBean.getIdentity());
        tvDynamicContentTime.setText(DateUtils.getDate(statusesDetailBean.getCreate_time()));
        valuation_title.setText("作品:\u3000" + statusesDetailBean.getTitle());
        if (statusesDetailBean.getContent() != null && !statusesDetailBean.getContent().equals("")) {
            tvDynamicContentText.setText("描述:　" + statusesDetailBean.getContent());
        } else {
            tvDynamicContentText.setVisibility(View.GONE);
        }
        tvDynamicContentBrowse.setText(statusesDetailBean.getBrowse_num() + "");
        tvDynamicContentLike.setText(statusesDetailBean.getLike_num() + "");
        UIUtil.showLog("tvDynamicContentLike", statusesDetailBean.getIs_like());
        if (statusesDetailBean.getIs_like().equals("yes")) {
            LikeAnimatorSet.setLikeImage(this, tvDynamicContentLike, R.mipmap.like_yes);
        } else {
            LikeAnimatorSet.setLikeImage(this, tvDynamicContentLike, R.mipmap.like_no);
        }
        tvDynamicContentComment.setText(statusesDetailBean.getComment_num() + "");
//        tvDynamicContentCommentNum.setText("全部评论(" + statusesDetailBean.getComment_num() + ")");

    }

    //设置视频附件
    @Override
    public void setVideoInfo(final AttachmentBean attInfo) {
        dynameicVideo.setVisibility(View.VISIBLE);
        void_url = attInfo.getStore_path();
        jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.custom_videoplayer_standard);
        jcVideoPlayerStandard.setUp(void_url, "");

        new Thread(new Runnable() {
            @Override
            public void run() {
                thumbnail_pic = FileUtil.returnBitmap(attInfo.getThumbnail());
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    //设置缩略图
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            jcVideoPlayerStandard.thumbImageView.setImageBitmap(thumbnail_pic);
        }
    };

    //设置音频附件
    @Override
    public void setAudioInfo(AttachmentBean attInfo) {
        audio_url = attInfo.getStore_path();
        llDynamicContentMusic.setVisibility(View.VISIBLE);
        if (attInfo.getDuration() != null) {
            String[] strarray = attInfo.getDuration().split("\\.");
            tvDynamicMusicTime.setText(strarray[0] + "s");
        }
    }

    @Override
    public void setPicInfo(List<AttachmentBean> attInfo) {
        gv_dynamic_content_img.setVisibility(View.VISIBLE);
        DynamicImageAdapter adapter = new DynamicImageAdapter(this, attInfo);
        gv_dynamic_content_img.setAdapter(adapter);
        //gridView空白部分点击事件
        gv_dynamic_content_img.setOnTouchInvalidPositionListener(new EmptyGridView.OnTouchInvalidPositionListener() {
            @Override
            public boolean onTouchInvalidPosition(int motionEvent) {
                return false;
            }
        });
    }

    @Override
    public void setTecData(List<ParseCommentDetail> tecDataList) {
        llDynamicTeacherComment.setVisibility(View.VISIBLE);
        tvDynamicContentTeacherNum.setText("名师点评(" + statusesDetailBean.getTec_comment_num() + ")");
//        tec_comments_list = statusesDetailBean.getTec_comments_list();
        teacherContentAdapter = new DynamicContentTeacherAdapter(this, tecDataList, new DynamicContentTeacherAdapter.TeacherCommentBack() {
            @Override
            public void getTeacherCommentFlag() {

            }

            @Override
            public void getTeacherCommentBack(PlayAudioUtil playAudioUtil, AnimationDrawable MusicAnim) {

            }
        });
        ivDynamicTeacher.setAdapter(teacherContentAdapter);
        if (tecDataList.size() == 0) {
            ivDynamicContentTeacherNo.setVisibility(View.VISIBLE);
        } else {
            ivDynamicContentTeacherNo.setVisibility(View.GONE);
        }
    }

    @Override
    public void OnFailure(String error_code, String error_msg) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        playAudioUtil.stop(0);
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    //音频播放完成
    @Override
    public void playCompletion() {

    }
}
