package com.example.kk.arttraining.ui.me;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.utils.CompressImage;
import com.example.kk.arttraining.utils.FileUtil;
import com.example.kk.arttraining.utils.UIUtil;
import com.example.kk.arttraining.utils.UploadUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/9/22 11:37
 * 说明:
 */
public class AboutActivity extends BaseActivity {
    @InjectView(R.id.title_back)
    ImageView btn_bcak;
    @InjectView(R.id.title_barr)
    TextView title_bar;
    @InjectView(R.id.img_userheader)
    ImageView user_header;

    private List<File> fileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_about_activity);
        init();
    }

    @Override
    public void init() {
        ButterKnife.inject(this);
        choseImage();
        title_bar.setText("关于");
        btn_bcak.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }


    public void choseImage() {

        Intent intent = new Intent(Intent.ACTION_PICK
        );
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 103);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK && requestCode == 103) {// 是否选择，没选择就不会继续
            String img_path;
            Uri uri = data.getData();// 得到uri，后面就是将uri转化成file的过程。
            Log.i("uri",uri+"");
//            startPhotoZoom(uri);

            String type = FileUtil.getFileType(uri.toString());
            if (type.equals("txt") || type.equals("zip") || type.equals("mp3")|| type.equals("mp4")|| type.equals("mkv")) {
                int start = uri.toString().lastIndexOf(":");
                img_path = uri.toString().substring(start + 1);

            } else {
                String[] proj = {MediaStore.Images.Media.DATA};


                Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
                int actual_image_column_index = actualimagecursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                img_path = actualimagecursor
                        .getString(actual_image_column_index);
            }
            File file = new File(img_path);
            Callback callback = new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.body() != null) UIUtil.showLog("chenggss", response.toString());
                }

                @Override
                public void onFailure(Call call, Throwable t) {

                }
            };
            fileList=new ArrayList<File>();
            for (int i=0;i<3;i++)fileList.add(file);
            UploadUtils.uploadFiles(fileList, callback);


        } else if (requestCode == 002) {

            Bundle bundle = data.getExtras();
            if (bundle != null) {
                //这里也可以做文件上传
                Bitmap mBitmap = bundle.getParcelable("data");
                user_header.setImageBitmap(mBitmap);
            }
        }
    }


    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true); //黑边
        intent.putExtra("scaleUpIfNeeded", true); //黑边
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, 002);

    }
}

