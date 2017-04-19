package com.example.kk.arttraining.ui.live.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.custom.dialog.MyDialog;
import com.example.kk.arttraining.ui.homePage.bean.LiveHistoryBean;
import com.example.kk.arttraining.ui.homePage.function.refresh.IRefresh;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullToRefreshLayout;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullableGridView;
import com.example.kk.arttraining.ui.homePage.function.refresh.RefreshData;
import com.example.kk.arttraining.ui.homePage.function.refresh.RefreshUtil;
import com.example.kk.arttraining.ui.homePage.prot.ILiveList;
import com.example.kk.arttraining.ui.live.adapter.LiveHistoryAdapter;
import com.example.kk.arttraining.ui.live.bean.LiveHistoryTypeBean;
import com.example.kk.arttraining.ui.live.presenter.LiveHistoryTypeData;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2017/4/14.
 * QQ邮箱:515849594@qq.com
 */
public class LiveHistoryFragment extends Fragment implements PullToRefreshLayout.OnRefreshListener,RefreshUtil.IRefreshUtil,IRefresh,LiveHistoryTypeData.ILiveHistory{

    @InjectView(R.id.gv_live_list)
    PullableGridView gvLiveList;
    @InjectView(R.id.refresh_view)
    PullToRefreshLayout refreshView;

    View historyView;
    Activity activity;
    LoadingDialog loadingDialog;
    Map<String, Object> historyMap = new HashMap<String, Object>();

    int page = 1;

    List<LiveHistoryBean> historyList = new ArrayList<LiveHistoryBean>();
    LiveHistoryAdapter liveHistoryAdapter;

    RefreshData refreshData;
    RefreshUtil refreshUtil;

    LiveHistoryTypeData liveHistoryTypeData;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = getActivity();
        if (historyView == null) {
            historyView = View.inflate(activity, R.layout.homepage_live_list, null);
            ButterKnife.inject(this, historyView);

            loadingDialog = LoadingDialog.getInstance(activity);
            loadingDialog.show();

            refreshView.setOnRefreshListener(this);
            refreshUtil = new RefreshUtil(activity, gvLiveList, refreshView,historyList , this);

            historyMap.put("page", 1);

            refreshData = new RefreshData(this);
            refreshData.refreshData(HttpRequest.getLiveApi().liveHistory(historyMap));

            liveHistoryTypeData = new LiveHistoryTypeData(this);
        }

        ViewGroup parent = (ViewGroup) historyView.getParent();
        if (parent != null) {
            parent.removeView(historyView);
        }

        return historyView;
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        refreshUtil.onRefresh();
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        refreshUtil.onLoadMore();
    }


    @Override
    public void refreshData() {
        historyMap.put("page", 1);
        if (historyMap.containsKey("self")) historyMap.remove("self");
        refreshData.refreshData(HttpRequest.getLiveApi().liveHistory(historyMap));
    }

    @Override
    public void loadData() {
        historyMap.put("page", page+1);
        historyMap.put("self",liveHistoryAdapter.getSelfInfo());
        refreshData.loadData(HttpRequest.getLiveApi().liveHistory(historyMap));
    }

    @Override
    public void newAdapter() {
        liveHistoryAdapter = new LiveHistoryAdapter(activity, historyList);
        gvLiveList.setAdapter(liveHistoryAdapter);
        gvLiveList.setOnItemClickListener(new clickLiveHistory());
    }

    @Override
    public void notifyAdapter() {
        liveHistoryAdapter.changeCount(historyList.size());
        liveHistoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshSuccess(List list) {
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
        page = 1;
        refreshUtil.refreshSuccess(list);
    }

    @Override
    public void loadSuccess(List list) {
        page++;
        refreshUtil.loadSuccess(list);
    }

    @Override
    public void onFailure(String error_code, String error_msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
        refreshUtil.onFailure(error_code,error_msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        refreshData.cancelSubscription();
        liveHistoryTypeData.cancelSubscription();
    }



    private class clickLiveHistory implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            liveHistoryTypeData.getLiveHistoryType(liveHistoryAdapter.getChapterId(position),liveHistoryAdapter.getItem(position));
        }
    }

    @Override
    public void successHistory(LiveHistoryTypeBean liveHistoryTypeBean,LiveHistoryBean liveHistoryBean) {
        if (liveHistoryTypeBean.getIs_free() == 1) {
            intoPlayVideo(liveHistoryTypeBean.getRecord_url());
        }else {
            if (liveHistoryTypeBean.getOrder_status() == 0) {
                MyDialog.getBuyRecord(activity, liveHistoryBean.getRecord_price(),new MyDialog.IBuyRecord() {
                    @Override
                    public void getBuyRecord() {
                        Intent intent = new Intent(activity, LivePayActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("chapter_name", liveHistoryBean.getChapter_name());
                        bundle.putDouble("live_price",liveHistoryBean.getRecord_price());
                        bundle.putString("buy_type", "live");
                        bundle.putInt("room_id", liveHistoryBean.getRoom_id());
                        bundle.putInt("chapter_id", liveHistoryBean.getChapter_id());
                        intent.putExtras(bundle);
                        intent.putExtra("liveType", "liveHistory");
                        startActivity(intent);
                    }
                });
            }else {
                intoPlayVideo(liveHistoryTypeBean.getRecord_url());
            }
        }
    }

    public void intoPlayVideo(String url) {
        Intent intentRecord = new Intent(activity, PlayCallBackVideo.class);
        intentRecord.putExtra("path", url);
        activity.startActivity(intentRecord);
    }

    @Override
    public void failureHistory(String error_code, String error_msg) {
        if (error_code.equals("20032") || error_code.equals("20028")) {
            if (error_code.equals("20032")) {
                error_msg = "请先登录哦！";
            }
            activity.startActivity(new Intent(activity, UserLoginActivity.class));
        }
        UIUtil.ToastshowShort(activity,error_msg);
    }



}
