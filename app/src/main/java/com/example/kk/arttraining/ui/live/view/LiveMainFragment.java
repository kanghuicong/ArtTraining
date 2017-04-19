package com.example.kk.arttraining.ui.live.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.ui.live.adapter.LiveListAdapter;
import com.example.kk.arttraining.ui.homePage.bean.LiveList;
import com.example.kk.arttraining.ui.homePage.bean.LiveListBean;
import com.example.kk.arttraining.ui.homePage.function.live.LiveListData;
import com.example.kk.arttraining.ui.homePage.function.live.LiveType;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullToRefreshLayout;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullableGridView;
import com.example.kk.arttraining.ui.homePage.prot.ILiveList;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2017/4/14.
 * QQ邮箱:515849594@qq.com
 */
public class LiveMainFragment extends Fragment implements ILiveList, PullToRefreshLayout.OnRefreshListener {

    View liveView;
    Activity activity;

    LiveListAdapter liveAdapter;
    LiveListData liveListData;
    List<LiveListBean> liveList = new ArrayList<LiveListBean>();
    int livePage = 1;

    int refreshResult = PullToRefreshLayout.FAIL;
    @InjectView(R.id.gv_live_list)
    PullableGridView gvLiveList;
    @InjectView(R.id.refresh_view)
    PullToRefreshLayout refreshView;

    HashMap<String, Object> typeMap;
    LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = getActivity();
        if (liveView == null) {
            liveView = View.inflate(activity, R.layout.homepage_live_list, null);
            ButterKnife.inject(this, liveView);

            loadingDialog = LoadingDialog.getInstance(activity);
            loadingDialog.show();

            liveListData = new LiveListData(this);
            liveListData.getLiveListData();

            refreshView.setOnRefreshListener(this);
        }

        ViewGroup parent = (ViewGroup) liveView.getParent();
        if (parent != null) {
            parent.removeView(liveView);
        }
        return liveView;
    }


    @Override
    public void getLiveListData(LiveList liveListBeanList) {
        livePage = 1;
        if (liveList.isEmpty()) {
            liveList.addAll(liveListBeanList.getOpenclass_list());
            liveAdapter = new LiveListAdapter(activity, liveList);
            gvLiveList.setAdapter(liveAdapter);
            gvLiveList.setOnItemClickListener(new LiveItemClick());
        } else {
            liveList.clear();
            liveList.addAll(liveListBeanList.getOpenclass_list());
            liveAdapter.ChangeCount(liveList.size());
            liveAdapter.notifyDataSetChanged();
            refreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
        }

        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
    }

    @Override
    public void OnLiveListFailure(String result) {
        loadingDialog.dismiss();
        refreshView.refreshFinish(PullToRefreshLayout.FAIL);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        liveListData.getLiveListData();
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        if (!liveList.isEmpty()) {
            LiveListBean liveListBean = liveAdapter.getSelfInfo();
            liveListData.loadLiveListData(liveListBean.getRoom_id(), livePage+1);
        } else {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    refreshView.loadmoreFinish(PullToRefreshLayout.FAIL);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }
    }

    @Override
    public void loadLiveList(LiveList liveListBeanList) {
        livePage++;
        if (liveList.size() == 0 || liveList == null) {
            getLiveListData(liveListBeanList);
        } else {
            liveList.addAll(liveListBeanList.getOpenclass_list());
            liveAdapter.ChangeCount(liveList.size());
            liveAdapter.notifyDataSetChanged();
        }
        refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void OnLoadLiveListFailure(int result) {
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


    private class LiveItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (Config.ACCESS_TOKEN != null && !Config.ACCESS_TOKEN.equals("")) {
                if (typeMap == null)
                    typeMap = new HashMap<String, Object>();
                typeMap.put("access_token", Config.ACCESS_TOKEN);
                typeMap.put("uid", Config.UID);
                typeMap.put("utype", Config.USER_TYPE);
                typeMap.put("room_id", liveAdapter.getLiveRoom(position));
                typeMap.put("chapter_id", liveAdapter.getLiveChapter(position));
                liveListData.getLiveTypeData(typeMap);
            } else {
                OnLiveTypeFailure(Config.TOKEN_INVALID, "请先登录哦！");
            }
        }
    }

    //获取直播状态成功
    @Override
    public void getLiveType(int type, int room_id, int chapter_id) {
        LiveType.getLiveType(activity, type, room_id, chapter_id);
    }

    //获取直播状态失败
    @Override
    public void OnLiveTypeFailure(String error_code, String error_msg) {

        if (error_code.equals(Config.TOKEN_INVALID)) {
            startActivity(new Intent(activity, UserLoginActivity.class));
        }
        UIUtil.ToastshowShort(activity, error_msg);
    }
}
