package com.example.kk.arttraining.ui.me.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.GroupBean;
import com.example.kk.arttraining.custom.view.BottomPullSwipeRefreshLayout;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicAdapter;
import com.example.kk.arttraining.ui.homePage.function.homepage.DynamicItemClick;
import com.example.kk.arttraining.ui.me.presenter.MyBBSPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：wschenyongyin on 2016/11/14 12:37
 * 说明:我的帖子
 */
public class MyBBS extends BaseActivity implements IMyBBS, SwipeRefreshLayout.OnRefreshListener, BottomPullSwipeRefreshLayout.OnLoadListener {
    private ListView lv_myBBs;
    private List<Map<String, Object>> mapListData;
    private DynamicAdapter dynamicAdapter;
    private int self_id = 0;
    private MyBBSPresenter myBBSPresenter;
    private BottomPullSwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_mygroup_activity);
        init();

    }

    @Override
    public void init() {
        TitleBack.TitleBackActivity(this, "我的帖子");
        lv_myBBs = (ListView) findViewById(R.id.lv_mygroup);

        myBBSPresenter = new MyBBSPresenter(this);
        swipeRefreshLayout = new BottomPullSwipeRefreshLayout(getApplicationContext());
        swipeRefreshLayout = (BottomPullSwipeRefreshLayout) findViewById(R.id.my_group_swipe);
        swipeRefreshLayout.setColorSchemeColors(android.graphics.Color.parseColor("#87CEFA"));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setOnLoadListener(this);
        swipeRefreshLayout.autoRefresh();
    }


    @Override
    public void onClick(View v) {

    }


    @Override
    public void RefreshData() {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        myBBSPresenter.RefreshData(map);

    }

    @Override
    public void LoadData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("self", self_id);
        myBBSPresenter.LoadData(map);

    }

    @Override
    public void SuccessRefresh(List<Map<String, Object>> mapList) {
        swipeRefreshLayout.setRefreshing(false);
        mapListData = mapList;
        dynamicAdapter = new DynamicAdapter(this, mapListData);
        lv_myBBs.setAdapter(dynamicAdapter);
        lv_myBBs.setOnItemClickListener(new DynamicItemClick(this,mapListData)) ;
    }

    @Override
    public void SuccessLoad(List<Map<String, Object>> mapList) {
        swipeRefreshLayout.setLoading(false);
        mapListData.addAll(mapList);
        dynamicAdapter.changeCount(mapListData.size());
        dynamicAdapter.notifyDataSetChanged();

    }

    @Override
    public void OnFailure(String error_code) {
        swipeRefreshLayout.setLoading(false);
        swipeRefreshLayout.setRefreshing(false);
        switch (error_code){
            case "20028":
                UIUtil.ToastshowShort(this,"用户身份信息失效，请重新登陆！");
                startActivity(new Intent(this,UserLoginActivity.class));
            break;
            case Config.Connection_Failure:
                UIUtil.ToastshowShort(this,getResources().getString(R.string.connection_timeout));
                break;
        }

    }

    @Override
    public void onLoad() {
        self_id++;
        LoadData();
    }

    @Override
    public void onRefresh() {
        RefreshData();
        self_id = 0;
    }



}
