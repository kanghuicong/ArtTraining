package com.example.kk.arttraining.ui.live.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.AutoSwipeRefreshLayout;
import com.example.kk.arttraining.custom.view.BottomPullSwipeRefreshLayout;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.live.adapter.MemberAdapter;
import com.example.kk.arttraining.ui.live.bean.MemberBean;
import com.example.kk.arttraining.ui.live.presenter.MemberPresenter;
import com.example.kk.arttraining.ui.me.view.IMessageListView;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.UIUtil;

import java.security.spec.KeySpec;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2017/1/10 14:34
 * 说明:直播房间成员列表
 */
public class MemberListActivity extends BaseActivity implements IMemberListView, BottomPullSwipeRefreshLayout.OnRefreshListener, BottomPullSwipeRefreshLayout.OnLoadListener,View.OnKeyListener {


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
    //适配器
    private MemberAdapter memberAdapter;
    //分页id
    private int self_id;
    //搜索的内容
    private String search_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_member_activity);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {
        room_id=getIntent().getIntExtra("room_id",0);

        memberPresenter = new MemberPresenter(this);

        swipeRefreshLayout = new BottomPullSwipeRefreshLayout(this);
        swipeRefreshLayout = (BottomPullSwipeRefreshLayout) findViewById(R.id.swipe_lv_member);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#87CEFA"));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.autoRefresh();

    }

    @OnClick(R.id.iv_live_search)
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_live_search:
                search_content=etLiveSearch.getText().toString();
                if (search_content.equals("")){
                    UIUtil.ToastshowShort(this,"请输入搜索内容");
                }else {
                    SearchData(search_content);
                }
            break;
        }

    }

        //修改键盘回车键为发送按钮
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_SEARCH && event.getAction() == KeyEvent.ACTION_UP) {
            // 先隐藏键盘
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(MemberListActivity.this.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
            search_content=etLiveSearch.getText().toString();
            if (search_content.equals("")){
                UIUtil.ToastshowShort(this,"请输入搜索内容");
            }else {
                SearchData(search_content);
            }
        }
        return false;
    }

    //刷新数据
    @Override
    public void onRefresh() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("room_id", room_id);
        memberPresenter.RefreshData(map, "refresh");
    }

    //刷新数据成功
    @Override
    public void SuccessRefresh(List<MemberBean> memberBeanList) {
        swipeRefreshLayout.setRefreshing(false);
        dataList = memberBeanList;
        if (memberAdapter == null) {
            memberAdapter = new MemberAdapter(this, dataList);
            lvMember.setAdapter(memberAdapter);
        } else {
            memberAdapter.RefreshCount(dataList.size());
            memberAdapter.notifyDataSetChanged();
        }
    }

    //刷新数据失败
    @Override
    public void FailureRefresh(String error_code, String error_msg) {
        UIUtil.ToastshowShort(this, error_msg);
    }

    //上拉加载数据
    @Override
    public void onLoad() {
        self_id = memberAdapter.getSelfId();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("room_id", room_id);
        map.put("self", self_id);
        memberPresenter.LoadData(map);
    }

    //上拉加载成功
    @Override
    public void SuccessLoadData(List<MemberBean> memberBeanList) {
        swipeRefreshLayout.setLoading(false);
        dataList.addAll(memberBeanList);
        memberAdapter.RefreshCount(dataList.size());
        memberAdapter.notifyDataSetChanged();
    }

    //加载失败
    @Override
    public void FailureLoadData(String error_code, String error_msg) {
        swipeRefreshLayout.setLoading(false);
        UIUtil.ToastshowShort(this, error_msg);
    }

    //搜索
    @Override
    public void SearchData(String search_content) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("room_id", room_id);
        map.put("name", search_content);
        memberPresenter.RefreshData(map,"search");
    }

    //成功
    @Override
    public void SuccessSearchData(List<MemberBean> memberBeanList) {
        dataList=memberBeanList;
        memberAdapter.RefreshCount(dataList.size());
        memberAdapter.notifyDataSetChanged();
    }

    //搜索失败
    @Override
    public void FailureSearchData() {
    }


}
