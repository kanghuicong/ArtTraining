package com.example.kk.arttraining.utils;

import android.app.Activity;
import android.content.Intent;
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

    public static void TitleBackActivity(final Activity activity, String title) {
        RelativeLayout rl_title = (RelativeLayout) activity.findViewById(R.id.rl_title);
        TextView tv_title_bar = (TextView) activity.findViewById(R.id.tv_title_bar);
        tv_title_bar.setText(title);
        ImageView iv_title_back = (ImageView) activity.findViewById(R.id.iv_title_back);
        iv_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    public static void schoolTitleBackFragment(final View view, String title,int image) {
        RelativeLayout rl_title = (RelativeLayout) view.findViewById(R.id.rl_title);
        TextView tv_title_bar = (TextView) view.findViewById(R.id.tv_title_bar);
        ImageView iv_title_image = (ImageView) view.findViewById(R.id.iv_title_image);
        ImageView iv_title_back = (ImageView) view.findViewById(R.id.iv_title_back);
        tv_title_bar.setText(title);
        iv_title_back.setVisibility(View.GONE);
        iv_title_image.setImageResource(image);
        iv_title_image.setVisibility(View.VISIBLE);
    }

    public static void SearchBackActivity(final Activity activity, String title, final int image, final String type) {
        RelativeLayout rl_title = (RelativeLayout) activity.findViewById(R.id.rl_title);
        TextView tv_title_bar = (TextView) activity.findViewById(R.id.tv_title_bar);
        ImageView iv_title_image = (ImageView) activity.findViewById(R.id.iv_title_image);
        ImageView iv_title_back = (ImageView) activity.findViewById(R.id.iv_title_back);
        tv_title_bar.setText(title);
        iv_title_image.setImageResource(image);
        iv_title_image.setVisibility(View.VISIBLE);
        iv_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });

        iv_title_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SearchMain.class);
                intent.putExtra("type", type);
                activity.startActivity(intent);
            }
        });
    }

    public static void PosingTitleBackActivity(final Activity activity, String title,String subtitle) {
        RelativeLayout rl_title = (RelativeLayout) activity.findViewById(R.id.rl_title);
        TextView tv_title_bar = (TextView) activity.findViewById(R.id.tv_title_bar);
        tv_title_bar.setText(title);
        ImageView iv_title_back = (ImageView) activity.findViewById(R.id.iv_title_back);
        TextView tv_title_subtitle = (TextView) activity.findViewById(R.id.tv_title_subtitle);
        tv_title_subtitle.setVisibility(View.VISIBLE);
        tv_title_subtitle.setText(subtitle);
        iv_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }
}
