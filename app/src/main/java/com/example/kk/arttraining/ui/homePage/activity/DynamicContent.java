package com.example.kk.arttraining.ui.homePage.activity;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kk.arttraining.MainActivity;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.modelbean.AdvertisBean;
import com.example.kk.arttraining.bean.modelbean.AttachmentBean;
import com.example.kk.arttraining.bean.modelbean.ReplyBean;
import com.example.kk.arttraining.bean.modelbean.StatusesDetailBean;
import com.example.kk.arttraining.bean.modelbean.UserLoginBean;
import com.example.kk.arttraining.bean.parsebean.CommentsBean;
import com.example.kk.arttraining.bean.parsebean.ParseCommentDetail;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.custom.dialog.MyDialog;
import com.example.kk.arttraining.custom.view.EmptyGridView;
import com.example.kk.arttraining.custom.view.GlideCircleTransform;
import com.example.kk.arttraining.custom.view.JustifyText;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.media.recodevoice.PlayAudioListenter;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicContentCommentAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicContentTeacherAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicImageAdapter;
import com.example.kk.arttraining.ui.homePage.function.homepage.DynamicContentData;
import com.example.kk.arttraining.ui.homePage.function.homepage.FollowCreate;
import com.example.kk.arttraining.ui.homePage.function.homepage.LikeAnimatorSet;
import com.example.kk.arttraining.ui.homePage.function.homepage.LikeData;
import com.example.kk.arttraining.ui.homePage.function.homepage.MusicAnimator;
import com.example.kk.arttraining.ui.homePage.function.homepage.MusicTouch;
import com.example.kk.arttraining.ui.homePage.function.homepage.ShareDialog;
import com.example.kk.arttraining.ui.homePage.function.homepage.TokenVerfy;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullToRefreshLayout;
import com.example.kk.arttraining.ui.homePage.prot.IDynamicContent;
import com.example.kk.arttraining.ui.homePage.prot.IFollow;
import com.example.kk.arttraining.ui.homePage.prot.ILike;
import com.example.kk.arttraining.ui.homePage.prot.IMusic;
import com.example.kk.arttraining.ui.me.presenter.MeMainPresenter;
import com.example.kk.arttraining.ui.me.view.PersonalHomePageActivity;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DateUtils;
import com.example.kk.arttraining.utils.FileUtil;
import com.example.kk.arttraining.utils.PlayAudioUtil;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by kanghuicong on 2016/10/30.
 * QQ邮箱:515849594@qq.com
 */

public class DynamicContent extends Activity implements IMusic, IDynamicContent, ILike, IFollow, PullToRefreshLayout.OnRefreshListener, PlayAudioListenter, DynamicContentTeacherAdapter.TeacherCommentBack, MyDialog.IDelete{

    @InjectView(R.id.refresh_view)
    PullToRefreshLayout refreshView;
    @InjectView(R.id.iv_music_art)
    ImageView ivMusicArt;
    @InjectView(R.id.ll_dynamic_work_content)
    LinearLayout llDynamicWorkContent;
    @InjectView(R.id.ll_dynamic_status)
    LinearLayout llDynamicStatus;
    @InjectView(R.id.tv_dynamic_work_title)
    TextView tvDynamicWorkTitle;
    @InjectView(R.id.tv_dynamic_work_content)
    JustifyText tvDynamicWorkContent;
    @InjectView(R.id.ll_button)
    LinearLayout llButton;
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
    @InjectView(R.id.ll_work_content_mark)
    ImageView llWorkContentMark;
    @InjectView(R.id.ll_comment)
    LinearLayout llComment;
    @InjectView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @InjectView(R.id.tv_title_bar)
    TextView tvTitleBar;
    @InjectView(R.id.view_kb)
    View viewKb;
    @InjectView(R.id.iv_title_image)
    ImageView ivTitleImage;
    @InjectView(R.id.ll_facechoose)
    RelativeLayout llFacechoose;
    @InjectView(R.id.btn_face)
    ImageButton btnFace;
    @InjectView(R.id.no_wifi)
    TextView noWifi;
    @InjectView(R.id.fl_dynamic_all)
    FrameLayout flDynamicAll;

    MusicAnimator musicAnimatorSet;
    AnimatorSet MusicSet = null;
    AnimationDrawable MusicAnim = null;
    AnimationDrawable teacherMusicAnim = null;

    DynamicContentData dynamicContentData;

    List<ParseCommentDetail> tec_comments_list = new ArrayList<ParseCommentDetail>();
    List<CommentsBean> commentList = new ArrayList<CommentsBean>();
    AttachmentBean attachmentBean;
    CommentsBean replyComment = new CommentsBean();
    StatusesDetailBean statusesDetailBean;

    DynamicContentTeacherAdapter teacherContentAdapter;
    DynamicContentCommentAdapter contentAdapter;

    boolean isReply = false;
    int comment_num = 0;
    int refreshResult = PullToRefreshLayout.FAIL;
    int status_id;
    String CommentType = "comment";
    String video_path;
    String voice_path = "voice_path";
    String att_type;
    String type;
    String stus_type;
    static String REPLY = "reply";
    static String COMMENT = "comment";

    Bitmap video_pic;
    JCVideoPlayerStandard jcVideoPlayerStandard;
    LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_dynamic_content);
        ButterKnife.inject(this);

        loadingDialog = LoadingDialog.getInstance(this);
        loadingDialog.show();

        tvTitleBar.setText("详情");
        refreshView.setOnRefreshListener(this);
        getIntentData();
    }

    @OnClick({R.id.iv_title_back, R.id.bt_dynamic_content_comment, R.id.tv_dynamic_content_focus, R.id.ll_dynamic_content_music, R.id.iv_dynamic_content_header, R.id.tv_dynamic_content_like, R.id.tv_homepage_dynamic_content_share, R.id.iv_title_image})
    public void onClick(View view) {
        switch (view.getId()) {
            //评论
            case R.id.bt_dynamic_content_comment:
                if (Config.ACCESS_TOKEN != null && !Config.ACCESS_TOKEN.equals("")) {
                    if ("".equals(etDynamicContentComment.getText().toString())) {
                        Toast.makeText(DynamicContent.this, "请输入评论内容...", Toast.LENGTH_SHORT).show();
                    } else if (etDynamicContentComment.getText().toString().length() >= 200) {
                        Toast.makeText(DynamicContent.this, "亲，您的评论太长啦...", Toast.LENGTH_SHORT).show();
                    } else {
                        //发布评论，刷新列表
                        if (CommentType.equals(COMMENT)) {
                            dynamicContentData.getCreateComment(DynamicContent.this, status_id, etDynamicContentComment.getText().toString());
                        } else {
                            dynamicContentData.getCreateReply(DynamicContent.this, status_id, etDynamicContentComment.getText().toString(), replyComment.getUser_id(), replyComment.getUser_type());
                        }
                        ivDynamicContentCommentNo.setVisibility(View.GONE);
                        llFacechoose.setVisibility(View.GONE);
                    }
                } else {
                    TokenVerfy.Login(this, 2);
                }
                break;
            //关注
            case R.id.tv_dynamic_content_focus:
                FollowCreate followCreate = new FollowCreate(this);
                followCreate.getFocus(statusesDetailBean.getOwner_type(), statusesDetailBean.getOwner());
                break;
            //播放音频
            case R.id.ll_dynamic_content_music:
                if (attachmentBean.getStore_path() != null && !attachmentBean.getStore_path().equals("")) {
                    if (!voice_path.equals(attachmentBean.getStore_path())) {
                        if (Config.playAudioUtil == null) {
                            musicAnimatorSet.doMusicArtAnimator(ivMusicArt);
                            Config.playAudioUtil = new PlayAudioUtil(new PlayAudioListenter() {
                                @Override
                                public void playCompletion() {
                                    MusicTouch.stopMusicAnimatorSet(MusicSet);
                                }
                            });
                            Config.playAudioUtil.playUrl(attachmentBean.getStore_path());
                            voice_path = attachmentBean.getStore_path();
                        } else {
                            musicAnimatorSet.doMusicArtAnimator(ivMusicArt);
                            Config.playAudioUtil.playUrl(attachmentBean.getStore_path());
                            voice_path = attachmentBean.getStore_path();
                        }
                    } else {
                        MusicTouch.stopMusicAnimatorSet(MusicSet);
                        voice_path = "voice_path";
                    }
                } else {
                    UIUtil.ToastshowShort(this, "发生错误，无法播放！");
                }
                break;
            //点击头像
            case R.id.iv_dynamic_content_header:
                try {
                    Intent intent = new Intent(this, PersonalHomePageActivity.class);
                    intent.putExtra("uid", statusesDetailBean.getOwner());
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //点赞
            case R.id.tv_dynamic_content_like:

                if (Config.ACCESS_TOKEN == null || Config.ACCESS_TOKEN.equals("")) {
                    TokenVerfy.Login(this, 2);
                } else {
                    LikeData likeData = new LikeData(this);
                    likeData.getLikeData(DynamicContent.this, statusesDetailBean.getIs_like(), status_id, stus_type, tvDynamicContentLike);
                }
                break;
            //返回按钮
            case R.id.iv_title_back:
                if (type.equals("jpush_bbs") || type.equals("jpush_work")) {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    finish();
                }
                break;
            //分享系列
            case R.id.tv_homepage_dynamic_content_share:
                ShareDialog.getShareDialog(DynamicContent.this, stus_type, statusesDetailBean.getStus_id());
                break;
            //删帖
            case R.id.iv_title_image:
                MyDialog.getDeleteDialog(this,this);
                break;
        }
    }


    private void getIntentData() {
        musicAnimatorSet = new MusicAnimator(this);

        Intent intent = getIntent();
        UIUtil.showLog("status_id222---->", intent.getStringExtra("status_id") + "");
        UIUtil.showLog("stus_type---->", intent.getStringExtra("stus_type") + "");
        UIUtil.showLog("type---->", intent.getStringExtra("type") + "");
        status_id = Integer.valueOf(intent.getStringExtra("status_id"));
        stus_type = intent.getStringExtra("stus_type");
        type = intent.getStringExtra("type");
        UIUtil.showLog("delete", status_id + "----" + Config.UID);

        dynamicContentData = new DynamicContentData(this, stus_type);
        dynamicContentData.getDynamicContentData(status_id, type);
    }

    public void getData() {
        //读取基本数据
//        if (statusesDetailBean.getOwner() == Config.UID) {
//            ivTitleImage.setImageResource(R.mipmap.delete);
//        }else {
        ivTitleImage.setVisibility(View.GONE);
//        }

        Glide.with(this).load(statusesDetailBean.getOwner_head_pic()).transform(new GlideCircleTransform(this)).error(R.mipmap.default_user_header).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivDynamicContentHeader);
        tvDynamicContentOrdinaryName.setText(statusesDetailBean.getOwner_name());
        tvDynamicContentAddress.setText(statusesDetailBean.getCity());
        tvDynamicContentIdentity.setText(statusesDetailBean.getIdentity());
        tvDynamicContentTime.setText(DateUtils.getDate(statusesDetailBean.getCreate_time()));

        tvDynamicContentBrowse.setText(DateUtils.getBrowseNumber(statusesDetailBean.getBrowse_num()));
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
                    switch (attachmentBeanList.size()) {
                        case 1:
                            gvDynamicContentImg.setNumColumns(1);
                            gvDynamicContentImg.setSelector(new ColorDrawable(Color.TRANSPARENT));
                            break;
                        case 2:
                            gvDynamicContentImg.setNumColumns(2);
                            break;
                        case 3:
                            gvDynamicContentImg.setNumColumns(3);
                            break;
                    }

                    gvDynamicContentImg.setVisibility(View.VISIBLE);
                    DynamicImageAdapter adapter = new DynamicImageAdapter(DynamicContent.this, attachmentBeanList);
                    gvDynamicContentImg.setAdapter(adapter);
                    break;
                case "music":

                    Glide.with(this).load(statusesDetailBean.getOwner_head_pic()).transform(new GlideCircleTransform(this)).error(R.mipmap.music_art).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivMusicArt);
                    llDynamicContentMusic.setVisibility(View.VISIBLE);
                    break;
                case "video":
                    viewKb.setVisibility(View.GONE);
                    dynameic_video.setVisibility(View.VISIBLE);
                    video_path = attachmentBean.getStore_path();
                    Config.test_video = video_path;

                    jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.custom_videoplayer_standard);
                    jcVideoPlayerStandard.setUp(video_path, "");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                video_pic = FileUtil.returnBitmap(attachmentBean.getThumbnail());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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
                llWorkContentMark.setBackgroundResource(R.mipmap.dynamic_status);
                if (statusesDetailBean.getContent() != null && !statusesDetailBean.getContent().equals("")) {
                    tvDynamicContentText.setText(statusesDetailBean.getContent());
                } else {
                    tvDynamicContentText.setVisibility(View.GONE);
                }
                break;
            case "work":
                llDynamicTeacherComment.setVisibility(View.VISIBLE);
                llWorkContentMark.setBackgroundResource(R.mipmap.dynamic_work);
                llDynamicWorkContent.setVisibility(View.VISIBLE);
                llDynamicStatus.setVisibility(View.GONE);

                tvDynamicWorkTitle.setText("作品名称：" + statusesDetailBean.getTitle());
                if (statusesDetailBean.getContent() != null && !statusesDetailBean.getContent().equals("")) {
                    tvDynamicWorkContent.setText("作品描述：" + statusesDetailBean.getContent());
                } else {
                    tvDynamicWorkContent.setVisibility(View.GONE);
                }

                tvDynamicContentTeacherNum.setText("老师点评(" + statusesDetailBean.getTec_comment_num() + ")");
                tec_comments_list = statusesDetailBean.getTec_comments_list();
                teacherContentAdapter = new DynamicContentTeacherAdapter(this, tec_comments_list, this);
                ivDynamicTeacher.setAdapter(teacherContentAdapter);
                if (tec_comments_list.size() == 0) {
                    ivDynamicContentTeacherNo.setVisibility(View.VISIBLE);
                } else {
                    ivDynamicContentTeacherNo.setVisibility(View.GONE);
                }
                break;
        }

        if (type.equals("valuationContent") || type.equals("jpush_work")) {
            llComment.setVisibility(View.GONE);
            llButton.setVisibility(View.GONE);
        } else {
            //插入广告
            AdvertisBean advertisBean = statusesDetailBean.getAd();
            if (advertisBean != null) {
                Glide.with(this).load(advertisBean.getAd_pic()).error(R.mipmap.default_advertisement).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivDynamicContentAd);
            }

            //全部评论
            commentList = statusesDetailBean.getComments();
            comment_num = commentList.size();
            contentAdapter = new DynamicContentCommentAdapter(this, commentList);
            lvDynamicContentComment.setAdapter(contentAdapter);
            lvDynamicContentComment.setOnItemClickListener(new ContentCommentItemClick());
            if (commentList.size() == 0) {
                ivDynamicContentCommentNo.setVisibility(View.VISIBLE);
            } else {
                ivDynamicContentCommentNo.setVisibility(View.GONE);
            }
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
        loadingDialog.dismiss();
    }

    @Override
    public void getWorkData(StatusesDetailBean statusesDetailBean) {
        UIUtil.showLog("DateUtils", statusesDetailBean + "----2");
        this.statusesDetailBean = statusesDetailBean;
        getData();
        loadingDialog.dismiss();
    }

    @Override
    public void OnFailure(String error_code) {
        UIUtil.ToastshowShort(this, error_code);
        loadingDialog.dismiss();
    }

    @Override
    public void NoWifi() {
        noWifi.setVisibility(View.VISIBLE);
        flDynamicAll.setVisibility(View.GONE);
        loadingDialog.dismiss();
    }

    //关注成功
    @Override
    public void getCreateFollow() {
        UIUtil.ToastshowShort(this, "关注成功！");
    }

    //关注失败
    @Override
    public void OnFollowFailure(String code, String msg) {
        UIUtil.ToastshowShort(this, msg);
        if (code.equals("20028")) {
            if (code.equals("20028")) {
                startActivity(new Intent(this, UserLoginActivity.class));
            }
        }
    }

    //发布评论
    @Override
    public void getCreateComment(String result) {
        if (result.equals("ok")) {

            String edContent = etDynamicContentComment.getText().toString();
            etDynamicContentComment.setText("");

            tvDynamicContentComment.setText(String.valueOf(Integer.valueOf(tvDynamicContentComment.getText().toString()) + 1));
            tvDynamicContentCommentNum.setText("全部评论(" + tvDynamicContentComment.getText().toString() + ")");

            MeMainPresenter meMainPresenter = new MeMainPresenter();
            UserLoginBean userLoginBean = meMainPresenter.getLocalUserInfo(getApplicationContext());

            CommentsBean info = new CommentsBean();
            info.setName(userLoginBean.getName());
            info.setTime(DateUtils.getCurrentDate());
            info.setUser_id(Config.UID);
            info.setCity(Config.CITY);
            info.setContent(edContent);
            info.setUser_pic(userLoginBean.getHead_pic());

            if (CommentType.equals(REPLY)) {
                ReplyBean replyBean = new ReplyBean();
                replyBean.setName(replyComment.getName());
                UIUtil.showLog("replyComment1", replyComment.getName());
                info.setReply(replyBean);
                info.setComm_type(REPLY);
            } else {
                info.setComm_type(COMMENT);
            }

            commentList.add(0, info);
            contentAdapter.changeCount(commentList.size());
            contentAdapter.notifyDataSetChanged();
            comment_num = comment_num + 1;

        } else {
            UIUtil.ToastshowShort(this, "发布失败");
        }
    }

    //点赞
    public void getLike() {
        LikeAnimatorSet.likeAnimatorSet(this, tvDynamicContentLike, R.mipmap.like_yes);
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
        MusicTouch.stopMusicAll(MusicSet, MusicAnim);
        MusicTouch.stopMusicAll(MusicSet, teacherMusicAnim);
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

    //下拉刷新
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        MusicTouch.stopMusicAnimator(MusicSet, MusicAnim);
        MusicTouch.stopMusicAnimator(MusicSet, teacherMusicAnim);
        getIntentData();

        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    //上拉加载
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

//        MusicTouch.stopMusicAnimator(MusicSet, MusicAnim);
//        MusicTouch.stopMusicAnimator(MusicSet, teacherMusicAnim);
        if (commentList.size() != 0) {
            dynamicContentData.loadComment(status_id, contentAdapter.getSelf());
        } else {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    refreshView.loadmoreFinish(PullToRefreshLayout.EMPTY);
                }
            }.sendEmptyMessageDelayed(0, 1000);
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
    public void playCompletion() {}

    @Override
    public void StopArtMusic(AnimatorSet MusicSet) {
        this.MusicSet = MusicSet;
    }

    @Override
    public void StopMusic(AnimationDrawable MusicAnim) {
        this.MusicAnim = MusicAnim;
    }


    @Override
    public void getTeacherCommentFlag() {
        MusicTouch.stopMusicAnimator(MusicSet, MusicAnim);
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void getTeacherCommentBack(AnimationDrawable MusicAnim) {
        teacherMusicAnim = MusicAnim;
    }

    //删除帖子
    @Override
    public void getDelete() {
        finish();
    }

    //举报


    private class ContentCommentItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CommentsBean commentsBean = contentAdapter.getItem(position);
            isReply = true;
            replyComment.setUser_type(commentsBean.getUser_type());
            replyComment.setUser_id(commentsBean.getUser_id());
            replyComment.setName(commentsBean.getName());
            etDynamicContentComment.setHint("@" + commentsBean.getName());
            if (!("").equals(etDynamicContentComment.getText().toString())) {
                etDynamicContentComment.setText("");
            }
        }
    }


    //收键盘的方法与收表情的方法冲突、
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                if (v.getWindowToken() != null) {
                    InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(v.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    if (llFacechoose.getVisibility() == View.VISIBLE) {
                        if (!inRangeOfView(btnFace, ev)) {
                            if (!inRangeOfView(llFacechoose, ev)) {
                                llFacechoose.setVisibility(View.GONE);
                            }
                        }
                    }

                    if (etDynamicContentComment.getText().toString() != null && !("").equals(etDynamicContentComment.getText().toString())) {
                        if (isReply) {
                            CommentType = REPLY;
                        } else {
                            isReply = false;
                            CommentType = COMMENT;
                        }
                    } else {
                        if (!inRangeOfView(btnFace, ev)) {
                            if (!inRangeOfView(llFacechoose, ev)) {
                                isReply = false;
                                CommentType = COMMENT;
                                etDynamicContentComment.setHint("评论");
                            }
                        }
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    private boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (ev.getX() < x || ev.getX() > (x + view.getWidth()) || ev.getY() < y || ev.getY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            if (llFacechoose.getVisibility() == View.VISIBLE) {
                llFacechoose.setVisibility(View.GONE);
            } else {
                finish();
            }
        }
        return true;
    }

}

