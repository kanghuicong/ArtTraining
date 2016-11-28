package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.SaveImageDialogUtil;
import com.example.kk.arttraining.custom.view.SmoothImageView;
import com.example.kk.arttraining.utils.UIUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by kanghuicong on 2016/10/22.
 * QQ邮箱:515849594@qq.com
 */
public class ShareDynamicImage extends Activity {

    private int mLocationX;
    private int mLocationY;
    private int mWidth;
    private int mHeight;
    SmoothImageView imageView = null;
    private static String image_path;
    Bitmap imageBitmap;
    Toast toast;
    SaveImageDialogUtil diaLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        image_path = getIntent().getStringExtra("image_path");
        mLocationX = getIntent().getIntExtra("locationX", 0);
        mLocationY = getIntent().getIntExtra("locationY", 0);
        mWidth = getIntent().getIntExtra("width", 0);
        mHeight = getIntent().getIntExtra("height", 0);

        imageView = new SmoothImageView(this);
        imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
        imageView.transformIn();
        imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setContentView(imageView);
        if (image_path == null) {
            //失败加载默认图
            imageView.setImageResource(R.mipmap.ic_launcher);
        } else {
            final ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(ShareDynamicImage.this));
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisc(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
            imageLoader.displayImage(image_path, imageView, options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {
                    //失败加载默认图

                    imageView.setImageResource(R.mipmap.ic_launcher);
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    imageBitmap = bitmap;
                }

                @Override
                public void onLoadingCancelled(String s, View view) {
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancel();
                }
            });
            //长按保存
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    diaLog=new SaveImageDialogUtil(ShareDynamicImage.this, R.layout.dialog_save_image,R.style.transparentDialog, new SaveImageDialogUtil.SaveImageClick() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()){
                                case R.id.bt_save_image_logout:
                                    saveBitmap(imageBitmap);
                                    break;
                                case R.id.bt_save_image_cancel:
                                    diaLog.dismiss();
                                    break;
                            }
                        }
                    });
                    Window window = diaLog.getWindow();
                    diaLog.show();
                    window.setGravity(Gravity.BOTTOM);
                    window.getDecorView().setPadding(0, 0, 0, 0);
                    WindowManager.LayoutParams lp = window.getAttributes();
                    lp.width = WindowManager.LayoutParams.FILL_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(lp);

                    return true;
                }
            });
        }
    }

    private void cancel() {
        imageView.setOnTransformListener(new SmoothImageView.TransformListener() {
            @Override
            public void onTransformComplete(int mode) {
                if (mode == 2) {
                    finish();
                }
            }
        });
        imageView.transformOut();
    }

    @Override
    public void onBackPressed() {
        imageView.setOnTransformListener(new SmoothImageView.TransformListener() {
            @Override
            public void onTransformComplete(int mode) {
                if (mode == 2) {
                    finish();
                }
            }
        });
        imageView.transformOut();
    }


    public void saveBitmap(Bitmap bm) {
        String imageFilePath = Environment
                .getExternalStorageDirectory()
                .getAbsolutePath()
                + "/" + getRandomInt() + ".jpg";
        File f = new File(imageFilePath);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            //刷新相册
            Uri localUri = Uri.fromFile(f);
            Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
            sendBroadcast(localIntent);
            out.flush();
            out.close();

            //设置带图片的toast
            toast = Toast.makeText(getApplicationContext(),
                    "保存成功", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            LinearLayout toastView = (LinearLayout) toast.getView();
            ImageView imageCodeProject = new ImageView(getApplicationContext());
            imageCodeProject.setImageResource(R.mipmap.save_image_success);
            toastView.addView(imageCodeProject, 0);
            toast.show();
            diaLog.dismiss();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public int getRandomInt(){
        Random random=new Random();
        int randNum = random.nextInt(1000000000-1)+1;
        return randNum;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(0, 0);
        }
    }
}

