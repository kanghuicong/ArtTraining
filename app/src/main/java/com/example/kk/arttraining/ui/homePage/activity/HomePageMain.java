package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.service.LocationService;
import com.example.kk.arttraining.MyApplication;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.HeadNews;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.bean.parsebean.TecherList;
import com.example.kk.arttraining.custom.view.HorizontalListView;
import com.example.kk.arttraining.custom.view.InnerView;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.homePage.adapter.AuthorityAdapter;
import com.example.kk.arttraining.ui.homePage.function.homepage.AuthorityData;
import com.example.kk.arttraining.ui.homePage.function.homepage.DynamicData;
import com.example.kk.arttraining.ui.homePage.function.homepage.FindTitle;
import com.example.kk.arttraining.ui.homePage.function.homepage.Headlines;
import com.example.kk.arttraining.ui.homePage.function.homepage.Shuffling;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
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
public class HomePageMain extends Fragment implements IHomePageMain {

    View view_institution, view_teacher, view_test, view_performance;
    @InjectView(R.id.tv_homepage_address)
    TextView tvHomepageAddress;
    @InjectView(R.id.lv_authority)
    HorizontalListView lvAuthority;
    @InjectView(R.id.lv_homepage_dynamic)
    MyListView lvHomepageDynamic;
    @InjectView(R.id.vp_img)
    InnerView vpImg;

    ExecutorService mThreadService;
    @InjectView(R.id.iv_homepage_posting)
    ImageView ivHomepagePosting;
    private LocationService locationService;
    Activity activity;
    View view_homepage;
    Headlines headlines;

    private String error_code;
    private Boolean HEADNEWS_FLAG = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        activity = getActivity();
        if (view_homepage == null) {
            view_homepage = View.inflate(activity, R.layout.homepage_main, null);
            ButterKnife.inject(this, view_homepage);


            mThreadService = Executors.newFixedThreadPool(1);
            Shuffling.initShuffling(vpImg, activity);//轮播
            //获取头条
            headlines = new Headlines(this);
            headlines.getHeadNews("");

            DynamicData.getDynamicData(lvHomepageDynamic, activity, this);//listView数据
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

    @OnClick({R.id.ll_homepage_search, R.id.tv_homepage_address, R.id.iv_homepage_posting, R.id.layout_theme_institution, R.id.layout_theme_teacher, R.id.layout_theme_test, R.id.layout_theme_performance})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_homepage_search:
                UIUtil.IntentActivity(activity, new SearchMain());
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
        FindTitle.findTitle(FindTitle.findView(view_homepage, R.id.layout_authority_title), activity, "测评权威", R.mipmap.add_more, "authority");//为测评权威添加标题
        AuthorityData.getAuthorityData(lvAuthority, activity, this);//获取测评权威数据
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
        if (HEADNEWS_FLAG)
            Headlines.stopEffect();
    }

    @Override
    public void onResume() {
        super.onResume();
        vpImg.startAutoScroll();
        if (HEADNEWS_FLAG)
            Headlines.startEffect();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void getHeadNews(List<HeadNews> headNewsList) {
        HEADNEWS_FLAG = true;
        Headlines.initHeadlines(view_homepage, activity, headNewsList);//头条动画
    }

    @Override
    public void OnFailure(String error_code) {
        this.error_code = error_code;
        UIUtil.showLog("homeMain_error_code", error_code);
        mHandler.sendEmptyMessage(0);
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
}
