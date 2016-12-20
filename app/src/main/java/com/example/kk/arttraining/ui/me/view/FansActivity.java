package com.example.kk.arttraining.ui.me.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.BottomPullSwipeRefreshLayout;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.homePage.bean.Follow;
import com.example.kk.arttraining.ui.me.adapter.FansAdapter;
import com.example.kk.arttraining.ui.me.presenter.FansPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/11/9 09:18
 * 说明:粉丝和关注列表
 */
public class FansActivity extends BaseActivity implements IFansActivity, BottomPullSwipeRefreshLayout.OnLoadListener, BottomPullSwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.tv_failure_hint_)
    TextView tvFailureHint;
    @InjectView(R.id.failure_hint_layout)
    LinearLayout failureHintLayout;
    private ListView lv_fans;
    private FansPresenter presenter;
    private String error_code;
    private String type;
    private int uid;
    private String error_msg;
    private int sele_id;
    private FansAdapter fansAdapter;
    private boolean REFRESH_FLAG = true;
    private List<Follow> listData;
    private BottomPullSwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_fans_actviity);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {
        lv_fans = (ListView) findViewById(R.id.lv_fans);
        presenter = new FansPresenter(this);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        uid = intent.getIntExtra("uid", 1);
        if (type.equals("fans")) {
            TitleBack.TitleBackActivity(this, "粉丝");
            tvFailureHint.setText("天啦噜，竟然还木有粉丝！");
        } else if (type.equals("foucs")) {
            tvFailureHint.setText("您还没有关注任何人哦！");
            TitleBack.TitleBackActivity(this, "关注");
        }

        swipeRefreshLayout = new BottomPullSwipeRefreshLayout(this);
        swipeRefreshLayout = (BottomPullSwipeRefreshLayout) findViewById(R.id.fans_swipe);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#87CEFA"));
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.autoRefresh();
    }

    @Override
    public void RefreshData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", uid);
        map.put("utype", Config.USER_TYPE);
        if (type.equals("fans")) {
            presenter.getFansData(map, "refresh");
        } else if (type.equals("foucs")) {
            presenter.getFocusData(map, "refresh");
        }
    }

    @Override
    public void LoadData() {
        sele_id = fansAdapter.getSelfId();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", uid);
        map.put("utype", Config.USER_TYPE);
        map.put("self", sele_id);
        if (type.equals("fans")) {
            presenter.getFansData(map, "load");
        } else if (type.equals("foucs")) {
            presenter.getFocusData(map, "load");
        }
    }

    //获取信息成功
    @Override
    public void SuccessRefresh(List<Follow> followList) {
        swipeRefreshLayout.setRefreshing(false);
        listData = followList;
        failureHintLayout.setVisibility(View.GONE);

        if (listData.size() >= 8) {
            swipeRefreshLayout.setOnLoadListener(this);
        }
        if (REFRESH_FLAG) {
            fansAdapter = new FansAdapter(FansActivity.this, listData, type);
            lv_fans.setAdapter(fansAdapter);
        } else {
            fansAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void SuccessLoad(List<Follow> followList) {
        swipeRefreshLayout.setLoading(false);
        listData.addAll(followList);
        fansAdapter.notifyDataSetChanged();
    }

    //获取信息失败
    @Override
    public void Failure(String error_code, String error_msg) {
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setLoading(false);
        this.error_code = error_code;
        this.error_msg = error_msg;
        failureHintLayout.setVisibility(View.VISIBLE);
        lv_fans.setVisibility(View.GONE);
        mHandler.sendEmptyMessage(0);
    }


    @Override
    public void onClick(View v) {

    }

    //错误处理handler
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (error_code.equals(Config.TOKEN_INVALID)) {
                UIUtil.ToastshowShort(FansActivity.this, getResources().getString(R.string.toast_user_login));
                startActivity(new Intent(FansActivity.this, UserLoginActivity.class));
            } else {
                UIUtil.ToastshowShort(FansActivity.this, error_msg);
            }
        }
    };

    @Override
    public void onLoad() {
        LoadData();
    }

    @Override
    public void onRefresh() {

        RefreshData();
    }
}
