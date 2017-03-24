package com.example.kk.arttraining.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.activity.SearchMain;

/**
 * Created by kanghuicong on 2016/9/22.
 * QQ邮箱:515849594@qq.com
 */
public class TitleBack {
    static TextView tv_title_bar;
    static ImageView iv_title_back;
    static TextView tv_title_subtitle;
    static Drawable drawable;
    //返回，标题
    public static void TitleBackActivity(final Activity activity, String title) {
        tv_title_bar = (TextView) activity.findViewById(R.id.tv_title_bar);
        tv_title_bar.setText(title);
        iv_title_back = (ImageView) activity.findViewById(R.id.iv_title_back);
        iv_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    //返回，标题，图
    public static void toImageBackActivity(final Activity activity, String title, final int image, final String type) {
        tv_title_bar = (TextView) activity.findViewById(R.id.tv_title_bar);
        tv_title_subtitle = (TextView) activity.findViewById(R.id.tv_title_subtitle);
        iv_title_back = (ImageView) activity.findViewById(R.id.iv_title_back);
        tv_title_bar.setText(title);
        drawable = activity.getResources().getDrawable(image);
        tv_title_subtitle.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        iv_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });

        tv_title_subtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_school = new Intent(activity, SearchMain.class);
                intent_school.putExtra("type", type);
                activity.startActivity(intent_school);
            }
        });
    }


    public static void PosingTitleBackActivity(final Activity activity, String title, String subtitle) {

        tv_title_bar = (TextView) activity.findViewById(R.id.tv_title_bar);
        tv_title_bar.setText(title);
        iv_title_back = (ImageView) activity.findViewById(R.id.iv_title_back);
        tv_title_subtitle = (TextView) activity.findViewById(R.id.tv_title_subtitle);
        tv_title_subtitle.setVisibility(View.VISIBLE);
        tv_title_subtitle.setText(subtitle);
        iv_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
                if (Config.ShowImageList != null) {
                    Config.ShowImageList.clear();
                }
            }
        });
    }

    //返回，标题，字
    public static void toTitleBackActivity(final Activity activity, String title, String subtitle) {
        tv_title_bar = (TextView) activity.findViewById(R.id.tv_title_bar);
        tv_title_bar.setText(title);
        iv_title_back = (ImageView) activity.findViewById(R.id.iv_title_back);
        tv_title_subtitle = (TextView) activity.findViewById(R.id.tv_title_subtitle);
        tv_title_subtitle.setText(subtitle);
        iv_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

}
