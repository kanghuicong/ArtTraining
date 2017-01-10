package com.example.kk.arttraining.ui.live.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.AutoSwipeRefreshLayout;
import com.example.kk.arttraining.custom.view.BottomPullSwipeRefreshLayout;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.live.bean.MemberBean;
import com.example.kk.arttraining.ui.live.presenter.MemberPresenter;
import com.example.kk.arttraining.ui.me.view.IMessageListView;
import com.example.kk.arttraining.utils.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2017/1/10 14:34
 * 说明:直播房间成员列表
 */
public class MemberListActivity extends BaseActivity implements IMemberListView,BottomPullSwipeRefreshLayout.OnRefreshListener,BottomPullSwipeRefreshLayout.OnLoadListener {


    @InjectView(R.id.et_live_search)
    EditText etLiveSearch;
    @InjectView(R.id.iv_live_search)
    ImageView ivLiveSearch;
    @InjectView(R.id.lv_member)
    ListView lvMember;
    //房间id
    private int room_id;
    private MemberPresenter memberPresenter;
    BottomPullSwipeRefreshLayout swipeRefreshLayout;
    //数据集合
    private List<MemberBean> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_member_activity);
        ButterKnife.inject(this);
    }

    @Override
    public void init() {
        memberPresenter=new MemberPresenter(this);

         swipeRefreshLayout = new BottomPullSwipeRefreshLayout(this);
        swipeRefreshLayout = (BottomPullSwipeRefreshLayout) findViewById(R.id.me_swipe);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#87CEFA"));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.autoRefresh();

    }

    @Override
    public void onClick(View v) {

    }

    //刷新数据
    @Override
    public void onLoad() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("room_id", room_id);
    }
    //刷新数据成功
    @Override
    public void SuccessRefresh(List<MemberBean> memberBeanList) {
        dataList=memberBeanList;

    }

    //刷新数据失败
    @Override
    public void FailureRefresh(String error_code, String error_msg) {

    }

    //上拉加载数据
    @Override
    public void onRefresh() {

    }
    //上拉加载成功
    @Override
    public void SuccessLoadData(List<MemberBean> memberBeanList) {

    }

    //加载失败
    @Override
    public void FailureLoadData(String error_code, String error_msg) {

    }

    //搜索
    @Override
    public void SearchData() {

    }

    //成功
    @Override
    public void SuccessSearchData(List<MemberBean> memberBeanList) {

    }

    //搜索失败
    @Override
    public void FailureSearchData() {

    }




}
