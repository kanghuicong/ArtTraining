package com.example.kk.arttraining.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * 作者：wschenyongyin on 2016/12/28 09:33
 * 说明:
 */
public class PhotoLoader {

    public static void displayImageTarget(final ImageView imageView, final String
            url, BitmapImageViewTarget target) {
        Glide.get(imageView.getContext()).with(imageView.getContext())
                .load(url)
                .asBitmap()//强制转换Bitmap
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .thumbnail(0.5f)
                .into(target);
    }


    /**
     * 获取BitmapImageViewTarget
     */
    public static BitmapImageViewTarget getTarget(ImageView imageView, final String url,
                                            final int position) {
        return new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
                //缓存Bitmap，以便于在没有用到时，自动回收
                LruCacheUtils.getInstance().addBitmapToMemoryCache(url,
                        resource);
            }
        };
    }
}
