package com.example.kk.arttraining.ui.live.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.activity.ThemeTeacherContent;
import com.example.kk.arttraining.ui.homePage.adapter.LiveChapterAdapter;
import com.example.kk.arttraining.ui.homePage.bean.LiveWaitBean;
import com.example.kk.arttraining.ui.homePage.function.live.LiveWaitData;
import com.example.kk.arttraining.ui.homePage.prot.ILiveWait;
import com.example.kk.arttraining.utils.DateUtils;
import com.example.kk.arttraining.utils.UIUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2017/1/18.
 * QQ邮箱:515849594@qq.com
 */
public class LiveWaitActivity extends Activity implements ILiveWait {

    LiveWaitData liveBeforeData;
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

    @InjectView(R.id.iv_liveBefore_pic)
    ImageView ivLiveBeforePic;
    @InjectView(R.id.tv_liveBefore_name)
    TextView tvLiveBeforeName;
    @InjectView(R.id.tv_liveBefore_like_num)
    TextView tvLiveBeforeLikeNum;
    @InjectView(R.id.iv_liveBefore_back)
    ImageView ivLiveBeforeBack;
    @InjectView(R.id.tv_liveBefore_days)
    TextView tvLiveBeforeDays;
    @InjectView(R.id.tv_liveBefore_hours)
    TextView tvLiveBeforeHours;
    @InjectView(R.id.tv_liveBefore_minute)
    TextView tvLiveBeforeMinute;
    @InjectView(R.id.tv_liveBefore_chapter)
    TextView tvLiveBeforeChapter;
    @InjectView(R.id.bt_liveBefore_join)
    Button btLiveBeforeJoin;
    @InjectView(R.id.gv_liveBefore_chapter)
    GridView gvLiveBeforeChapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_wait);
        ButterKnife.inject(this);

        room_id = getIntent().getIntExtra("room_id", 0);
        chapter_id = getIntent().getIntExtra("chapter_id", 0);

        liveBeforeData = new LiveWaitData(this);
        liveBeforeData.getLiveWaitData(room_id, chapter_id);

    }

    @Override
    public void getLiveWait(LiveWaitBean liveWaitBean) {
        tec_id = liveWaitBean.getOwner();
        Glide.with(this).load(liveWaitBean.getHead_pic()).error(R.mipmap.default_user_header).into(ivLiveBeforePic);
        tvLiveBeforeName.setText(liveWaitBean.getName());
        tvLiveBeforeLikeNum.setText(liveWaitBean.getLike_number() + "");
        tvLiveBeforeChapter.setText("《" + liveWaitBean.getChapter_name() + "》");

        UIUtil.showLog("Pre_time",liveWaitBean.getPre_time());
        UIUtil.showLog("Curr_time",liveWaitBean.getCurr_time());

        long pre_time = DateUtils.getStringToDate(liveWaitBean.getPre_time());
        long curr_time = DateUtils.getStringToDate(liveWaitBean.getCurr_time());
        long rentTime = pre_time - curr_time;

        UIUtil.showLog("pre_time",pre_time+"---");
        UIUtil.showLog("curr_time",curr_time+"---");
        UIUtil.showLog("rentTime",rentTime+"---");

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

        liveWaitChapterAdapter = new LiveChapterAdapter(this, liveWaitBean.getChapter_list(),liveWaitBean.getCurr_time());
        gvLiveBeforeChapter.setAdapter(liveWaitChapterAdapter);
        gvLiveBeforeChapter.setOnItemClickListener(new WaitChapterItemClick());

    }

    //倒计时
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (MINUTE - recMinute >= 0) {
                getTime(tvLiveBeforeMinute,MINUTE - recMinute);
                getTime(tvLiveBeforeHours,HOUR - recHour);
                getTime(tvLiveBeforeDays,DAY - recDay);
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
                        getTime(tvLiveBeforeDays,DAY - recDay);
                        getTime(tvLiveBeforeHours,HOUR - recHour);
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
                                getTime(tvLiveBeforeDays,DAY - recDay);
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
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void OnLiveWaitFailure(String result) {
        UIUtil.ToastshowShort(this, result);
    }

    @OnClick({R.id.iv_liveBefore_pic, R.id.iv_liveBefore_back, R.id.bt_liveBefore_join})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_liveBefore_pic:
                Intent intent = new Intent(this, ThemeTeacherContent.class);
                intent.putExtra("tec_id", tec_id+"");
                startActivity(intent);
                break;
            case R.id.iv_liveBefore_back:
                finish();
                break;
            case R.id.bt_liveBefore_join:

                break;
        }
    }

    private class WaitChapterItemClick implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            switch (liveWaitChapterAdapter.getChapterFree(position)) {
                //付费
                case 0:
                    switch (liveWaitChapterAdapter.getOrderStatus(position)){
                        //未购买
                        case 0:
                            UIUtil.ToastshowShort(LiveWaitActivity.this,"当前版本过低，请升级版本后购买本章节!");
//                            MyDialog.getChapterDialog(LiveWaitActivity.this, new MyDialog.IChapter() {
//                                @Override
//                                public void getBuyChapter() {
//                                    CommitOrderBean commitOrderBean = new CommitOrderBean();
//                                    int chapterPrice;
//                                    commitOrderBean.setOrder_title(liveWaitChapterAdapter.getChapterName(position));
//                                    if (liveWaitChapterAdapter.getChapterType(position) == 0 || liveWaitChapterAdapter.getChapterType(position) == 1) {
//                                        chapterPrice = liveWaitChapterAdapter.getChapterLivePrice(position);
//                                    }else {
//                                        chapterPrice = liveWaitChapterAdapter.getChapterRecordPrice(position);
//                                    }
//                                    commitOrderBean.setOrder_number("1");
//                                    commitOrderBean.setOrder_price(chapterPrice + "");
//                                    Intent commitIntent = new Intent(LiveWaitActivity.this, LivePayActivity.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putSerializable("order_bean", commitOrderBean);
//                                    bundle.putInt("remaining_time", 1800);
//                                    commitIntent.putExtras(bundle);
//                                    //保存密码
////                            Config.order_num = commitOrderBean.getOrder_number();
////                            Config.order_att_path = production_path;
//                                    startActivity(commitIntent);
//                                }
//                            });
                            break;
                        //已购买
                        case 1:

                            break;
                    }
                    break;
                //免费
                case 1:
                    CheckChapter(position);
                    break;
            }


        }
    }

    public void CheckChapter(int position) {
        switch (liveWaitChapterAdapter.getChapterType(position)) {
            //未开播
            case 0:
                UIUtil.ToastshowShort(LiveWaitActivity.this,"亲，该章节还未开播！");
                break;
            //直播中
            case 1:
                Intent intentBeing = new Intent(this, PLVideoViewActivity.class);
                intentBeing.putExtra("room_id", room_id);
                intentBeing.putExtra("chapter_id",liveWaitChapterAdapter.getChapterId(position) );
                startActivity(intentBeing);
                break;
            //重播
            case 2:
                Intent intentRecord = new Intent(this, PlayCallBackVideo.class);
                intentRecord.putExtra("path", liveWaitChapterAdapter.getChapterRecord(position));
                startActivity(intentRecord);
                break;
        }
    }
}
