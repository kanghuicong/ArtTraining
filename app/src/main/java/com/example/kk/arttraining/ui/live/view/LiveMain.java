package com.example.kk.arttraining.ui.live.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.ui.homePage.adapter.LiveAdapter;
import com.example.kk.arttraining.ui.homePage.bean.LiveList;
import com.example.kk.arttraining.ui.homePage.bean.LiveListBean;
import com.example.kk.arttraining.ui.homePage.function.live.LiveListData;
import com.example.kk.arttraining.ui.homePage.function.live.LiveType;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullToRefreshLayout;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullableGridView;
import com.example.kk.arttraining.ui.homePage.prot.ILiveList;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2017/1/7.
 * QQ邮箱:515849594@qq.com
 */
public class LiveMain extends Activity implements ILiveList, PullToRefreshLayout.OnRefreshListener {


    LiveAdapter liveAdapter;
    LiveListData liveListData;
    List<LiveListBean> liveList = new ArrayList<LiveListBean>();
    boolean FLAG = false;
    int LiveFlag = 0;
    int refreshResult = PullToRefreshLayout.FAIL;

    @InjectView(R.id.gv_live_list)
    PullableGridView gvLiveList;
    @InjectView(R.id.refresh_view)
    PullToRefreshLayout refreshView;

    HashMap<String, Object> map;

    LoadingDialog loadingDialog;
    int pre_page;
    int finish_page;
    int page_limit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_live_list);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this, "直播");
        loadingDialog = LoadingDialog.getInstance(this);
        loadingDialog.show();

        liveListData = new LiveListData(this, "live");
        liveListData.getLiveListData();

        refreshView.setOnRefreshListener(this);

    }

    @Override
    public void getLiveListData(LiveList liveListBeanList) {
        FLAG = true;
        pre_page = liveListBeanList.getPre_page();
        finish_page = liveListBeanList.getFinish_page();
        page_limit = liveListBeanList.getPage_limit();

        if (LiveFlag == 0) {
            liveList.addAll(liveListBeanList.getOpenclass_list());
            liveAdapter = new LiveAdapter(this, liveList);
            gvLiveList.setAdapter(liveAdapter);
            gvLiveList.setOnItemClickListener(new LiveItemClick());
            LiveFlag++;
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
        UIUtil.ToastshowShort(getApplicationContext(), result + "");
        refreshView.refreshFinish(PullToRefreshLayout.FAIL);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        liveListData.getLiveListData();
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        if (FLAG) {
            if (finish_page != 0) {
                finish_page++;
            }
            pre_page++;

            LiveListBean liveListBean=liveAdapter.getSelfInfo();
            liveListData.loadLiveListData(liveListBean.getRoom_id(),pre_page,finish_page,page_limit,liveListBean.getLive_status());
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
        pre_page = liveListBeanList.getPre_page();
        finish_page = liveListBeanList.getFinish_page();
        page_limit = liveListBeanList.getPage_limit();

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
//            Intent intentBefore = new Intent(LiveMain.this, LiveWaitActivity.class);
//            intentBefore.putExtra("room_id", 1);
//            intentBefore.putExtra("chapter_id", 1);
//            startActivity(intentBefore);
//            UIUtil.showLog("live","点击事件");
            if (Config.ACCESS_TOKEN != null && !Config.ACCESS_TOKEN.equals("")) {
                if (map == null)
                    map = new HashMap<String, Object>();
                map.put("access_token", Config.ACCESS_TOKEN);
                map.put("uid", Config.UID);
                map.put("utype", Config.USER_TYPE);
                map.put("room_id", liveAdapter.getLiveRoom(position));
                map.put("chapter_id", liveAdapter.getLiveChapter(position));
                liveListData.getLiveTypeData(map);
            } else {
                OnLiveTypeFailure(Config.TOKEN_INVALID, "请先登录哦！");
            }


        }
    }

    //获取直播状态成功
    @Override
    public void getLiveType(int type, int room_id, int chapter_id) {
        LiveType.getLiveType(this,type,room_id,chapter_id);
    }

    //获取直播状态失败
    @Override
    public void OnLiveTypeFailure(String error_code, String error_msg) {

        if (error_code.equals(Config.TOKEN_INVALID)) {
            startActivity(new Intent(this, UserLoginActivity.class));
        }
        UIUtil.ToastshowShort(this, error_msg);
    }
}
