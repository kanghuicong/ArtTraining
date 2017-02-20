package com.example.kk.arttraining.ui.homePage.activity;

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
import com.example.kk.arttraining.ui.homePage.adapter.LiveChapterAdapter;
import com.example.kk.arttraining.ui.homePage.bean.LiveFinishBean;
import com.example.kk.arttraining.ui.homePage.function.homepage.MyDialog;
import com.example.kk.arttraining.ui.homePage.function.live.LiveFinishData;
import com.example.kk.arttraining.ui.homePage.prot.ILiveFinish;
import com.example.kk.arttraining.ui.live.view.PLVideoViewActivity;
import com.example.kk.arttraining.ui.live.view.PlayCallBackVideo;
import com.example.kk.arttraining.ui.valuation.bean.CommitOrderBean;
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
    @InjectView(R.id.iv_liveFinish_back)
    ImageView ivLiveFinishBack;
    @InjectView(R.id.gv_liveFinish_chapter)
    GridView gvLiveFinishChapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_finish);
        ButterKnife.inject(this);

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

    @OnClick({R.id.iv_liveFinish_pic, R.id.iv_liveFinish_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_liveFinish_pic:
                Intent intent = new Intent(this, ThemeTeacherContent.class);
                intent.putExtra("tec_id", tec_id + "");
                startActivity(intent);
                break;
            case R.id.iv_liveFinish_back:
                finish();
                break;
        }
    }

    private class FinishChapterItemClick implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

            switch (liveFinishChapterAdapter.getChapterFree(position)){
                //付费
                case 0:
                    UIUtil.ToastshowShort(LiveFinishActivity.this,"当前版本过低，请升级版本后购买本章节!");
                    switch (liveFinishChapterAdapter.getOrderStatus(position)) {
                        //未购买
                        case 0:
//                            MyDialog.getChapterDialog(LiveFinishActivity.this, new MyDialog.IChapter() {
//                                @Override
//                                public void getBuyChapter() {
//                                    CommitOrderBean commitOrderBean = new CommitOrderBean();
//                                    int chapterPrice;
//                                    commitOrderBean.setOrder_title(liveFinishChapterAdapter.getChapterName(position));
//                                    if (liveFinishChapterAdapter.getChapterType(position) == 0 || liveFinishChapterAdapter.getChapterType(position) == 1) {
//                                        chapterPrice = liveFinishChapterAdapter.getChapterLivePrice(position);
//                                    } else {
//                                        chapterPrice = liveFinishChapterAdapter.getChapterRecordPrice(position);
//                                    }
//
//                                    commitOrderBean.setOrder_number("1");
//                                    commitOrderBean.setOrder_price(chapterPrice + "");
//                                    Intent commitIntent = new Intent(LiveFinishActivity.this, LivePayActivity.class);
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
        switch (liveFinishChapterAdapter.getChapterType(position)) {
            //未开播
            case 0:
                UIUtil.ToastshowShort(LiveFinishActivity.this,"亲，该章节还未开播！");
                break;
            //直播中
            case 1:
                Intent intentBeing = new Intent(this, PLVideoViewActivity.class);
                intentBeing.putExtra("room_id", room_id);
                intentBeing.putExtra("chapter_id",liveFinishChapterAdapter.getChapterId(position) );
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
