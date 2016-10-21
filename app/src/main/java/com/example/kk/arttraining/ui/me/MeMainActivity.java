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
import com.example.kk.arttraining.bean.testBean;
import com.example.kk.arttraining.pay.wxapi.WXPayUtils;
import com.example.kk.arttraining.playvideo.activity.PlayVideoActivity;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.GlideCircleTransform;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.PlayAudioUtil;
import com.example.kk.arttraining.utils.UIUtil;
import com.example.kk.arttraining.utils.UploadUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

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
    @InjectView(R.id.collect_count)
    TextView collect_count;
    @InjectView(R.id.coupons_count)
    TextView coupons_count;
    @InjectView(R.id.order_count)
    TextView order_count;
//    @InjectView(R.id.user_name)
//    TextView user_name;
    @InjectView(R.id.user_header)
    ImageView user_header;

    @InjectView(R.id.ll_collect)
    LinearLayout ll_collect;
    @InjectView(R.id.ll_order)
    LinearLayout ll_order;
    @InjectView(R.id.ll_coupons)
    LinearLayout ll_coupons;
    @InjectView(R.id.ll_feedback)
    LinearLayout ll_feedback;
    @InjectView(R.id.ll_setting)
    LinearLayout ll_setting;
    Context context;

    private String user_id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_main);
        context = getApplicationContext();
        init();
        upload();
    }

    @Override
    public void init() {
        ButterKnife.inject(this);

        ll_collect.setOnClickListener(this);
        ll_coupons.setOnClickListener(this);
        ll_feedback.setOnClickListener(this);
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
            case R.id.ll_feedback:
                startActivity(new Intent(context, FeedbackActivity.class));
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

    //获取用户信息
    private void getUserInfo() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("flag", "lerun");
        map.put("index", "0");
        Callback callback = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.body() != null) {

                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        };

//        Call<ResponseObject> call = HttpRequest.getApiService().userinfo();
//        call.enqueue(callback);

    }

    //获取订单 优惠券 收藏数量
    private void getStatisticData() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("flag", "test");
        map.put("index","0");

        Callback<testBean> callback = new Callback<testBean>() {
            @Override
            public void onResponse(Call<testBean> call, Response<testBean> response) {
                UIUtil.showLog("result",response.body().toString()+"");
                testBean testbean=response.body();
                List<testBean.UserBean> list=testbean.getDatas();



                for(int i=0;i<list.size();i++){
                    testBean.UserBean userBean=list.get(i);
                    UIUtil.showLog("name",userBean.getUser_name()+"");
                }
            }

            @Override
            public void onFailure(Call<testBean> call, Throwable t) {

            }
        };

        Call<testBean> call = HttpRequest.getUserApi().test(map);
        call.enqueue(callback);
    }


    void upload() {

        Log.i("url", Config.BASE_URL);
        String filepath = "www.baidu.com";
        File file = new File(filepath);


        Callback callback = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.i("onResponse", "--------------->");
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.i("onFailure", "--------------->");
            }
        };

        UploadUtils.uploadFile(file, callback);


    }
}
