package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by kanghuicong on 2016/10/29.
 * QQ邮箱:515849594@qq.com
 */
public class Headlines {

    public static Animation anim_in, anim_out;
    public static LinearLayout llContainer;
    public static Handler mHandler;
    public static boolean runFlag = true;
    public static int index = 0;
    //头条
    public static void initHeadlines(View view_homepage, final Activity activity) {
        // TODO Auto-generated method stub
        // 找到装载这个滚动TextView的LinearLayout
        llContainer = (LinearLayout) view_homepage.findViewById(R.id.ll_container);
        anim_in = AnimationUtils.loadAnimation(activity, R.anim.anim_tv_marquee_in);
        anim_out = AnimationUtils.loadAnimation(activity, R.anim.anim_tv_marquee_out);

        final List<String> list = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            list.add("滚动的文字" + i);
        }

        for (int i = 0; i < list.size(); i++) {
            TextView tvTemp = new TextView(activity);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;
            tvTemp.setGravity(Gravity.CENTER);
            tvTemp.setGravity(Gravity.LEFT);
            final String tv = list.get(i);
            tvTemp.setText(list.get(i));
            tvTemp.setSingleLine(true);
            tvTemp.setId(i + 10000);
            tvTemp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIUtil.ToastshowShort(activity, tv);
                }
            });
            llContainer.addView(tvTemp);
        }

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        // 移除
                        TextView tvTemp = (TextView) msg.obj;
                        Log.d("tag", "out->" + tvTemp.getId());
                        tvTemp.startAnimation(anim_out);
                        tvTemp.setVisibility(View.GONE);
                        break;
                    case 1:
                        // 进入
                        TextView tvTemp2 = (TextView) msg.obj;
                        Log.d("tag", "in->" + tvTemp2.getId());
                        tvTemp2.startAnimation(anim_in);
                        tvTemp2.setVisibility(View.VISIBLE);
                        break;
                }
            }
        };
    }

    //头条开始
    public static void startEffect(ExecutorService mThreadService) {
        runFlag = true;
        mThreadService.execute(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (runFlag) {
                    try {
                        // 每隔2秒轮换一次
                        Thread.sleep(3000);
                        // 至于这里还有一个if(runFlag)判断是为什么？大家自己试验下就知道了
                        if (runFlag) {
                            // 获取第index个TextView开始移除动画
                            TextView tvTemp = (TextView) llContainer
                                    .getChildAt(index);
                            mHandler.obtainMessage(0, tvTemp).sendToTarget();
                            if (index < llContainer.getChildCount()) {
                                index++;
                                if (index == llContainer.getChildCount()) {
                                    index = 0;
                                }
                                // index+1个动画开始进入动画
                                tvTemp = (TextView) llContainer
                                        .getChildAt(index);
                                mHandler.obtainMessage(1, tvTemp)
                                        .sendToTarget();
                            }
                        }
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        // 如果有异常，那么停止轮换。当然这种情况很难发生
                        runFlag = false;
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //头条终止
    public static void stopEffect() {
        runFlag = false;
    }
}
