package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.service.LocationService;
import com.bumptech.glide.Glide;
import com.example.kk.arttraining.MyApplication;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.parsebean.StatusesBean;
import com.example.kk.arttraining.custom.view.HorizontalListView;
import com.example.kk.arttraining.custom.view.InnerView;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.playvideo.activity.VideoListLayout;
import com.example.kk.arttraining.ui.homePage.adapter.AuthorityAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicAdapter;
import com.example.kk.arttraining.ui.homePage.function.homepage.DynamicData;
import com.example.kk.arttraining.ui.homePage.function.homepage.DynamicItemClick;
import com.example.kk.arttraining.ui.homePage.function.homepage.FindTitle;
import com.example.kk.arttraining.ui.homePage.function.homepage.Shuffling;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.JsonTools;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.jaeger.library.StatusBarUtil;

/**
 * Created by kanghuicong on 2016/10/17.
 * QQ邮箱:515849594@qq.com
 */
public class HomePageMain extends Fragment {

    View view_institution, view_teacher, view_test, view_performance;
    @InjectView(R.id.tv_homepage_address)
    TextView tvHomepageAddress;
    @InjectView(R.id.lv_authority)
    HorizontalListView lvAuthority;
    @InjectView(R.id.lv_homepage_dynamic)
    MyListView lvHomepageDynamic;
    @InjectView(R.id.vp_img)
    InnerView vpImg;

    private Animation anim_in, anim_out;
    private LinearLayout llContainer;
    private Handler mHandler;
    private boolean runFlag = true;
    private int index = 0;

    ExecutorService mThreadService;
    private LocationService locationService;
    Activity activity;
    View view_homepage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        activity = getActivity();
        if (view_homepage == null) {
            view_homepage = View.inflate(activity, R.layout.homepage_main, null);
            ButterKnife.inject(this, view_homepage);
            mThreadService = Executors.newFixedThreadPool(2);
            initHeadlines();//头条数据及View
            Shuffling.initShuffling(vpImg, activity);//轮播
            DynamicData.getDynamicData(lvHomepageDynamic, activity);
            initAuthority();//测评权威
            initTheme();//四个Theme
//        initShuffling();//轮播
//        getDynamicData();//listView
        }
        ViewGroup parent = (ViewGroup) view_homepage.getParent();
        if (parent != null) {
            parent.removeView(view_homepage);
        }
        return view_homepage;
    }

    @OnClick({R.id.ll_homepage_search, R.id.tv_homepage_address, R.id.layout_theme_institution, R.id.layout_theme_teacher, R.id.layout_theme_test, R.id.layout_theme_performance})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_homepage_search:
                UIUtil.IntentActivity(activity, new SearchMain());
                break;
            case R.id.tv_homepage_address:
//                UIUtil.IntentActivity(this, new ChooseProvinceMain());
                Intent intent = new Intent(activity,
                        ChooseProvinceMain.class);
                startActivityForResult(intent, 002);

                break;
            case R.id.layout_theme_institution:
                UIUtil.IntentActivity(activity, new ThemeInstitution());
                break;
            case R.id.layout_theme_teacher:
                UIUtil.IntentActivity(activity, new ThemeTeacher());
                break;
            case R.id.layout_theme_test:
                UIUtil.IntentActivity(activity, new ThemeTest());
                break;
            case R.id.layout_theme_performance:
                UIUtil.IntentActivity(activity, new ThemePerformance());
                break;
        }
    }

    //头条
    private void initHeadlines() {
        // TODO Auto-generated method stub
        // 找到装载这个滚动TextView的LinearLayout
        llContainer = (LinearLayout) view_homepage.findViewById(R.id.ll_container);
        anim_in = AnimationUtils.loadAnimation(activity, R.anim.anim_tv_marquee_in);
        anim_out = AnimationUtils.loadAnimation(activity, R.anim.anim_tv_marquee_out);

        final List<String> list = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            list.add("滚动的文字" + i);
        }

        for (int i = 0; i < list.size(); i++) {
            TextView tvTemp = new TextView(activity);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;
            tvTemp.setGravity(Gravity.CENTER);
            tvTemp.setGravity(Gravity.LEFT);
            final String tv = list.get(i);
            tvTemp.setText(list.get(i));
            tvTemp.setSingleLine(true);
            tvTemp.setId(i + 10000);
            tvTemp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIUtil.ToastshowShort(activity, tv);
                }
            });
            llContainer.addView(tvTemp);
        }

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        // 移除
                        TextView tvTemp = (TextView) msg.obj;
                        Log.d("tag", "out->" + tvTemp.getId());
                        tvTemp.startAnimation(anim_out);
                        tvTemp.setVisibility(View.GONE);
                        break;
                    case 1:
                        // 进入
                        TextView tvTemp2 = (TextView) msg.obj;
                        Log.d("tag", "in->" + tvTemp2.getId());
                        tvTemp2.startAnimation(anim_in);
                        tvTemp2.setVisibility(View.VISIBLE);
                        break;
                }
            }
        };
    }

    //头条开始
    private void startEffect() {
        runFlag = true;
        mThreadService.execute(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (runFlag) {
                    try {
                        // 每隔3秒轮换一次
                        Thread.sleep(3000);
                        if (runFlag) {
                            TextView tvTemp = (TextView) llContainer
                                    .getChildAt(index);
                            mHandler.obtainMessage(0, tvTemp).sendToTarget();
                            if (index < llContainer.getChildCount()) {
                                index++;
                                if (index == llContainer.getChildCount()) {
                                    index = 0;
                                }
                                // index+1个动画开始进入动画
                                tvTemp = (TextView) llContainer
                                        .getChildAt(index);
                                mHandler.obtainMessage(1, tvTemp)
                                        .sendToTarget();
                            }
                        }
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        runFlag = false;
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //头条终止
    private void stopEffect() {
        runFlag = false;
    }

    //轮播
    private void initShuffling() {
        vpImg.startAutoScroll();
        final List<ImageView> imgList = new ArrayList<ImageView>();
        for (int i = 0; i < 4; i++) {
            ImageView img = new ImageView(activity);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(this).load("http://pic76.nipic.com/file/20150823/9448607_122042419000_2.jpg").into(img);
            imgList.add(img);
        }

        String[] titles = {"", "", "", ""};
        vpImg.setTitlesAndImages(titles, imgList);
        vpImg.setOnLunBoClickListener(new InnerView.OnLunBoClickListener() {
            @Override
            public void clickLunbo(int position) {
                Toast.makeText(activity, "点击有效，位置为：" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //四个Theme
    private void initTheme() {
        view_institution = FindTitle.findView(view_homepage, R.id.layout_theme_institution);
        TextView tv_institution = FindTitle.findText(view_institution);
        FindTitle.initImage(activity, R.mipmap.view_institution, tv_institution, "机构");

        view_teacher = FindTitle.findView(view_homepage, R.id.layout_theme_teacher);
        TextView tv_teacher = FindTitle.findText(view_teacher);
        FindTitle.initImage(activity, R.mipmap.view_teacher, tv_teacher, "名师");

        view_test = FindTitle.findView(view_homepage, R.id.layout_theme_test);
        TextView tv_test = FindTitle.findText(view_test);
        FindTitle.initImage(activity, R.mipmap.view_test, tv_test, "艺考");

        view_performance = FindTitle.findView(view_homepage, R.id.layout_theme_performance);
        TextView tv_performance = FindTitle.findText(view_performance);
        FindTitle.initImage(activity, R.mipmap.view_performance, tv_performance, "商演");
    }

    //测评权威
    private void initAuthority() {
        FindTitle.findTitle(FindTitle.findView(view_homepage, R.id.layout_authority_title), activity, "测评权威", R.mipmap.add_more, "authority");//为测评权威添加标题

        AuthorityAdapter authorityAdapter = new AuthorityAdapter(activity);
        lvAuthority.setAdapter(authorityAdapter);
    }

    //listView数据
    private void getDynamicData() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", "");
        map.put("uid", Config.User_Id);
        map.put("type", "all");

        Callback<StatusesBean> callback = new Callback<StatusesBean>() {
            @Override
            public void onResponse(Call<StatusesBean> call, Response<StatusesBean> response) {
                StatusesBean statusesBean = response.body();
                if (response.body() != null) {
                    if (statusesBean.getError_code().equals("0")) {
                        List<Map<String, Object>> mapList = JsonTools.ParseStatuses(statusesBean.getStatuses());
                        DynamicAdapter dynamicadapter = new DynamicAdapter(activity, mapList);
                        lvHomepageDynamic.setAdapter(dynamicadapter);
                        lvHomepageDynamic.setOnItemClickListener(new DynamicItemClick(activity));//Item点击事件
                    }
                }
            }

            @Override
            public void onFailure(Call<StatusesBean> call, Throwable t) {
                String data = VideoListLayout.readTextFileFromRawResourceId(activity, R.raw.statuses);
                List<Map<String, Object>> mapList = JsonTools.ParseStatuses(data);
                DynamicAdapter dynamicadapter = new DynamicAdapter(activity, mapList);
                lvHomepageDynamic.setAdapter(dynamicadapter);
                lvHomepageDynamic.setOnItemClickListener(new DynamicItemClick(activity));
            }
        };

        Call<StatusesBean> call = HttpRequest.getStatusesApi().statusesGoodList(map);
        call.enqueue(callback);
    }


    // 定位结果回调
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                tvHomepageAddress.setText(location.getCity());
                if (Config.CITY.equals("")) {
                    Config.CITY = tvHomepageAddress.getText().toString();
                } else {
                    if (!Config.CITY.equals(tvHomepageAddress.getText().toString())) {
                        UIUtil.ToastshowShort(activity, "位置不对哦");
                    }
                }
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                UIUtil.ToastshowShort(activity, "网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                UIUtil.ToastshowShort(activity, "无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        startEffect();
        mThreadService.execute(new Runnable() {
            @Override
            public void run() {
                locationService = ((MyApplication) activity.getApplication()).locationService;
                locationService.registerListener(mListener);
                //注册监听
                int type = activity.getIntent().getIntExtra("from", 0);
                if (type == 0) {
                    locationService.setLocationOption(locationService.getDefaultLocationClientOption());
                } else if (type == 1) {
                    locationService.setLocationOption(locationService.getOption());
                }
                locationService.start();// 定位SDK
            }
        });
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();

    }

    @Override
    public void onPause() {
        super.onPause();
        vpImg.stopAutoScroll();
        Log.i("355onPause", "onPause");
        stopEffect();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 开启图片轮播
        vpImg.startAutoScroll();
        Log.i("355onResume", "onResume");
        startEffect();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 002:
                String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
