package com.example.kk.arttraining.ui.homePage.activity;

import android.animation.AnimatorSet;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.Media.recodevideo.PlayAudioListenter;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.AdvertisBean;
import com.example.kk.arttraining.bean.AttachmentBean;
import com.example.kk.arttraining.bean.GeneralBean;
import com.example.kk.arttraining.bean.StatusesDetailBean;
import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.bean.parsebean.CommentsBean;
import com.example.kk.arttraining.bean.parsebean.ParseCommentDetail;
import com.example.kk.arttraining.custom.view.EmptyGridView;
import com.example.kk.arttraining.custom.view.HideKeyboardActivity;
import com.example.kk.arttraining.custom.view.JustifyText;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicContentCommentAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicContentTeacherAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicImageAdapter;
import com.example.kk.arttraining.ui.homePage.function.homepage.DynamicContentData;
import com.example.kk.arttraining.ui.homePage.function.homepage.FollowCreate;
import com.example.kk.arttraining.ui.homePage.function.homepage.LikeAnimatorSet;
import com.example.kk.arttraining.ui.homePage.function.homepage.LikeData;
import com.example.kk.arttraining.ui.homePage.function.homepage.MusicAnimator;
import com.example.kk.arttraining.ui.homePage.function.homepage.MusicTouch;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullToRefreshLayout;
import com.example.kk.arttraining.ui.homePage.prot.IDynamic;
import com.example.kk.arttraining.ui.homePage.prot.IFollow;
import com.example.kk.arttraining.ui.homePage.prot.ILike;
import com.example.kk.arttraining.ui.homePage.prot.IMusic;
import com.example.kk.arttraining.ui.me.presenter.MeMainPresenter;
import com.example.kk.arttraining.ui.me.view.PersonalHomePageActivity;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DateUtils;
import com.example.kk.arttraining.utils.FileUtil;
import com.example.kk.arttraining.utils.GlideCircleTransform;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.PlayAudioUtil;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/10/30.
 * QQ邮箱:515849594@qq.com
 */

public class DynamicContent extends HideKeyboardActivity implements IMusic, IDynamic, ILike, IFollow, PullToRefreshLayout.OnRefreshListener, PlayAudioListenter {


    @InjectView(R.id.rl_title)
    RelativeLayout rlTitle;
    @InjectView(R.id.ll_count)
    LinearLayout llCount;
    @InjectView(R.id.ll_button)
    LinearLayout llButton;
    @InjectView(R.id.ll_user_title)
    LinearLayout ll_user_title;
    @InjectView(R.id.view_splitter1)
    View viewSplitter1;
    @InjectView(R.id.view_splitter2)
    View viewSplitter2;
    @InjectView(R.id.view_splitter3)
    View viewSplitter3;
    @InjectView(R.id.view_splitter4)
    View viewSplitter4;
    //    @InjectView(R.id.view_splitter5)
//    View viewSplitter5;
    @InjectView(R.id.view_splitter6)
    View viewSplitter6;
    @InjectView(R.id.view_splitter7)
    View viewSplitter7;

    @InjectView(R.id.refresh_view)
    PullToRefreshLayout refreshView;
    @InjectView(R.id.ll_work_content_mark)
    LinearLayout llWorkContentMark;
    @InjectView(R.id.iv_music_art)
    ImageView ivMusicArt;
    @InjectView(R.id.no_wifi)
    TextView noWifi;
    //    private SuperPlayer player;
    private boolean isLive;

    /**
     * 测试地址
     */
    private String url;
    MusicAnimator musicAnimatorSet;
    AnimatorSet MusicSet;
    AnimationDrawable MusicAnim;
    String att_type;
    AttachmentBean attachmentBean;
    List<ParseCommentDetail> tec_comments_list = new ArrayList<ParseCommentDetail>();
    PlayAudioUtil playAudioUtil;
    int music_position = 0;
    List<CommentsBean> commentList = new ArrayList<CommentsBean>();
    DynamicContentTeacherAdapter teacherContentAdapter;
    DynamicContentCommentAdapter contentAdapter;
    DynamicContentData dynamicContentData;
    int status_id;
    String stus_type;
    int refreshResult = PullToRefreshLayout.FAIL;
    StatusesDetailBean statusesDetailBean;
    @InjectView(R.id.iv_dynamic_content_header)
    ImageView ivDynamicContentHeader;
    @InjectView(R.id.tv_dynamic_content_ordinary_name)
    TextView tvDynamicContentOrdinaryName;
    @InjectView(R.id.tv_dynamic_content_time)
    TextView tvDynamicContentTime;
    @InjectView(R.id.tv_dynamic_content_address)
    TextView tvDynamicContentAddress;
    @InjectView(R.id.tv_dynamic_content_identity)
    TextView tvDynamicContentIdentity;
    @InjectView(R.id.tv_dynamic_content_text)
    JustifyText tvDynamicContentText;
    @InjectView(R.id.gv_dynamic_content_img)
    EmptyGridView gvDynamicContentImg;
    @InjectView(R.id.ll_dynamic_content_music)
    FrameLayout llDynamicContentMusic;
    @InjectView(R.id.lv_dynamic_content_comment)
    MyListView lvDynamicContentComment;
    @InjectView(R.id.et_dynamic_content_comment)
    EditText etDynamicContentComment;
    @InjectView(R.id.ll_dynamic_teacher_comment)
    LinearLayout llDynamicTeacherComment;
    @InjectView(R.id.iv_dynamic_teacher)
    MyListView ivDynamicTeacher;
    @InjectView(R.id.tv_dynamic_content_browse)
    TextView tvDynamicContentBrowse;
    @InjectView(R.id.tv_dynamic_content_like)
    TextView tvDynamicContentLike;
    @InjectView(R.id.tv_dynamic_content_comment)
    TextView tvDynamicContentComment;
    @InjectView(R.id.tv_dynamic_content_teacher_num)
    TextView tvDynamicContentTeacherNum;
    @InjectView(R.id.tv_dynamic_content_comment_num)
    TextView tvDynamicContentCommentNum;
    @InjectView(R.id.iv_dynamic_content_ad)
    ImageView ivDynamicContentAd;
    @InjectView(R.id.dynameic_video)
    FrameLayout dynameic_video;
    @InjectView(R.id.iv_dynamic_content_teacher_no)
    TextView ivDynamicContentTeacherNo;
    @InjectView(R.id.iv_dynamic_content_comment_no)
    TextView ivDynamicContentCommentNo;

    String video_path;
    int comment_num = 0;

    private Bitmap video_pic;
    JCVideoPlayerStandard jcVideoPlayerStandard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_dynamic_content);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this, "详情");
        refreshView.setOnRefreshListener(this);
        getIntentData();
    }

    @OnClick({R.id.bt_dynamic_content_comment, R.id.tv_dynamic_content_focus, R.id.ll_dynamic_content_music, R.id.iv_dynamic_content_header, R.id.tv_dynamic_content_like, R.id.tv_homepage_dynamic_content_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_dynamic_content_comment:
                UIUtil.showLog("iDynamic", "TOKEN" + Config.ACCESS_TOKEN);
                if (Config.ACCESS_TOKEN != null && !Config.ACCESS_TOKEN.equals("")) {
                    if ("".equals(etDynamicContentComment.getText().toString())) {
                        Toast.makeText(DynamicContent.this, "请输入评论内容...", Toast.LENGTH_SHORT).show();
                    } else if (etDynamicContentComment.getText().toString().length() >= 400) {
                        Toast.makeText(DynamicContent.this, "亲，您的评论太长啦...", Toast.LENGTH_SHORT).show();
                    } else {
                        //发布评论，刷新列表
                        dynamicContentData.getCreateComment(status_id, etDynamicContentComment.getText().toString());
                        ivDynamicContentCommentNo.setVisibility(View.GONE);
                    }
                } else {
                    UIUtil.ToastshowShort(this, getResources().getString(R.string.toast_user_login));
                    startActivity(new Intent(this, UserLoginActivity.class));
                }
                break;

            case R.id.tv_dynamic_content_focus:
                FollowCreate followCreate = new FollowCreate(this);
                followCreate.getFocus(statusesDetailBean.getOwner_type(), statusesDetailBean.getOwner());
//                dynamicContentTeacher.getFocus(statusesDetailBean.getOwner_type(), statusesDetailBean.getStus_id());
                break;
            case R.id.ll_dynamic_content_music:
                if (attachmentBean.getStore_path() != null && !attachmentBean.getStore_path().equals("")) {
                    if (music_position == 0) {
                        playAudioUtil = new PlayAudioUtil(this);
                        playAudioUtil.playUrl(attachmentBean.getStore_path());
                        musicAnimatorSet.doMusicArtAnimator(ivMusicArt);
                        music_position = 2;
                    } else if (music_position == 2) {
                        playAudioUtil.pause();
                        MusicSet.end();
                        music_position--;
                    } else if (music_position == 1) {
                        playAudioUtil.play();
                        musicAnimatorSet.doMusicArtAnimator(ivMusicArt);
                        music_position++;
                    } else {
                        playAudioUtil.stop();
                        MusicSet.end();
                        music_position = 0;
                    }
                } else {
                    UIUtil.ToastshowShort(this, "发生错误，无法播放！");
                }
                break;
            case R.id.iv_dynamic_content_header:
                Intent intent = new Intent(this, PersonalHomePageActivity.class);
                intent.putExtra("uid", statusesDetailBean.getOwner());
                startActivity(intent);

                break;
            case R.id.tv_dynamic_content_like:
                UIUtil.showLog("like_state", "222");
                if (Config.ACCESS_TOKEN == null || Config.ACCESS_TOKEN.equals("")) {
                    UIUtil.ToastshowShort(this, getResources().getString(R.string.toast_user_login));
                    startActivity(new Intent(this, UserLoginActivity.class));
                } else {
                    UIUtil.showLog("like_state", "111111");
                    LikeData likeData = new LikeData(this);
                    likeData.getLikeData(this, statusesDetailBean.getIs_like(), status_id, stus_type, tvDynamicContentLike);
                }
                break;
            case R.id.tv_homepage_dynamic_content_share:
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("access_token", Config.ACCESS_TOKEN);
                map.put("uid", Config.UID);
                map.put("type", stus_type);
                map.put("utype", Config.USER_TYPE);
                map.put("favorite_id", statusesDetailBean.getStus_id());

                Callback<GeneralBean> callback = new Callback<GeneralBean>() {
                    @Override
                    public void onResponse(Call<GeneralBean> call, Response<GeneralBean> response) {
                        GeneralBean generalBean = response.body();
                        if (response.body() != null) {
                            if (generalBean.getError_code().equals("0")) {
                                UIUtil.ToastshowLong(DynamicContent.this, "收藏成功！");
                            }else {
                                UIUtil.ToastshowLong(DynamicContent.this, generalBean.getError_msg());
                                if (generalBean.getError_code().equals("20028")){
                                    startActivity(new Intent(DynamicContent.this, UserLoginActivity.class));
                                }
                            }
                        } else {
                            UIUtil.ToastshowLong(DynamicContent.this, generalBean.getError_code());
                        }
                    }
                    @Override
                    public void onFailure(Call<GeneralBean> call, Throwable t) {
                        UIUtil.ToastshowLong(DynamicContent.this, "收藏失败！");
                    }
                };
                Call<GeneralBean> call = HttpRequest.getStatusesApi().statusesFavoritesCreate(map);
                call.enqueue(callback);
                break;
        }
    }


    private void getIntentData() {
        playAudioUtil = new PlayAudioUtil(this);
        musicAnimatorSet = new MusicAnimator(this);

        Intent intent = getIntent();
        status_id = Integer.valueOf(intent.getStringExtra("status_id"));
        stus_type = intent.getStringExtra("stus_type");
        UIUtil.showLog("DateUtils-stus_type", stus_type);
        UIUtil.showLog("DateUtils-status_id", status_id + "");

        dynamicContentData = new DynamicContentData(this, stus_type);
        dynamicContentData.getDynamicContentData(this, status_id);
    }

    public void getData() {
        //读取基本数据

        Glide.with(getApplicationContext()).load(statusesDetailBean.getOwner_head_pic()).transform(new GlideCircleTransform(this)).error(R.mipmap.default_user_header).into(ivDynamicContentHeader);
        tvDynamicContentOrdinaryName.setText(statusesDetailBean.getOwner_name());
        tvDynamicContentAddress.setText(statusesDetailBean.getCity());
        tvDynamicContentIdentity.setText(statusesDetailBean.getIdentity());
        tvDynamicContentTime.setText(DateUtils.getDate(statusesDetailBean.getCreate_time()));

        if (statusesDetailBean.getContent() != null && !statusesDetailBean.getContent().equals("")) {
            tvDynamicContentText.setText(statusesDetailBean.getContent());
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
        tvDynamicContentCommentNum.setText("全部评论(" + statusesDetailBean.getComment_num() + ")");

        //判断附件类型
        List<AttachmentBean> attachmentBeanList = statusesDetailBean.getAtt();
        if (attachmentBeanList != null && attachmentBeanList.size() != 0) {
            for (int i = 0; i < 1; i++) {
                attachmentBean = attachmentBeanList.get(i);
                att_type = attachmentBean.getAtt_type();
            }

            switch (att_type) {
                case "pic":
                    gvDynamicContentImg.setVisibility(View.VISIBLE);
                    DynamicImageAdapter adapter = new DynamicImageAdapter(DynamicContent.this, attachmentBeanList);
                    gvDynamicContentImg.setAdapter(adapter);
                    break;
                case "music":
                    llDynamicContentMusic.setVisibility(View.VISIBLE);

                    break;
                case "video":
                    dynameic_video.setVisibility(View.VISIBLE);
                    video_path = attachmentBean.getStore_path();
                    Config.test_video = video_path;

                    jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.custom_videoplayer_standard);
                    jcVideoPlayerStandard.setUp(video_path
                            , "");

                    File file = new File(video_path);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            video_pic = FileUtil.returnBitmap(attachmentBean.getThumbnail());
                            handler.sendEmptyMessage(0);
                        }
                    }).start();

                    break;
                default:
                    break;
            }
        }


        //判断是测评还是动态
        switch (stus_type) {
            case "status":
                llDynamicTeacherComment.setVisibility(View.GONE);
                break;
            case "work":
                llDynamicTeacherComment.setVisibility(View.VISIBLE);
                llWorkContentMark.setVisibility(View.VISIBLE);
                tvDynamicContentTeacherNum.setText("老师点评(" + statusesDetailBean.getTec_comment_num() + ")");
                tec_comments_list = statusesDetailBean.getTec_comments_list();
                teacherContentAdapter = new DynamicContentTeacherAdapter(this, tec_comments_list);
                ivDynamicTeacher.setAdapter(teacherContentAdapter);
                if (tec_comments_list.size() == 0) {
                    ivDynamicContentTeacherNo.setVisibility(View.VISIBLE);
                } else {
                    ivDynamicContentTeacherNo.setVisibility(View.GONE);
                }
                break;
        }

        //插入广告
        AdvertisBean advertisBean = statusesDetailBean.getAd();
        if (advertisBean != null) {
            Glide.with(getApplicationContext()).load(advertisBean.getAd_pic()).error(R.mipmap.default_advertisement).into(ivDynamicContentAd);
        }

        //全部评论
        commentList = statusesDetailBean.getComments();
        comment_num = commentList.size();
        contentAdapter = new DynamicContentCommentAdapter(this, commentList);
        lvDynamicContentComment.setAdapter(contentAdapter);
        if (commentList.size() == 0) {
            ivDynamicContentCommentNo.setVisibility(View.VISIBLE);
        } else {
            ivDynamicContentCommentNo.setVisibility(View.GONE);
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            jcVideoPlayerStandard.thumbImageView.setImageBitmap(video_pic);
        }
    };

    @Override
    public void getDynamicData(StatusesDetailBean statusesDetailBean) {
        this.statusesDetailBean = statusesDetailBean;
        getData();
    }

    @Override
    public void getWorkData(StatusesDetailBean statusesDetailBean) {
        UIUtil.showLog("DateUtils", statusesDetailBean + "----2");
        this.statusesDetailBean = statusesDetailBean;
        getData();
    }


    @Override
    public void getCreateFollow() {
        UIUtil.ToastshowShort(this, "关注成功！");
    }

    @Override
    public void getOnFollowFailure(String result) {
        UIUtil.ToastshowShort(this, result);
        if (result.equals("登录失效，请重新登录")) {
            startActivity(new Intent(this, UserLoginActivity.class));
        }
    }

    @Override
    public void OnFailure(String error_code) {
    }

    //发布评论
    @Override
    public void getCreateComment(String result) {
        if (result.equals("ok")) {

            tvDynamicContentComment.setText(String.valueOf(Integer.valueOf(tvDynamicContentLike.getText().toString()) + 1));

            MeMainPresenter meMainPresenter = new MeMainPresenter();
            UserLoginBean userLoginBean = meMainPresenter.getLocalUserInfo(this);

            CommentsBean info = new CommentsBean();
            info.setName(userLoginBean.getName());
            info.setTime(DateUtils.getCurrentDate());
            info.setUser_id(Config.UID);
            info.setCity(Config.CITY);
            info.setContent(etDynamicContentComment.getText().toString());
            info.setUser_pic(userLoginBean.getHead_pic());
            commentList.add(0, info);
            contentAdapter.changeCount(commentList.size());
            contentAdapter.notifyDataSetChanged();
            comment_num = comment_num + 1;
            UIUtil.showLog("评论发布后", comment_num + "----");
            etDynamicContentComment.setText("");
        } else {
            UIUtil.ToastshowShort(this, "发布失败");
        }
    }

    @Override
    public void OnFailureCreateComment(String result) {
        UIUtil.ToastshowShort(this, result);
        if (result.equals("登录失效，请重新登录")){
            startActivity(new Intent(this, UserLoginActivity.class));
        }
    }


    public void getLike() {
        LikeAnimatorSet.likeAnimatorSet(this, tvDynamicContentLike, R.mipmap.like_yes);
    }


    private void initData() {
        isLive = getIntent().getBooleanExtra("isLive", false);
        url = getIntent().getStringExtra("url");
    }


    //    /**
//     * 初始化播放器
//     */
//    private void initPlayer() {
//        player = (SuperPlayer) findViewById(R.id.view_super_player);
//        if (isLive) {
//            player.setLive(true);//设置该地址是直播的地址
//        }
//        player.setNetChangeListener(true)//设置监听手机网络的变化
//                .setOnNetChangeListener(this)//实现网络变化的回调
//                .onPrepared(new SuperPlayer.OnPreparedListener() {
//                    @Override
//                    public void onPrepared() {
//                        /**
//                         * 监听视频是否已经准备完成开始播放。（可以在这里处理视频封面的显示跟隐藏）
//                         */
//                    }
//                }).onComplete(new Runnable() {
//            @Override
//            public void run() {
//                /**
//                 * 监听视频是否已经播放完成了。（可以在这里处理视频播放完成进行的操作）
//                 */
//            }
//        }).onInfo(new SuperPlayer.OnInfoListener() {
//            @Override
//            public void onInfo(int what, int extra) {
//                /**
//                 * 监听视频的相关信息。
//                 */
//
//            }
//        }).onError(new SuperPlayer.OnErrorListener() {
//            @Override
//            public void onError(int what, int extra) {
//                /**
//                 * 监听视频播放失败的回调
//                 */
//
//            }
//        }).setTitle(url)//设置视频的titleName
//                .play(video_path);//开始播放视频
//        player.setScaleType(SuperPlayer.SCALETYPE_FITXY);
//        player.setPlayerWH(0, player.getMeasuredHeight());//设置竖屏的时候屏幕的高度，如果不设置会切换后按照16:9的高度重置
//    }
//
//
//    /**
//     * 网络链接监听类
//     */
//    @Override
//    public void onWifi() {
//        Toast.makeText(this, "当前网络环境是WIFI", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onMobile() {
//        Toast.makeText(this, "当前网络环境是手机网络", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onDisConnect() {
//        Toast.makeText(this, "网络链接断开", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onNoAvailable() {
//        Toast.makeText(this, "无网络链接", Toast.LENGTH_SHORT).show();
//    }
//
//
//    /**
//     * 下面的这几个Activity的生命状态很重要
//     */
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (player != null) {
//            player.onPause();
//        }
//        playAudioUtil.stop();
//    }
//
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
        MusicTouch.stopMusicAnimator(playAudioUtil, MusicSet,MusicAnim);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        if (player != null) {
//            player.onConfigurationChanged(newConfig);
//        }
//
//        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { // 竖屏
//            UIUtil.showLog("竖屏", "----->");
//            rlTitle.setVisibility(View.VISIBLE);
//            tvDynamicContentText.setVisibility(View.VISIBLE);
//            llDynamicTeacherComment.setVisibility(View.VISIBLE);
//            lvDynamicContentComment.setVisibility(View.VISIBLE);
//            llCount.setVisibility(View.VISIBLE);
//            llButton.setVisibility(View.VISIBLE);
//            ivDynamicContentCommentNo.setVisibility(View.VISIBLE);
//            tvDynamicContentCommentNum.setVisibility(View.VISIBLE);
//            ivDynamicContentAd.setVisibility(View.VISIBLE);
//            ll_user_title.setVisibility(View.VISIBLE);
//
//            ll_user_title.setVisibility(View.VISIBLE);
//            viewSplitter1.setVisibility(View.VISIBLE);
//            viewSplitter2.setVisibility(View.VISIBLE);
//            viewSplitter3.setVisibility(View.VISIBLE);
//            viewSplitter4.setVisibility(View.VISIBLE);
////            viewSplitter5.setVisibility(View.VISIBLE);
//            viewSplitter6.setVisibility(View.VISIBLE);
//            viewSplitter7.setVisibility(View.VISIBLE);
//        } else {
//            UIUtil.showLog("横屏", "----->");
//            rlTitle.setVisibility(View.GONE);
//            tvDynamicContentText.setVisibility(View.GONE);
//            llDynamicTeacherComment.setVisibility(View.GONE);
//            lvDynamicContentComment.setVisibility(View.GONE);
//            llCount.setVisibility(View.GONE);
//            llButton.setVisibility(View.GONE);
//            ivDynamicContentCommentNo.setVisibility(View.GONE);
//            tvDynamicContentCommentNum.setVisibility(View.GONE);
//            ivDynamicContentAd.setVisibility(View.GONE);
//            ll_user_title.setVisibility(View.GONE);
//            viewSplitter1.setVisibility(View.GONE);
//            viewSplitter2.setVisibility(View.GONE);
//            viewSplitter3.setVisibility(View.GONE);
//            viewSplitter4.setVisibility(View.GONE);
////            viewSplitter5.setVisibility(View.GONE);
//            viewSplitter6.setVisibility(View.GONE);
//            viewSplitter7.setVisibility(View.GONE);
//
//
//        }
//    }

    @Override
    public void onBackPressed() {
//        if (player != null && player.onBackPressed()) {
//            return;
//        }
        super.onBackPressed();
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        getIntentData();
        if (playAudioUtil!=null){
            playAudioUtil.stop();
        }
        if (MusicSet != null) {
            MusicSet.end();
        }

        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        if (playAudioUtil!=null){
            playAudioUtil.stop();
        }
        if (MusicSet != null) {
            MusicSet.end();
        }
        if (commentList.size() != 0) {
            dynamicContentData.loadComment(status_id, contentAdapter.getSelf());
        }
    }

    @Override
    public void loadDynamic(List<CommentsBean> commentsBeanList) {
        commentList.addAll(commentsBeanList);
        comment_num = comment_num + commentsBeanList.size();
        contentAdapter.changeCount(comment_num);
        contentAdapter.notifyDataSetChanged();
        refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void OnLoadDynamicFailure(int result) {
        switch (result) {
            case 0:
                refreshResult = PullToRefreshLayout.EMPTY;
                break;
            case 1:
                refreshResult = PullToRefreshLayout.FAIL;
                break;
            case 2:
                refreshResult = PullToRefreshLayout.FAIL;
                UIUtil.ToastshowShort(this, "网络连接失败！");
                break;
        }
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                refreshView.loadmoreFinish(refreshResult);
            }
        }.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public void NoWifi() {
        noWifi.setVisibility(View.VISIBLE);
        llButton.setVisibility(View.GONE);
        refreshView.setVisibility(View.GONE);
    }

    @Override
    public void playCompletion() {
        music_position = 0;
    }


    @Override
    public void StopArtMusic(AnimatorSet MusicSet) {
        this.MusicSet = MusicSet;
    }

    @Override
    public void StopMusic(AnimationDrawable MusicAnim) {
        this.MusicAnim = MusicAnim;
    }

}
