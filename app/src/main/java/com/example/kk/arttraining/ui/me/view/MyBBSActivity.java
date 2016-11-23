package com.example.kk.arttraining.ui.me.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.BottomPullSwipeRefreshLayout;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicAdapter;
import com.example.kk.arttraining.ui.me.presenter.MyBBSPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.PlayAudioUtil;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/11/14 12:37
 * 说明:我的帖子
 */

public class MyBBSActivity extends BaseActivity implements IMyBBS, SwipeRefreshLayout.OnRefreshListener, BottomPullSwipeRefreshLayout.OnLoadListener,DynamicAdapter.MusicCallBack {
    private ListView lv_myBBs;
    private List<Map<String, Object>> mapListData;
    private DynamicAdapter dynamicAdapter;
    private MyBBSPresenter myBBSPresenter;
    private BottomPullSwipeRefreshLayout swipeRefreshLayout;
    PlayAudioUtil playAudioUtil;
    int MusicPosition=-2;

    @InjectView(R.id.tv_failure_hint_)
    TextView tvFailureHint;
    @InjectView(R.id.failure_hint_layout)
    LinearLayout failureHintLayout;


    private String fromType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_mygroup_activity);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {

        Intent intent=getIntent();
        fromType=intent.getStringExtra("type");
        if(fromType.equals("comments")){
            TitleBack.TitleBackActivity(this, "我的评论");
        }else {
            TitleBack.TitleBackActivity(this, "我的帖子");
        }


        lv_myBBs = (ListView) findViewById(R.id.lv_mygroup);

        myBBSPresenter = new MyBBSPresenter(this);
        swipeRefreshLayout = new BottomPullSwipeRefreshLayout(getApplicationContext());
        swipeRefreshLayout = (BottomPullSwipeRefreshLayout) findViewById(R.id.my_group_swipe);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#87CEFA"));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setOnLoadListener(this);
        swipeRefreshLayout.autoRefresh();
    }


    @Override
    public void onClick(View v) {

    }


    @Override
    public void RefreshData() {
        if (playAudioUtil != null) {
            playAudioUtil.stop();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        myBBSPresenter.RefreshData(map,fromType);

    }

    @Override
    public void LoadData() {
        if (playAudioUtil != null) {
            playAudioUtil.stop();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("self", dynamicAdapter.getSelfId());
        myBBSPresenter.LoadData(map,fromType);
        UIUtil.showLog("sele_id", dynamicAdapter.getSelfId() + "");

    }

    @Override
    public void SuccessRefresh(List<Map<String, Object>> mapList) {
        swipeRefreshLayout.setRefreshing(false);
        failureHintLayout.setVisibility(View.GONE);
        mapListData = mapList;
        dynamicAdapter = new DynamicAdapter(this, mapListData,this);
        lv_myBBs.setAdapter(dynamicAdapter);

        lv_myBBs.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        // 触摸移动时的操作
                        if (lv_myBBs.getFirstVisiblePosition()-1 == MusicPosition ||lv_myBBs.getLastVisiblePosition() -1 ==MusicPosition){
                            UIUtil.showLog("MusicStart","onScroll");
                            playAudioUtil.stop();
                        }
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void SuccessLoad(List<Map<String, Object>> mapList) {
        swipeRefreshLayout.setLoading(false);
        mapListData.addAll(mapList);
        dynamicAdapter.changeCount(mapListData.size());
        dynamicAdapter.notifyDataSetChanged();

    }

    @Override
    public void OnFailure(String error_code,String error_msg) {
        swipeRefreshLayout.setLoading(false);
        swipeRefreshLayout.setRefreshing(false);
        failureHintLayout.setVisibility(View.VISIBLE);
        switch (error_code) {
            case "20007":
                tvFailureHint.setText("您还木有发布任何动态哦！");
                break;
            case "20028":
                UIUtil.ToastshowShort(this, "用户身份信息失效，请重新登陆！");
                startActivity(new Intent(this, UserLoginActivity.class));
                break;
            case Config.Connection_Failure:
                UIUtil.ToastshowShort(this, getResources().getString(R.string.connection_timeout));
                tvFailureHint.setText(getResources().getString(R.string.connection_timeout));
                break;
        }

    }

    @Override
    public void OnFailureLoad(String error_code,String error_msg) {
        UIUtil.ToastshowShort(this, error_code);
        swipeRefreshLayout.setLoading(false);
    }

    @Override
    public void onLoad() {

        LoadData();
    }

    @Override
    public void onRefresh() {
        RefreshData();

    }


    @Override
    public void backPlayAudio(PlayAudioUtil playAudioUtil,int position) {
        this.playAudioUtil = playAudioUtil;
        this.MusicPosition = position;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (playAudioUtil != null) {
            playAudioUtil.stop();
        }
    }
}
