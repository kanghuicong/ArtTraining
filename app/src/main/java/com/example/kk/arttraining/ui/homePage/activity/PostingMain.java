package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.ChooseImageDialogUtil;
import com.example.kk.arttraining.ui.homePage.adapter.PostingImageGridViewAdapter;
import com.example.kk.arttraining.ui.homePage.function.posting.ImageUtil;
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
    @InjectView(R.id.fm_reshow)
    FrameLayout fmReshow;
    @InjectView(R.id.ll_cancel)
    LinearLayout llCancel;
    @InjectView(R.id.ll_send)
    LinearLayout llSend;
    @InjectView(R.id.back)
    RelativeLayout back;
    @InjectView(R.id.et_text)
    EditText etText;
    @InjectView(R.id.noScrollgridview)
    GridView noScrollgridview;
    @InjectView(R.id.ll_reshow)
    LinearLayout llReshow;
    @InjectView(R.id.iv_choose_posting_type)
    ImageView ivChoosePostingType;

    private ProgressDialog progressDialog;
    String success_imagePath;
    String content;
    String imageFilePath;
    File temp;
    List<String> listfile = new ArrayList<String>();
    ArrayList<String> compressfile = new ArrayList<String>();
    Bitmap bmp;
    int count;
    private ChooseImageDialogUtil dialog;
    int content_number = 250;
    int max_number = 280;
    PostingImageGridViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_posting);
        ButterKnife.inject(this);
        progressDialog = ProgressDialog.show(PostingMain.this, "正在发表");

        intDatas();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getStringArrayList("files") != null) {
                listfile = bundle.getStringArrayList("files");
                count = listfile.size() + 1;
                try {
                    compressfile = ImageUtil.compressImage(this,listfile);
                    PostingImageGridViewAdapter adapter = new PostingImageGridViewAdapter(
                            PostingMain.this, compressfile, count, bmp);
                    noScrollgridview.setAdapter(adapter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bundle.getString("evaluate_content") != null) {
                String content = bundle.getString("evaluate_content");
                etText.setText(content + "");
            }
        }
    }

    private void intDatas() {
        Resources res = getResources();
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        bmp = BitmapFactory.decodeResource(res, R.mipmap.icon_addpic_focused);
        count = 1;
        adapter = new PostingImageGridViewAdapter(PostingMain.this, bmp, count);
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new GridViewItemOnClick());
    }

    public void showDailog() {
        dialog = new ChooseImageDialogUtil(PostingMain.this, R.layout.homepage_posting_dialog_chooseimg,
                R.style.Dialog, new ChooseImageDialogUtil.LeaveMyDialogListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_takephoto:
                        String pictruename = ImageUtil.Randompictrue();
                        imageFilePath = Environment
                                .getExternalStorageDirectory()
                                .getAbsolutePath()
                                + "/" + pictruename + ".jpg";
                        listfile.add(imageFilePath);

                        Log.e("listfile", listfile.size() + "");
                        temp = new File(imageFilePath);
                        Uri imageFileUri = Uri.fromFile(temp);// 获取文件的Uri
                        Intent it = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);// 跳转到相机Activity
                        it.putExtra(
                                MediaStore.EXTRA_OUTPUT,
                                imageFileUri);// 告诉相机拍摄完毕输出图片到指定的Uri
                        startActivityForResult(it, 102);
                        dialog.dismiss();
                        break;
                    case R.id.btn_picture:
                        Intent intent = new Intent(PostingMain.this,
                                PostingImgFileList.class);
                        intent.putExtra("evaluate_content", etText.getText().toString());
                        startActivity(intent);
                        dialog.dismiss();
                        finish();
                        break;
                    case R.id.btn_cancel:
                        dialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });
        ImageUtil.showDialog(dialog);
    }


    @OnClick({R.id.ll_cancel, R.id.ll_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_send:
                content = etText.getText().toString();
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
            case R.id.ll_cancel:
                finish();
                break;
        }
    }

    public class GridViewItemOnClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
            if (position + 1 == count) {
                showDailog();
            } else {
                if (compressfile.size() != 0) {
                    String image_path = compressfile.get(position);
                    //放大
                }
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}
    @Override
    public void afterTextChanged(Editable s) {
        if (etText.length() > content_number) {
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
