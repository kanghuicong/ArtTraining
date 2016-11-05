package com.example.kk.arttraining.utils;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;

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
}
