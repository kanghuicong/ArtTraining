package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.app.Activity;
import android.content.Intent;
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
import com.example.kk.arttraining.bean.HeadNews;
import com.example.kk.arttraining.bean.parsebean.HeadNewsListBean;
import com.example.kk.arttraining.ui.homePage.prot.IHomePageMain;
import com.example.kk.arttraining.ui.webview.CourseWebView;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    IHomePageMain iHomePageMain;

    public Headlines(IHomePageMain iHomePageMain) {
        this.iHomePageMain = iHomePageMain;
    }

    public void getHeadNews(String token) {
        Callback<HeadNewsListBean> callback = new Callback<HeadNewsListBean>() {
            @Override
            public void onResponse(Call<HeadNewsListBean> call, Response<HeadNewsListBean> response) {

                if (response.body() != null) {
                    HeadNewsListBean headNewsListBean = response.body();
                    UIUtil.showLog("headlines", headNewsListBean.getError_code());
                    if (headNewsListBean.getError_code().equals("0")) {
                        iHomePageMain.getHeadNews(headNewsListBean.getInformations());
                    } else {
                        iHomePageMain.OnHeadNewsFailure(headNewsListBean.getError_msg());
                    }
                }else {
                    iHomePageMain.OnHeadNewsFailure("onFailure");
                }
            }

            @Override
            public void onFailure(Call<HeadNewsListBean> call, Throwable t) {
                iHomePageMain.OnHeadNewsFailure("onFailure");
            }
        };

        Call<HeadNewsListBean> call = HttpRequest.getCommonApi().headnewsList(token);
        call.enqueue(callback);

    }

    //头条
    public static void initHeadlines(View view_homepage, final Activity activity, List<HeadNews> informations,String state) {
        // TODO Auto-generated method stub
        // 找到装载这个滚动TextView的LinearLayout
        llContainer = (LinearLayout) view_homepage.findViewById(R.id.ll_container);
        anim_in = AnimationUtils.loadAnimation(activity, R.anim.anim_tv_marquee_in);
        anim_out = AnimationUtils.loadAnimation(activity, R.anim.anim_tv_marquee_out);
        int count;

        final List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        if (state.equals("yes")) {
            list.clear();
            for (int i = 0; i < informations.size(); i++) {
                HeadNews headNews = informations.get(i);
                UIUtil.showLog("headNews", headNews.toString());
                Map<String, String> map = new HashMap<String, String>();

                map.put("title", headNews.getTitle());
                map.put("info_id", headNews.getInfo_id() + "");
                list.add(map);
            }
            count = list.size();
        }else {
            list.clear();
            for (int i = 0; i < 3; i++) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("title", "欢迎使用云互艺APP");
                map.put("url", "");
                list.add(map);
            }
            count = 3;
        }

        llContainer.removeAllViews();
        for (int i = 0; i < count; i++) {
            TextView tvTemp = new TextView(activity);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;
            tvTemp.setGravity(Gravity.CENTER);
            tvTemp.setGravity(Gravity.LEFT);
            tvTemp.setTextSize(12);
            tvTemp.setText(list.get(i).get("title"));
            tvTemp.setSingleLine(true);
            tvTemp.setId(i + 10000);

            final String url = list.get(i).get("url");
            if (url != null && !url.equals("")) {
                tvTemp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, CourseWebView.class);
                        intent.putExtra("url", url);
                        intent.putExtra("type", "头条");
                        activity.startActivity(intent);
                    }
                });
            }
            llContainer.addView(tvTemp);
        }
            getHandler();
    }

    public static void getHandler() {
        mHandler = new

                Handler() {
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
    public static void startEffect() {
        runFlag = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (runFlag) {
                    try {
                        // 每隔3秒轮换一次
                        Thread.sleep(3000);
                        if (runFlag) {
                            // 获取第index个TextView开始移除动画
                            TextView tvTemp = (TextView) llContainer.getChildAt(index);
                            mHandler.obtainMessage(0, tvTemp).sendToTarget();
                            if (index < llContainer.getChildCount()) {
                                index++;
                                if (index == llContainer.getChildCount()) {
                                    index = 0;
                                }
                                // index+1个动画开始进入动画
                                tvTemp = (TextView) llContainer.getChildAt(index);
                                mHandler.obtainMessage(1, tvTemp).sendToTarget();
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
        }).start();
    }

    //头条终止
    public static void stopEffect() {
        runFlag = false;
    }
}
