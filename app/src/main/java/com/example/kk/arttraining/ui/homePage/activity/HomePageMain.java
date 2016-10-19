package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.ShufflingEntity;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicAdapter;
import com.example.kk.arttraining.ui.homePage.function.homepage.DynamicItemClick;
import com.example.kk.arttraining.ui.homePage.function.homepage.FindTitle;
import com.example.kk.arttraining.ui.homePage.function.homepage.HomepageViewPager;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/10/17.
 * QQ邮箱:515849594@qq.com
 */
public class HomePageMain extends Activity {
    @InjectView(R.id.ll_homepage_search)
    LinearLayout llHomepageSearch;
    @InjectView(R.id.iv_homepage_message)
    ImageView ivHomepageMessage;
    @InjectView(R.id.lv_homepage_dynamic)
    ListView lvHomepageDynamic;
    @InjectView(R.id.vp_image)
    ViewPager vpImage;
    @InjectView(R.id.ll_homepage_container)
    LinearLayout llHomepageContainer;
    @InjectView(R.id.tv_homepage_address)
    TextView tvHomepageAddress;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_main);
        ButterKnife.inject(this);

        FindTitle.findTitle(FindView(R.id.layout_authority_title), "测评权威");//为测评权威添加标题
        FindTitle.findTitle(FindView(R.id.layout_dynamic_title), "精选动态");//为精选动态添加标题

//        getShuffling();//轮播
        initListview();//listView操作

    }

    @OnClick({R.id.ll_homepage_search, R.id.iv_homepage_message,R.id.tv_homepage_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_homepage_search:
                UIUtil.IntentActivity(this, new SearchMain());
                break;
            case R.id.iv_homepage_message:

                break;
            case R.id.tv_homepage_address:
                UIUtil.IntentActivity(this, new ChoseProvinceMain(new ChoseProvinceMain.GetProvince() {
                    @Override
                    public String getprovince(String provice) {
                        tvHomepageAddress.setText(provice);
                        return null;
                    }
                }));
                break;
        }
    }

    private View FindView(int id) {
        View view = (View) findViewById(id);
        return view;
    }

    //轮播
    private void getShuffling() {
        List<ShufflingEntity> list = new ArrayList<ShufflingEntity>();

        List<ImageView> imgList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ShufflingEntity entity = list.get(i);
            ImageView img = new ImageView(this);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(this).load(entity.getLunbo_image()).into(img);
            imgList.add(img);
        }
        HomepageViewPager.initViewpager(vpImage, this, imgList, llHomepageContainer);
    }

    //listView操作
    private void initListview() {
        DynamicAdapter dynamicadapter = new DynamicAdapter(getApplicationContext());
        lvHomepageDynamic.setAdapter(dynamicadapter);
        lvHomepageDynamic.setSelector(new ColorDrawable());//去掉点击时背景颜色
        lvHomepageDynamic.setOnItemClickListener(new DynamicItemClick(this));//Item点击事件
    }
}
