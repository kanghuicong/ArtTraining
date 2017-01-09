package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.InfoBean;
import com.example.kk.arttraining.ui.homePage.adapter.InfoAdapter;
import com.example.kk.arttraining.ui.homePage.function.info.InfoListData;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullToRefreshLayout;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullableListView;
import com.example.kk.arttraining.ui.homePage.prot.IInfo;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2017/1/6.
 * QQ邮箱:515849594@qq.com
 */
public class InfoListMain extends Activity implements IInfo,PullToRefreshLayout.OnRefreshListener {
    @InjectView(R.id.lv_info)
    PullableListView lvInfo;
    @InjectView(R.id.refresh_view)
    PullToRefreshLayout refreshView;

    List<InfoBean> infoList = new ArrayList<InfoBean>();
    InfoListData infoListData = new InfoListData(this);
    InfoAdapter topicAdapter;
    int refreshResult = PullToRefreshLayout.FAIL;
    boolean Flag = false;
    int InfoFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_info_list);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this,"更多资讯");
        refreshView.setOnRefreshListener(this);

        infoListData.getInfoListData();
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        infoListData.getInfoListData();
        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        if (Flag) {
            infoListData.loadInfoListData(topicAdapter.getSelfId(),"");
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
        Flag = true;

        if (InfoFlag == 0) {
            infoList.addAll(infoList1);
            topicAdapter = new InfoAdapter(this, infoList);
            lvInfo.setAdapter(topicAdapter);
            InfoFlag++;
        }else {
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
}
