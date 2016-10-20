package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.activity.TopicMain;
import com.example.kk.arttraining.utils.UIUtil;

/**
 * Created by kanghuicong on 2016/10/19.
 * QQ邮箱:515849594@qq.com
 */
public class FindTitle {

    //添加标题
    public static void findTitle(View view, final Context context, String tv, int image, final String type) {
        LinearLayout ll_more = (LinearLayout) view.findViewById(R.id.ll_homepage_more);
        TextView title = (TextView) view.findViewById(R.id.tv_homepage_title);
        TextView tv_more = (TextView) view.findViewById(R.id.tv_homepage_more);
        final ImageView iv_more = (ImageView) view.findViewById(R.id.iv_homepage_more);
        title.setText(tv);
        iv_more.setBackgroundResource(image);

        ll_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("authority")) {
                    RotateAnimation ra = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                    ra.setDuration(800);
                    iv_more.startAnimation(ra);
                } else if (type.equals("topic")) {
                    Activity activity = (Activity) context;
                    UIUtil.IntentActivity(activity, new TopicMain());
                }
            }
        });
    }
}
