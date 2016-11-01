package com.example.kk.arttraining.ui.me;

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
import com.example.kk.arttraining.dao.UserDao;
import com.example.kk.arttraining.dao.UserDaoImpl;
import com.example.kk.arttraining.pay.wxapi.WXPayUtils;
import com.example.kk.arttraining.playvideo.activity.PlayVideoActivity;
import com.example.kk.arttraining.ui.me.view.OrderActivity;
import com.example.kk.arttraining.ui.me.view.SettingActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.GlideCircleTransform;
import com.example.kk.arttraining.utils.PreferencesUtils;
import com.jaeger.library.StatusBarUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/8/30 16:13
 * 说明:我的主activity
 */
public class MeMainActivity extends Fragment implements View.OnClickListener {

    @InjectView(R.id.user_header)
    ImageView user_header;
    ;
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

    @InjectView(R.id.me_ll_userinfo)
    LinearLayout ll_userinfo;
    ;

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

    private String user_id;
    private UserDao userDao;
    private String user_code;
    private UserLoginBean userInfoBean;
    private UserLoginBean serverUserBean;

    Context context;
    Activity activity;
    View view_me;

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
        return view_me;
    }

    public void init() {

        userInfoBean = new UserLoginBean();


        Glide.with(context).load(Config.USER_HEADER_Url).transform(new GlideCircleTransform(context)).error(R.mipmap.default_user_header).into(user_header);
//        initUserInfo();
        //            initView();

    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.obj.toString()) {
                //获取用户数据成功
                case "0":
                    //设置页面显示信息
                    initView();
                    //更新本地数据库信息
                    UserDao userDao = new UserDaoImpl(context);
                    userDao.Insert(userInfoBean);
                    Config.userBean = userInfoBean;
                    break;
                //获取用户信息失败 token失效
                case "20039":
                    // TODO: 2016/10/21 设置要登陆的页面 隐藏用户信息页面
                    break;
            }
        }
    };

    //初始化用户信息
    public void initUserInfo() {
        getLocalUserInfo();
        Message msg = new Message();
        //判断本地数据库是否有用户信息
        if (userInfoBean == null) {
            // TODO: 2016/10/21 请求网络从服务器获取数据
            userInfoBean = getServerUserInfo();
        } else {
            msg.obj = "0";
        }
        mHandler.sendMessage(msg);

    }

    public void initView() {
        tv_phoneNum.setText(userInfoBean.getMobile());
        tv_city.setText(userInfoBean.getCity());
        tv_fansNum.setText(userInfoBean.getUfans_num() + "");
        tv_focusNum.setText(userInfoBean.getUfocus_num() + "");
        tv_groupNum.setText(userInfoBean.getUgroup_num() + "");
        tv_topicNum.setText(userInfoBean.getUtopic_num() + "");
        tv_grade.setText(userInfoBean.getIdentity());
        tv_schoolName.setText(userInfoBean.getSchool());
        Glide.with(context).load(userInfoBean.getHead_pic()).transform(new GlideCircleTransform(context)).error(R.mipmap.default_user_header).into(user_header);

    }

    //从本地数据库读取用户数据
    private void getLocalUserInfo() {
        userDao = new UserDaoImpl(context);
        user_code = PreferencesUtils.get(context, "user_code", "").toString();
        userInfoBean = userDao.QueryAll(user_code);


    }

    //从服务器请求用户数据
    private UserLoginBean getServerUserInfo() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);

        Callback<UserLoginBean> callback = new Callback<UserLoginBean>() {
            @Override
            public void onResponse(Call<UserLoginBean> call, Response<UserLoginBean> response) {
                if (response.body() != null) {
                    serverUserBean = response.body();
                } else {
                    serverUserBean = null;
                }
            }

            @Override
            public void onFailure(Call<UserLoginBean> call, Throwable t) {
                serverUserBean = null;
            }
        };
        return serverUserBean;

    }

    //按钮点击事件
    @OnClick({R.id.ll_collect, R.id.ll_coupons, R.id.ll_setting, R.id.ll_order, R.id.me_ll_userinfo})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_collect:

                startActivity(new Intent(context, CollectActivity.class));

                break;
            //优惠券
            case R.id.ll_coupons:
                startActivity(new Intent(context, PlayVideoActivity.class));
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

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
