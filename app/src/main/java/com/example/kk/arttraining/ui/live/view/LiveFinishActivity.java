package com.example.kk.arttraining.ui.live.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.activity.ThemeTeacherContent;
import com.example.kk.arttraining.ui.homePage.adapter.LiveChapterAdapter;
import com.example.kk.arttraining.ui.homePage.bean.LiveFinishBean;
import com.example.kk.arttraining.ui.homePage.function.live.LiveFinishData;
import com.example.kk.arttraining.ui.homePage.prot.ILiveFinish;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2017/1/18.
 * QQ邮箱:515849594@qq.com
 */
public class LiveFinishActivity extends Activity implements ILiveFinish {

    LiveFinishData liveFinishData;
    int room_id;
    int tec_id;
    LiveChapterAdapter liveFinishChapterAdapter;
    @InjectView(R.id.iv_liveFinish_pic)
    ImageView ivLiveFinishPic;
    @InjectView(R.id.tv_liveFinish_name)
    TextView tvLiveFinishName;
    @InjectView(R.id.tv_liveFinish_like_num)
    TextView tvLiveFinishLikeNum;
    @InjectView(R.id.gv_liveFinish_chapter)
    GridView gvLiveFinishChapter;

    double price = 0.00;
    String buy_type = "record";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_finish);
        ButterKnife.inject(this);

        TitleBack.toTitleBackActivity(this, "直播", "课程列表");
        room_id = getIntent().getIntExtra("room_id", 0);

        liveFinishData = new LiveFinishData(this);
        liveFinishData.getLiveAfterData(room_id);
    }

    @Override
    public void getLiveFinish(LiveFinishBean liveFinishBean) {
        tec_id = liveFinishBean.getOwner();
        Glide.with(this).load(liveFinishBean.getHead_pic()).error(R.mipmap.default_user_header).into(ivLiveFinishPic);
        tvLiveFinishName.setText(liveFinishBean.getName());
        tvLiveFinishLikeNum.setText(liveFinishBean.getLike_number() + "");

        liveFinishChapterAdapter = new LiveChapterAdapter(this, liveFinishBean.getChapter_list(), liveFinishBean.getCurr_time());
        gvLiveFinishChapter.setAdapter(liveFinishChapterAdapter);
        gvLiveFinishChapter.setOnItemClickListener(new FinishChapterItemClick());

    }

    @Override
    public void OnLiveFinishFailure(String result) {
        UIUtil.ToastshowShort(this, result);
    }

    @OnClick({R.id.iv_liveFinish_pic,R.id.tv_title_subtitle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_liveFinish_pic:
                Intent intent = new Intent(this, ThemeTeacherContent.class);
                intent.putExtra("tec_id", tec_id + "");
                startActivity(intent);
                break;
            case R.id.tv_title_subtitle:
                Intent intentCourse = new Intent(this, LiveCourseActivity.class);
                intentCourse.putExtra("room_id", room_id);
                startActivity(intentCourse);
                break;
        }
    }

    private class FinishChapterItemClick implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

            switch (liveFinishChapterAdapter.getChapterFree(position)) {
                case 0://付费
                    UIUtil.ToastshowShort(LiveFinishActivity.this, "当前版本过低，请升级版本后购买本章节!");
                    switch (liveFinishChapterAdapter.getOrderStatus(position)) {
                        case 0://未购买
                            switch (liveFinishChapterAdapter.getChapterType(position)) {
                                case 2:
                                    price = liveFinishChapterAdapter.getChapterRecordPrice(position);
                                    buy_type = "record";
                                    break;
                                default:
                                    price = liveFinishChapterAdapter.getChapterLivePrice(position);
                                    buy_type = "live";
                                    break;
                            }
                            Intent intent = new Intent(LiveFinishActivity.this, LivePayActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("chapter_name", liveFinishChapterAdapter.getChapterName(position));
                            bundle.putDouble("live_price",price);
                            bundle.putString("buy_type", buy_type);
                            bundle.putInt("room_id", room_id);
                            bundle.putInt("chapter_id", liveFinishChapterAdapter.getChapterId(position));
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
    }

    public void CheckChapter(int position) {
        switch (liveFinishChapterAdapter.getChapterType(position)) {
            //未开播
            case 0:
                UIUtil.ToastshowShort(LiveFinishActivity.this, "亲，该章节还未开播！");
                break;
            //直播中
            case 1:
                Intent intentBeing = new Intent(this, PLVideoViewActivity.class);
                intentBeing.putExtra("room_id", room_id);
                intentBeing.putExtra("chapter_id", liveFinishChapterAdapter.getChapterId(position));
                startActivity(intentBeing);
                break;
            //重播
            case 2:
                Intent intentRecord = new Intent(this, PlayCallBackVideo.class);
                intentRecord.putExtra("path", liveFinishChapterAdapter.getChapterRecord(position));
                startActivity(intentRecord);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        liveFinishData.cancel();
    }
}
