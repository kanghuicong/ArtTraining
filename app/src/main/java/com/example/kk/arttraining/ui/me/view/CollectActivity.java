package com.example.kk.arttraining.ui.me.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.AttachmentBean;
import com.example.kk.arttraining.custom.view.BottomPullSwipeRefreshLayout;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.homePage.activity.DynamicContent;
import com.example.kk.arttraining.ui.me.adapter.CollectAdapter;
import com.example.kk.arttraining.ui.me.bean.CollectBean;
import com.example.kk.arttraining.ui.me.presenter.CollectPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.utils.ErrorHandleUtils;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/9/23 11:16
 * 说明:收藏页面
 */
public class CollectActivity extends Activity implements ICollectActivity, AdapterView.OnItemClickListener, BottomPullSwipeRefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener {
    @InjectView(R.id.lv_collect)
    ListView lv_collect;

    private CollectAdapter adapter;
    private Dialog loadingDialog;
    private CollectPresenter presenter;
    private int self_id = 0;
    private List<CollectBean> collectList;
    BottomPullSwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_collect_activity);
        init();
    }

    //初始化
    public void init() {
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this, "收藏");
        loadingDialog = DialogUtils.createLoadingDialog(getApplicationContext(), "");
        presenter = new CollectPresenter(this);
        swipeRefreshLayout = new BottomPullSwipeRefreshLayout(CollectActivity.this);
        swipeRefreshLayout = (BottomPullSwipeRefreshLayout) findViewById(R.id.me_collect_swipe);
        swipeRefreshLayout.setColorSchemeColors(android.graphics.Color.parseColor("#87CEFA"));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setOnLoadListener(this);
        //自动刷新
//        swipeRefreshLayout.autoRefresh();
        collectList = new ArrayList<CollectBean>();
        Success(null);
        lv_collect.setOnItemClickListener(this);

    }

    //listview点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CollectBean collectBean = (CollectBean) parent.getItemAtPosition(position);
        //动态的id
        int stus_id = collectBean.getStus_id();
        //动态类型
        String type = collectBean.getFav_type();
        Intent intent = new Intent(CollectActivity.this, DynamicContent.class);
        intent.putExtra("status_id", stus_id);
        intent.putExtra("stus_type", type);
        startActivity(intent);

    }

    //获取收藏数据（刷新）
    @Override
    public void getCollectData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        presenter.getCollectData(map, 0);
    }

    //获取收藏数据（上拉）
    @Override
    public void getLoadData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("self", self_id);
        presenter.getCollectData(map, 1);
    }

    //获取数据成功（刷新）
    @Override
    public void Success(List<CollectBean> collectBeanList) {
        //停止刷新
        swipeRefreshLayout.setRefreshing(false);

        collectList = collectBeanList;
        adapter = new CollectAdapter(CollectActivity.this, collectList);
        lv_collect.setAdapter(adapter);
    }

    //获取数据成功（上啦）
    @Override
    public void SuccessLoadData(List<CollectBean> collectBeanList) {
        //停止加载
        swipeRefreshLayout.setLoading(false);
        //更新数据源
        collectList.addAll(collectBeanList);
        //更新页面
        adapter.notifyDataSetChanged();
    }

    //获取数据失败
    @Override
    public void Failure(String error_code) {
        swipeRefreshLayout.setRefreshing(false);
        UIUtil.ToastshowShort(getApplicationContext(), ErrorHandleUtils.errorMsg(error_code));
    }

    //显示加载dialog
    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    //隐藏加载dialog
    @Override
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    //上拉加载
    @Override
    public void onLoad() {
        self_id++;
        getLoadData();

    }

    //下拉刷新
    @Override
    public void onRefresh() {
        self_id = 0;
        getCollectData();
    }
}