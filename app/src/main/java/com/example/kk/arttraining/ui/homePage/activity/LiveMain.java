package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
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
import com.example.kk.arttraining.ui.live.view.PLVideoViewActivity;
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
    ;
    @InjectView(R.id.gv_live_list)
    PullableGridView gvLiveList;
    @InjectView(R.id.refresh_view)
    PullToRefreshLayout refreshView;

    HashMap<String, Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_live_list);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this, "直播");

        liveListData = new LiveListData(this);
        liveListData.getLiveListData();

//        liveAdapter = new LiveAdapter(this);
//        gvLiveList.setAdapter(liveAdapter);
//        gvLiveList.setOnItemClickListener(new LiveItemClick());

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
            UIUtil.showLog("123123", "123");
            liveList.clear();
            liveList.addAll(liveListBeanList);
            liveAdapter.ChangeCount(liveList.size());
            liveAdapter.notifyDataSetChanged();
            refreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
        }
    }

    @Override
    public void OnLiveListFailure(String result) {
        refreshView.refreshFinish(PullToRefreshLayout.FAIL);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        liveListData.getLiveListData();
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
        switch (type) {
            //还未开始直播状态
            case 0:
                Intent intentBefore = new Intent(this, LiveWaitActivity.class);
                intentBefore.putExtra("room_id", room_id);
                intentBefore.putExtra("chapter_id", chapter_id);
                startActivity(intentBefore);
                break;
            //正在直播
            case 1:
                Intent intentBeing = new Intent(this, PLVideoViewActivity.class);
                intentBeing.putExtra("room_id", room_id);
                intentBeing.putExtra("chapter_id", chapter_id);
                startActivity(intentBeing);
                break;
            //直播结束
            case 2:
                Intent intentAfter = new Intent(this, LiveFinishActivity.class);
                intentAfter.putExtra("room_id", room_id);
                startActivity(intentAfter);
                break;
        }
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
