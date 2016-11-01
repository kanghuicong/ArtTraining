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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.ChooseImageDialogUtil;
import com.example.kk.arttraining.ui.homePage.adapter.PostingImageGridViewAdapter;
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
                    compressfile = compressImage(listfile);
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


    public void showDailog() {
        dialog = new ChooseImageDialogUtil(PostingMain.this, R.layout.homepage_posting_dialog_chooseimg,
                R.style.Dialog, new ChooseImageDialogUtil.LeaveMyDialogListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_takephoto:
                        String pictruename = Randompictrue();
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
        // 设置dialog弹出框显示在底部，并且宽度和屏幕一样
        Window window = dialog.getWindow();
        dialog.show();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    public String Randompictrue(){

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(date);
        Random random=new Random();
        int randNum = random.nextInt(100000-1)+1;
        String Randompictrue=time+randNum;
        return Randompictrue;
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

    //对取回来的图片进行压缩
    private ArrayList<String> compressImage(List<String> list) throws IOException {
        ArrayList<String> imageList = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            File file = new File(list.get(i));
            String compressimage = null;

            String imagepath = list.get(i);
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            long size = fis.available();

            //当图片小于524kb时 不进行图片压缩
            if (size < 524288) {
                compressimage = imagepath;
            } else {

                compressimage = CompressImage.compressBitmap(PostingMain.this, imagepath, 300, 300, true);
            }
            imageList.add(compressimage);
        }
        return imageList;
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
                compressfile = compressImage(listfile);
                Config.ShowImageList=compressfile;
                count = compressfile.size() + 1;
                adapter = new PostingImageGridViewAdapter(PostingMain.this,
                        compressfile, count, bmp);
                noScrollgridview.setAdapter(adapter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == 001&&requestCode==001) {
            int postion = data.getIntExtra("postion", 1);
            compressfile.remove(postion);

            Config.ShowImageList=compressfile;
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
    protected void onStop() {
        super.onStop();
        if (Config.ShowImageList != null) {
            Config.ShowImageList.clear();
        }
    }
}
