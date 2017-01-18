package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.adapter.LiveAdapter;
import com.example.kk.arttraining.ui.homePage.bean.LiveListBean;
import com.example.kk.arttraining.ui.homePage.function.live.LiveListData;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullToRefreshLayout;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullableGridView;
import com.example.kk.arttraining.ui.homePage.prot.ILiveList;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2017/1/7.
 * QQ邮箱:515849594@qq.com
 */
public class LiveMain extends Activity implements ILiveList ,PullToRefreshLayout.OnRefreshListener{


    LiveAdapter liveAdapter;
    LiveListData liveListData;
    List<LiveListBean> liveList = new ArrayList<LiveListBean>();
    boolean FLAG = false;
    int LiveFlag = 0;
    int refreshResult = PullToRefreshLayout.FAIL;;
    @InjectView(R.id.gv_live_list)
    PullableGridView gvLiveList;
    @InjectView(R.id.refresh_view)
    PullToRefreshLayout refreshView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_live_list);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this, "直播");

//        liveListData = new LiveListData(this);
//        liveListData.getLiveListData();

        liveAdapter = new LiveAdapter(this);
        gvLiveList.setAdapter(liveAdapter);

        refreshView.setOnRefreshListener(this);
        
    }

    @Override
    public void getLiveListData(List<LiveListBean> liveListBeanList) {
        FLAG = true;
        if (LiveFlag == 0) {
            liveList.addAll(liveListBeanList);
            liveAdapter = new LiveAdapter(this, liveListBeanList);
            gvLiveList.setAdapter(liveAdapter);
            gvLiveList.setOnItemClickListener(new LiveItemClick());
            LiveFlag++;
        } else {
            liveList.clear();
            liveList.addAll(liveListBeanList);
            liveAdapter.ChangeCount(liveList.size());
            liveAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnLiveListFailure(String result) {
        refreshView.refreshFinish(PullToRefreshLayout.FAIL);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        liveListData.getLiveListData();
        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        if (FLAG) {
            liveListData.loadLiveListData(liveAdapter.getSelfId());
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
    public void loadLiveList(List<LiveListBean> liveListBeanList) {
        if (liveList.size() == 0 || liveList == null) {
            getLiveListData(liveListBeanList);
        } else {
            liveList.addAll(liveListBeanList);
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
                UIUtil.ToastshowShort(this, "网络连接失败！");
                break;
        }
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                refreshView.loadmoreFinish(refreshResult);
            }
        }.sendEmptyMessageDelayed(0, 1000);
    }

    private class LiveItemClick implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            switch (liveAdapter.getLiveStatus(position)){
                //还未开始直播状态
                case 0:
                    break;
                //正在直播
                case 1:
                    break;
                //直播结束
                case 2:
                    break;
            }
        }
    }
}
