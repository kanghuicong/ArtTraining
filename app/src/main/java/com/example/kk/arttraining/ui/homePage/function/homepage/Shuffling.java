package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.BannerBean;
import com.example.kk.arttraining.custom.view.InnerView;
import com.example.kk.arttraining.ui.homePage.prot.IShuffling;
import com.example.kk.arttraining.utils.GlideRoundTransform;
import com.example.kk.arttraining.utils.UIUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanghuicong on 2016/10/28.
 * QQ邮箱:515849594@qq.com
 * 说明：轮播
 */
public class Shuffling {

    public static void initShuffling(InnerView vpImg, final Context context, List<BannerBean> list,String state) {
//        vpImg.startAutoScroll();
        List<ImageView> imgList = new ArrayList<ImageView>();
        List<String> titles = new ArrayList<String>();

        if (state.equals("yes")) {
            imgList.clear();
            for (int i = 0; i < list.size(); i++) {
                ImageView img = new ImageView(context);
                img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                UIUtil.showLog("iShuffling-Url", list.get(i).getUrl());
                Glide.with(context).load(list.get(i).getPic()).error(R.mipmap.default_advertisement).into(img);
                imgList.add(img);
                titles.add(list.get(i).getTitle());
            }
            if (list.size()<3){
                ImageView img = new ImageView(context);
                img.setScaleType(ImageView.ScaleType.FIT_XY);
                img.setImageResource(R.mipmap.default_advertisement);
                imgList.add(img);
                titles.add("");
            }
        }else {
            imgList.clear();
            for (int i = 0; i < 4; i++) {
                ImageView img = new ImageView(context);
                img.setScaleType(ImageView.ScaleType.FIT_XY);
                img.setImageResource(R.mipmap.default_advertisement);
                imgList.add(img);
                titles.add("");
            }
        }
        vpImg.setTitlesAndImages(titles, imgList);
        vpImg.setOnLunBoClickListener(new InnerView.OnLunBoClickListener() {
            @Override
            public void clickLunbo(int position) {
                Toast.makeText(context, "点击有效，位置为：" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
