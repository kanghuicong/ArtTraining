package com.example.kk.arttraining.ui.me.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.modelbean.GroupBean;
import com.example.kk.arttraining.custom.view.BottomPullSwipeRefreshLayout;
import com.example.kk.arttraining.ui.me.adapter.MyGroupAdapter;
import com.example.kk.arttraining.ui.me.presenter.MyGroupPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.utils.TitleBack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/11/10 19:05
 * 说明:我的小组
 */
public class MyGroupActivity extends Activity implements IMyGroupActivity, SwipeRefreshLayout.OnRefreshListener, BottomPullSwipeRefreshLayout.OnLoadListener {

    @InjectView(R.id.lv_mygroup)
    ListView lv_mygroup;

    private MyGroupPresenter presenter;
    private BottomPullSwipeRefreshLayout swipeRefreshLayout;
    private int self_id = 0;
    private Dialog dialog;
    private MyGroupAdapter myGroupAdapter;
    private List<GroupBean> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_mygroup_activity);
        init();
    }

    //初始化
    @Override
    public void init() {
        TitleBack.TitleBackActivity(this, "我的小组");
        presenter = new MyGroupPresenter(this);
        dialog = DialogUtils.createLoadingDialog(this, "");

        swipeRefreshLayout = new BottomPullSwipeRefreshLayout(getApplicationContext());
        swipeRefreshLayout = (BottomPullSwipeRefreshLayout) findViewById(R.id.my_group_swipe);
        swipeRefreshLayout.setColorSchemeColors(android.graphics.Color.parseColor("#87CEFA"));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setOnLoadListener(this);
        swipeRefreshLayout.autoRefresh();
    }

    //下拉刷新
    @Override
    public void RefreshData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        presenter.getRefreshData(map);

    }

    //上拉加载
    @Override
    public void LoadData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("self_id", self_id);
        presenter.getRefreshData(map);
    }

    //刷新成功
    @Override
    public void SuccessRefresh(List<GroupBean> groupBeanList) {
        dataList = groupBeanList;
        myGroupAdapter = new MyGroupAdapter(this, dataList);
        swipeRefreshLayout.setRefreshing(false);
    }

    //加载成功
    @Override
    public void SuccessLoad(List<GroupBean> groupBeanList) {
        dataList.addAll(groupBeanList);
        myGroupAdapter.RefreshCount(dataList.size());
        myGroupAdapter.notifyDataSetChanged();
    }

    //刷新失败
    @Override
    public void FailureRefresh(String error_code) {
        swipeRefreshLayout.setRefreshing(false);
    }

    //加载失败
    @Override
    public void FailureLoad(String error_code) {
        swipeRefreshLayout.setLoading(false);
    }


    @Override
    public void ShowLoading() {
        dialog.show();
    }

    @Override
    public void HideLoading() {
        dialog.dismiss();
    }

    //上拉加载
    @Override
    public void onLoad() {
        self_id++;
        LoadData();
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        RefreshData();
        self_id = 0;
    }
}
