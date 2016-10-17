package com.example.kk.arttraining.ui.me;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/9/23 14:58
 * 说明:传输列表页面
 */
public class TransforListActivity extends BaseActivity {
    private FragmentManager fManager;
    private UploadFragment uploadFragment;
    private DownloadFragment downloadFragment;
    private int gray = 0xFF7597B3;
    private int whirt = 0xFFFFFFFF;

    @InjectView(R.id.fl_transfor)
    FrameLayout fl_transfor;
    @InjectView(R.id.tv_download)
    TextView tv_download;
    @InjectView(R.id.tv_upload)
    TextView tv_upload;
    @InjectView(R.id.title_back)
    ImageView img_back;
    @InjectView(R.id.title_barr)
    TextView title_barr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_transfor_activity);
        init();

    }

    @Override
    public void init() {
        fManager=getFragmentManager();
        DefaultShow();
        ButterKnife.inject(this);
        title_barr.setText("传输列表");

        tv_download.setOnClickListener(this);
        tv_upload.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_download:
                setChioceItem(0);
                break;
            case R.id.tv_upload:
                setChioceItem(1);
                break;
            case R.id.title_back:
                finish();
                break;
        }

    }


    public void setChioceItem(int index) {

        FragmentTransaction transaction = fManager.beginTransaction();
        clearChioce();
        hideFragments(transaction);
        switch (index) {
            case 0:
//                img_homepage.setImageResource(R.drawable.tab_run_yes);
                tv_download.setBackgroundColor(Color.GREEN);
                // rv_lesson.setBackgroundResource(R.drawable.);
                if ( downloadFragment== null) {

                    downloadFragment = new DownloadFragment();
                    transaction.add(R.id.fl_transfor, downloadFragment);
                } else {

                    transaction.show(downloadFragment);
                }
                break;

            case 1:
                tv_upload.setBackgroundColor(Color.GREEN);
                // rv_lesson.setBackgroundResource(R.drawable.);
                if (uploadFragment == null) {

                    uploadFragment = new UploadFragment();
                    transaction.add(R.id.fl_transfor, uploadFragment);
                } else {

                    transaction.show(uploadFragment);
                }
                break;


        }
        transaction.commit();
    }

    // 隐藏fragment
    private void hideFragments(FragmentTransaction transaction) {
        if (downloadFragment != null) {
            transaction.hide(downloadFragment);
        }
        if (uploadFragment != null) {
            transaction.hide(uploadFragment);
        }

    }

    public void clearChioce() {
        tv_download.setBackgroundColor(whirt);
        tv_upload.setBackgroundColor(whirt);
    }

    private void DefaultShow() {

        downloadFragment = new DownloadFragment();

        FragmentManager fm = getFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.fl_transfor, downloadFragment);

        ft.commit();
    }
}
