package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.adapter.ViewPagerAdapter;

import java.util.List;

/**
 * Created by kanghuicong on 2016/10/17.
 * QQ邮箱:515849594@qq.com
 */
public class HomepageViewPager {
    private static int mPreviousPos;
    private static ViewPager viewpager;

    public static void initViewpager(ViewPager vp, Activity activity, final List<ImageView> imgList, final LinearLayout layout){
        viewpager =vp;
        vp.setAdapter(new ViewPagerAdapter(activity, imgList, vp, true));
        vp.setCurrentItem(Integer.MAX_VALUE / 2);
        vp.setCurrentItem(imgList.size() * 10000);
        // 设置滑动监听
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            // 某个页面被选中
            @Override
            public void onPageSelected(int position) {
                int pos = position % imgList.size();
                // 更新小圆点
                layout.getChildAt(pos).setEnabled(true);// 将选中的页面的圆点设置为红色
                // 将上一个圆点变为灰色
                layout.getChildAt(mPreviousPos).setEnabled(false);
                // 更新上一个页面位置
                mPreviousPos = pos;
            }
            // 滑动过程中
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            // 滑动状态变化
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        // 动态添加5个小圆点
        for (int i = 0; i < imgList.size(); i++) {
            ImageView view = new ImageView(activity);
            view.setImageResource(R.mipmap.ic_launcher);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i != 0) {// 从第2个圆点开始设置左边距, 保证圆点之间的间距
                params.leftMargin = 6;
                view.setEnabled(false);// 设置不可用, 变为灰色圆点
            }
            view.setLayoutParams(params);
            layout.addView(view);
        }
        // 延时2秒更新广告条的消息
        mHandler.sendEmptyMessageDelayed(0, 3000);
        vp.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.removeCallbacksAndMessages(null);// 清除所有消息和Runnable对象
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mHandler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_UP:
                        // 继续轮播广告
                        mHandler.sendEmptyMessageDelayed(0, 3000);
                        break;
                    default:
                        break;
                }
                return false;// 返回false, 让viewpager原生触摸效果正常运行
            }
        });
    }

    private static Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int currentItem = viewpager.getCurrentItem();// 获取当前页面位置
            viewpager.setCurrentItem(++currentItem);// 跳到下一个页面
            // 继续发送延时2秒的消息, 形成类似递归的效果, 使广告一直循环切换
            mHandler.sendEmptyMessageDelayed(0, 2000);
        }
    };
}
