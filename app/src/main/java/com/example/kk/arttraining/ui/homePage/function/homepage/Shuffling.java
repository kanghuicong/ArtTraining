package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.bean.BannerBean;
import com.example.kk.arttraining.custom.view.InnerView;
import com.example.kk.arttraining.ui.homePage.prot.IShuffling;
import com.example.kk.arttraining.utils.GlideRoundTransform;
import com.example.kk.arttraining.utils.UIUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanghuicong on 2016/10/28.
 * QQ邮箱:515849594@qq.com
 * 说明：轮播
 */
public class Shuffling {

    public static void initShuffling(InnerView vpImg, final Context context, List<BannerBean> list) {
//        public static void initShuffling(InnerView vpImg, final Context context) {
        vpImg.startAutoScroll();

        List<ImageView> imgList = new ArrayList<ImageView>();
//        List<String> urlList = new ArrayList<String>();
        List<String> titles = new ArrayList<String>();
        for (int i = 0; i < list.size() ; i++) {
            ImageView img = new ImageView(context);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            UIUtil.showLog("iShuffling-Url", list.get(i).getUrl());
            Glide.with(context).load(list.get(i).getUrl()).into(img);
//            Glide.with(context).load("http://image.baidu.com/search/redirect?tn=redirect&word=j&juid=F82A0C&sign=cibwbbkkbz&url=http%3A%2F%2Fwww.jjl.cn%2Fhushi%2Fzytj%2F772192.shtml&bakfurl1=http%3A%2F%2Fwww.youboy.com%2Fs12643483.html&bakfurl2=http%3A%2F%2Fb2b.youboy.com%2Fshow0cp15827511.html&objurl=http%3A%2F%2Fwww.jjl.cn%2Fhushi%2Fzytj%2Fimages%2F2014%2F04%2F23%2FCB51871A9FEE206AA550A93A7070343F.png").into(img);
            imgList.add(img);
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
