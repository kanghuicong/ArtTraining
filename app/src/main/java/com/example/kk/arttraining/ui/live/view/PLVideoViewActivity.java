package com.example.kk.arttraining.ui.live.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.ExitDialog;
import com.example.kk.arttraining.custom.dialog.MyDialog;
import com.example.kk.arttraining.custom.view.GlideCircleTransform;
import com.example.kk.arttraining.custom.view.MyGridView;
import com.example.kk.arttraining.pay.view.RechargeICloudActivity;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxBus;
import com.example.kk.arttraining.ui.homePage.activity.ThemeTeacherContent;
import com.example.kk.arttraining.ui.live.LiveUtil;
import com.example.kk.arttraining.ui.live.MediaController;
import com.example.kk.arttraining.ui.live.adapter.CommentDataAdapter;
import com.example.kk.arttraining.ui.live.adapter.GiftGridViewAdpter;
import com.example.kk.arttraining.ui.live.adapter.MyViewPagerAdapter;
import com.example.kk.arttraining.ui.live.bean.GiftBean;
import com.example.kk.arttraining.ui.live.bean.LiveBeingBean;
import com.example.kk.arttraining.ui.live.bean.LiveCommentBean;
import com.example.kk.arttraining.ui.live.gitanimation.GiftFrameLayout;
import com.example.kk.arttraining.ui.live.presenter.LiveBuyData;
import com.example.kk.arttraining.ui.live.presenter.LivePayData;
import com.example.kk.arttraining.ui.live.presenter.PLVideoViewPresenter;
import com.example.kk.arttraining.ui.valuation.bean.CommitOrderBean;
import com.example.kk.arttraining.utils.AutomaticKeyboard;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.ScreenUtils;
import com.example.kk.arttraining.utils.StringUtils;
import com.example.kk.arttraining.utils.UIUtil;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

/**
 * 作者：wschenyongyin on 2017/1/21 15:24
 * 说明:直播页面
 */
public class PLVideoViewActivity extends Activity implements IPLVideoView, View.OnClickListener, MyDialog.IPay,ILiveBuy{

    private static final String TAG = PLVideoViewActivity.class.getSimpleName();

    private static final int MESSAGE_ID_RECONNECTING = 0x01;
    @InjectView(R.id.lv_comment_data)
    ListView lvCommentData;

    @InjectView(R.id.live_menu)
    LinearLayout liveMenu;
    @InjectView(R.id.ll_comment)
    LinearLayout llComment;
    @InjectView(R.id.et_comment)
    EditText etComment;
    @InjectView(R.id.btn_send_comment)
    Button btnSendComment;
    @InjectView(R.id.live_fl)
    FrameLayout rootView;
    @InjectView(R.id.iv_menu_comment)
    ImageView ivMenuComment;
    @InjectView(R.id.iv_menu_course)
    ImageView ivMenuCourse;
    @InjectView(R.id.iv_menu_member)
    ImageView ivMenuMember;
    @InjectView(R.id.iv_menu_share)
    ImageView ivMenuShare;
    @InjectView(R.id.iv_menu_gift)
    ImageView ivMenuGift;
    @InjectView(R.id.iv_menu_clean)
    ImageView ivMenuClean;
    @InjectView(R.id.iv_head_pic)
    ImageView ivHeadPic;
    @InjectView(R.id.tv_tec_name)
    TextView tvTecName;
    @InjectView(R.id.tv_room_num)
    TextView tvRoomNum;
    @InjectView(R.id.tv_like_num)
    TextView tvLikeNum;
    @InjectView(R.id.iv_like)
    ImageView ivLike;
    @InjectView(R.id.iv_exit_live)
    ImageView ivExitLive;
    @InjectView(R.id.rv_live_userinfo)
    RelativeLayout rvLiveUserinfo;
    @InjectView(R.id.live_gift_two)
    GiftFrameLayout liveGiftTwo;
    @InjectView(R.id.live_gift_one)
    GiftFrameLayout liveGiftOne;
    @InjectView(R.id.rl_gift_layout)
    RelativeLayout rlGiftLayout;
    @InjectView(R.id.CoverView)
    ImageView CoverView;
    @InjectView(R.id.LoadingView)
    LinearLayout LoadingView;
    @InjectView(R.id.btn_send_gift)
    Button btnSendGift;
    @InjectView(R.id.tv_cloud)
    TextView tvCloud;
    @InjectView(R.id.tv_recharge)
    TextView tvRecharge;

    private AVOptions options;
    private MediaController mMediaController;
    private PLVideoView mVideoView;
    private Toast mToast = null;
    private String mVideoPath = null;
    private int mDisplayAspectRatio = PLVideoView.ASPECT_RATIO_FIT_PARENT;
    private boolean mIsActivityPaused = true;
    private View mLoadingView;
    private View mCoverView = null;
    private int mIsLiveStreaming = 1;
    //主播的id
    private int owner_id = 0;
    private int screen_hight;

    private int chapter_id;
    //直播间id
    private int room_id;
    //封装获取评论请求数据
    Map<String, Object> mapCommentDtata;
    //封装获取评论请求数据
    Map<String, Object> mapCommentCreate;
    //封装获取发言状态
    Map<String, Object> mapTalkStatus;
    //封装送礼物参数
    Map<String, Object> mapGiveGift;
    //封装消费积分送礼物参数
    HashMap<String, Object> mapConsumeScore;
    //分装消费云币送礼物参数
    HashMap<String, Object> mapConsumeICloud;


    //请求评论数据标记
    private boolean IS_FIRST_REQUEST_COMMENT = true;
    //分页id
    private int self_id;
    //评论adapter
    CommentDataAdapter commentDataAdapter;
    //直播presenter
    PLVideoViewPresenter plVideoViewPresenter;
    //评论列表
    List<LiveCommentBean> commentDataList;
    //评论bean
    LiveCommentBean commentBean;
    //评论内容
    private String comment_content;
    //房主的id
    private int room_uid;
    //定时刷新handler
    private Handler handler = null;
    private Handler numHandler = null;
    //退出dialog
    private ExitDialog exitDialog;
    //是否开启禁言
    private String is_talk = "yes";
    //礼物数组集合
    List<GiftBean> giftSendModelList = new ArrayList<GiftBean>();

    //view显示状态
    private boolean VIEW_SHOW_STATE = true;

    //查询评论间隔时间
    private int intervalTime = 1 * 1000;
    private int numTime = 60 * 1000;

    //用户积分数量
    private int scoreNum;
    //消费的积分数量
    private int consumeScoreNum;
    //用户云币数量
    private double icloudNum;
    //消费的云币数量
    private double consumeCloudNum;
    //默认赠送礼物的数量
    private int giftNum = 1;

    //充值状态
    private Observable<Double> rechargeObservable;

    /**********************************************************************************/
    private ViewPager giftViewPager;
    private LinearLayout group;//圆点指示器
    private ImageView[] ivPoints;//小圆点图片的集合
    private int totalPage; //总的页数
    private int mPageSize = 8; //每页显示的最大的数量
    private List<GiftBean> giftDataList;//总的数据源
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中
    //private int currentPage;//当前页
    //用于装载要发送的的礼物的信息
    private GiftBean sendGiftBean;
    GiftGridViewAdpter myGridViewAdpter;

    //标记直播状态
    private boolean live_start = false;

    private PowerManager.WakeLock wakeLock = null;

    String shareName = "";
    String shareChapter = "";
    UMWeb web;
    LiveBeingBean roomBeingBean;
    Dialog dialog;
    double cloudNum = 0.00;
    LiveBuyData liveBuyData;


    private void setOptions(int codecType) {
        AVOptions options = new AVOptions();

        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_PROBESIZE, 128 * 1024);
        // Some optimization with buffering mechanism when be set to 1
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, mIsLiveStreaming);
        if (mIsLiveStreaming == 1) {
            options.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
        }

        // 1 -> hw codec enable, 0 -> disable [recommended]
        options.setInteger(AVOptions.KEY_MEDIACODEC, codecType);

        // whether start play automatically after prepared, default value is 1
        options.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);

        mVideoView.setAVOptions(options);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_pl_video_view);
        ButterKnife.inject(this);
        init();

    }

    //初始化
    private void init() {
        room_id = getIntent().getIntExtra("room_id", 1);
        chapter_id = getIntent().getIntExtra("chapter_id", 0);
        plVideoViewPresenter = new PLVideoViewPresenter(this);
        screen_hight = ScreenUtils.getScreenHeight(this);
        mVideoView = (PLVideoView) findViewById(R.id.VideoView);

        //注册Rxbus
        rechargeObservable = RxBus.get().register("rechargeNum", Double.class);

//        ViewGroup.LayoutParams para = mVideoView.getLayoutParams();//获取布局
//        int width= ScreenUtils.getScreenWidth(this);
//        int height=ScreenUtils.getScreenHeight(this);
//        mVideoView.setLayoutParams(para);
        giftViewPager = (ViewPager) findViewById(R.id.viewpager);
        group = (LinearLayout) findViewById(R.id.points);
        mCoverView = (ImageView) findViewById(R.id.CoverView);
        mVideoView.setCoverView(mCoverView);
        mLoadingView = findViewById(R.id.LoadingView);
        mVideoView.setBufferingIndicator(mLoadingView);
        mLoadingView.setVisibility(View.VISIBLE);

//        mVideoPath = getIntent().getStringExtra("videoPath");
//        mIsLiveStreaming = getIntent().getIntExtra("liveStreaming", 1);
        mIsLiveStreaming = 1;
//         1 -> hw codec enable, 0 -> disable [recommended]
        setOptions(1);
        // Set some listeners
        mVideoView.setOnPreparedListener(mOnPreparedListener);
        mVideoView.setOnInfoListener(mOnInfoListener);
        mVideoView.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
        mVideoView.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        mVideoView.setOnCompletionListener(mOnCompletionListener);
        mVideoView.setOnSeekCompleteListener(mOnSeekCompleteListener);
        mVideoView.setOnErrorListener(mOnErrorListener);

        plVideoViewPresenter = new PLVideoViewPresenter(this);
        liveBuyData = new LiveBuyData(this);
        getRoomData();
        setListenerToRootView();
        getGiftList();
        QueryScore();
        QueryICloud();
    }

    @OnClick({R.id.tv_recharge,R.id.btn_send_gift, R.id.btn_send_comment, R.id.iv_head_pic, R.id.iv_exit_live, R.id.iv_menu_comment, R.id.iv_menu_clean, R.id.iv_menu_course, R.id.iv_menu_gift, R.id.iv_menu_member, R.id.iv_menu_share})
    public void onClick(View v) {
        switch (v.getId()) {
            //评论
            case R.id.iv_menu_comment:
                if (live_start) {
                    getTalkStatus();
                    UIUtil.showLog("getTalkStatus---------->", "ssssssssss");
                } else {
                    UIUtil.ToastshowShort(getApplicationContext(), "正在加载直播资源");
                }
                break;
            case R.id.btn_send_comment:
                comment_content = etComment.getText().toString();
                if (comment_content != null && !comment_content.equals("")) {
                    createComment();
                    etComment.setText("");
                } else {
                    UIUtil.ToastshowShort(getApplicationContext(), "请输入评论内容");
                }

                break;
            //课程列表
            case R.id.iv_menu_course:
                Intent intentCourse = new Intent(this, LiveCourseActivity.class);
                intentCourse.putExtra("room_id", room_id);
                startActivity(intentCourse);
                break;
            //分享
            case R.id.iv_menu_share:
                web = new UMWeb(Config.ArtForYou);
                web.setTitle(shareName + "老师正在云互艺平台直播");//标题
                web.setDescription("《" + shareChapter + "》，大家快来围观吧！");//描述

                new ShareAction(PLVideoViewActivity.this)
                        .withMedia(web)
                        .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN_FAVORITE)
                        .setCallback(umShareListener).open();
//                new ShareAction(this).withText(shareName+"老师正在云互艺平台直播《"+shareChapter+"》,欢迎大家强势围观！").withTargetUrl("http://baidu.com")
//                        .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN_FAVORITE)
//                        .setCallback(umShareListener).open();
                break;
            //送礼物
            case R.id.iv_menu_gift:
//                GiftSendModel giftSendModel = new GiftSendModel((int) (Math.random() * 10));
//                SuccessSendGift(giftSendModel);
                liveBuyData.getCouldData(0);
                rlGiftLayout.setVisibility(View.VISIBLE);
//                UIUtil.ToastshowShort(this, "功能暂未开放");
                break;
            case R.id.tv_recharge:
                startActivity(new Intent(this, RechargeICloudActivity.class));
                break;
            case R.id.iv_menu_member:
                Intent intent = new Intent(this, MemberListActivity.class);
                intent.putExtra("room_id", room_id);
                startActivity(intent);
                break;
            //清屏
            case R.id.iv_menu_clean:
                if (VIEW_SHOW_STATE) {
                    HideAllView();
                } else {
                    ShowAllView();
                }
                break;
            //退出直播
            case R.id.iv_exit_live:
                showExitDialog();
                break;
            //点击用户头像
            case R.id.iv_head_pic:
                if (owner_id != 0) {
                    Intent intentHeadPic = new Intent(this, ThemeTeacherContent.class);
                    intentHeadPic.putExtra("type", "PLvideoViewActvity");
                    intentHeadPic.putExtra("tec_id", owner_id + "");
                    startActivity(intentHeadPic);
                }
                break;

            case R.id.btn_send_gift:
                if (live_start) {
                    if (sendGiftBean != null) {
                        //判断是否免费
                        if (sendGiftBean.getPrice() != 0) {
                            if (sendGiftBean.getPrice() * giftNum > icloudNum) {
                                // TODO: 2017/3/1  进行云币充值
                                startActivity(new Intent(this, RechargeICloudActivity.class));
                            } else {
                                btnSendGift.isEnabled();
                                consumeScoreNum = sendGiftBean.getScore() * giftNum;
                                sendGiftByICloud(sendGiftBean);
                            }
                        } else {
                            if (sendGiftBean.getScore() * giftNum > scoreNum) {
                                // TODO: 2017/3/1  进行积分充值
                            } else {
                                consumeCloudNum = sendGiftBean.getPrice() * giftNum;
                                sendGiftByScore(sendGiftBean);
                            }
                        }
//                        sendGift(sendGiftBean.getGift_id(), sendGiftBean.getGift_num());
                    } else {
                        UIUtil.ToastshowShort(getApplicationContext(), "请选择要赠送的礼物");
                    }
                } else {
                    UIUtil.ToastshowShort(getApplicationContext(), "正在加载直播资源");
                }
                break;
        }
    }

    //用户进入房间
    @Override
    public void getRoomData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("room_id", room_id);
        map.put("chapter_id", chapter_id);
        plVideoViewPresenter.getRoomData(map);
    }

    //获取房间数据成功
    @Override
    public void SuccessRoom(LiveBeingBean roomBean) {
        roomBeingBean = roomBean;
        shareName = roomBean.getName();
        shareChapter = roomBean.getChapter_name();

        owner_id = roomBean.getOwner();

        Glide.with(this).load(roomBean.getHead_pic()).error(R.mipmap.default_user_header).transform(new GlideCircleTransform(this)).into(ivHeadPic);
        tvTecName.setText(roomBean.getName());
        tvRoomNum.setText(roomBean.getFollow_number() + "");
        tvLikeNum.setText(roomBean.getLike_number() + "");
        is_talk = roomBean.getIs_talk();

        mVideoPath = roomBean.getPlay_url();
        UIUtil.showLog("mVideoPath---------->", mVideoPath + "");
        mVideoView.setVideoPath(mVideoPath);
        // You can also use a custom `MediaController` widget
        mMediaController = new MediaController(this, false, mIsLiveStreaming == 1);
        mVideoView.setMediaController(mMediaController);
        mVideoView.start();

        UIUtil.showLog("getOrder_status",roomBean.getOrder_status()+"");

        if (roomBean.getIs_free() == 0) {
            if (roomBean.getOrder_status() == 0) {
                mVideoView.setVolume(0.0f, 0.0f);
                MyDialog.getPayDialog(this, roomBean.getLive_name(), roomBean.getChapter_name(), roomBean.getPre_time(), roomBean.getIntroduction(), roomBean.getLive_price(), this);
            }
        }

        if (numHandler == null) {
            numHandler = new Handler();
            numHandler.postDelayed(numRunnable, numTime);
        }
    }

    Runnable numRunnable = new Runnable() {
        @Override
        public void run() {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("access_token", Config.ACCESS_TOKEN);
            map.put("uid", Config.UID);
            map.put("utype", Config.USER_TYPE);
            map.put("room_id", room_id);
            map.put("chapter_id", chapter_id);
            plVideoViewPresenter.getRoomNumData(map);
        }
    };

    @Override
    public void successNumData(LiveBeingBean roomBean) {
        tvRoomNum.setText(roomBean.getFollow_number() + "");
        tvLikeNum.setText(roomBean.getLike_number() + "");
        numHandler.postDelayed(numRunnable, numTime);
    }

    @Override
    public void failureNumData(String error_code, String error_msg) {
        if (numHandler == null) {
            numHandler = new Handler();
        }
        numHandler.postDelayed(numRunnable, numTime);
    }

    //进入房间失败
    @Override
    public void FailureRoom(String error_code, String error_msg) {
        UIUtil.ToastshowShort(this, error_msg);
//        mVideoView.setVideoPath("rtmp://pili-live-rtmp.artforyou.cn/yhy-live/test02");
//        // You can also use a custom `MediaController` widget
//        mMediaController = new MediaController(this, false, mIsLiveStreaming == 1);
//        mVideoView.setMediaController(mMediaController);
//        mVideoView.start();
    }


    //定时获取数据线程
    private Runnable runnable = new Runnable() {
        public void run() {
            getCommentData();
        }
    };

    //获取用户评论数据
    @Override
    public void getCommentData() {

        UIUtil.showLog("liveComment", "获取数据");
        if (mapCommentDtata == null)
            mapCommentDtata = new HashMap<String, Object>();
        mapCommentDtata.put("access_token", Config.ACCESS_TOKEN);
        mapCommentDtata.put("uid", Config.UID);
        mapCommentDtata.put("utype", Config.USER_TYPE);
        mapCommentDtata.put("chapter_id", chapter_id);
        if (commentDataList != null && commentDataList.size() != 0) {
            self_id = commentDataAdapter.getLastCommentId();
            if (self_id != 0)
                mapCommentDtata.put("self", self_id);
        }
        plVideoViewPresenter.getCommentListData(mapCommentDtata);
    }

    //获取评论数据成功
    @Override
    public void SuccessCommentData(List<LiveCommentBean> liveCommentBeanList) {
        UIUtil.showLog("liveComment", "获取数据成功-----" + liveCommentBeanList.toString());
        if (commentDataList == null) commentDataList = new ArrayList<LiveCommentBean>();
        commentDataList.addAll(liveCommentBeanList);
        if (commentDataAdapter == null) {
            commentDataAdapter = new CommentDataAdapter(this, commentDataList);
            lvCommentData.setAdapter(commentDataAdapter);
//            lvCommentData.setSelection(lvCommentData.getBottom());
        } else {
            commentDataAdapter.RefreshCount(commentDataList.size());
            commentDataAdapter.notifyDataSetChanged();
            UIUtil.showLog("liveComment", "更新数据");
        }
        handler.postDelayed(runnable, intervalTime);
    }

    //获取评论数据失败
    @Override
    public void FailureCommentData(String error_code, String error_msg) {
        UIUtil.showLog("liveComment", "failure");
        if (handler == null) {
            handler = new Handler();
        }
        handler.postDelayed(runnable, intervalTime);
    }

    //获取发言状态
    @Override
    public void getTalkStatus() {
        if (mapTalkStatus == null)
            mapTalkStatus = new HashMap<String, Object>();
        mapTalkStatus.put("access_token", Config.ACCESS_TOKEN);
        mapTalkStatus.put("chapter_id", chapter_id);
        plVideoViewPresenter.getTalkStatus(mapTalkStatus);
    }

    //获取发言状态成功
    @Override
    public void SuccessGetTalk(String talkStatus) {
        switch (talkStatus) {
            case "yes":
                UIUtil.ToastshowShort(getApplicationContext(), "老师暂未开放发言");
                break;
            case "no":
                AutomaticKeyboard.getClick(this, etComment);
                break;
        }
    }

    //获取发言状态失败
    @Override
    public void FailureGetTalk(String error_code, String error_msg) {
        UIUtil.ToastshowShort(getApplicationContext(), "老师暂未开放发言");
    }

    //发表评论
    @Override
    public void createComment() {
        if (mapCommentCreate == null)
            mapCommentCreate = new HashMap<String, Object>();
        mapCommentCreate.put("access_token", Config.ACCESS_TOKEN);
        mapCommentCreate.put("uid", Config.UID);
        mapCommentCreate.put("utype", Config.USER_TYPE);
        mapCommentCreate.put("room_id", room_id);
        mapCommentCreate.put("content", comment_content);
        mapCommentCreate.put("chapter_id", chapter_id);
        plVideoViewPresenter.create(mapCommentCreate);

    }

    //评论成功
    @Override
    public void SuccessComment() {
//        commentBean = new LiveCommentBean();
//        commentBean.setUid(Config.UID);
//        commentBean.setName(Config.USER_NAME);
//        commentBean.setContent(comment_content);
//        commentBean.setType("text");
////        if (commentDataList != null && commentDataList.size() != 0){
////            commentDataList.add(commentBean);
////        }else {
////            commentDataList=new ArrayList<LiveCommentBean>();
////            commentDataList.add(commentBean);
////        }
//
////        if (commentDataList != null && commentDataList.size() == 5) {
////            commentDataList.remove(0);
////            commentDataList.add(0, commentBean);
////            UIUtil.showLog("commentDataList--->", commentDataList.toString());
////        } else {
////            if (commentDataList == null) commentDataList = new ArrayList<LiveCommentBean>();
////            commentDataList.add(commentBean);
////        }
//
//        if (commentDataList == null) commentDataList = new ArrayList<LiveCommentBean>();
//        commentDataList.add(commentBean);
//        if (commentDataAdapter == null) {
//            commentDataAdapter = new CommentDataAdapter(this, commentDataList);
//            lvCommentData.setAdapter(commentDataAdapter);
//        } else {
//            commentDataAdapter.RefreshCount(commentDataList.size());
//            commentDataAdapter.notifyDataSetChanged();
//        }
//        handler.postDelayed(runnable, 100);
    }

    //评论失败
    @Override
    public void FailureComment(String error_code, String error_msg) {
        UIUtil.ToastshowShort(this, error_msg);
    }

    //点赞
    @Override
    public void createLike() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("room_id", room_id);
        plVideoViewPresenter.createLike(map);

    }

    //点赞成功
    @Override
    public void SuccessCreateLike() {

    }

    //点赞失败
    @Override
    public void FailureCreateLike(String error_code, String error_msg) {

    }

    //关注
    @Override
    public void Focus() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("type", "tec");
        plVideoViewPresenter.Focus(map);

    }

    //关注成功
    @Override
    public void SuccessFocus() {

        UIUtil.ToastshowShort(getApplicationContext(), "关注成功");
    }

    //关注失败
    @Override
    public void FailureFocus(String error_code, String error_msg) {
        UIUtil.ToastshowShort(getApplicationContext(), error_msg);
    }


    /*****************************************************
     * send gift start
     *******************************************************/
    //送礼物请求
    //获取礼物列表
    @Override
    public void getGiftList() {
        plVideoViewPresenter.getGiftList();
    }

    //获取礼物列表成功
    @Override
    public void SuccessGetGiftList(List<GiftBean> giftBeenList) {

        giftDataList = giftBeenList;
        totalPage = (int) Math.ceil(giftDataList.size() * 1.0 / mPageSize);
        viewPagerList = new ArrayList<View>();
        for (int i = 0; i < totalPage; i++) {
            //每个页面都是inflate出一个新实例
            final MyGridView gridView = (MyGridView) View.inflate(PLVideoViewActivity.this, R.layout.view_gridview, null);
            myGridViewAdpter = new GiftGridViewAdpter(this, this, giftDataList, i, mPageSize);
            gridView.setAdapter(myGridViewAdpter);
            //添加item点击监听
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {
                    sendGiftBean = (GiftBean) gridView.getItemAtPosition(position);
                }
            });
            //每一个GridView作为一个View对象添加到ViewPager集合中
            viewPagerList.add(gridView);
        }
        //设置ViewPager适配器
        giftViewPager.setAdapter(new MyViewPagerAdapter(viewPagerList));

        //添加小圆点
        ivPoints = new ImageView[totalPage];
        for (int i = 0; i < totalPage; i++) {
            //循坏加入点点图片组
            ivPoints[i] = new ImageView(this);
            if (i == 0) {
                ivPoints[i].setImageResource(R.mipmap.page_focuese);
            } else {
                ivPoints[i].setImageResource(R.mipmap.page_unfocused);
            }
            ivPoints[i].setPadding(8, 8, 8, 8);
            group.addView(ivPoints[i]);
        }
        //设置ViewPager的滑动监听，主要是设置点点的背景颜色的改变
        giftViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                //currentPage = position;
                for (int i = 0; i < totalPage; i++) {
                    if (i == position) {
                        ivPoints[i].setImageResource(R.mipmap.page_focuese);
                    } else {
                        ivPoints[i].setImageResource(R.mipmap.page_unfocused);
                    }
                }
            }
        });
    }


    //获取礼物列表失败
    @Override
    public void FailureGetGiftList() {

    }

    //查询当前积分数量
    @Override
    public void QueryScore() {
        HashMap<String, Object> scoreMap = new HashMap<String, Object>();
        scoreMap.put("access_token", Config.ACCESS_TOKEN);
        scoreMap.put("uid", Config.UID);
        scoreMap.put("utype", Config.USER_TYPE);
        plVideoViewPresenter.QueryScore(scoreMap);

    }

    //查询当前积分数量成功
    @Override
    public void SuccessQueryScore(int score) {
        scoreNum = score;
    }

    //查询当前云币数量
    @Override
    public void QueryICloud() {
        HashMap<String, Object> cloudMap = new HashMap<String, Object>();
        cloudMap.put("access_token", Config.ACCESS_TOKEN);
        cloudMap.put("uid", Config.UID);
        cloudMap.put("utype", Config.USER_TYPE);
        plVideoViewPresenter.QueryICloud(cloudMap);
    }

    //查询当前云币成功
    @Override
    public void SuccessQueryCloud(double ICloud) {
        icloudNum = ICloud;

    }

    //查询用户积分或云币失败
    @Override
    public void FailureQuery(String error_code, String error_msg) {}

    @Override
    public void setGiftBean(GiftBean giftBean) {
        sendGiftBean = giftBean;
    }

    //积分消费送礼物
    @Override
    public void sendGiftByScore(GiftBean giftBean) {
        if (mapConsumeScore == null)
            mapConsumeScore = new HashMap<String, Object>();
        mapConsumeScore.put("access_token", Config.ACCESS_TOKEN);
        mapConsumeScore.put("uid", Config.UID);
        mapConsumeScore.put("utype", Config.USER_TYPE);
        mapConsumeScore.put("gift_id", giftBean.getGift_id());
        mapConsumeScore.put("number", giftNum);
        mapConsumeScore.put("room_id", room_id);
        mapConsumeScore.put("chapter_id", chapter_id);
        plVideoViewPresenter.sendGiftByScore(mapConsumeScore);
    }

    //消费云币送积分
    @Override
    public void sendGiftByICloud(GiftBean giftBean) {
        if (mapConsumeICloud == null)
            mapConsumeICloud = new HashMap<String, Object>();
        mapConsumeICloud.put("access_token", Config.ACCESS_TOKEN);
        mapConsumeICloud.put("uid", Config.UID);
        mapConsumeICloud.put("utype", Config.USER_TYPE);
        mapConsumeICloud.put("gift_id", giftBean.getGift_id());
        mapConsumeICloud.put("number", giftNum);
        mapConsumeICloud.put("room_id", room_id);
        mapConsumeICloud.put("chapter_id", chapter_id);
        plVideoViewPresenter.sendGiftByICloud(mapConsumeICloud);
    }

    //送礼物
    @Override
    public void sendGift(int gift_id, int gift_num) {
        if (mapGiveGift == null) mapGiveGift = new HashMap<String, Object>();
        mapGiveGift.put("access_token", Config.ACCESS_TOKEN);
        mapGiveGift.put("uid", Config.UID);
        mapGiveGift.put("utype", Config.USER_TYPE);
        mapGiveGift.put("room_id", room_id);
        mapGiveGift.put("chapter_id", chapter_id);
        mapGiveGift.put("gift_id", gift_id);
        mapGiveGift.put("number", gift_num);
        plVideoViewPresenter.sendGift(mapGiveGift);
    }

    //送礼物成功
    @Override
    public void SuccessSendGift() {
        btnSendGift.isClickable();
        if (rlGiftLayout.getVisibility() == View.VISIBLE)
            rlGiftLayout.setVisibility(View.GONE);
        //消费积分或者云币送礼物成功后 初始值相应减少
        if (consumeCloudNum != 0.0) {
            icloudNum = icloudNum - consumeCloudNum;
            consumeCloudNum = 0.0;
        }
        if (consumeScoreNum != 0) {
            scoreNum = scoreNum - consumeScoreNum;
            consumeScoreNum = 0;
        }
//        handler.postDelayed(runnable, 100);
    }


    //开启送礼物动画
    @Override
    public void starGiftAnimation(List<GiftBean> giftDataList) {
        GiftBean giftBean;
        for (int i = 0; i < giftDataList.size(); i++) {
            giftBean = giftDataList.get(i);
            if (!liveGiftOne.isShowing()) {
                sendGiftAnimation(liveGiftOne, giftBean);
            } else if (!liveGiftTwo.isShowing()) {
                sendGiftAnimation(liveGiftTwo, giftBean);
            } else {
                giftSendModelList.add(giftBean);
            }
        }


    }

    //显示礼物动画
    @Override
    public void sendGiftAnimation(final GiftFrameLayout view, GiftBean giftBean) {
        Glide.with(this).load(giftBean.getPic()).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(view.anim_gift);
        view.setModel(giftBean);
        AnimatorSet animatorSet = view.startAnimation(giftBean.getGift_num());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                synchronized (giftSendModelList) {
                    if (giftSendModelList.size() > 0) {
                        view.startAnimation(giftSendModelList.get(giftSendModelList.size() - 1).getGift_num());
                        giftSendModelList.remove(giftSendModelList.size() - 1);
                    }
                }
            }
        });
    }

    //送礼物失败
    @Override
    public void FailureSendGift() {
        // TODO: 2017/2/8
        btnSendGift.isClickable();
    }

    /*****************************************************
     * send gift end
     *******************************************************/
    //退出房间
    @Override
    public void exitRoom() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("room_id", room_id);
        plVideoViewPresenter.exitRoom(map);
    }

    @Override
    public void showExitDialog() {
        exitDialog = new ExitDialog(this, "退出直播", new ExitDialog.ExitDialogListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    //取消
                    case R.id.btn_cancel:
                        exitDialog.dismiss();
                        break;
                    //确定
                    case R.id.btn_sure:
                        exitDialog.dismiss();
                        exitRoom();
                        break;
                }
            }
        });
        exitDialog.setCanceledOnTouchOutside(false);
        Window window = exitDialog.getWindow();
        exitDialog.show();
        window.setGravity(Gravity.CENTER);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    //退出房间成功
    @Override
    public void SuccessExiyRoom() {
        finish();
    }

    //隐藏view
    @Override
    public void HideAllView() {
        liveMenu.setVisibility(View.GONE);
        ivMenuClean.setImageResource(R.mipmap.live_icon_unclear);
        lvCommentData.setVisibility(View.GONE);
        rvLiveUserinfo.setVisibility(View.GONE);
        ivLike.setVisibility(View.GONE);
        VIEW_SHOW_STATE = false;
    }

    //显示所有view
    @Override
    public void ShowAllView() {
        ivMenuClean.setImageResource(R.mipmap.icon_live_clean);
        liveMenu.setVisibility(View.VISIBLE);
        lvCommentData.setVisibility(View.VISIBLE);
        rvLiveUserinfo.setVisibility(View.VISIBLE);
        ivLike.setVisibility(View.VISIBLE);
        VIEW_SHOW_STATE = true;
    }

    //获取充值状态
    @Override
    public void getRechargeState() {
        rechargeObservable.subscribe(new Action1<Double>() {
            @Override
            public void call(Double rechargeCloud) {
                icloudNum = icloudNum + rechargeCloud;
            }
        });
    }



    //旋转屏幕
    public void onClickSwitchScreen(View v) {
        mDisplayAspectRatio = (mDisplayAspectRatio + 1) % 5;
        mVideoView.setDisplayAspectRatio(mDisplayAspectRatio);
        switch (mVideoView.getDisplayAspectRatio()) {
            case PLVideoView.ASPECT_RATIO_ORIGIN:
                showToastTips("Origin mode");
                break;
            case PLVideoView.ASPECT_RATIO_FIT_PARENT:
                showToastTips("Fit parent !");
                break;
            case PLVideoView.ASPECT_RATIO_PAVED_PARENT:
                showToastTips("Paved parent !");
                break;
            case PLVideoView.ASPECT_RATIO_16_9:
                showToastTips("16 : 9 !");
                break;
            case PLVideoView.ASPECT_RATIO_4_3:
                showToastTips("4 : 3 !");
                break;
            default:
                break;
        }
    }

    private PLMediaPlayer.OnInfoListener mOnInfoListener = new PLMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(PLMediaPlayer plMediaPlayer, int what, int extra) {
            Log.d(TAG, "onInfo: " + what + ", " + extra);
            return false;
        }
    };

    private PLMediaPlayer.OnPreparedListener mOnPreparedListener = new PLMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(PLMediaPlayer plMediaPlayer) {
            live_start = true;
            if (handler == null) {
                handler = new Handler();
                handler.postDelayed(runnable, 0);
                UIUtil.showLog("liveComment", "开启线程");
            }
            UIUtil.showLog("liveComment", "live_start_success:" + live_start);
//            plMediaPlayer.start();
        }
    };

    private PLMediaPlayer.OnErrorListener mOnErrorListener = new PLMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(PLMediaPlayer plMediaPlayer, int errorCode) {
            live_start = false;
            boolean isNeedReconnect = false;
            Log.e(TAG, "Error happened, errorCode = " + errorCode);
            switch (errorCode) {
                case PLMediaPlayer.ERROR_CODE_INVALID_URI:
                    showToastTips("Invalid URL !");
                    break;
                case PLMediaPlayer.ERROR_CODE_404_NOT_FOUND:
                    showToastTips("404 resource not found !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_REFUSED:
                    showToastTips("Connection refused !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_TIMEOUT:
                    showToastTips("Connection timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_EMPTY_PLAYLIST:
                    showToastTips("Empty playlist !");
                    break;
                case PLMediaPlayer.ERROR_CODE_STREAM_DISCONNECTED:
                    showToastTips("Stream disconnected !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_IO_ERROR:
                    showToastTips("Network IO Error !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_UNAUTHORIZED:
                    showToastTips("Unauthorized Error !");
                    break;
                case PLMediaPlayer.ERROR_CODE_PREPARE_TIMEOUT:
                    showToastTips("Prepare timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_READ_FRAME_TIMEOUT:
                    showToastTips("Read frame timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_HW_DECODE_FAILURE:
                    setOptions(AVOptions.MEDIA_CODEC_SW_DECODE);
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.MEDIA_ERROR_UNKNOWN:
                    break;
                default:
                    showToastTips("unknown error !");
                    break;
            }
            // Todo pls handle the error status here, reconnect or call finish()
            if (isNeedReconnect) {
                sendReconnectMessage();
            } else {
                finish();
            }
            // Return true means the error has been handled
            // If return false, then `onCompletion` will be called
            return true;
        }
    };

    //播放完成
    private PLMediaPlayer.OnCompletionListener mOnCompletionListener = new PLMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(PLMediaPlayer plMediaPlayer) {
            Log.d(TAG, "Play Completed !");
            showToastTips("Play Completed !");
            finish();
        }
    };

    private PLMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new PLMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(PLMediaPlayer plMediaPlayer, int precent) {
            Log.d(TAG, "onBufferingUpdate: " + precent);
        }
    };

    private PLMediaPlayer.OnSeekCompleteListener mOnSeekCompleteListener = new PLMediaPlayer.OnSeekCompleteListener() {
        @Override
        public void onSeekComplete(PLMediaPlayer plMediaPlayer) {
            Log.d(TAG, "onSeekComplete !");
        }
    };

    private PLMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener = new PLMediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(PLMediaPlayer plMediaPlayer, int width, int height) {
            Log.d(TAG, "onVideoSizeChanged: " + width + "," + height);
        }
    };

    private void showToastTips(final String tips) {
        if (mIsActivityPaused) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(PLVideoViewActivity.this, tips, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    protected Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what != MESSAGE_ID_RECONNECTING) {
                return;
            }
//            if (mIsActivityPaused || !LiveUtil.isLiveStreamingAvailable()) {
//                finish();
//                return;
//            }
            if (!LiveUtil.isLiveStreamingAvailable()) {
                finish();
                return;
            }
            if (!LiveUtil.isNetworkAvailable(PLVideoViewActivity.this)) {
                sendReconnectMessage();
                return;
            }
            mVideoView.setVideoPath(mVideoPath);
            mVideoView.start();
        }
    };

    private void sendReconnectMessage() {
        showToastTips("正在重连");
        Log.i("sendReconnectMessage", "正在重连");
//        UIUtil.ToastshowShort(this,"正在重连...");
        mLoadingView.setVisibility(View.VISIBLE);
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_ID_RECONNECTING), 500);
    }


    //分享监听
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            com.umeng.socialize.utils.Log.d("plat", "platform" + platform);
            UIUtil.ToastshowShort(getApplicationContext(), " 分享成功啦");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            UIUtil.ToastshowShort(getApplicationContext(), " 分享失败");
            UIUtil.showLog("throw", "throw:" + t.getMessage());
            if (t != null) {
                com.umeng.socialize.utils.Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            UIUtil.ToastshowShort(getApplicationContext(), " 分享取消了");
        }
    };

    /**
     * 监听键盘状态
     */
    private boolean mKeyboardUp;
    long waitTime = 500;

    private void setListenerToRootView() {
        final View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isFastClick(500)) {
                    //判断键盘的状态
                    boolean status = isKeyboardShown(rootView);
                    if (status) {
                        liveMenu.setVisibility(View.GONE);
                        llComment.setVisibility(View.VISIBLE);
                    } else {
                        if (VIEW_SHOW_STATE) {
                            liveMenu.setVisibility(View.VISIBLE);
                        }
                        llComment.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    //判断软键盘状态
    private boolean isKeyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = screen_hight - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }

    private long lastClickTime = 0;

    public boolean isFastClick(int wait_time) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < wait_time) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v.getWindowToken() != null) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(v.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                if (rlGiftLayout.getVisibility() == View.VISIBLE) {
                    if (!inRangeOfView(ivMenuGift, ev)) {
                        if (!inRangeOfView(rlGiftLayout, ev)) {
                            rlGiftLayout.setVisibility(View.GONE);
                            myGridViewAdpter.cleanSelected();
                            myGridViewAdpter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
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
    public void onBackPressed() {
//        super.onBackPressed();
        if (dialog != null && dialog.isShowing()) {
            finish();
        } else {
            showExitDialog();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mIsActivityPaused = false;
        if (mVideoView != null)
            mVideoView.start();
        if (handler != null) {
            handler.postDelayed(runnable,intervalTime);
        }
        if (numHandler != null) {
            numHandler.postDelayed(runnable, numTime);
        }
        if (Config.liveType) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            mVideoView.setVolume(1f, 1f);
            Config.liveType = false;
            Config.liveName = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        releaseWakeLock();
        mToast = null;
        mIsActivityPaused = true;
        if (mVideoView != null)
            mVideoView.pause();
        if (handler != null)
            handler.removeCallbacks(runnable);
        if (numHandler != null)
            numHandler.removeCallbacks(numRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null)
            mVideoView.stopPlayback();
        if (handler != null) handler.removeCallbacks(runnable);
        if (numHandler != null) numHandler.removeCallbacks(numRunnable);
        plVideoViewPresenter.cancelSubscription();
    }

    @Override
    public void getPay() {
        Intent intent = new Intent(this, LivePayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("chapter_name", roomBeingBean.getChapter_name());
        bundle.putDouble("live_price",roomBeingBean.getLive_price());
        bundle.putString("buy_type", "live");
        bundle.putInt("room_id", room_id);
        bundle.putInt("chapter_id", chapter_id);
        intent.putExtras(bundle);
        intent.putExtra("liveType", "liveBeing");
        startActivity(intent);
    }

    @Override
    public void onPayFailure() {
        finish();
    }

    @Override
    public void getDialog(Dialog mDialog) {
        dialog = mDialog;
        DialogInterface.OnKeyListener keyListener = new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface mdialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    finish();
                    return true;
                } else {
                    return false;
                }
            }
        };
        dialog.setOnKeyListener(keyListener);
    }

    @Override
    public void getCloud(Double aDouble, int position) {
        cloudNum = StringUtils.getDouble(aDouble);
        tvCloud.setText("我的云币：" + cloudNum);
    }

    @Override
    public void getPayCloud() {}

    @Override
    public void onFailure(String code, String msg) {
        UIUtil.ToastshowShort(this, msg);
    }


}
