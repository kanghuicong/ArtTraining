package com.example.kk.arttraining.ui.valuation.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.MusicInfoBean;
import com.example.kk.arttraining.bean.VideoInfoBean;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.valuation.adapter.MediaAdapter;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.utils.MediaUtils;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 作者：wschenyongyin on 2016/10/31 14:48
 * 说明:
 */
public class MediaActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @InjectView(R.id.media_back)
    ImageView mediaBack;
    @InjectView(R.id.media_title)
    TextView mediaTitle;
    @InjectView(R.id.lv_media)
    ListView lvMedia;

    private MediaAdapter mediaAdapter;
    private String media_type;
    private List<MusicInfoBean> musicInfoBeanList;
    private List<VideoInfoBean> videoInfoBeanList;
    MusicInfoBean musicInfoBean;
    VideoInfoBean videoInfoBean;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valuation_media_activity);
        ButterKnife.inject(this);
        init();

    }

    @Override
    public void init() {
        loadingDialog = DialogUtils.createLoadingDialog(MediaActivity.this, "正在加载...");
        Intent intent = getIntent();
        media_type = intent.getStringExtra("media_type");

        switch (media_type) {
            case "video":
                mediaTitle.setText(getString(R.string.valuation_media_video_title));
                loadingDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        videoInfoBeanList = MediaUtils.scanAllVideoFiles(MediaActivity.this);
                        handler.sendEmptyMessage(101);
                    }
                }).start();

                break;

            case "music":
                mediaTitle.setText(getString(R.string.valuation_media_music_title));
                loadingDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        musicInfoBeanList = MediaUtils.scanAllAudioFiles(MediaActivity.this);
                        handler.sendEmptyMessage(102);

                    }
                }).start();

                break;
        }

    }

    @OnClick(R.id.media_back)
    public void onClick(View v) {
        finish();

    }

    @OnItemClick(R.id.lv_media)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String production_path = (String) parent.getItemAtPosition(position);
        Intent intent = new Intent();
        intent.putExtra("production_path", production_path);
        setResult(ValuationMain.CHOSE_PRODUCTION, intent);
        finish();
    }

    Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 101:
                    loadingDialog.dismiss();
                    mediaAdapter = new MediaAdapter(MediaActivity.this, videoInfoBeanList, "video");
                    lvMedia.setAdapter(mediaAdapter);
                    break;

                case 102:
                    loadingDialog.dismiss();
                    mediaAdapter = new MediaAdapter(MediaActivity.this, musicInfoBeanList);
                    lvMedia.setAdapter(mediaAdapter);
                    break;
            }
        }
    };
}
