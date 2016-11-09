package com.example.kk.arttraining.ui.me.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.me.adapter.FansAdapter;
import com.example.kk.arttraining.ui.me.bean.FansBean;
import com.example.kk.arttraining.ui.me.presenter.FansPresenter;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/9 09:18
 * 说明:粉丝
 */
public class FansActivity extends BaseActivity implements IFansActivity {

    private ListView lv_fans;
    private FansPresenter presenter;
    private String error_code;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_fans_actviity);
        init();
    }

    @Override
    public void init() {
        lv_fans = (ListView) findViewById(R.id.lv_fans);
        presenter = new FansPresenter(this);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if (type.equals("fans")) {
//            getFansData();
            Success(null);
        } else if (type.equals("focus")) {
//            getFocusData();
            Success(null);
        }

    }


    //获取粉丝信息
    @Override
    public void getFansData() {
        presenter.getFansData();
    }

    //获取关注信息
    @Override
    public void getFocusData() {
        presenter.getFocusData();
    }

    //获取信息成功
    @Override
    public void Success(List<FansBean> fansBeanList) {
        FansAdapter fansAdapter = new FansAdapter(FansActivity.this,fansBeanList);
        lv_fans.setAdapter(fansAdapter);
    }

    //获取信息失败
    @Override
    public void Failure(String error_code) {
        this.error_code = error_code;
    }

    //显示loading
    @Override
    public void showLoading() {

    }

    //隐藏loading
    @Override
    public void hideLoading() {

    }

    @Override
    public void onClick(View v) {

    }

    //错误处理handler
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (error_code) {
                case "":

                    break;
            }
        }
    };
}
