package com.example.kk.arttraining.ui.me.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullToRefreshLayout;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullableListView;
import com.example.kk.arttraining.ui.homePage.function.refresh.RefreshUtil;
import com.example.kk.arttraining.ui.me.adapter.CloudContentAdapter;
import com.example.kk.arttraining.ui.me.bean.CloudContentBean;
import com.example.kk.arttraining.ui.me.presenter.CloudContentData;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2017/3/15.
 * QQ邮箱:515849594@qq.com
 */
public class CloudContentActivity extends Activity implements PullToRefreshLayout.OnRefreshListener,CloudContentData.ICloudContent,RefreshUtil.IRefresh {
    @InjectView(R.id.lv_cloud_content)
    PullableListView lvCloudContent;
    @InjectView(R.id.refresh_view)
    PullToRefreshLayout refreshView;

    CloudContentData cloudContentData;
    CloudContentAdapter cloudContentAdapter;
    List<CloudContentBean> cloudBeanList = new ArrayList<CloudContentBean>();
    RefreshUtil refreshUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_cloud_content);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this, "云币明细");

        refreshView.setOnRefreshListener(this);
        refreshUtil = new RefreshUtil(this, lvCloudContent, refreshView, cloudBeanList, this);

        cloudContentData = new CloudContentData(this);
        cloudContentData.refreshData();
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
    public void refreshData() {cloudContentData.refreshData();}
    //上拉加载数据请求
    @Override
    public void loadData() {cloudContentData.loadData(cloudContentAdapter.getSelf());}
    //刷新成功
    @Override
    public void refreshSuccess(List<CloudContentBean> CloudBeanList) {refreshUtil.refreshSuccess(CloudBeanList);}
    //上拉加载成功
    @Override
    public void loadSuccess(List<CloudContentBean> CloudBeanList) {refreshUtil.loadSuccess(CloudBeanList);}
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
        cloudContentData.cancelSubscription();
    }
}
