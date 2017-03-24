package com.example.kk.arttraining.ui.homePage.function.refresh;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;

import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

/**
 * Created by kanghuicong on 2017/3/16.
 * QQ邮箱:515849594@qq.com
 */
public class RefreshUtil {
    List list;
    PullToRefreshLayout refreshView;
    IRefresh iRefresh;
    Context context;
    AbsListView listView;

    public RefreshUtil(Context context, PullableListView listView, PullToRefreshLayout refreshView, List list, IRefresh iRefresh) {
        this.context = context;
        this.listView = listView;
        this.list = list;
        this.refreshView = refreshView;
        this.iRefresh = iRefresh;
    }

    public RefreshUtil(Context context, PullableGridView listView, PullToRefreshLayout refreshView, List list, IRefresh iRefresh) {
        this.context = context;
        this.listView = listView;
        this.list = list;
        this.refreshView = refreshView;
        this.iRefresh = iRefresh;
    }

    //onRefresh的操作就是请求数据
    public void onRefresh() {
        iRefresh.refreshData();
    }

    //onRefresh之后成功刷新的操作
    public void refreshSuccess(List mlist) {
        if (list.isEmpty()) {
            list.addAll(mlist);
            iRefresh.newAdapter();
        } else {
            list.clear();
            list.addAll(mlist);
            iRefresh.notifyAdapter();
        }
        if (refreshView.isRefresh()) {
            refreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
        }

    }


    //onLoadMore的操作
    public void onLoadMore() {
        if (list.isEmpty()) {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    refreshView.loadmoreFinish(PullToRefreshLayout.FAIL);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        } else {
            iRefresh.loadData();//执行load数据的网络请求方法
        }
    }

    //加载load数据成功
    public void loadSuccess(List mlist) {
        list.addAll(mlist);
        iRefresh.notifyAdapter();
        refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    //数据请求失败
    public void onFailure(String error_code, String error_msg) {
        UIUtil.ToastshowShort(context, error_msg);
        if (refreshView.isRefresh()) {
            refreshView.refreshFinish(PullToRefreshLayout.FAIL);
        }
        if (refreshView.isLoad()) {
            if ("20007".equals(error_code)) {
                refreshView.loadmoreFinish(PullToRefreshLayout.EMPTY);
            } else {
                refreshView.loadmoreFinish(PullToRefreshLayout.FAIL);
            }
        }
    }

    public interface IRefresh {
        void refreshData();

        void loadData();

        void newAdapter();

        void notifyAdapter();


    }

}
