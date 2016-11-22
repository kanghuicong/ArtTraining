package com.example.kk.arttraining.ui.me.view;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/11/15 10:19
 * 说明:订单详情页面
 */
public class OrderDetail extends BaseActivity {
    @InjectView(R.id.tv_title_back)
    TextView tvTitleBack;
    @InjectView(R.id.tv_title_bar)
    TextView tvTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_order_detail);
        ButterKnife.inject(this);
    }

    @Override
    public void init() {

    }

    @OnClick(R.id.tv_title_back)
    public void onClick(View v) {
        finish();
    }
}
