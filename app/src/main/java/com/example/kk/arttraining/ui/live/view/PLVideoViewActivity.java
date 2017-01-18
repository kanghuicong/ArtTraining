package com.example.kk.arttraining.ui.live.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.live.LiveUtil;
import com.example.kk.arttraining.ui.live.MediaController;
import com.example.kk.arttraining.ui.live.adapter.CommentDataAdapter;
import com.example.kk.arttraining.ui.live.bean.LiveCommentBean;
import com.example.kk.arttraining.ui.live.bean.LiveBeingBean;
import com.example.kk.arttraining.ui.live.presenter.PLVideoViewPresenter;
import com.example.kk.arttraining.utils.AutomaticKeyboard;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.UIUtil;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * This is a demo activity of PLVideoView
 */
public class PLVideoViewActivity extends Activity implements IPLVideoView, View.OnClickListener {

    private static final String TAG = PLVideoViewActivity.class.getSimpleName();

    private static final int MESSAGE_ID_RECONNECTING = 0x01;
    @InjectView(R.id.lv_comment_data)
    MyListView lvCommentData;
    @InjectView(R.id.iv_create_comment)
    ImageView ivCreateComment;
    @InjectView(R.id.iv_screenshots)
    ImageView ivScreenshots;
    @InjectView(R.id.iv_share)
    ImageView ivShare;
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


    private MediaController mMediaController;
    private PLVideoView mVideoView;
    private Toast mToast = null;
    private String mVideoPath = null;
    private int mDisplayAspectRatio = PLVideoView.ASPECT_RATIO_FIT_PARENT;
    private boolean mIsActivityPaused = true;
    private View mLoadingView;
    private View mCoverView = null;
    private int mIsLiveStreaming = 1;

    //直播间id
    private int room_id;
    //封装获取评论请求数据
    Map<String, Object> mapCommentDtata;
    //封装获取评论请求数据
    Map<String, Object> mapCommentCreate;
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
//        room_id = getIntent().getIntExtra("room_id", 1);
        room_id =25;
        plVideoViewPresenter = new PLVideoViewPresenter(this);

        mVideoView = (PLVideoView) findViewById(R.id.VideoView);

//        ViewGroup.LayoutParams para = mVideoView.getLayoutParams();//获取布局
//        int width= ScreenUtils.getScreenWidth(this);
//        int height=ScreenUtils.getScreenHeight(this);
//        mVideoView.setLayoutParams(para);

        mCoverView = (ImageView) findViewById(R.id.CoverView);
        mVideoView.setCoverView(mCoverView);
        mLoadingView = findViewById(R.id.LoadingView);
        mVideoView.setBufferingIndicator(mLoadingView);
        mLoadingView.setVisibility(View.VISIBLE);

//        mVideoPath = getIntent().getStringExtra("videoPath");
//        mIsLiveStreaming = getIntent().getIntExtra("liveStreaming", 1);
        mIsLiveStreaming = 1;
//         1 -> hw codec enable, 0 -> disable [recommended]
        int codec = getIntent().getIntExtra("mediaCodec", AVOptions.MEDIA_CODEC_SW_DECODE);
        setOptions(1);
        // Set some listeners
        mVideoView.setOnInfoListener(mOnInfoListener);
        mVideoView.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
        mVideoView.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        mVideoView.setOnCompletionListener(mOnCompletionListener);
        mVideoView.setOnSeekCompleteListener(mOnSeekCompleteListener);
        mVideoView.setOnErrorListener(mOnErrorListener);

        plVideoViewPresenter = new PLVideoViewPresenter(this);
        getRoomData();
    }

    @OnClick({R.id.iv_create_comment, R.id.iv_screenshots, R.id.iv_share})
    public void onClick(View v) {
        switch (v.getId()) {
            //评论
            case R.id.iv_create_comment:
//                liveMenu.setVisibility(View.GONE);
//                llComment.setVisibility(View.VISIBLE);
                AutomaticKeyboard.getClick(this, etComment);
                break;
            //截图
            case R.id.iv_screenshots:
                Intent intent = new Intent(this, MemberListActivity.class);
                intent.putExtra("room_id", room_id);
                startActivity(intent);
                break;
            //分享
            case R.id.iv_share:
                new ShareAction(this).withText("hello")
                        .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN_FAVORITE)
                        .setCallback(umShareListener).open();
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
        plVideoViewPresenter.getRoomData(map);
    }

    //获取房间数据成功
    @Override
    public void SuccessRoom(LiveBeingBean roomBean) {
        mVideoPath = roomBean.getPlay_url();
        UIUtil.showLog("mVideoPath---------->", mVideoPath + "");
        mVideoView.setVideoPath("rtmp://pili-live-rtmp.artforyou.cn/yhy-live/test02");
        // You can also use a custom `MediaController` widget
        mMediaController = new MediaController(this, false, mIsLiveStreaming == 1);
        mVideoView.setMediaController(mMediaController);
        mVideoView.start();
        handler = new Handler();
        handler.postDelayed(runnable, 1000 * 5);// 间隔5秒
    }

    //进入房间失败
    @Override
    public void FailureRoom(String error_code, String error_msg) {
        UIUtil.ToastshowShort(this, error_msg);
        mVideoView.setVideoPath("rtmp://pili-live-rtmp.artforyou.cn/yhy-live/test02");
        // You can also use a custom `MediaController` widget
        mMediaController = new MediaController(this, false, mIsLiveStreaming == 1);
        mVideoView.setMediaController(mMediaController);
        mVideoView.start();
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
        if (mapCommentDtata == null)
            mapCommentDtata = new HashMap<String, Object>();
        mapCommentDtata.put("access_token", Config.ACCESS_TOKEN);
        mapCommentDtata.put("uid", Config.UID);
        mapCommentDtata.put("utype", Config.USER_TYPE);
        mapCommentDtata.put("room_id", room_id);
        if (!IS_FIRST_REQUEST_COMMENT) {
            self_id = commentDataAdapter.getLastCommentId();
            mapCommentDtata.put("room_id", self_id);
        }
        plVideoViewPresenter.getCommentListData(mapCommentDtata);
    }

    //获取评论数据成功
    @Override
    public void SuccessCommentData(List<LiveCommentBean> liveCommentBeanList) {
        commentDataList = liveCommentBeanList;
        if (commentDataAdapter == null) {
            commentDataAdapter = new CommentDataAdapter(this, commentDataList);
            lvCommentData.setAdapter(commentDataAdapter);
        } else {
            commentDataAdapter.RefreshCount(commentDataList.size());
            commentDataAdapter.notifyDataSetChanged();
        }
        handler.postDelayed(runnable, 1000 * 5);// 间隔5秒
    }

    //获取评论数据失败
    @Override
    public void FailureCommentData(String error_code, String error_msg) {
        handler.postDelayed(runnable, 1000 * 5);// 间隔5秒
    }

    //发表评论
    @Override
    public void createComment() {
        mapCommentCreate = new HashMap<String, Object>();
        mapCommentCreate.put("access_token", Config.ACCESS_TOKEN);
        mapCommentCreate.put("uid", Config.UID);
        mapCommentCreate.put("utype", Config.USER_TYPE);
        mapCommentCreate.put("room_id", room_id);
        mapCommentCreate.put("content", comment_content);
        plVideoViewPresenter.create(mapCommentCreate);

    }

    //评论成功
    @Override
    public void SuccessComment() {
        commentBean = new LiveCommentBean();
        commentBean.setUid(Config.UID);
        commentBean.setName(Config.USER_NAME);
        commentBean.setContent(comment_content);
        if (commentDataList.size() == 5) {
            commentDataList.remove(0);
            commentDataList.add(commentBean);
        } else {
            commentDataList.add(commentBean);
        }
        commentDataAdapter.RefreshCount(commentDataList.size());
        commentDataAdapter.notifyDataSetChanged();
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
    protected void onResume() {
        super.onResume();
        mIsActivityPaused = false;
        if (mVideoView != null)
            mVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mToast = null;
        mIsActivityPaused = true;
        if (mVideoView != null)
            mVideoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null)
            mVideoView.stopPlayback();
        if (handler != null) handler.removeCallbacks(runnable);
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

    private PLMediaPlayer.OnErrorListener mOnErrorListener = new PLMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(PLMediaPlayer plMediaPlayer, int errorCode) {
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
            if (mIsActivityPaused || !LiveUtil.isLiveStreamingAvailable()) {
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
        showToastTips("正在重连...");
        mLoadingView.setVisibility(View.VISIBLE);
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_ID_RECONNECTING), 500);
    }


    //分享监听
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            com.umeng.socialize.utils.Log.d("plat", "platform" + platform);
            UIUtil.ToastshowShort(getApplicationContext(), " 分享成功啦");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            UIUtil.ToastshowShort(getApplicationContext(), " 分享失败");
            if (t != null) {
                com.umeng.socialize.utils.Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            UIUtil.ToastshowShort(getApplicationContext(), " 分享取消了");
        }
    };

}
