package com.example.kk.arttraining.ui.me;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/9/22 11:37
 * 说明:
 */
public class AboutActivity extends BaseActivity {
    @InjectView(R.id.title_back)
    ImageView btn_bcak;
    @InjectView(R.id.title_barr)
    TextView title_bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_about_activity);
    }

    @Override
    public void init() {
        ButterKnife.inject(this);
        title_bar.setText("关于");
        btn_bcak.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
