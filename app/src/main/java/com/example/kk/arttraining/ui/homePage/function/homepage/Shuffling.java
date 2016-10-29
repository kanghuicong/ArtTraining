package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.custom.view.InnerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanghuicong on 2016/10/28.
 * QQ邮箱:515849594@qq.com
 * 说明：轮播
 */
public class Shuffling {
    public static void initShuffling(InnerView vpImg, final Context context) {
        vpImg.startAutoScroll();
        final List<ImageView> imgList = new ArrayList<ImageView>();
        for (int i = 0; i < 4; i++) {
            ImageView img = new ImageView(context);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context).load("http://pic76.nipic.com/file/20150823/9448607_122042419000_2.jpg").into(img);
            imgList.add(img);
        }

        String[] titles = {"", "", "", ""};
        vpImg.setTitlesAndImages(titles, imgList);
        vpImg.setOnLunBoClickListener(new InnerView.OnLunBoClickListener() {
            @Override
            public void clickLunbo(int position) {
                Toast.makeText(context, "点击有效，位置为：" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
