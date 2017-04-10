package com.example.kk.arttraining.ui.me.view;

import android.app.Activity;
import android.os.Bundle;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.function.refresh.IRefresh;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullToRefreshLayout;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullableListView;
import com.example.kk.arttraining.ui.homePage.function.refresh.RefreshData;
import com.example.kk.arttraining.ui.homePage.function.refresh.RefreshUtil;
import com.example.kk.arttraining.ui.me.adapter.CloudContentAdapter;
import com.example.kk.arttraining.ui.me.bean.CloudContentBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.TitleBack;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;

/**
 * Created by kanghuicong on 2017/3/15.
 * QQ邮箱:515849594@qq.com
 */
public class CloudContentActivity extends Activity implements PullToRefreshLayout.OnRefreshListener,IRefresh,RefreshUtil.IRefreshUtil {
    @InjectView(R.id.lv_cloud_content)
    PullableListView lvCloudContent;
    @InjectView(R.id.refresh_view)
    PullToRefreshLayout refreshView;

    RefreshData refreshData;
    CloudContentAdapter cloudContentAdapter;
    List<CloudContentBean> cloudBeanList = new ArrayList<CloudContentBean>();
    RefreshUtil refreshUtil;
    HashMap<String, Object> map = new HashMap<String, Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_cloud_content);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this, "云币明细");

        refreshView.setOnRefreshListener(this);
        refreshUtil = new RefreshUtil(this, lvCloudContent, refreshView, cloudBeanList, this);

        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);

        refreshData = new RefreshData(this);
        refreshData.refreshData(HttpRequest.getCloudApi().QueryDetailCloud(map));

    }

    //下拉刷新
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        refreshUtil.onRefresh();
    }
    //上拉加载
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {refreshUtil.onLoadMore();}
    //下拉刷新数据请求
    @Override
    public void refreshData() {
        if (map.containsKey("self")) map.remove("self");
        refreshData.refreshData(HttpRequest.getCloudApi().QueryDetailCloud(map));
    }
    //上拉加载数据请求
    @Override
    public void loadData() {
        map.put("self", cloudContentAdapter.getSelf());
        refreshData.loadData(HttpRequest.getCloudApi().QueryDetailCloud(map));
    }
    //刷新成功
    @Override
    public void refreshSuccess(List list) {
        refreshUtil.refreshSuccess(list);
    }
    //上拉加载成功
    @Override
    public void loadSuccess(List list) {
        refreshUtil.loadSuccess(list);
    }

    //数据请求失败
    @Override
    public void onFailure(String error_code, String error_msg) {refreshUtil.onFailure(error_code, error_msg);}
    @Override
    public void newAdapter() {
        cloudContentAdapter = new CloudContentAdapter(this, cloudBeanList);
        lvCloudContent.setAdapter(cloudContentAdapter);
    }

    @Override
    public void notifyAdapter() {
        cloudContentAdapter.changeCount(cloudBeanList.size());
        cloudContentAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        refreshData.cancelSubscription();
    }
}
