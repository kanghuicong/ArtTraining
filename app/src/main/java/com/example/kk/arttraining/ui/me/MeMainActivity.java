package com.example.kk.arttraining.ui.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.ResponseObject;
import com.example.kk.arttraining.bean.UserInfoBean;
import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.bean.testBean;
import com.example.kk.arttraining.dao.UserDao;
import com.example.kk.arttraining.dao.UserDaoImpl;
import com.example.kk.arttraining.pay.wxapi.WXPayUtils;
import com.example.kk.arttraining.playvideo.activity.PlayVideoActivity;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.GlideCircleTransform;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.PlayAudioUtil;
import com.example.kk.arttraining.utils.PreferencesUtils;
import com.example.kk.arttraining.utils.UIUtil;
import com.example.kk.arttraining.utils.UploadUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/9/19.
 * QQ邮箱:515849594@qq.com
 */
public class MeMainActivity extends BaseActivity {
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

    @InjectView(R.id.collect_count)
    TextView collect_count;
    @InjectView(R.id.coupons_count)
    TextView coupons_count;
    @InjectView(R.id.order_count)
    TextView order_count;
    @InjectView(R.id.user_header)
    ImageView user_header;

    @InjectView(R.id.ll_collect)
    LinearLayout ll_collect;
    @InjectView(R.id.ll_order)
    LinearLayout ll_order;
    @InjectView(R.id.ll_coupons)
    LinearLayout ll_coupons;
    @InjectView(R.id.ll_setting)
    LinearLayout ll_setting;
    @InjectView(R.id.ll_comments)
    LinearLayout ll_comments;
    @InjectView(R.id.ll_certificate)
    LinearLayout ll_certificate;

    Context context;

    private String user_id;
    private UserDao userDao;
    private String user_code;
    private UserLoginBean userInfoBean;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_main);
        context = getApplicationContext();
        init();
    }

    @Override
    public void init() {
        ButterKnife.inject(this);

        ll_collect.setOnClickListener(this);
        ll_coupons.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        ll_order.setOnClickListener(this);
        user_header.setOnClickListener(this);

        Glide.with(context).load(Config.USER_HEADER_Url).transform(new GlideCircleTransform(context)).error(R.mipmap.default_user_header).into(user_header);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_collect:
                WXPayUtils utils = new WXPayUtils(MeMainActivity.this, "http://121.43.172.150:8080/LeRun/servlet/LeRunServlet");
                utils.pay("测试", "1", "sdhi2837816238263");

                break;
            case R.id.ll_coupons:
                startActivity(new Intent(context, PlayVideoActivity.class));
                break;
            case R.id.ll_setting:
                startActivity(new Intent(context, SettingActivity.class));
                break;
            case R.id.ll_order:
                getStatisticData();
                break;
            case R.id.user_header:
                startActivity(new Intent(context, AboutActivity.class));
                break;

        }
    }

    //从本地数据库读取用户数据
    private UserLoginBean getLocalUserInfo() {
        userDao = new UserDaoImpl(getApplicationContext());
        user_code = PreferencesUtils.get(getApplicationContext(), "user_code", "").toString();
        userInfoBean = userDao.QueryAll(user_code);
        //判断本地数据库是否有用户信息
        if (userInfoBean == null) {
            // TODO: 2016/10/21 请求网络从服务器获取数据
        }
        return userInfoBean;

    }

    private UserLoginBean getServerUserInfo() {
        UserLoginBean userBean = null;
        Map<String, String> map = new HashMap<String, String>();
        return userBean;

    }


    //获取订单 优惠券 收藏数量
    private void getStatisticData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("flag", "test");
        map.put("index", "0");

        Callback<UserLoginBean> callback = new Callback<UserLoginBean>() {
            @Override
            public void onResponse(Call<UserLoginBean> call, Response<UserLoginBean> response) {
                UserLoginBean userLoginBean = response.body();

            }

            @Override
            public void onFailure(Call<UserLoginBean> call, Throwable t) {

            }
        };

        Call<UserLoginBean> call = HttpRequest.getUserApi().Login(map);
        call.enqueue(callback);
    }


}
