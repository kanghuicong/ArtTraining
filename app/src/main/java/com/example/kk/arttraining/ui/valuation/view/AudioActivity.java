package com.example.kk.arttraining.ui.valuation.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/10/31 20:51
 * 说明:
 */
public class AudioActivity extends BaseActivity {

    @InjectView(R.id.audio_image_bg)
    ImageView audioImageBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valuation_audio_activity);
        ButterKnife.inject(this);
        Glide.with(this).load("http://img.mp.itc.cn/upload/20160507/a365569e40f24f158b1ff716e676f825_th.jpg").asGif().into(audioImageBg);
//        Glide.with(this).load("http://img.zcool.cn/community/016802564476186ac7259e0f9c2b56.gif").asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(audioImageBg);


    }

    @Override
    public void init() {

    }

    @Override
    public void onClick(View v) {

    }
}
