package com.example.kk.arttraining.ui.valuation.chooseimage;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.activity.ShareDynamicImage;
import com.example.kk.arttraining.ui.homePage.function.posting.PostingDialog;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanghuicong on 2016/11/6.
 * QQ邮箱:515849594@qq.com
 */
public class ProductionImageGridClick implements AdapterView.OnItemClickListener {
    Activity activity;
    int count;
    ArrayList<String> compressfile = new ArrayList<String>();
    String mold;

    public ProductionImageGridClick(Activity activity, ArrayList<String> compressfile,String mold) {
        this.activity = activity;
        this.compressfile = compressfile;
        this.mold = mold;
        UIUtil.showLog("ImageGridClick", "ImageGridClick");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageView imageView = (ImageView) view.findViewById(R.id.gridvieww_image);
        if (position == compressfile.size()) {
            Intent intent = new Intent(activity, ProductionImgFileList.class);
            if (!mold.equals("all")) {
                intent.putExtra("type", "all");
            } else {
                intent.putExtra("type", "onlyOne");
            }
            activity.startActivity(intent);
        } else {
            if (compressfile.size() != 0) {
                String image_path = compressfile.get(position);
                Intent intent = new Intent(activity, ShareDynamicImage.class);
                UIUtil.showLog("ShareDynamicImage",image_path);
                intent.putExtra("image_path", image_path);
                intent.putExtra("position", 0);
                int[] location = new int[2];
                imageView.getLocationOnScreen(location);
                intent.putExtra("locationX", location[0]);
                intent.putExtra("locationY", location[1]);
                intent.putExtra("width", imageView.getWidth());
                intent.putExtra("height", imageView.getHeight());
                activity.startActivity(intent);
                activity.overridePendingTransition(0, 0);
            }
        }
    }
}
