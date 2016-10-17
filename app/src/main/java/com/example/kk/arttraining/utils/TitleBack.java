package com.example.kk.arttraining.utils;

import android.app.Activity;
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
}
