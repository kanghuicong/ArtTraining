package com.example.kk.arttraining.ui.me.view;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.BottomPullSwipeRefreshLayout;
import com.example.kk.arttraining.ui.homePage.activity.DynamicContent;
import com.example.kk.arttraining.ui.homePage.function.homepage.Headlines;
import com.example.kk.arttraining.ui.homePage.function.homepage.MusicTouch;
import com.example.kk.arttraining.ui.me.adapter.CollectAdapter;
import com.example.kk.arttraining.ui.me.bean.CollectBean;
import com.example.kk.arttraining.ui.me.presenter.CollectPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.utils.PlayAudioUtil;
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
public class CollectActivity extends Activity implements ICollectActivity, AdapterView.OnItemClickListener, BottomPullSwipeRefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener,CollectAdapter.MusicCallBack {
    @InjectView(R.id.lv_collect)
    ListView lv_collect;
    @InjectView(R.id.tv_failure_hint_)
    TextView tvFailureHint;
    @InjectView(R.id.failure_hint_layout)
    LinearLayout failureHintLayout;

    private CollectAdapter adapter;
    private Dialog loadingDialog;
    private CollectPresenter presenter;
    private int self_id = 0;
    private List<CollectBean> collectList;
    BottomPullSwipeRefreshLayout swipeRefreshLayout;
    private boolean REFRESH_FIRST_FLAG = true;

    PlayAudioUtil playAudioUtil = null;
    int MusicPosition=-5;
    AnimatorSet MusicArtSet = null;
    AnimationDrawable MusicAnim = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_collect_activity);
        ButterKnife.inject(this);
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
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#87CEFA"));
        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.setOnLoadListener(this);
        //自动刷新
        swipeRefreshLayout.autoRefresh();
        collectList = new ArrayList<CollectBean>();
        lv_collect.setOnItemClickListener(this);

    }

    //listview点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CollectBean collectBean = collectList.get(position);
        UIUtil.showLog("collectBean",collectBean+"---");
        //动态的id
        int stus_id = collectBean.getB_fav_id();
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
        map.put("utype", Config.USER_TYPE);
        presenter.getCollectData(map, 0);
    }

    //获取收藏数据（上拉）
    @Override
    public void getLoadData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("self", self_id);
        map.put("utype", Config.USER_TYPE);
        presenter.getCollectData(map, 1);
    }

    //获取数据成功（刷新）
    @Override
    public void Success(List<CollectBean> collectBeanList) {
        //停止刷新
        swipeRefreshLayout.setRefreshing(false);
        collectList = collectBeanList;
        if(collectBeanList.size()>=9){
            swipeRefreshLayout.setOnLoadListener(this);
        }
        if (REFRESH_FIRST_FLAG) {
            adapter = new CollectAdapter(CollectActivity.this, collectList,this);
            lv_collect.setAdapter(adapter);
            lv_collect.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_MOVE:
                            // 触摸移动时的操作
                            if (MusicPosition!=-5) {
                                if (lv_collect.getFirstVisiblePosition() - 2 >= MusicPosition || lv_collect.getLastVisiblePosition() <= MusicPosition) {
                                    UIUtil.showLog("MusicStart", "onScroll");
                                    MusicTouch.stopMusicAnimator(playAudioUtil, MusicArtSet,MusicAnim);
                                }
                            }
                            break;
                    }
                    return false;
                }
            });
            REFRESH_FIRST_FLAG = false;
        } else {
            adapter.notifyDataSetChanged();
        }


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
        failureHintLayout.setVisibility(View.GONE);
    }

    //获取数据失败
    @Override
    public void Failure(String error_code, String error_msg) {
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setLoading(false);
        if (error_code.equals(Config.TOKEN_INVALID)) {
            startActivity(new Intent(this, UserLoginActivity.class));
            UIUtil.ToastshowShort(getApplicationContext(), getResources().getString(R.string.toast_user_login));
        } else {
            UIUtil.ToastshowShort(getApplicationContext(), error_msg);
            tvFailureHint.setText(error_msg);
        }
        if(collectList==null&&collectList.size()==0){
            failureHintLayout.setVisibility(View.VISIBLE);
        }


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
        MusicTouch.stopMusicAnimator(playAudioUtil, MusicArtSet,MusicAnim);
        self_id = adapter.getSelfId();
        getLoadData();

    }

    //下拉刷新
    @Override
    public void onRefresh() {
        MusicTouch.stopMusicAnimator(playAudioUtil, MusicArtSet,MusicAnim);
        getCollectData();
    }

    @Override
    public void backPlayAudio(PlayAudioUtil playAudioUtil, AnimatorSet MusicArtSet, int position) {
        this.playAudioUtil = playAudioUtil;
        this.MusicPosition = position;
        this.MusicArtSet = MusicArtSet;
    }

    @Override
    public void onPause() {
        super.onPause();
        MusicTouch.stopMusicAnimator(playAudioUtil, MusicArtSet,MusicAnim);
    }
}