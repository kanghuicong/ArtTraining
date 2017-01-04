package com.example.kk.arttraining.ui.homePage.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.AttachmentBean;
import com.example.kk.arttraining.ui.discover.view.ImageViewPagerActivity;
import com.example.kk.arttraining.ui.homePage.activity.ShareDynamicImage;
import com.example.kk.arttraining.utils.LruCacheUtils;
import com.example.kk.arttraining.utils.PhotoLoader;
import com.example.kk.arttraining.utils.ScreenUtils;
import com.example.kk.arttraining.utils.UIUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanghuicong on 2016/10/22.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicImageAdapter extends BaseAdapter {
    private Context context;
    private List<AttachmentBean> attachmentBeanList;
    private AttachmentBean attachmentBean;
    private int width;
    private ArrayList<String> imageList;

    public DynamicImageAdapter(Context context, List<AttachmentBean> attachmentBeanList) {
        this.context = context;
        this.attachmentBeanList = attachmentBeanList;
        width = ScreenUtils.getScreenWidth(context);
        imageList = new ArrayList<>();
        for (int i = 0; i < attachmentBeanList.size(); i++) {
            imageList.add(attachmentBeanList.get(i).getStore_path());
        }
    }

    @Override
    public int getCount() {
        return attachmentBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        attachmentBean = attachmentBeanList.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.homepage_dynamic_image_item, null);
            holder = new ViewHolder();
            holder.grid_image = (ImageView) convertView.findViewById(R.id.iv_dynamic_gridview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        switch (attachmentBeanList.size()) {
            case 1:
                ScreenUtils.accordHeight(holder.grid_image, width, 1, 3);//设置gv的高度
                ScreenUtils.accordWidth(holder.grid_image, width, 1, 2);
                break;
            case 2:
                ScreenUtils.accordHeight(holder.grid_image, width, 1, 2);//设置gv的高度
                break;
            case 3:
                ScreenUtils.accordHeight(holder.grid_image, width, 1, 3);//设置gv的高度
                break;
        }


        String thumbnail=attachmentBean.getThumbnail();

//        Glide.with(context).load(image_path).thumbnail(0.1f).error(R.mipmap.ic_launcher).into(holder.grid_image);
//        采用LruCache缓存回收机制
        Bitmap bitmap = LruCacheUtils.getInstance().getBitmapFromMemCache(thumbnail);
        if (bitmap != null) {
            holder.grid_image.setImageBitmap(bitmap);
        } else {
            PhotoLoader.displayImageTarget(holder.grid_image, thumbnail, PhotoLoader.getTarget(holder.grid_image,
                    thumbnail, position),R.mipmap.default_video_icon);
        }

        holder.grid_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageViewPagerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("imageList", imageList);
                bundle.putInt("position", position);
                intent.putExtras(bundle);
                context.startActivity(intent);
                Activity activity = (Activity) context;
                activity.overridePendingTransition(R.anim.activity_enter_anim, R.anim.activity_exit_anim);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView grid_image;
    }
}
