package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.adapter.PostingImageGridViewAdapter;
import com.example.kk.arttraining.ui.homePage.function.posting.ImageGridClick;
import com.example.kk.arttraining.ui.homePage.function.posting.ImageUtil;
import com.example.kk.arttraining.ui.homePage.function.posting.PostingDialog;
import com.example.kk.arttraining.ui.homePage.function.posting.PostingTextChangeListener;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.ProgressDialog;
import com.example.kk.arttraining.utils.TimeDelayClick;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/10/31.
 * QQ邮箱:515849594@qq.com
 */
public class PostingMain extends Activity implements View.OnClickListener, PostingImageGridViewAdapter.PostingCallBack {

    @InjectView(R.id.et_posting_text)
    EditText etPostingText;
    @InjectView(R.id.noScrollgridview)
    GridView noScrollgridview;
    @InjectView(R.id.ll_reshow)
    LinearLayout llReshow;
    @InjectView(R.id.ll_posting_type)
    LinearLayout llPostingType;
    @InjectView(R.id.iv_posting_image)
    ImageView ivPostingImage;
    @InjectView(R.id.iv_posting_video)
    ImageView ivPostingVideo;
    @InjectView(R.id.iv_posting_audio)
    ImageView ivPostingAudio;

    private ProgressDialog progressDialog;
    String success_imagePath;
    String content;
    List<String> listfile = new ArrayList<String>();
    ArrayList<String> compressfile = new ArrayList<String>();
    Bitmap bmp;
    int content_number = 250;
    PostingImageGridViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_posting);
        ButterKnife.inject(this);
        progressDialog = ProgressDialog.show(PostingMain.this, "正在发表");
        TitleBack.PosingTitleBackActivity(this, "发帖", "发布");
        PostingTextChangeListener.getTextChangeListener(this, etPostingText, content_number);
        Bundle bundle = getIntent().getExtras();
        bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_addpic_focused);
        if (bundle != null) {
            if (bundle.get("type").equals("iamge")) ;
            {
                noScrollgridview.setVisibility(View.VISIBLE);
                llPostingType.setVisibility(View.GONE);
                if (bundle.getStringArrayList("files") != null) {
                    listfile = bundle.getStringArrayList("files");
                    try {
                        compressfile = ImageUtil.compressImage(this, listfile);
                        PostingImageGridViewAdapter adapter = new PostingImageGridViewAdapter(PostingMain.this, compressfile, bmp, this);
                        noScrollgridview.setAdapter(adapter);
                        noScrollgridview.setOnItemClickListener(new ImageGridClick(PostingMain.this, compressfile, listfile, etPostingText.getText().toString()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    noScrollgridview.setVisibility(View.GONE);
                    llPostingType.setVisibility(View.VISIBLE);
                }
                if (bundle.getString("evaluate_content") != null) {
                    String content = bundle.getString("evaluate_content");
                    etPostingText.setText(content + "");
                }
            }
        }
    }

    @OnClick({R.id.iv_posting_image, R.id.iv_posting_video, R.id.iv_posting_audio, R.id.tv_title_subtitle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_title_subtitle:
                content = etPostingText.getText().toString();
                if (listfile != null && listfile.size() != 0 && content != null) {
                    if (content.length() < content_number) {
//                        progressDialog.show();
//                        new Thread(runnable).start();
                    } else {
                        UIUtil.ToastshowShort(this, "您输入的内容过长，无法发表...");
                    }
                } else if ((listfile == null || listfile.size() == 0) && content != null && !content.equals("")) {
                    if (content.length() < content_number) {
                        success_imagePath = "";
//                        new Thread(ReleaseShowRunnable).start();
                    } else {
                        UIUtil.ToastshowShort(this, "请输入发布的内容");
                    }
                } else {
                    if (TimeDelayClick.isFastClick(500)) {
                        return;
                    } else {
                        UIUtil.ToastshowShort(this, "请输入发布的内容");
                    }

                }
                break;
            case R.id.iv_posting_image:
                PostingDialog.showDialog(this, listfile, etPostingText.getText().toString());
                break;
            case R.id.iv_posting_video:
                break;
            case R.id.iv_posting_audio:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 102) {
            try {
                compressfile = ImageUtil.compressImage(this, listfile);
                Config.ShowImageList = compressfile;
                adapter = new PostingImageGridViewAdapter(PostingMain.this,
                        compressfile, bmp, this);
                noScrollgridview.setAdapter(adapter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == 001 && requestCode == 001) {
            int postion = data.getIntExtra("postion", 1);
            compressfile.remove(postion);
            Config.ShowImageList = compressfile;
            handler.sendEmptyMessage(0);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter = new PostingImageGridViewAdapter(PostingMain.this,
                    compressfile, bmp, new PostingImageGridViewAdapter.PostingCallBack() {
                @Override
                public void backResult() {
                    noScrollgridview.setVisibility(View.GONE);
                    llPostingType.setVisibility(View.VISIBLE);
                }

                @Override
                public void subResult(List<String> listfile) {
                    noScrollgridview.setOnItemClickListener(new ImageGridClick(PostingMain.this, compressfile, listfile, etPostingText.getText().toString()));
                }
            });
            noScrollgridview.setAdapter(adapter);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Config.ShowImageList != null) {
            Config.ShowImageList.clear();
        }
    }

    @Override
    public void backResult() {
        noScrollgridview.setVisibility(View.GONE);
        llPostingType.setVisibility(View.VISIBLE);

    }

    @Override
    public void subResult(List<String> listfile) {
        noScrollgridview.setOnItemClickListener(new ImageGridClick(PostingMain.this, compressfile, listfile, etPostingText.getText().toString()));
    }
}
