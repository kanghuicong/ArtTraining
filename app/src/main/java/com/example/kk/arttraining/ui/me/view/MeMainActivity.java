package com.example.kk.arttraining.ui.me.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.MainActivity;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.custom.view.AutoSwipeRefreshLayout;
import com.example.kk.arttraining.custom.view.GlideCircleTransform;
import com.example.kk.arttraining.receiver.bean.JpushMessageBean;
import com.example.kk.arttraining.sqlite.bean.UploadBean;
import com.example.kk.arttraining.sqlite.dao.UserDao;
import com.example.kk.arttraining.sqlite.dao.UserDaoImpl;
import com.example.kk.arttraining.ui.live.view.PLVideoViewActivity;
import com.example.kk.arttraining.ui.me.AboutActivity;
import com.example.kk.arttraining.ui.me.bean.UserCountBean;
import com.example.kk.arttraining.ui.me.presenter.MeMainPresenter;
import com.example.kk.arttraining.ui.me.presenter.UploadPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.JsonTools;
import com.example.kk.arttraining.utils.UIUtil;
import com.example.kk.arttraining.utils.upload.presenter.SignleUploadPresenter;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * 作者：wschenyongyin on 2016/8/30 16:13
 * 说明:我的主activity
 */
public class MeMainActivity extends Fragment implements View.OnClickListener, IMeMain, SwipeRefreshLayout.OnRefreshListener {
    @InjectView(R.id.user_header)
    ImageView user_header;
    @InjectView(R.id.me_tv_phoneNum)
    TextView tv_phoneNum;
    @InjectView(R.id.me_tv_city)
    TextView tv_city;
    @InjectView(R.id.me_tv_grade)
    TextView tv_grade;
    @InjectView(R.id.me_tv_schoolName)
    TextView tv_schoolName;
    @InjectView(R.id.me_tv_topicNum)
    TextView tv_topicNum;
    @InjectView(R.id.me_tv_focusNum)
    TextView tv_focusNum;
    @InjectView(R.id.me_tv_fansNum)
    TextView tv_fansNum;
    @InjectView(R.id.me_tv_works)
    TextView tv_worksNum;
    //用户统计信息
    @InjectView(R.id.tv_collect_num)
    TextView tv_collect_num;
    @InjectView(R.id.tv_comment_num)
    TextView tv_comment_num;
    @InjectView(R.id.tv_transfor_num)
    TextView tv_transfor_num;

    @InjectView(R.id.me_ll_userinfo)
    LinearLayout ll_userinfo;
    @InjectView(R.id.ll_order)
    LinearLayout ll_order;
    @InjectView(R.id.ll_coupons)
    LinearLayout ll_coupons;
    @InjectView(R.id.ll_collect)
    LinearLayout llCollect;
    @InjectView(R.id.ll_comments)
    LinearLayout ll_comments;
    @InjectView(R.id.ll_certificate)
    LinearLayout ll_certificate;
    @InjectView(R.id.ll_setting)
    LinearLayout ll_setting;
    @InjectView(R.id.ll_transfor)
    LinearLayout ll_transfor;

    @InjectView(R.id.me_ll_topic)
    LinearLayout meLlTopic;
    @InjectView(R.id.me_ll_foucs)
    LinearLayout meLlFoucs;
    @InjectView(R.id.me_ll_fans)
    LinearLayout meLlFans;
    @InjectView(R.id.me_ll_works)
    LinearLayout meLlWorks;
    @InjectView(R.id.ll_msg)
    LinearLayout llMsg;
    @InjectView(R.id.msg_right)
    ImageView msgRight;


    private String user_id;
    private static UserDao userDao;
    private String user_code;

    private MeMainPresenter meMainPresenter;
    private static Context context;
    private static Activity activity;
    private View view_me;
    //用户信息
    private UserLoginBean userInfoBean;
    //用户统计信息bean
    private UserCountBean userCountBean;
    //成功信息码
    private int success_code;
    //是被信息码
    private String error_code;
    AutoSwipeRefreshLayout swipeRefreshLayout;

    private String thumbnail_pic;
    private UploadBean uploadBean;
    private String jsonString;
    private String order_id;
    SignleUploadPresenter signleUploadPresenter;
    private UploadPresenter presenter;
    public static int INTENT_ABOUT = 10004;

    private TextView iv_me_msg_remind;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        activity = getActivity();
        context = activity.getApplicationContext();
        if (view_me == null) {
            view_me = View.inflate(activity, R.layout.me_main, null);
            ButterKnife.inject(this, view_me);
            init();
        }
        ViewGroup parent = (ViewGroup) view_me.getParent();
        if (parent != null) {
            parent.removeView(view_me);
        }
        ButterKnife.inject(this, view_me);
        return view_me;
    }

    public void init() {
        iv_me_msg_remind = (TextView) view_me.findViewById(R.id.iv_me_msg_remind);
        swipeRefreshLayout = new AutoSwipeRefreshLayout(context);
        swipeRefreshLayout = (AutoSwipeRefreshLayout) view_me.findViewById(R.id.me_swipe);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#87CEFA"));
        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.autoRefresh();

        meMainPresenter = new MeMainPresenter(this);
        userInfoBean = new UserLoginBean();
        //获取用户信息
        getUserInfo();
        //获取用户统计信息
//        getUserCount();
        Glide.with(context).load(Config.USER_HEADER_Url).transform(new GlideCircleTransform(context)).error(R.mipmap.default_user_header).into(user_header);
        //注册广播
        RegisterReceiver();
    }


    //按钮点击事件
    @OnClick({R.id.ll_msg, R.id.ll_comments, R.id.ll_collect, R.id.ll_coupons, R.id.ll_setting, R.id.ll_order, R.id.me_ll_userinfo, R.id.ll_transfor, R.id.me_ll_topic, R.id.me_ll_fans, R.id.me_ll_foucs, R.id.me_ll_works})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_collect:
                startActivity(new Intent(activity, CollectActivity.class));
//                startActivity(new Intent(activity, PLVideoViewActivity.class));
//                startActivity(new Intent(context, CourseDetailActivity.class));
//                new ShareAction(activity).withText("hello")
//                        .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN_FAVORITE)
//                        .setCallback(umShareListener).open();
//                new ShareAction(activity).withText("hello")
//                        .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN_FAVORITE)
//                        .setCallback(umShareListener).open();
                break;
            //优惠券
            case R.id.ll_coupons:
                Intent intent = new Intent(activity, CouponActivity.class);
                intent.putExtra("from", "meMainActivity");
                startActivity(intent);

//                UMShareAPI.get(context).getPlatformInfo(activity, SHARE_MEDIA.WEIXIN, umAuthListener);
//                new ShareAction(activity).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
//                        .withText("http://www.artforyou.cn/")
//                        .setCallback(umShareListener)
//                        .share();
                break;
            //设置
            case R.id.ll_setting:
                startActivity(new Intent(activity, SettingActivity.class));
//                new ShareAction(activity).setPlatform(SHARE_MEDIA.QQ)
//                        .withText("http://www.artforyou.cn/")
//                        .setCallback(umShareListener)
//                        .share();
                break;
            //我的订单
            case R.id.ll_order:
                startActivity(new Intent(activity, OrderActivity.class));
//                new ShareAction(activity).setPlatform(SHARE_MEDIA.QZONE)
//                        .withText("http://www.artforyou.cn/")
//                        .setCallback(umShareListener)
//                        .share();
                break;

            //点击用户头像
            case R.id.me_ll_userinfo:
                Intent intentAbout = new Intent(activity, AboutActivity.class);
                startActivityForResult(intentAbout, INTENT_ABOUT);
                break;

            //传输列表
            case R.id.ll_transfor:
                startActivity(new Intent(activity, TransforListActivity.class));
                break;
            //粉丝
            case R.id.me_ll_fans:
                Intent intentFans = new Intent(activity, FansActivity.class);
                intentFans.putExtra("type", "fans");
                intentFans.putExtra("uid", Config.UID);
                startActivity(intentFans);
                break;
            //我的关注
            case R.id.me_ll_foucs:
                Intent intentFocus = new Intent(activity, FansActivity.class);
                intentFocus.putExtra("type", "foucs");
                intentFocus.putExtra("uid", Config.UID);
                startActivity(intentFocus);
                break;
            //我的小组
            case R.id.me_ll_group:
                startActivity(new Intent(activity, MyGroupActivity.class));
                break;
            //我的帖子
            case R.id.me_ll_topic:
                Intent intentTopic = new Intent(activity, MyBBSActivity.class);
                intentTopic.putExtra("type", "topic");
                startActivity(intentTopic);
                break;
            case R.id.ll_comments:
                Intent intentComments = new Intent(activity, MyBBSActivity.class);
                intentComments.putExtra("type", "comments");
                startActivity(intentComments);
                break;
            case R.id.me_ll_works:
                startActivity(new Intent(activity, MyWorksActivity.class));

                break;
            //我的消息
            case R.id.ll_msg:
                Intent intentMsg = new Intent(activity, MessageListActviity.class);
                if (iv_me_msg_remind.getVisibility() == View.VISIBLE) {
                    intentMsg.putExtra("type", "msg_yes");
                    ((MainActivity) activity).setRemindImageGone();
                    iv_me_msg_remind.setVisibility(View.GONE);
                    msgRight.setVisibility(View.VISIBLE);
                } else {
                    intentMsg.putExtra("type", "msg_no");
                }
                startActivity(intentMsg);
                break;

        }
    }

    //调用获取用户方法
    @Override
    public void getUserInfo() {
        meMainPresenter.getUserInfoData(context);
    }

    //调用获取统计信息方法
    @Override
    public void getUserCount() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        meMainPresenter.getUserCount(map);
    }

    //获取用户信息成功
    @Override
    public void getUserInfoSuccess(UserLoginBean userBean) {
        swipeRefreshLayout.setRefreshing(false);
        userInfoBean = userBean;
        Config.userBean = userBean;
        success_code = 0;
        SuccessHandler.sendEmptyMessage(0);
    }

    //获取用户信息失败
    @Override
    public void getUserInfoFailure(String error_code) {
        swipeRefreshLayout.setRefreshing(false);
        this.error_code = error_code;
        ErrorHandler.sendEmptyMessage(0);
    }

    //获取用户统计信息成功
    @Override
    public void getUserCountSuccess(UserCountBean userCountBean) {
        this.userCountBean = userCountBean;
        UIUtil.showLog("用户统计信息", userCountBean.toString());
        success_code = 1;
        SuccessHandler.sendEmptyMessage(0);
        UserDao userDao = new UserDaoImpl(context);
        userDao.updateCountAll(userCountBean);
    }

    //获取用户统计信息失败
    @Override
    public void getUserCountFailure(String error_code) {
        this.error_code = error_code;
        ErrorHandler.sendEmptyMessage(0);
    }


    //错误信息处理handler
    Handler ErrorHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (error_code) {
                case "0":
                    break;
                case "20039":
                    // TODO: 2016/10/21 设置要登陆的页面 隐藏用户信息页面
                    break;
            }
        }
    };
    //成功信息处理handler
    Handler SuccessHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (success_code) {
                case 0:
                    UIUtil.showLog("用户信息：", userInfoBean.toString());
                    if (userInfoBean != null) {
                        if (userInfoBean.getName() != null && !userInfoBean.getName().equals(""))
                            tv_phoneNum.setText(userInfoBean.getName() + "");
                        if (userInfoBean.getCity() != null && !userInfoBean.getCity().equals(""))
                            tv_city.setText(userInfoBean.getCity() + "");
                        if (userInfoBean.getIdentity() != null && !userInfoBean.getIdentity().equals(""))
                            tv_grade.setText(userInfoBean.getIdentity() + "");
                        if (userInfoBean.getSchool() != null && !userInfoBean.getSchool().equals(""))
                            tv_schoolName.setText(userInfoBean.getSchool() + "");
                        if (userInfoBean.getHead_pic() != null && !userInfoBean.getHead_pic().equals("")) {
                            Glide.with(context).load(userInfoBean.getHead_pic()).transform(new GlideCircleTransform(context)).error(R.mipmap.default_user_header).into(user_header);
                        } else {
                            Glide.with(context).load(R.mipmap.default_user_header).transform(new GlideCircleTransform(context)).into(user_header);
                        }

                        tv_fansNum.setText(userInfoBean.getFans_num() + "");
                        tv_focusNum.setText(userInfoBean.getFollow_num() + "");
                        tv_worksNum.setText(userInfoBean.getWork_num() + "");
                        tv_topicNum.setText(userInfoBean.getBbs_num() + "");
//                       tv_collect_num.setText("(" + userInfoBean.getFavorite_num() + ")");
                        tv_comment_num.setText("(" + userInfoBean.getComment_num() + ")");
                    }
                    break;
                case 1:
                    UIUtil.showLog("userCountBean---->", userCountBean.toString() + "---->");
                    UIUtil.showLog("works_num---->", userCountBean.getWork_num() + "--->");
                    UIUtil.showLog("getFollow_num---->", userCountBean.getFollow_num() + "--->");
                    tv_fansNum.setText(userCountBean.getFans_num() + "");
                    tv_focusNum.setText(userCountBean.getFollow_num() + "");
                    tv_worksNum.setText(userCountBean.getWork_num() + "");
                    tv_topicNum.setText(userCountBean.getBbs_num() + "");
//                    tv_collect_num.setText("(" + userCountBean.getFavorite_num() + ")");
                    tv_comment_num.setText("(" + userCountBean.getComment_num() + ")");
                    break;
            }
        }
    };


//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.reset(this);
//    }

    @Override
    public void onRefresh() {
        getUserInfo();
        //获取用户统计信息
        getUserCount();
    }


    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10004:
                getUserInfo();
                break;
        }
    }


    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);

            Toast.makeText(context, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(context, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(context, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    //接收推送消息
    //接收后台推送消息
    BroadcastReceiver JpushMessageReceiver = new BroadcastReceiver() {
        //推送的原始数据
        private String extras;
        //装推送的数据
        private JpushMessageBean jpushBean;
        //推送的类型
        private String type;
        //推送的值
        private String value;

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            jpushBean = JsonTools.ParseJpushMessage("msg", extras);

            if (jpushBean != null) {
                type = jpushBean.getType();
                UIUtil.showLog("jpush_msg", jpushBean.toString());
                Message msg = new Message();
                //显示我的页面有消息

                if (type != null && !type.equals("")) {
                    switch (type) {
                        //帖子动态评论
                        case "comment_bbs":
                            msg.obj = jpushBean.getMsg_num();
                            msg.what = 3;
                            JpushHandler.sendMessage(msg);
                            break;
                        //作品评论
                        case "comment_work":
                            msg.obj = jpushBean.getMsg_num();
                            msg.what = 3;
                            JpushHandler.sendMessage(msg);
                            break;
                        //小组动态评论
                        case "comment_gstus":
                            msg.obj = jpushBean.getMsg_num();
                            msg.what = 3;
                            JpushHandler.sendMessage(msg);
                            break;
                        //帖子回复
                        case "reply_bbs":
                            msg.obj = jpushBean.getMsg_num();
                            msg.what = 3;
                            JpushHandler.sendMessage(msg);
                            break;
                        //作品回复
                        case "reply_work":
                            msg.obj = jpushBean.getMsg_num();
                            msg.what = 3;
                            JpushHandler.sendMessage(msg);
                            break;
                        //小组动态评论
                        case "reply_gstus":
                            msg.obj = jpushBean.getMsg_num();
                            msg.what = 3;
                            JpushHandler.sendMessage(msg);
                            break;
                        //老师评论
                        case "tec_comment":
                            msg.obj = jpushBean.getMsg_num();
                            msg.what = 3;
                            JpushHandler.sendMessage(msg);
                            break;
                        //老师回复
                        case "tec_reply":
                            msg.obj = jpushBean.getMsg_num();
                            msg.what = 3;
                            JpushHandler.sendMessage(msg);
                            break;
                        //学生测评
                        case "stu_ass":
                            msg.obj = jpushBean.getWorks_num();
                            msg.what = 4;
                            JpushHandler.sendMessage(msg);
                            updateCountNum("work_num", jpushBean.getWorks_num() + "");
                            break;

                        case "publish_bbs":
                            msg.obj = jpushBean.getBbs_num();

                            msg.what = 2;
                            JpushHandler.sendMessage(msg);
                            updateCountNum("bbs_num", jpushBean.getBbs_num() + "");
                            break;
                        //帖子点赞
                        case "like_bbs":
                            msg.obj = jpushBean.getMsg_num();
                            msg.what = 3;
                            JpushHandler.sendMessage(msg);
                            break;
                        //作品点赞
                        case "like_work":
                            msg.obj = jpushBean.getMsg_num();
                            msg.what = 3;
                            JpushHandler.sendMessage(msg);
                            break;
                        //小组动态点赞
                        case "like_gstus":
                            msg.obj = jpushBean.getMsg_num();
                            msg.what = 3;
                            JpushHandler.sendMessage(msg);
                            break;
                        //关注
                        case "follow":
                            Bundle bundleFollow = new Bundle();
                            bundle.putInt("follow_num", jpushBean.getFollow_num());
                            bundle.putInt("fans_num", jpushBean.getFans_num());
                            bundle.putInt("msg_num", jpushBean.getMsg_num());
                            msg.setData(bundle);
                            msg.what = 1;
                            msg.obj = 0;
                            JpushHandler.sendMessage(msg);
                            break;
                    }
                }
            }

        }
    };

    Handler JpushHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int value = (int) msg.obj;
            switch (msg.what) {
                case 1:

                    Bundle bundle = msg.getData();
                    int follow_num = bundle.getInt("follow_num");
                    int fans_num = bundle.getInt("fans_num");
                    if (follow_num != 0) {
                        tv_focusNum.setText(follow_num + "");
                        updateCountNum("favorite_num", follow_num + "");
                    } else {
                        tv_fansNum.setText(fans_num + "");
                        updateCountNum("favorite_num", fans_num + "");
                    }
                    if (iv_me_msg_remind != null)
                        iv_me_msg_remind.setText(bundle.getInt("msg_num"));
                    break;
                case 2:
                    tv_worksNum.setText(value + "");
                    break;
                case 3:
                    ((MainActivity) activity).setRemindImageVISIBLE();
                    if (iv_me_msg_remind != null)
                        iv_me_msg_remind.setVisibility(View.VISIBLE);
                    msgRight.setVisibility(View.GONE);
                    if (iv_me_msg_remind != null)
                        iv_me_msg_remind.setText(value + "");
                    break;
                case 4:
                    tv_topicNum.setText(value + "");
                    break;

            }
        }
    };

    //更新用户统计
    public static void updateCountNum(String type, String value) {
        if (userDao == null) {
            userDao = new UserDaoImpl(context);
        }
        userDao.Update(Config.UID, value, type);
    }


    //注册广播
    void RegisterReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("cn.jpush.android.intent.MESSAGE_RECEIVED");
        filter.addCategory("com.example.kk.arttraining");
        activity.registerReceiver(JpushMessageReceiver, filter);
    }

    //注销广播
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (JpushMessageReceiver != null) activity.unregisterReceiver(JpushMessageReceiver);
    }
}
