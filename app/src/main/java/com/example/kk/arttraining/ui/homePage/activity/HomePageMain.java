package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.AdvertisementEntity;
import com.example.kk.arttraining.bean.DynamicContentEntity;
import com.example.kk.arttraining.bean.ShufflingEntity;
import com.example.kk.arttraining.bean.TopicEntity;
import com.example.kk.arttraining.ui.homePage.adapter.AuthorityAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicAdapter;
import com.example.kk.arttraining.ui.homePage.function.homepage.DynamicItemClick;
import com.example.kk.arttraining.ui.homePage.function.homepage.FindTitle;
import com.example.kk.arttraining.ui.homePage.function.homepage.HomepageViewPager;
import com.example.kk.arttraining.ui.homePage.function.homepage.HorizontalListView;
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
    @InjectView(R.id.lv_homepage_dynamic)
    ListView lvHomepageDynamic;
    @InjectView(R.id.vp_image)
    ViewPager vpImage;
    @InjectView(R.id.ll_homepage_container)
    LinearLayout llHomepageContainer;
    @InjectView(R.id.tv_homepage_address)
    TextView tvHomepageAddress;

    View view_institution, view_teacher, view_test, view_performance;
    List<DynamicContentEntity> dynamicList = new ArrayList<DynamicContentEntity>();
    List<AdvertisementEntity> advertisementList = new ArrayList<AdvertisementEntity>();
    List<TopicEntity> topicList = new ArrayList<TopicEntity>();
    @InjectView(R.id.lv_authority)
    HorizontalListView lvAuthority;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_main);
        ButterKnife.inject(this);

        FindTitle.findTitle(FindView(R.id.layout_authority_title), "测评权威");//为测评权威添加标题
        FindTitle.findTitle(FindView(R.id.layout_dynamic_title), "精选动态");//为精选动态添加标题

        AuthorityAdapter authorityAdapter = new AuthorityAdapter(this);
        lvAuthority.setAdapter(authorityAdapter);
//        getShuffling();//轮播
        initListview();//listView操作
        initTheme();
    }

    @OnClick({R.id.ll_homepage_search, R.id.tv_homepage_address, R.id.layout_theme_institution, R.id.layout_theme_teacher, R.id.layout_theme_test, R.id.layout_theme_performance})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_homepage_search:
                UIUtil.IntentActivity(this, new SearchMain());
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

        for (int i = 0; i < 5; i++) {
            DynamicContentEntity dynamicMolder = new DynamicContentEntity();
            dynamicMolder.setContent("dynamic" + i);
            dynamicMolder.setLike_state("0");
            dynamicList.add(dynamicMolder);
        }

        AdvertisementEntity advertisementMolder = new AdvertisementEntity();
        advertisementMolder.setImage("123");
        advertisementList.add(advertisementMolder);


        for (int i = 0; i < 3; i++) {
            TopicEntity topicMolder = new TopicEntity();
            topicMolder.setPic("topic" + i);
            topicList.add(topicMolder);
        }

        DynamicAdapter dynamicadapter = new DynamicAdapter(this, dynamicList, advertisementList, topicList);
        lvHomepageDynamic.setAdapter(dynamicadapter);
        lvHomepageDynamic.setOnItemClickListener(new DynamicItemClick(this));//Item点击事件
    }

    private void initTheme() {
        view_institution = FindView(R.id.layout_theme_institution);
        TextView tv_institution = FindText(view_institution);
        initImage(R.mipmap.rb_homepage_checked, tv_institution, "机构");

        view_teacher = FindView(R.id.layout_theme_teacher);
        TextView tv_teacher = FindText(view_teacher);
        initImage(R.mipmap.rb_valuation_checked, tv_teacher, "名师");

        view_test = FindView(R.id.layout_theme_test);
        TextView tv_test = FindText(view_test);
        initImage(R.mipmap.rb_me_checked, tv_test, "艺考");

        view_performance = FindView(R.id.layout_theme_performance);
        TextView tv_performance = FindText(view_performance);
        initImage(R.mipmap.rb_discover_checked, tv_performance, "商演");
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
}
