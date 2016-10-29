package com.example.kk.arttraining.ui.homePage.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.AttachmentBean;
import com.example.kk.arttraining.ui.homePage.activity.ShareDynamicImage;
import com.example.kk.arttraining.utils.ScreenUtils;
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
    Context context;
    List<AttachmentBean> attachmentBeanList;
    AttachmentBean attachmentBean;


    public DynamicImageAdapter(Context context, List<AttachmentBean> attachmentBeanList) {
        this.context = context;
        this.attachmentBeanList = attachmentBeanList;
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

        final String image_path = attachmentBean.getThumbnail();
        final ImageLoader imageLoader = ImageLoader.getInstance();
        if(!imageLoader.isInited()) imageLoader.init(ImageLoaderConfiguration.createDefault(context));


        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        imageLoader.displayImage(image_path, holder.grid_image, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {}

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                //加载失败，显示默认图
                holder.grid_image.setImageResource(R.mipmap.ic_launcher);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
            }
        });


        holder.grid_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShareDynamicImage.class);
                intent.putExtra("image_path", image_path);
                intent.putExtra("position", position);
                int[] location = new int[2];
                holder.grid_image.getLocationOnScreen(location);
                intent.putExtra("locationX", location[0]);
                intent.putExtra("locationY", location[1]);
                intent.putExtra("width", holder.grid_image.getWidth());
                intent.putExtra("height", holder.grid_image.getHeight());
                context.startActivity(intent);
                Activity activity = (Activity)context;
                activity.overridePendingTransition(0, 0);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView grid_image;
    }
}
