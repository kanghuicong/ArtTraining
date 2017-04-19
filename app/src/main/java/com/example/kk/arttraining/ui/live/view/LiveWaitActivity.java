package com.example.kk.arttraining.ui.live.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.activity.ThemeTeacherContent;
import com.example.kk.arttraining.ui.live.adapter.LiveChapterAdapter;
import com.example.kk.arttraining.ui.homePage.bean.LiveChapterBean;
import com.example.kk.arttraining.ui.homePage.bean.LiveWaitBean;
import com.example.kk.arttraining.ui.homePage.function.live.LiveType;
import com.example.kk.arttraining.ui.homePage.function.live.LiveWaitData;
import com.example.kk.arttraining.ui.homePage.prot.ILiveWait;
import com.example.kk.arttraining.utils.DateUtils;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by kanghuicong on 2017/1/18.
 * QQ邮箱:515849594@qq.com
 */
public class LiveWaitActivity extends Activity implements ILiveWait{

    LiveWaitData liveBeforeData;
//    LiveBuyData liveBuyData;
    int room_id;
    int chapter_id;
    int tec_id;
    int DAY;
    int HOUR;
    int MINUTE;
    int recMinute = 0;
    int recHour = 0;
    int recDay = 0;
    boolean dayType = true;
    boolean hourType = true;
    boolean minuteType = true;

    LiveChapterAdapter liveWaitChapterAdapter;

    double price = 0.0;
    String buy_type = "record";

    private Subscription queryCloudSub;

    @InjectView(R.id.iv_liveBefore_pic)
    ImageView ivLiveBeforePic;
    @InjectView(R.id.tv_liveBefore_name)
    TextView tvLiveBeforeName;
    @InjectView(R.id.tv_liveBefore_like_num)
    TextView tvLiveBeforeLikeNum;
    @InjectView(R.id.tv_liveBefore_days)
    TextView tvLiveBeforeDays;
    @InjectView(R.id.tv_liveBefore_hours)
    TextView tvLiveBeforeHours;
    @InjectView(R.id.tv_liveBefore_minute)
    TextView tvLiveBeforeMinute;
    @InjectView(R.id.tv_liveBefore_chapter)
    TextView tvLiveBeforeChapter;
    @InjectView(R.id.tv_liveBefore_join)
    TextView tvLiveBeforeJoin;
    @InjectView(R.id.gv_liveBefore_chapter)
    GridView gvLiveBeforeChapter;
    @InjectView(R.id.tv_chapter_title)
    TextView tvChapterTitle;

    List<LiveChapterBean> liveChapterList = new ArrayList<LiveChapterBean>();
    int isFree = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_wait);
        ButterKnife.inject(this);

        TitleBack.toTitleBackActivity(this, "直播", "课程列表");

        room_id = getIntent().getIntExtra("room_id", 0);
        chapter_id = getIntent().getIntExtra("chapter_id", 0);

        liveBeforeData = new LiveWaitData(this);
        liveBeforeData.getLiveWaitData(room_id, chapter_id);

//        liveBuyData = new LiveBuyData(this);

    }

    @Override
    public void getLiveWait(LiveWaitBean liveWaitBean) {
        tec_id = liveWaitBean.getOwner();
        Glide.with(this).load(liveWaitBean.getHead_pic()).error(R.mipmap.default_user_header).into(ivLiveBeforePic);
        tvLiveBeforeName.setText(liveWaitBean.getName());
        tvLiveBeforeLikeNum.setText(liveWaitBean.getLike_number() + "");
        tvLiveBeforeChapter.setText("《" + liveWaitBean.getChapter_name() + "》");

        if (liveWaitBean.getLive_price() > 0) {
            isFree = 0;
            if (liveWaitBean.getOrder_status() == 0) {
                tvLiveBeforeJoin.setBackgroundResource(R.drawable.bt_click);
                tvLiveBeforeJoin.setText("购买（¥" + liveWaitBean.getLive_price() + "）");
            }else {
                tvLiveBeforeJoin.setText("等待开播");
            }
        } else {
            isFree = 1;
            tvLiveBeforeJoin.setText("免费观看");
        }

        long pre_time = DateUtils.getStringToDate(liveWaitBean.getPre_time());
        long curr_time = DateUtils.getStringToDate(liveWaitBean.getCurr_time());
        long rentTime = pre_time - curr_time;

        if (rentTime > 0) {
            DAY = DateUtils.getDay(rentTime);
            HOUR = DateUtils.getHours(rentTime, DAY);
            MINUTE = DateUtils.getMinute(rentTime, DAY, HOUR);
            handler.postDelayed(runnable, 0);
        } else {
            tvLiveBeforeDays.setText("00");
            tvLiveBeforeHours.setText("00");
            tvLiveBeforeMinute.setText("00");
        }

        if (liveWaitBean.getChapter_list().isEmpty()) {
            tvChapterTitle.setVisibility(View.GONE);
        }


        LiveChapterBean liveChapterBean = new LiveChapterBean();
        liveChapterBean.setIs_free(isFree);
        liveChapterBean.setChapter_name(liveWaitBean.getChapter_name());
        liveChapterBean.setChapter_id(liveWaitBean.getChapter_id());
        liveChapterBean.setOrder_status(liveWaitBean.getOrder_status());
        liveChapterBean.setLive_price(liveWaitBean.getLive_price());
        liveChapterBean.setRecord_price(liveWaitBean.getRecord_price());
        liveWaitBean.getChapter_list().add(liveChapterBean);
        liveChapterList.addAll(liveWaitBean.getChapter_list());

        liveWaitChapterAdapter = new LiveChapterAdapter(this, liveChapterList, liveChapterList.size()-1,liveWaitBean.getCurr_time());
        gvLiveBeforeChapter.setAdapter(liveWaitChapterAdapter);
        gvLiveBeforeChapter.setOnItemClickListener(new WaitChapterItemClick());
    }

    //倒计时
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (MINUTE - recMinute >= 0) {
                getTime(tvLiveBeforeMinute, MINUTE - recMinute);
                getTime(tvLiveBeforeHours, HOUR - recHour);
                getTime(tvLiveBeforeDays, DAY - recDay);
                recMinute++;
            } else {
                if (hourType) {
                    tvLiveBeforeMinute.setText("59");
                    MINUTE = 59;
                } else {
                    tvLiveBeforeMinute.setText("00");
                    minuteType = false;
                }
                recMinute = 0;
                recHour++;
                if (hourType) {
                    if (HOUR - recHour >= 0) {
                        getTime(tvLiveBeforeDays, DAY - recDay);
                        getTime(tvLiveBeforeHours, HOUR - recHour);
                    } else {
                        if (dayType) {
                            tvLiveBeforeHours.setText("23");
                            HOUR = 23;
                        } else {
                            tvLiveBeforeHours.setText("00");
                            hourType = false;
                        }
                        recHour = 0;
                        recDay++;
                        if (dayType) {
                            if (DAY - recDay >= 0) {
                                getTime(tvLiveBeforeDays, DAY - recDay);
                            } else {
                                tvLiveBeforeDays.setText("00");
                                dayType = false;
                            }
                        }
                    }
                }
            }
            if (minuteType) {
                handler.postDelayed(this, 60000);
            } else {
                handler.removeCallbacks(runnable);
            }
        }
    };

    public void getTime(TextView tv, int time) {
        if (time >= 0 && time < 10) {
            tv.setText("0" + (time));
        } else {
            tv.setText(time + "");
        }
    }

    @Override
    public void OnLiveWaitFailure(String result) {
        UIUtil.ToastshowShort(this, result);
    }

    @OnClick({R.id.iv_liveBefore_pic, R.id.tv_liveBefore_join,R.id.tv_title_subtitle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_liveBefore_pic:
                Intent intent = new Intent(this, ThemeTeacherContent.class);
                intent.putExtra("tec_id", tec_id + "");
                startActivity(intent);
                break;
            case R.id.tv_liveBefore_join:
                payChapter(liveChapterList.size() - 1);
                break;
            case R.id.tv_title_subtitle:
                Intent intentCourse = new Intent(this, LiveCourseActivity.class);
                intentCourse.putExtra("room_id", room_id);
                startActivity(intentCourse);
                break;
        }
    }

    private class WaitChapterItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            payChapter(position);
        }
    }

    public void payChapter(int position) {
        switch (liveWaitChapterAdapter.getChapterFree(position)) {
            case 0://付费
                switch (liveWaitChapterAdapter.getOrderStatus(position)) {
                    case 0: //未购买
//                        liveBuyData.getCouldData(position);
                        switch (liveWaitChapterAdapter.getChapterType(position)) {
                            case 2:
                                price = liveWaitChapterAdapter.getChapterRecordPrice(position);
                                buy_type = "record";
                                break;
                            default:
                                price = liveWaitChapterAdapter.getChapterLivePrice(position);
                                buy_type = "live";
                                break;
                        }
                        Intent intent = new Intent(this, LivePayActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("chapter_name", liveWaitChapterAdapter.getChapterName(position));
                        bundle.putDouble("live_price",price);
                        bundle.putString("buy_type", buy_type);
                        bundle.putInt("room_id", room_id);
                        bundle.putInt("chapter_id", chapter_id);
                        intent.putExtras(bundle);
                        intent.putExtra("liveType", "liveWait");
                        startActivity(intent);
                        break;
                    case 1://已购买
                        CheckChapter(position);
                        break;
                }
                break;
            case 1://免费
                CheckChapter(position);
                break;
        }
    }

    //云币查询成功后，继续支付
//    @Override
//    public void getCloud(Double aDouble, int position) {
//        switch (liveWaitChapterAdapter.getChapterType(position)) {
//            case 2:
//                price = liveWaitChapterAdapter.getChapterRecordPrice(position);
//                buy_type = "record";
//                break;
//            default:
//                price = liveWaitChapterAdapter.getChapterLivePrice(position);
//                buy_type = "live";
//                break;
//        }
//        MyDialog.getChapterDialog(LiveWaitActivity.this, price, aDouble, new MyDialog.IChapter() {
//            @Override
//            public void getBuyChapter() {//购买章节
//                liveBuyData.getPayCould(room_id, liveWaitChapterAdapter.getChapterId(position), buy_type);
//            }
//
//            @Override
//            public void getRecharge() {//充值云币
//                startActivity(new Intent(LiveWaitActivity.this, RechargeICloudActivity.class));
//            }
//        });
//    }
//
//    @Override
//    public void getPayCloud() {
//        UIUtil.ToastshowShort(this, "购买成功");
//    }
//
//    @Override
//    public void onFailure(String code, String msg) {
//        UIUtil.ToastshowShort(this, msg);
//    }

    public void CheckChapter(int position) {
        LiveType.getLiveListType(this,liveWaitChapterAdapter.getChapterType(position),room_id,liveWaitChapterAdapter.getChapterId(position),liveWaitChapterAdapter.getChapterRecord(position));
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        liveBuyData.cancelSubscription();
//    }

}
