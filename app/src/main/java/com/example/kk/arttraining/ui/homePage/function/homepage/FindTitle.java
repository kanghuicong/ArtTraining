package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.view.View;
import android.widget.TextView;

import com.example.kk.arttraining.R;

/**
 * Created by kanghuicong on 2016/10/18.
 * QQ邮箱:515849594@qq.com
 */
public class FindTitle {
    //为测评权威和精选动态添加标题
    public static void findTitle(View view,String tv) {
        TextView title = (TextView) view.findViewById(R.id.tv_homepage_title);
        title.setText(tv);
    }
}
