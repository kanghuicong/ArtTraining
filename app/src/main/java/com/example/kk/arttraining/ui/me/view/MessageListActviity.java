package com.example.kk.arttraining.ui.me.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.BottomPullSwipeRefreshLayout;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.me.adapter.MessageListAdapter;
import com.example.kk.arttraining.ui.me.bean.MessageBean;
import com.example.kk.arttraining.ui.me.presenter.MessageListPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/12/30 09:49
 * 说明:消息列表
 */
public class MessageListActviity extends BaseActivity implements IMessageListView, BottomPullSwipeRefreshLayout.OnRefreshListener, BottomPullSwipeRefreshLayout.OnLoadListener {

    @InjectView(R.id.tv_title_subtitle)
    TextView tvTitleSubtitle;
    @InjectView(R.id.me_lv_msg)
    ListView meLvMsg;
    //标记是否有新的消息
    private boolean IS_NEW = false;
    //网络请求处理类
    MessageListPresenter presenter;
    //适配器
    private MessageListAdapter adapter = null;
    //获取到的数据
    private List<MessageBean> dataList;
    private int self_id = 0;
    //判断是否为第一次刷新
    private boolean IS_FIRST_REFRESH = true;
    BottomPullSwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_message_list_activity);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this, "消息");
        init();
    }

    //初始化
    @Override
    public void init() {
        presenter = new MessageListPresenter(this);
        swipeRefreshLayout = new BottomPullSwipeRefreshLayout(getApplicationContext());
        swipeRefreshLayout = (BottomPullSwipeRefreshLayout) findViewById(R.id.swipe_msg_list);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#87CEFA"));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.autoRefresh();
    }

    @OnClick({R.id.tv_title_subtitle})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title_subtitle:
                break;
        }

    }

    //下拉刷新
    @Override
    public void onRefresh() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("utype", Config.USER_TYPE);
        map.put("uid", Config.UID);
        presenter.RefreshData(map);
    }

    //上拉加载更多
    @Override
    public void onLoad() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("utype", Config.USER_TYPE);
        map.put("uid", Config.UID);
        self_id = adapter.getSelfId();
        map.put("self", self_id);
        presenter.LoadData(map);
    }

    //下拉刷新成功
    @Override
    public void SuccessRefresh(List<MessageBean> messageBeanList) {
        dataList = messageBeanList;
        swipeRefreshLayout.setRefreshing(false);
        if (dataList.size() > 9) {
            swipeRefreshLayout.setOnLoadListener(this);
        }

        if (IS_FIRST_REFRESH) {
            adapter = new MessageListAdapter(MessageListActviity.this, dataList);
            meLvMsg.setAdapter(adapter);
            IS_FIRST_REFRESH = false;
        } else {
            adapter.refreshCount(dataList.size());
            adapter.notifyDataSetChanged();
        }
    }

    //上啦加载更多成功
    @Override
    public void SuccessLoad(List<MessageBean> messageBeanList) {
        swipeRefreshLayout.setLoading(false);
        dataList.addAll(messageBeanList);
        adapter.refreshCount(dataList.size());
        adapter.notifyDataSetChanged();

    }

    //刷新失败
    @Override
    public void FailureRefrsh(String error_code, String error_msg) {
        swipeRefreshLayout.setRefreshing(false);
        UIUtil.ToastshowShort(this, error_msg);
        if (error_code.equals("20027")){
            startActivity(new Intent(this,UserLoginActivity.class));
        }

    }

    //上啦加载失败
    @Override
    public void FailureLoad(String error_code, String error_msg) {
        swipeRefreshLayout.setLoading(false);
        UIUtil.ToastshowShort(this, error_msg);
    }


}
