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
import com.example.kk.arttraining.ui.homePage.prot.IAuthority;
import com.example.kk.arttraining.utils.TimeDelayClick;
import com.example.kk.arttraining.utils.UIUtil;

/**
 * Created by kanghuicong on 2016/10/19.
 * QQ邮箱:515849594@qq.com
 */
public class FindTitle {
    static IAuthority iAuthority;

    public FindTitle(IAuthority iAuthority) {
        this.iAuthority = iAuthority;
    }

    //添加Fragment标题
    public static void findTitle(View view, final Context context, String tv, int image, final String type) {
        LinearLayout ll_more = (LinearLayout) view.findViewById(R.id.ll_homepage_more);
        TextView title = (TextView) view.findViewById(R.id.tv_homepage_title);
        TextView tv_more = (TextView) view.findViewById(R.id.tv_homepage_more);
        final ImageView iv_more = (ImageView) view.findViewById(R.id.iv_homepage_more);

        title.setText(tv);
        iv_more.setBackgroundResource(image);

        if (type.equals("authority")) {
            tv_more.setText("换一组");
            LikeAnimatorSet.setLikeImage(context,title,R.mipmap.valuation_authority_icon);
        } else if (type.equals("dynamic_group")){
            tv_more.setVisibility(View.GONE);
            iv_more.setVisibility(View.GONE);
        }else {
            tv_more.setText("更多");
        }


        ll_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type) {
                    case "authority":
                        if (TimeDelayClick.isFastClick(800)) {
                            return;
                        } else {
                            RotateAnimation ra = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                            ra.setDuration(800);
                            iv_more.startAnimation(ra);
                            iAuthority.getAuthorityResult();
                        }
                        break;
                    case "topic":
                        Activity topic_activity = (Activity) context;
                        UIUtil.IntentActivity(topic_activity, new TopicMain());
                        break;
                    case "institution_teacher":
                        break;
                    case "institution_course":
                        break;
                    case "institution_sutdent":
                        break;
                }
            }
        });
    }

    public static void findTitleInstitution() {

    }


    public static TextView findText(View view) {
        TextView tv = (TextView) view.findViewById(R.id.tv_theme);
        return tv;
    }

    public static void initImage(Context context, int image, TextView tv, String text) {
        Drawable drawable = context.getResources().getDrawable(image);
        tv.setText(text);
        tv.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
    }

    public static View findView(View view, int id) {
        View v = (View) view.findViewById(id);
        return v;
    }
}
