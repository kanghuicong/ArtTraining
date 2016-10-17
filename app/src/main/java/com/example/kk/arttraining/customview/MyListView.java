package com.example.kk.arttraining.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by kanghuicong on 2016/9/19.
 * QQ邮箱:515849594@qq.com
 */
public class MyListView extends ListView {

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public MyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     */
    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context
     */
    public MyListView(Context context) {
        super(context);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
