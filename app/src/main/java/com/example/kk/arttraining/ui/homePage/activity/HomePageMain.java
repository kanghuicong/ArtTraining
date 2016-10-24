package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
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
import com.example.kk.arttraining.ui.homePage.function.homepage.DynamicItemClick;
import com.example.kk.arttraining.ui.homePage.function.homepage.FindTitle;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.JsonTools;
import com.example.kk.arttraining.utils.UIUtil;

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
    @InjectView(R.id.tv_headlines)
    TipView tvHeadlines;
    @InjectView(R.id.vp_img)
    InnerView vpImg;
    @InjectView(R.id.tv_homepage_address)
    TextView tvHomepageAddress;

    View view_institution, view_teacher, view_test, view_performance;
    List<String> tips = new ArrayList<>();

    private LocationService locationService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_main);
        ButterKnife.inject(this);

        initHeadlines();//头条
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

    //轮播
    private void initShuffling() {
        vpImg.startAutoScroll();
        List<ImageView> imgList = new ArrayList<ImageView>();
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

    //头条
    private void initHeadlines() {
        tips = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            tips.add("艺培达人" + i);
        }
        tvHeadlines.setTipList(tips);
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
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                tvHomepageAddress.setText(location.getCity());
                if (Config.CITY.equals("")) {
                    Config.CITY = tvHomepageAddress.getText().toString();
                }else {
                    if (!Config.CITY.equals(tvHomepageAddress.getText().toString())){
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
        // TODO Auto-generated method stub
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
        // 停止图片轮播
        vpImg.stopAutoScroll();
        super.onPause();
    }

    @Override
    protected void onResume() {
        // 开启图片轮播
        vpImg.startAutoScroll();
        super.onResume();
    }
}
