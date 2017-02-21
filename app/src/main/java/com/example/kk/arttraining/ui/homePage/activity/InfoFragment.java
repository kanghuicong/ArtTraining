package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.InfoBean;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.ui.homePage.adapter.InfoAdapter;
import com.example.kk.arttraining.ui.homePage.function.info.InfoListData;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullToRefreshLayout;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullableListView;
import com.example.kk.arttraining.ui.homePage.prot.IInfo;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2017/1/6.
 * QQ邮箱:515849594@qq.com
 */
public class InfoFragment extends Fragment implements IInfo, PullToRefreshLayout.OnRefreshListener {

    List<InfoBean> infoList = new ArrayList<InfoBean>();
    InfoListData infoListData = new InfoListData(this);
    InfoAdapter topicAdapter;
    int refreshResult = PullToRefreshLayout.FAIL;
    int InfoFlag = 0;
    View view;
    Activity activity;
    String type;

    @InjectView(R.id.lv_info)
    PullableListView lvInfo;
    @InjectView(R.id.refresh_view)
    PullToRefreshLayout refreshView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();

        if (view == null) {
            view = View.inflate(activity, R.layout.homepage_info_list, null);
            ButterKnife.inject(this, view);
            refreshView.setOnRefreshListener(this);

            type = getArguments().getString("type");
            infoListData.getInfoListData(type);
        }

        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }


    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        infoListData.getInfoListData(type);
        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        if (infoList != null && infoList.size() !=0) {
            infoListData.loadInfoListData(topicAdapter.getSelfId(), "");
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
    public void getInfoList(List<InfoBean> infoList1) {

        if (InfoFlag == 0) {
            infoList.addAll(infoList1);
            topicAdapter = new InfoAdapter(activity, infoList,"infoFragment");
            lvInfo.setAdapter(topicAdapter);
            InfoFlag++;
        } else {
            infoList.clear();
            infoList.addAll(infoList1);
            topicAdapter.ChangeCount(infoList.size());
            topicAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnInfoListFailure() {
        refreshView.refreshFinish(PullToRefreshLayout.FAIL);
    }

    @Override
    public void loadInfoList(List<InfoBean> infoList1) {
        if (infoList.size() == 0 || infoList == null) {
            getInfoList(infoList1);
        } else {
            infoList.addAll(infoList1);
            topicAdapter.ChangeCount(infoList.size());
            topicAdapter.notifyDataSetChanged();
        }
        refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void loadInfoListFailure(int Flag) {
        switch (Flag) {
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
}
