package com.example.kk.arttraining.ui.discover.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.LoadingDialogTransparent;
import com.example.kk.arttraining.custom.dialog.PopWindowDialogUtil;
import com.example.kk.arttraining.utils.FileUtil;
import com.example.kk.arttraining.utils.RandomUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者：wschenyongyin on 2016/12/22 17:13
 * 说明:浏览图片viewPagerAdapter
 */
public class ImageViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<String> imageList;
    PopWindowDialogUtil popWindowDialogUtil;
    ExecutorService singleThreadPool;

    private LoadingDialogTransparent loadingDialog;

    private String saveUrl;
    private Bitmap saveBitmap = null;

    public ImageViewPagerAdapter(Context context, List<String> imageList) {
        singleThreadPool = Executors.newSingleThreadExecutor();
        this.context = context;
        this.imageList = imageList;
        loadingDialog = LoadingDialogTransparent.getInstance(context);
        loadingDialog.setMessage("");
        loadingDialog.show();
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        PhotoView view = new PhotoView(context);
        view.enable();
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(context.getApplicationContext()).load(imageList.get(position)).thumbnail(0.1f).error(R.mipmap.default_logo).into(new GlideDrawableImageViewTarget(view) {
            @Override
            public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                super.onResourceReady(drawable, anim);
                //在这里添加一些图片加载完成的操作
                loadingDialog.dismiss();
            }
        });
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                svaeImageDialog(imageList.get(position));
                return true;
            }
        });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    //保存图片dialog
    void svaeImageDialog(final String url) {
        popWindowDialogUtil = new PopWindowDialogUtil(context, R.style.transparentDialog, R.layout.dialog_save_image, "dynamicImage", new PopWindowDialogUtil.ChosePicDialogListener() {
            @Override
            public void onClick(View view) {
                popWindowDialogUtil.dismiss();
                switch (view.getId()) {
                    case R.id.bt_dynamic_image_save:
                        saveUrl = url;
                        singleThreadPool.execute(getBitmapRunable);
                        break;
                }
            }
        });
        //设置从底部显示
        Window window = popWindowDialogUtil.getWindow();
        popWindowDialogUtil.show();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    //获取要保存的图片
    Runnable getBitmapRunable = new Runnable() {
        @Override
        public void run() {
            saveBitmap = FileUtil.returnBitmap(saveUrl);
            mHandler.sendEmptyMessage(0);
        }
    };

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (saveBitmap != null) {
                singleThreadPool.execute(saveRunnable);
            }
        }
    };
    //保存到本地相册的线程
    Runnable saveRunnable = new Runnable() {
        @Override
        public void run() {
            saveBitmap(saveBitmap);
        }
    };

    //保存到本地相册
    public void saveBitmap(Bitmap bm) {
        String imageFilePath = Environment
                .getExternalStorageDirectory()
                .getAbsolutePath()
                + "/" + RandomUtils.RandomFileName() + ".jpg";
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
            context.sendBroadcast(localIntent);
            successHandler.sendEmptyMessage(0);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    Handler successHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //设置带图片的toast
            Toast toast = Toast.makeText(context.getApplicationContext(),
                    "保存成功", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            LinearLayout toastView = (LinearLayout) toast.getView();
            ImageView imageCodeProject = new ImageView(context.getApplicationContext());
            imageCodeProject.setImageResource(R.mipmap.save_image_success);
            toastView.addView(imageCodeProject, 0);
            toast.show();
            popWindowDialogUtil.dismiss();
        }
    };
}
