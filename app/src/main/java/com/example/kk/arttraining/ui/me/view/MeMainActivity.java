package com.example.kk.arttraining.ui.me.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.sqlite.dao.UserDao;
import com.example.kk.arttraining.ui.me.AboutActivity;
import com.example.kk.arttraining.ui.me.bean.UserCountBean;
import com.example.kk.arttraining.ui.me.presenter.MeMainPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.GlideCircleTransform;
import com.example.kk.arttraining.utils.UIUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/8/30 16:13
 * 说明:我的主activity
 */
public class MeMainActivity extends Fragment implements View.OnClickListener, IMeMain {
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
    @InjectView(R.id.me_tv_groupNum)
    TextView tv_groupNum;
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
    @InjectView(R.id.me_ll_group)
    LinearLayout meLlGroup;


    private String user_id;
    private UserDao userDao;
    private String user_code;

    private MeMainPresenter meMainPresenter;
    private Context context;
    private Activity activity;
    private View view_me;
    //用户信息
    private UserLoginBean userInfoBean;
    //用户统计信息bean
    private UserCountBean userCountBean;
    //成功信息码
    private int success_code;
    //是被信息码
    private String error_code;

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
        meMainPresenter = new MeMainPresenter(this);
        userInfoBean = new UserLoginBean();
        //获取用户信息
        getUserInfo();
        //获取用户统计信息
//        getUserCount();
        Glide.with(context).load(Config.USER_HEADER_Url).transform(new GlideCircleTransform(context)).error(R.mipmap.default_user_header).into(user_header);
    }


    //按钮点击事件
    @OnClick({R.id.ll_comments, R.id.ll_collect, R.id.ll_coupons, R.id.ll_setting, R.id.ll_order, R.id.me_ll_userinfo, R.id.ll_transfor, R.id.me_ll_topic, R.id.me_ll_fans, R.id.me_ll_foucs, R.id.me_ll_group})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_collect:
                startActivity(new Intent(context, CollectActivity.class));
                break;
            //优惠券
            case R.id.ll_coupons:
                Intent intent = new Intent(context, CouponActivity.class);
                intent.putExtra("from", "meMainActivity");
                startActivity(intent);
                break;
            //设置
            case R.id.ll_setting:
                startActivity(new Intent(context, SettingActivity.class));
                break;
            //我的订单

            case R.id.ll_order:
                startActivity(new Intent(context, OrderActivity.class));
                break;

            //点击用户头像
            case R.id.me_ll_userinfo:
                startActivity(new Intent(context, AboutActivity.class));
                break;

            //传输列表
            case R.id.ll_transfor:
//                startActivity(new Intent(context, TransforListActivity.class));
                startActivity(new Intent(context, PersonalHomePageActivity.class));
                break;
            //粉丝
            case R.id.me_ll_fans:
                Intent intentFans = new Intent(context, FansActivity.class);
                intentFans.putExtra("type", "fans");
                startActivity(intentFans);
                break;
            //我的关注
            case R.id.me_ll_foucs:
                Intent intentFocus = new Intent(context, FansActivity.class);
                intentFocus.putExtra("type", "focus");
                startActivity(intentFocus);
                break;
            //我的小组
            case R.id.me_ll_group:
                startActivity(new Intent(context, MyGroupActivity.class));
                break;
            //我的帖子
            case R.id.me_ll_topic:
                startActivity(new Intent(context, MyBBS.class));
                break;
            case R.id.ll_comments:
                startActivity(new Intent(context, MyBBS.class));
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
        meMainPresenter.getUserCount();
    }

    //获取用户信息成功
    @Override
    public void getUserInfoSuccess(UserLoginBean userBean) {
        userInfoBean = userBean;
        Config.userBean = userBean;
        success_code = 0;
        SuccessHandler.sendEmptyMessage(0);
    }
    //获取用户信息失败
    @Override
    public void getUserInfoFailure(String error_code) {
        this.error_code = error_code;
        ErrorHandler.sendEmptyMessage(0);
    }
    //获取用户统计信息成功
    @Override
    public void getUserCountSuccess(UserCountBean userCountBean) {
        this.userCountBean = userCountBean;
        success_code = 1;
        SuccessHandler.sendEmptyMessage(0);
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
                    tv_phoneNum.setText(userInfoBean.getMobile());
                    if (!(userInfoBean.getCity() == null || userInfoBean.getCity().equals("")))
                        tv_city.setText(userInfoBean.getCity() + "");
                    if (!(userInfoBean.getIdentity() == null || userInfoBean.getIdentity().equals("")))
                        tv_grade.setText(userInfoBean.getIdentity() + "");
                    if (!(userInfoBean.getSchool() == null || userInfoBean.getSchool().equals("")))
                        tv_schoolName.setText(userInfoBean.getSchool() + "");
                    Glide.with(context).load(userInfoBean.getHead_pic()).transform(new GlideCircleTransform(context)).error(R.mipmap.default_user_header).into(user_header);
                    break;
                case 1:
                    tv_fansNum.setText(userCountBean.getUfans_num() + "");
                    tv_focusNum.setText(userCountBean.getUfocus_num() + "");
                    tv_groupNum.setText(userCountBean.getUgroup_num() + "");
                    tv_topicNum.setText(userCountBean.getUtopic_num() + "");
                    tv_collect_num.setText("(" + userCountBean.getUcollect_num() + ")");
                    tv_comment_num.setText("(" + userCountBean.getUcomment_num() + ")");
                    tv_transfor_num.setText("(" + userCountBean.getUtransfor_num() + ")");
                    break;
            }
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
