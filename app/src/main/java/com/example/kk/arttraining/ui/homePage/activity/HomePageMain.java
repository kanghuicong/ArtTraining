package com.example.kk.arttraining.ui.homePage.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.service.LocationService;
import com.example.kk.arttraining.MyApplication;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.BannerBean;
import com.example.kk.arttraining.bean.HeadNews;
import com.example.kk.arttraining.custom.view.BottomPullSwipeRefreshLayout;
import com.example.kk.arttraining.custom.view.HorizontalListView;
import com.example.kk.arttraining.custom.view.InnerView;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicFailureAdapter;
import com.example.kk.arttraining.ui.homePage.function.homepage.AuthorityData;
import com.example.kk.arttraining.ui.homePage.function.homepage.DynamicData;
import com.example.kk.arttraining.ui.homePage.function.homepage.FindTitle;
import com.example.kk.arttraining.ui.homePage.function.homepage.Headlines;
import com.example.kk.arttraining.ui.homePage.function.homepage.Shuffling;
import com.example.kk.arttraining.ui.homePage.function.homepage.ShufflingData;
import com.example.kk.arttraining.ui.homePage.prot.IAuthority;
import com.example.kk.arttraining.ui.homePage.prot.IHomePageMain;
import com.example.kk.arttraining.ui.homePage.prot.IShuffling;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

//import com.jaeger.library.StatusBarUtil;

/**
 * Created by kanghuicong on 2016/10/17.
 * QQ邮箱:515849594@qq.com
 */
public class HomePageMain extends Fragment implements IHomePageMain, IShuffling, IAuthority,SwipeRefreshLayout.OnRefreshListener, BottomPullSwipeRefreshLayout.OnLoadListener,View.OnClickListener {

    View view_institution, view_teacher, view_test, view_performance;
    @InjectView(R.id.tv_homepage_address)
    TextView tvHomepageAddress;
//    @InjectView(R.id.lv_authority)
    HorizontalListView lvAuthority;
    @InjectView(R.id.lv_homepage_dynamic)
    ListView lvHomepageDynamic;
//    @InjectView(R.id.vp_img)
    InnerView vpImg;

    ExecutorService mThreadService;
    private LocationService locationService;
    List<Map<String, Object>> DynamicList;
    Activity activity;
    View view_homepage,view_header;
    Headlines headlines;
    DynamicData dynamicData;
    ShufflingData shufflingData;
    private String error_code;
    private static final int BAIDU_READ_PHONE_STATE = 100;
    DynamicAdapter dynamicadapter;
    BottomPullSwipeRefreshLayout swipeRefreshLayout;
    int dynamicPosition = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        activity = getActivity();
        if (view_homepage == null) {
            view_homepage = View.inflate(activity, R.layout.homepage_main, null);
            view_header = View.inflate(activity, R.layout.homepage_listview_header, null);
            FindHeaderId();
            ButterKnife.inject(this, view_homepage);
            lvHomepageDynamic.addHeaderView(view_header);
            swipeRefreshLayout = new BottomPullSwipeRefreshLayout(activity.getApplicationContext());
            swipeRefreshLayout = (BottomPullSwipeRefreshLayout) view_homepage.findViewById(R.id.refresh_homepage);
            swipeRefreshLayout.setColorSchemeColors(android.graphics.Color.parseColor("#87CEFA"));
            swipeRefreshLayout.setOnRefreshListener(this);
            swipeRefreshLayout.setOnLoadListener(this);
            swipeRefreshLayout.autoRefresh();

            mThreadService = Executors.newFixedThreadPool(1);

            shufflingData = new ShufflingData(this);
            shufflingData.getShufflingData();//轮播

            headlines = new Headlines(this);
            headlines.getHeadNews("");//头条

            dynamicData = new DynamicData(this);
            dynamicData.getDynamicData();//动态

            initAuthority();//测评权威
            initTheme();//四个Theme
        }
        ViewGroup parent = (ViewGroup) view_homepage.getParent();
        if (parent != null) {
            parent.removeView(view_homepage);
        }
        ButterKnife.inject(this, view_homepage);
        return view_homepage;
    }

    private void FindHeaderId() {
        lvAuthority = (HorizontalListView) view_header.findViewById(R.id.lv_authority);
        vpImg = (InnerView)view_header.findViewById(R.id.vp_img);
        LinearLayout institution = (LinearLayout) view_header.findViewById(R.id.layout_theme_institution);
        LinearLayout teacher = (LinearLayout) view_header.findViewById(R.id.layout_theme_teacher);
        LinearLayout test = (LinearLayout) view_header.findViewById(R.id.layout_theme_test);
        LinearLayout performance = (LinearLayout) view_header.findViewById(R.id.layout_theme_performance);
        institution.setOnClickListener(this);
        teacher.setOnClickListener(this);
        test.setOnClickListener(this);
        performance.setOnClickListener(this);
    }

    @OnClick({R.id.ll_homepage_search, R.id.tv_homepage_address, R.id.iv_homepage_posting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_homepage_search:
                Intent intent = new Intent(activity, SearchMain.class);
                intent.putExtra("type", "homepage");
                activity.startActivity(intent);
                break;
            case R.id.tv_homepage_address:
                UIUtil.IntentActivity(activity, new ChooseProvinceMain());
                break;
            case R.id.iv_homepage_posting:
                UIUtil.IntentActivity(activity, new PostingMain());
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
        FindTitle mFindTitle = new FindTitle(this);
        mFindTitle.findTitle(FindTitle.findView(view_homepage, R.id.layout_authority_title), activity, "测评权威", R.mipmap.add_more, "authority");//为测评权威添加标题
        AuthorityData.getAuthorityData(lvAuthority, activity, this);//获取测评权威数据
    }

    // 定位结果回调
    private BDLocationListener mListener = new BDLocationListener() {

        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                tvHomepageAddress.setText(location.getCity());
                if (Config.CITY.equals("")) {
                    Config.CITY = tvHomepageAddress.getText().toString();
                } else {
                    if (!Config.CITY.equals(location.getCity())) {
                        UIUtil.ToastshowShort(activity, "位置不对哦");
                    }
                }
                locationService.unregisterListener(mListener); //注销掉监听
                locationService.stop(); //停止定位服务
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                UIUtil.ToastshowShort(activity, "网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                UIUtil.ToastshowShort(activity, "无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    Toast.makeText(activity, "获取到权限，作相应处理", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "没有获取到权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mThreadService.execute(new Runnable() {
            @Override
            public void run() {
                locationService = ((MyApplication) activity.getApplication()).locationService;
                locationService.registerListener(mListener);//注册监听
                int type = activity.getIntent().getIntExtra("from", 0);
                if (type == 0) {
                    locationService.setLocationOption(locationService.getDefaultLocationClientOption());
                } else if (type == 1) {
                    locationService.setLocationOption(locationService.getOption());
                    locationService.unregisterListener(mListener); //注销掉监听
                    locationService.stop(); //停止定位服务
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
        Headlines.stopEffect();
    }

    @Override
    public void onResume() {
        super.onResume();
        vpImg.startAutoScroll();
        if(Config.HeadlinesPosition == 1) {
            Headlines.startEffect();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    //获取动态数据
    @Override
    public void getDynamicListData(List<Map<String, Object>> mapList) {
        if (dynamicPosition == 0) {
            DynamicList = mapList;
            dynamicadapter = new DynamicAdapter(activity, DynamicList);
            lvHomepageDynamic.setAdapter(dynamicadapter);
//            lvHomepageDynamic.setOnItemClickListener(new DynamicItemClick(activity));//Item点击事件
            dynamicPosition++;
        }else {
            DynamicList.addAll(mapList) ;
            dynamicadapter.changeCount(DynamicList.size());
            dynamicadapter.notifyDataSetChanged();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    //上拉加载数据
    @Override
    public void loadDynamicListData(List<Map<String, Object>> mapList) {
        DynamicList.addAll(mapList);
        dynamicadapter.notifyDataSetChanged();
    }

    @Override
    public void OnDynamicFailure(String error_code) {
        DynamicFailureAdapter dynamicFailureAdapter = new DynamicFailureAdapter(activity);
        lvHomepageDynamic.setAdapter(dynamicFailureAdapter);
    }

    //头条数据
    @Override
    public void getHeadNews(List<HeadNews> headNewsList) {
        UIUtil.showLog("获取headNewsList数据", headNewsList + "----");
        Headlines.initHeadlines(view_homepage, activity, headNewsList,"yes");//头条动画
        Headlines.startEffect();

    }

    //获取头条数据失败
    @Override
    public void OnHeadNewsFailure(String error_code) {
        List<HeadNews> headNewsList = new ArrayList<HeadNews>();
        Headlines.initHeadlines(view_homepage, activity, headNewsList,"no");//头条获取失败
        if (Config.HeadlinesPosition == 0){
            Headlines.startEffect();
        }
        Config.HeadlinesPosition = 1;
    }

    @Override
    public void getShuffling(List<BannerBean> list) {
        UIUtil.showLog("获取iShuffling数据长度", list.size() + "-----");
        Shuffling.initShuffling(vpImg, activity,list,"yes");//轮播
    }

    @Override
    public void OnShufflingFailure(String failure) {
        List<BannerBean> list = new ArrayList<BannerBean>();
        Shuffling.initShuffling(vpImg, activity,list,"no");//获取轮播失败
    }

    @Override
    public void OnFailure(String error_code) {
        this.error_code = error_code;
        UIUtil.showLog("homeMain_error_code", error_code);
        mHandler.sendEmptyMessage(0);
        swipeRefreshLayout.setRefreshing(false);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String message = "";
            switch (error_code) {
                case Config.Connection_Failure:
                    message = getResources().getString(R.string.connection_failure);
                    break;
            }
            UIUtil.ToastshowShort(activity, message);
        }
    };

    @Override
    public void getAuthorityResult() {
        AuthorityData.getAuthorityData(lvAuthority, activity, this);//刷新测评权威数据
    }

    //上拉加载
    @Override
    public void onLoad() {
        UIUtil.showLog("onLoad","1");
//        dynamicData.loadDynamicData();
    }

    //下拉刷新
    @Override
    public void onRefresh() {

//        shufflingData.getShufflingData();//轮播

        dynamicData.getDynamicData();//动态

        headlines.getHeadNews("");//头条
    }
}
