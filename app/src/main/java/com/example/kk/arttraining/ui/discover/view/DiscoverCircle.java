package com.example.kk.arttraining.ui.discover.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.GroupBean;
import com.example.kk.arttraining.bean.parsebean.GroupListMyBean;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.discover.adapter.GroupAdapter;
import com.example.kk.arttraining.ui.discover.function.CircleGroupData;
import com.example.kk.arttraining.ui.discover.prot.IGroup;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicAdapter;
import com.example.kk.arttraining.ui.homePage.function.homepage.DynamicData;
import com.example.kk.arttraining.ui.homePage.function.homepage.FindTitle;
import com.example.kk.arttraining.ui.homePage.prot.IHomePageMain;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/11/9.
 * QQ邮箱:515849594@qq.com
 */
public class DiscoverCircle extends Activity implements IGroup{
    @InjectView(R.id.lv_circle_myGroup)
    MyListView lvCircleMyGroup;
    @InjectView(R.id.lv_circle_recommendGroup)
    MyListView lvCircleRecommendGroup;
    @InjectView(R.id.lv_circle_dynamic)
    MyListView lvCircleDynamic;

    GroupAdapter myGroupAdapter;
    GroupAdapter recommendGroupAdapter;
    DynamicAdapter dynamicAdapter;
    CircleGroupData circleGroupData = new CircleGroupData(this);
    View viewMyGroup,viewRecommendGroup,viewDynamic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_circle);
        ButterKnife.inject(this);

        circleGroupData.getMyCircleGroupData();//获取我的小组数据
        circleGroupData.getRecommendCircleGroupData();//获取推荐小组数据
        circleGroupData.getGroupDynamicData();//获取动态列表


        viewMyGroup = (View)findViewById(R.id.title_my_group) ;
        viewRecommendGroup = (View)findViewById(R.id.title_recommend_group) ;
        viewDynamic = (View)findViewById(R.id.title_dynamic_group) ;

        FindTitle.findTitle(viewMyGroup,this,"我的小组",R.mipmap.arrow_right_topic,"my_group");
        FindTitle.findTitle(viewRecommendGroup,this,"推荐小组",R.mipmap.arrow_right_topic,"recommend_group");
        FindTitle.findTitle(viewDynamic,this,"动态",R.mipmap.arrow_right_topic,"dynamic_group");

    }

    @Override
    public void getMyGroupData(List<GroupBean> groupBeanList) {
        //我的小组Adapter
        myGroupAdapter = new GroupAdapter(this,groupBeanList);
        lvCircleMyGroup.setAdapter(myGroupAdapter);
    }

    @Override
    public void getRecommendCircleGroupData(List<GroupBean> groupBeanList) {
        //推荐小组Adapter
        recommendGroupAdapter = new GroupAdapter(this,groupBeanList);
        lvCircleRecommendGroup.setAdapter(recommendGroupAdapter);
    }

    @Override
    public void getGroupDynamic(List<Map<String, Object>> mapList) {
        //获取动态列表
        dynamicAdapter = new DynamicAdapter(this, mapList);
        lvCircleDynamic.setAdapter(dynamicAdapter);
    }

    @Override
    public void OnFailure(String error_code) {

    }
}