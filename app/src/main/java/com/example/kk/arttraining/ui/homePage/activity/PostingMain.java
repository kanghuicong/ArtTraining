package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.ChooseImageDialogUtil;
import com.example.kk.arttraining.ui.homePage.adapter.PostingImageGridViewAdapter;
import com.example.kk.arttraining.ui.homePage.function.posting.ImageGridClick;
import com.example.kk.arttraining.ui.homePage.function.posting.ImageUtil;
import com.example.kk.arttraining.ui.homePage.function.posting.PostingPopWindow;
import com.example.kk.arttraining.utils.CompressImage;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.ProgressDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/10/31.
 * QQ邮箱:515849594@qq.com
 */
public class PostingMain extends Activity implements View.OnClickListener, TextWatcher {

    @InjectView(R.id.et_posting_text)
    EditText etPostingText;
    @InjectView(R.id.noScrollgridview)
    GridView noScrollgridview;
    @InjectView(R.id.ll_reshow)
    LinearLayout llReshow;
    @InjectView(R.id.iv_choose_posting_type)
    ImageView ivChoosePostingType;

    private ProgressDialog progressDialog;
    String success_imagePath;
    String content;
    List<String> listfile = new ArrayList<String>();
    ArrayList<String> compressfile = new ArrayList<String>();
    Bitmap bmp;
    int count;
    int content_number = 250;
    int max_number = 280;
    PostingImageGridViewAdapter adapter;
    PopupWindow window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_posting);
        ButterKnife.inject(this);
        progressDialog = ProgressDialog.show(PostingMain.this, "正在发表");



        intDatas();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.get("type").equals("iamge"));
            {
                noScrollgridview.setVisibility(View.VISIBLE);
                ivChoosePostingType.setVisibility(View.GONE);
                if (bundle.getStringArrayList("files") != null) {
                    listfile = bundle.getStringArrayList("files");
                    count = listfile.size() + 1;
                    try {
                        compressfile = ImageUtil.compressImage(this, listfile);
                        PostingImageGridViewAdapter adapter = new PostingImageGridViewAdapter(PostingMain.this, compressfile, count, bmp);
                        noScrollgridview.setAdapter(adapter);
                        noScrollgridview.setOnItemClickListener(new ImageGridClick(PostingMain.this,count,compressfile,listfile,etPostingText.getText().toString()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bundle.getString("evaluate_content") != null) {
                    String content = bundle.getString("evaluate_content");
                    etPostingText.setText(content + "");
                }
            }
        }
    }

    private void intDatas() {
        Resources res = getResources();
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        bmp = BitmapFactory.decodeResource(res, R.mipmap.icon_addpic_focused);
        count = 1;
        adapter = new PostingImageGridViewAdapter(PostingMain.this, bmp, count);
        //图片操作
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new ImageGridClick(PostingMain.this,count,compressfile,listfile,etPostingText.getText().toString()));
    }

    @OnClick({R.id.bt_posting_send,R.id.iv_choose_posting_type})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_posting_send:
                content = etPostingText.getText().toString();
                if (listfile != null && listfile.size() != 0 && content != null) {
                    if (content.length() < content_number) {
                        progressDialog.show();
//                        new Thread(runnable).start();
                    } else {
                        Toast.makeText(this, "您输入的内容过长，无法发表...", Toast.LENGTH_SHORT).show();
                    }
                } else if ((listfile == null || listfile.size() == 0) && content != null && !content.equals("")) {
                    if (content.length() < content_number) {
                        success_imagePath = "";
//                        new Thread(ReleaseShowRunnable).start();
                    } else {
                        Toast.makeText(this, "您输入的内容过长，无法发表...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PostingMain.this, "请输入发布的内容",
                            Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.iv_choose_posting_type:
                PostingPopWindow.showPopWindows(this,ivChoosePostingType,noScrollgridview);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}
    @Override
    public void afterTextChanged(Editable s) {
        if (etPostingText.length() > content_number) {
            Toast.makeText(this, "内容太长，无法发表...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 102) {
            try {
                compressfile = ImageUtil.compressImage(this,listfile);
                Config.ShowImageList = compressfile;
                count = compressfile.size() + 1;
                adapter = new PostingImageGridViewAdapter(PostingMain.this,
                        compressfile, count, bmp);
                noScrollgridview.setAdapter(adapter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == 001 && requestCode == 001) {
            int postion = data.getIntExtra("postion", 1);
            compressfile.remove(postion);
            Config.ShowImageList = compressfile;
            count = compressfile.size() + 1;
            handler.sendEmptyMessage(0);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter = new PostingImageGridViewAdapter(PostingMain.this,
                    compressfile, count, bmp);
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
}
