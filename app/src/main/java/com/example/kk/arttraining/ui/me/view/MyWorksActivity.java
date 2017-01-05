package com.example.kk.arttraining.ui.me.view;

import android.animation.AnimatorSet;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
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
import com.example.kk.arttraining.ui.discover.adapter.DynamicAdapter;
import com.example.kk.arttraining.ui.homePage.function.homepage.MusicTouch;
import com.example.kk.arttraining.ui.me.presenter.MyWorksPresenter;
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
 * 作者：wschenyongyin on 2016/11/22 11:07
 * 说明:我的作品
 */
public class MyWorksActivity extends BaseActivity implements IMyBBS, SwipeRefreshLayout.OnRefreshListener, BottomPullSwipeRefreshLayout.OnLoadListener, DynamicAdapter.MusicCallBack {
    private ListView lv_myBBs;
    private List<Map<String, Object>> mapListData;
    private DynamicAdapter dynamicAdapter;
    private MyWorksPresenter presenter;
    private BottomPullSwipeRefreshLayout swipeRefreshLayout;

    PlayAudioUtil playAudioUtil = null;
    int MusicPosition = -5;
    AnimatorSet MusicArtSet = null;
    AnimationDrawable MusicAnim = null;

    @InjectView(R.id.tv_failure_hint_)
    TextView tvFailureHint;
    @InjectView(R.id.failure_hint_layout)
    LinearLayout failureHintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_mygroup_activity);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {
        TitleBack.TitleBackActivity(this, "我的作品");
        lv_myBBs = (ListView) findViewById(R.id.lv_mygroup);
        lv_myBBs.setDividerHeight(10);

        presenter = new MyWorksPresenter(this);
        swipeRefreshLayout = new BottomPullSwipeRefreshLayout(getApplicationContext());
        swipeRefreshLayout = (BottomPullSwipeRefreshLayout) findViewById(R.id.my_group_swipe);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#87CEFA"));
        swipeRefreshLayout.setOnRefreshListener(this);
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
        presenter.getMyWorks(map, "refresh");

    }

    @Override
    public void LoadData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        map.put("self", dynamicAdapter.getSelfId());
        presenter.getMyWorks(map, "load");
        UIUtil.showLog("sele_id", dynamicAdapter.getSelfId() + "");

    }

    @Override
    public void SuccessRefresh(List<Map<String, Object>> mapList) {
        swipeRefreshLayout.setRefreshing(false);
        failureHintLayout.setVisibility(View.GONE);
        mapListData = mapList;
        if (mapList.size() >= 5) {
            swipeRefreshLayout.setOnLoadListener(this);
        }
        dynamicAdapter = new DynamicAdapter(this, mapListData, this, "myWork");
        lv_myBBs.setAdapter(dynamicAdapter);

//        lv_myBBs.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_MOVE:
//                        // 触摸移动时的操作
//                        if (MusicPosition != -5) {
//                            if (lv_myBBs.getFirstVisiblePosition() - 2 >= MusicPosition || lv_myBBs.getLastVisiblePosition() <= MusicPosition) {
//                                UIUtil.showLog("MusicStart", "onScroll");
//                                MusicTouch.stopMusicAnimator(playAudioUtil, MusicArtSet, MusicAnim);
//                            }
//                        }
//                        break;
//                }
//                return false;
//            }
//        });
    }

    @Override
    public void SuccessLoad(List<Map<String, Object>> mapList) {
        swipeRefreshLayout.setLoading(false);
        mapListData.addAll(mapList);
        dynamicAdapter.changeCount(mapListData.size());
        dynamicAdapter.notifyDataSetChanged();

    }

    @Override
    public void OnFailure(String error_code, String error_msg) {
        swipeRefreshLayout.setLoading(false);
        swipeRefreshLayout.setRefreshing(false);
        failureHintLayout.setVisibility(View.VISIBLE);
        UIUtil.showLog("我的作品错误代码--》", error_code);
        switch (error_code) {
            case "20007":
                tvFailureHint.setText("您还木有上传任何作品哦！");
                break;
            case "20028":
                UIUtil.ToastshowShort(this, "用户身份信息失效，请重新登陆！");
                startActivity(new Intent(this, UserLoginActivity.class));
                break;
            case Config.Connection_Failure:
                UIUtil.ToastshowShort(this, getResources().getString(R.string.connection_timeout));
                tvFailureHint.setText(getResources().getString(R.string.connection_timeout));
                break;
            case "404":
                UIUtil.ToastshowShort(this, getResources().getString(R.string.connection_timeout));
                tvFailureHint.setText(getResources().getString(R.string.connection_timeout));
                break;
        }

    }

    @Override
    public void OnFailureLoad(String error_code, String error_msg) {
        UIUtil.ToastshowShort(this, error_msg);
        swipeRefreshLayout.setLoading(false);
    }

    @Override
    public void onLoad() {
        MusicTouch.stopMusicAnimator(MusicArtSet, MusicAnim);
        LoadData();
    }

    @Override
    public void onRefresh() {
        MusicTouch.stopMusicAnimator(MusicArtSet, MusicAnim);
        RefreshData();

    }


    @Override
    public void backPlayAudio(AnimatorSet MusicArtSet, AnimationDrawable MusicAnim, int position) {
        this.MusicPosition = position;
        this.MusicArtSet = MusicArtSet;
        this.MusicAnim = MusicAnim;
    }

    @Override
    public void onPause() {
        super.onPause();
        MusicTouch.stopMusicAnimator(MusicArtSet, MusicAnim);
    }

}
