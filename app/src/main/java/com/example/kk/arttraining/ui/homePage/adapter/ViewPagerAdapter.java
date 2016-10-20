package com.example.kk.arttraining.ui.homePage.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by kanghuicong on 2016/10/17.
 * QQ邮箱:515849594@qq.com
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<ImageView> mImgList;
    private ViewPager mViewPager;
    private boolean isRunning = false;
    private Context context;
    int CurrentItem = -1;

    public ViewPagerAdapter(Context context,List<ImageView> ImgList, ViewPager viewPager,
                          Boolean istrue) {
        mImgList = ImgList;
        mViewPager = viewPager;
        this.context=context;
        if (istrue.equals(true)) {
            mAutoHandler.sendEmptyMessage(0);
        }
    }

    @Override
    public int getCount() {
        return mImgList.size();
    }
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @SuppressLint("HandlerLeak")
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=mImgList.get(position);
        container.addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.jxkuafu.com");
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(it);
            }
        });
        return mImgList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mImgList.get(position));
    }

    // 开启子线程，进行自动播放广告功能
    private Handler mAutoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            CurrentItem++;
            mViewPager.setCurrentItem(CurrentItem% mImgList.size(), false);
            isRunning = true;
            if (isRunning) {
                mAutoHandler.sendEmptyMessageDelayed(0, 3000);
            }
        }
    };
}
