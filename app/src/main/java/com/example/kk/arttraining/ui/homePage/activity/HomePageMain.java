package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
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
import com.example.kk.arttraining.custom.view.TipView;
import com.example.kk.arttraining.playvideo.activity.VideoListLayout;
import com.example.kk.arttraining.ui.homePage.adapter.AuthorityAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.ViewPagerAdapter;
import com.example.kk.arttraining.ui.homePage.function.homepage.DynamicItemClick;
import com.example.kk.arttraining.ui.homePage.function.homepage.FindTitle;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.JsonTools;
import com.example.kk.arttraining.utils.StatusBarCompat;
import com.example.kk.arttraining.utils.UIUtil;
//import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/10/17.
 * QQ邮箱:515849594@qq.com
 */
public class HomePageMain extends Activity {
    @InjectView(R.id.ll_homepage_search)
    LinearLayout llHomepageSearch;
    @InjectView(R.id.lv_homepage_dynamic)
    MyListView lvHomepageDynamic;
    @InjectView(R.id.lv_authority)
    HorizontalListView lvAuthority;
    @InjectView(R.id.vp_img)
    InnerView vpImg;
    @InjectView(R.id.tv_homepage_address)
    TextView tvHomepageAddress;

    View view_institution, view_teacher, view_test, view_performance;

    private Animation anim_in, anim_out;
    private LinearLayout llContainer;
    private Handler mHandler;
    private boolean runFlag = true;
    private int index = 0;


    private LocationService locationService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_main);
//        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.blue_overlay));
//        StatusBarUtil.setColor(this,this.getResources().getColor(R.color.blue_overlay));
//        StatusBarUtil.setTransparent(this);
        ButterKnife.inject(this);


        initHeadlines();//头条数据及View
        startEffect();//头条动画开始
        initShuffling();//轮播
        initAuthority();//测评权威
        initTheme();//四个Theme

        getDynamicData();//listView
    }


    @OnClick({R.id.ll_homepage_search, R.id.tv_homepage_address, R.id.layout_theme_institution, R.id.layout_theme_teacher, R.id.layout_theme_test, R.id.layout_theme_performance})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_homepage_search:
                UIUtil.IntentActivity(this, new SearchMain());
                break;
            case R.id.tv_homepage_address:
                UIUtil.IntentActivity(this, new ChooseProvinceMain());

                break;
            case R.id.layout_theme_institution:
                UIUtil.IntentActivity(this, new ThemeInstitution());
                break;
            case R.id.layout_theme_teacher:
                UIUtil.IntentActivity(this, new ThemeTeacher());
                break;
            case R.id.layout_theme_test:
                UIUtil.IntentActivity(this, new ThemeTest());
                break;
            case R.id.layout_theme_performance:
                UIUtil.IntentActivity(this, new ThemePerformance());
                break;
        }
    }

    //头条
    private void initHeadlines() {
        // TODO Auto-generated method stub
        // 找到装载这个滚动TextView的LinearLayout
        llContainer = (LinearLayout) findViewById(R.id.ll_container);
        anim_in = AnimationUtils.loadAnimation(this, R.anim.anim_tv_marquee_in);
        anim_out = AnimationUtils.loadAnimation(this, R.anim.anim_tv_marquee_out);

        final List<String> list = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            list.add("滚动的文字" + i);
        }

        for (int i = 0; i < list.size(); i++) {
            TextView tvTemp = new TextView(this);
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
                    UIUtil.ToastshowShort(HomePageMain.this,tv);
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
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (runFlag) {
                    try {
                        // 每隔2秒轮换一次
                        Thread.sleep(3000);
                        // 至于这里还有一个if(runFlag)判断是为什么？大家自己试验下就知道了
                        if (runFlag) {
                            // 获取第index个TextView开始移除动画
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
                        // 如果有异常，那么停止轮换。当然这种情况很难发生
                        runFlag = false;
                        e.printStackTrace();
                    }
                }
            }
        }).start();
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
            ImageView img = new ImageView(this);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(this).load("http://pic76.nipic.com/file/20150823/9448607_122042419000_2.jpg").into(img);
            imgList.add(img);
        }

        String[] titles = {"", "", "", ""};
        // 初始化数据
        vpImg.setTitlesAndImages(titles, imgList);
        // 设置点击事件
        vpImg.setOnLunBoClickListener(new InnerView.OnLunBoClickListener() {

            @Override
            public void clickLunbo(int position) {
                Toast.makeText(HomePageMain.this, "点击有效，位置为：" + position, Toast.LENGTH_SHORT).show();
            }
        });
        // 设置文字的颜色，透明即不可见
        //vpImg.setLlBackgroundAlph(color.transparent);
        // 设置文字的背景，默认半透明，可以设置不可见
        //vpImg.setTvTitleVisibility(View.GONE);
    }

    //四个Theme
    private void initTheme() {
        view_institution = FindView(R.id.layout_theme_institution);
        TextView tv_institution = FindText(view_institution);
        initImage(R.mipmap.view_institution, tv_institution, "机构");

        view_teacher = FindView(R.id.layout_theme_teacher);
        TextView tv_teacher = FindText(view_teacher);
        initImage(R.mipmap.view_teacher, tv_teacher, "名师");

        view_test = FindView(R.id.layout_theme_test);
        TextView tv_test = FindText(view_test);
        initImage(R.mipmap.view_test, tv_test, "艺考");

        view_performance = FindView(R.id.layout_theme_performance);
        TextView tv_performance = FindText(view_performance);
        initImage(R.mipmap.view_performance, tv_performance, "商演");
    }

    //测评权威
    private void initAuthority() {
        FindTitle.findTitle(FindView(R.id.layout_authority_title), this, "测评权威", R.mipmap.add_more, "authority");//为测评权威添加标题

        AuthorityAdapter authorityAdapter = new AuthorityAdapter(this);
        lvAuthority.setAdapter(authorityAdapter);
    }

    //listView数据
    private void getDynamicData() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", "");
        map.put("uid", Config.User_Id);
        map.put("type", "all");

        Log.i("path", Config.BASE_URL + Config.testapi);
        Callback<StatusesBean> callback = new Callback<StatusesBean>() {
            @Override
            public void onResponse(Call<StatusesBean> call, Response<StatusesBean> response) {
                StatusesBean statusesBean = response.body();
                String data = VideoListLayout.readTextFileFromRawResourceId(HomePageMain.this, R.raw.video_list);
                Log.i("response.body", response.body().toString());
                if (response.body() != null) {
                    if (statusesBean.getError_code().equals("0")) {
                        List<Map<String, Object>> mapList = JsonTools.ParseStatuses(statusesBean.getStatuses());
                        DynamicAdapter dynamicadapter = new DynamicAdapter(HomePageMain.this, mapList);
                        lvHomepageDynamic.setAdapter(dynamicadapter);
                        lvHomepageDynamic.setOnItemClickListener(new DynamicItemClick(HomePageMain.this));//Item点击事件
                    }
                }
            }

            @Override
            public void onFailure(Call<StatusesBean> call, Throwable t) {
                Log.i("response.body.ff", "123");
                String data = VideoListLayout.readTextFileFromRawResourceId(HomePageMain.this, R.raw.statuses);
                Log.i("data", data + "123");
                List<Map<String, Object>> mapList = JsonTools.ParseStatuses(data);
                DynamicAdapter dynamicadapter = new DynamicAdapter(HomePageMain.this, mapList);
                lvHomepageDynamic.setAdapter(dynamicadapter);
                lvHomepageDynamic.setOnItemClickListener(new DynamicItemClick(HomePageMain.this));
            }
        };

        Call<StatusesBean> call = HttpRequest.getStatusesApi().statusesGoodList(map);
        call.enqueue(callback);

    }

    private TextView FindText(View view) {
        TextView tv = (TextView) view.findViewById(R.id.tv_theme);
        return tv;
    }

    private View FindView(int id) {
        View view = (View) findViewById(id);
        return view;
    }

    private void initImage(int image, TextView tv, String text) {
        Drawable drawable = getResources().getDrawable(image);
        tv.setText(text);
        tv.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
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
                        UIUtil.ToastshowShort(HomePageMain.this, "位置不对哦");
                    }
                }
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                UIUtil.ToastshowShort(HomePageMain.this, "网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                UIUtil.ToastshowShort(HomePageMain.this, "无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        locationService = ((MyApplication) getApplication()).locationService;
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        locationService.start();// 定位SDK
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopEffect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 开启图片轮播
        vpImg.startAutoScroll();

    }

}
