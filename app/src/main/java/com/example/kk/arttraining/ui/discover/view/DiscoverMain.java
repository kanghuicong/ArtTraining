package com.example.kk.arttraining.ui.discover.view;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.discover.function.dynamic.DynamicData;
import com.example.kk.arttraining.ui.discover.prot.IDiscover;
import com.example.kk.arttraining.ui.discover.adapter.DynamicAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicFailureAdapter;
import com.example.kk.arttraining.ui.homePage.function.homepage.MusicTouch;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullToRefreshLayout;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullableListView;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.PlayAudioUtil;
import com.example.kk.arttraining.utils.UIUtil;
import com.mingle.widget.ShapeLoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/12/8.
 * QQ邮箱:515849594@qq.com
 */
public class DiscoverMain extends Fragment implements IDiscover, PullToRefreshLayout.OnRefreshListener, DynamicAdapter.MusicCallBack {

    @InjectView(R.id.lv_discover)
    PullableListView lvDiscover;
    @InjectView(R.id.refresh_view)
    PullToRefreshLayout refreshView;

    View view_discover;
    List<Map<String, Object>> DynamicList = new ArrayList<Map<String, Object>>();
    Activity activity;
    int dynamic_num;
    DynamicData dynamicData;
    DynamicAdapter dynamicadapter;
    int dynamicPosition = 0;
    boolean Flag = false;
    private ShapeLoadingDialog shapeLoadingDialog;
    int refreshResult = PullToRefreshLayout.FAIL;
    PlayAudioUtil playAudioUtil = null;
    int MusicPosition = -5;
    AnimatorSet MusicArtSet = null;
    AnimationDrawable MusicAnim = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        activity = getActivity();

        if (view_discover == null) {
            view_discover = View.inflate(activity, R.layout.discover_main, null);
            ButterKnife.inject(this, view_discover);


            refreshView.setOnRefreshListener(this);

            shapeLoadingDialog = new ShapeLoadingDialog(activity);
            shapeLoadingDialog.show();
            shapeLoadingDialog.setLoadingText("加载中...");
            dynamicData = new DynamicData(this);
            dynamicData.getDynamicData();//动态
        }
        ViewGroup parent = (ViewGroup) view_discover.getParent();
        if (parent != null) {
            parent.removeView(view_discover);
        }
        ButterKnife.inject(this, view_discover);
        return view_discover;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MusicTouch.stopMusicAnimator(playAudioUtil, MusicArtSet, MusicAnim);
    }

    @Override
    public void backPlayAudio(PlayAudioUtil playAudioUtil, AnimatorSet MusicArtSet, AnimationDrawable MusicAnim, int position) {
        this.playAudioUtil = playAudioUtil;
        this.MusicPosition = position;
        this.MusicArtSet = MusicArtSet;
        this.MusicAnim = MusicAnim;
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        MusicTouch.stopMusicAnimator(playAudioUtil, MusicArtSet, MusicAnim);
        dynamicData.getDynamicData();//动态
        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        MusicTouch.stopMusicAnimator(playAudioUtil, MusicArtSet, MusicAnim);

        if (Flag) {
            if (DynamicList.get(DynamicList.size() - 1).get("type").equals("work") || DynamicList.get(DynamicList.size() - 1).get("type").equals("status")) {
                dynamicData.loadDynamicData(dynamicadapter.getSelfId());
            } else {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        refreshView.loadmoreFinish(PullToRefreshLayout.EMPTY);
                    }
                }.sendEmptyMessageDelayed(0, 1000);
            }
        } else {
            UIUtil.ToastshowShort(activity, "网络连接失败！");
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    refreshView.loadmoreFinish(refreshResult);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }
    }

    //获取动态数据
    @Override
    public void getDynamicListData(List<Map<String, Object>> mapList) {

        Flag = true;
        if (dynamicPosition == 0) {
            DynamicList.addAll(mapList);
            dynamicadapter = new DynamicAdapter(activity, DynamicList, this);
            dynamic_num = mapList.size();
            try {
                lvDiscover.setAdapter(dynamicadapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
            dynamicPosition++;
        } else {
            DynamicList.clear();
            DynamicList.addAll(mapList);
            dynamicadapter.changeCount(DynamicList.size());
            dynamicadapter.notifyDataSetChanged();
            dynamic_num = mapList.size();
        }

        lvDiscover.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        // 触摸移动时的操作
                        UIUtil.showLog("触摸移动时的操作", lvDiscover.getFirstVisiblePosition() + "----==" + MusicPosition);
                        if (MusicPosition != -5) {
                            if (lvDiscover.getFirstVisiblePosition() - 2 >= MusicPosition || lvDiscover.getLastVisiblePosition() <= MusicPosition) {
                                MusicTouch.stopMusicAnimator(playAudioUtil, MusicArtSet, MusicAnim);
                            }
                        }
                        break;
                }
                return false;
            }
        });
//优化glide加载图片
        lvDiscover.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_FLING:
                        Glide.with(activity.getApplicationContext()).pauseRequests();
                        //刷新
                        break;
                    case SCROLL_STATE_IDLE:
                        Glide.with(activity.getApplicationContext()).resumeRequests();
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        Glide.with(activity.getApplicationContext()).resumeRequests();
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        shapeLoadingDialog.dismiss();
    }

    //获取动态数据失败
    @Override
    public void OnDynamicFailure(String result) {
        shapeLoadingDialog.dismiss();
        UIUtil.ToastshowShort(activity, result);

        if (DynamicList == null || DynamicList.size() == 0) {
            DynamicFailureAdapter dynamicFailureAdapter = new DynamicFailureAdapter(activity);
            try {
                lvDiscover.setAdapter(dynamicFailureAdapter);
                Flag = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void loadDynamicListData(List<Map<String, Object>> mapList) {
        DynamicList.addAll(mapList);
        dynamic_num = dynamic_num + mapList.size();
        dynamicadapter.changeCount(dynamic_num);
        dynamicadapter.notifyDataSetChanged();
        refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void OnLoadDynamicFailure(int result) {
        switch (result) {
            case 0:
                refreshResult = PullToRefreshLayout.EMPTY;
                break;
            case 1:
                refreshResult = PullToRefreshLayout.FAIL;
                break;
            case 2:
                refreshResult = PullToRefreshLayout.FAIL;
                UIUtil.ToastshowShort(activity, "网络连接失败！");
                break;
        }
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                refreshView.loadmoreFinish(refreshResult);
            }
        }.sendEmptyMessageDelayed(0, 1000);
    }

    @OnClick(R.id.iv_discover_posting)
    public void onClick() {
        if (Config.ACCESS_TOKEN != null && !Config.ACCESS_TOKEN.equals("")) {
            UIUtil.IntentActivity(activity, new PostingMain());
        } else {
            UIUtil.ToastshowShort(activity, getResources().getString(R.string.toast_user_login));
            startActivity(new Intent(activity, UserLoginActivity.class));
        }
    }
}
