package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.bean.BannerBean;
import com.example.kk.arttraining.custom.view.InnerView;
import com.example.kk.arttraining.ui.homePage.prot.IShuffling;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanghuicong on 2016/10/28.
 * QQ邮箱:515849594@qq.com
 * 说明：轮播
 */
public class Shuffling {

//    public static void initShuffling(InnerView vpImg, final Context context, List<BannerBean> list) {
        public static void initShuffling(InnerView vpImg, final Context context) {
        vpImg.startAutoScroll();

//        UIUtil.showLog("iShuffling", list + "----");
        List<ImageView> imgList = new ArrayList<ImageView>();
        List<String> titles = new ArrayList<String>();
        for (int i = 0; i < 3 ; i++) {
            ImageView img = new ImageView(context);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
//            UIUtil.showLog("iShuffling-Url", list.get(i).getUrl());
//            Glide.with(context).load(list.get(i).getUrl()).into(img);
            Glide.with(context).load("http://img1.imgtn.bdimg.com/it/u=3144465310,4114570573&fm=21&gp=0.jpg").into(img);

            imgList.add(img);
//            titles.add(list.get(i).getTitle());
            titles.add(i+"");
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
