package com.example.kk.arttraining.ui.discover.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.example.kk.arttraining.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/9/19.
 * QQ邮箱:515849594@qq.com
 */
public class DiscoverMain extends Activity{
    @InjectView(R.id.iv_title_back)
    ImageView iv_back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_main);
        initView();
    }
   void initView(){
       ButterKnife.inject(this);
       iv_back.setVisibility(View.GONE);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
