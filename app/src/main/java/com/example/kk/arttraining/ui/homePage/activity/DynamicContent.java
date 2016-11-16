package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.AdvertisBean;
import com.example.kk.arttraining.bean.AttachmentBean;
import com.example.kk.arttraining.bean.StatusesDetailBean;
import com.example.kk.arttraining.bean.TecCommentsBean;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.bean.parsebean.CommentsBean;
import com.example.kk.arttraining.bean.parsebean.ParseCommentDetail;
import com.example.kk.arttraining.custom.view.EmptyGridView;
import com.example.kk.arttraining.custom.view.HideKeyboardActivity;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicContentCommentAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicContentTeacherAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicImageAdapter;
import com.example.kk.arttraining.ui.homePage.function.homepage.DynamicContentData;
import com.example.kk.arttraining.ui.homePage.function.homepage.Headlines;
import com.example.kk.arttraining.ui.homePage.prot.IDynamic;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DateUtils;
import com.example.kk.arttraining.utils.GlideCircleTransform;
import com.example.kk.arttraining.utils.GlideRoundTransform;
import com.example.kk.arttraining.utils.PlayAudioUtil;
import com.example.kk.arttraining.utils.ScreenUtils;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.http.Url;

/**
 * Created by kanghuicong on 2016/10/30.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicContent extends HideKeyboardActivity implements IDynamic {

    String att_type;
    AttachmentBean attachmentBean;
    List<ParseCommentDetail> tec_comments_list = new ArrayList<ParseCommentDetail>();
    PlayAudioUtil playAudioUtil;
    int music_position = 0;
    List<CommentsBean> commentList = new ArrayList<CommentsBean>();
    DynamicContentTeacherAdapter teacherContentAdapter;
    DynamicContentCommentAdapter contentAdapter;
    DynamicContentData dynamicContentTeacher;
    int status_id;
    String stus_type;
    StatusesDetailBean statusesDetailBean;
    @InjectView(R.id.iv_dynamic_content_header)
    ImageView ivDynamicContentHeader;
    @InjectView(R.id.tv_dynamic_content_ordinary_name)
    TextView tvDynamicContentOrdinaryName;
    @InjectView(R.id.tv_dynamic_content_time)
    TextView tvDynamicContentTime;
    @InjectView(R.id.tv_dynamic_content_address)
    TextView tvDynamicContentAddress;
    @InjectView(R.id.tv_dynamic_content_identity)
    TextView tvDynamicContentIdentity;
    @InjectView(R.id.tv_dynamic_content_text)
    TextView tvDynamicContentText;
    @InjectView(R.id.gv_dynamic_content_img)
    EmptyGridView gvDynamicContentImg;
    @InjectView(R.id.ll_dynamic_content_music)
    LinearLayout llDynamicContentMusic;
    @InjectView(R.id.lv_dynamic_content_comment)
    MyListView lvDynamicContentComment;
    @InjectView(R.id.et_dynamic_content_comment)
    EditText etDynamicContentComment;
    @InjectView(R.id.ll_dynamic_teacher_comment)
    LinearLayout llDynamicTeacherComment;
    @InjectView(R.id.iv_dynamic_teacher)
    MyListView ivDynamicTeacher;
    @InjectView(R.id.tv_dynamic_content_browse)
    TextView tvDynamicContentBrowse;
    @InjectView(R.id.tv_dynamic_content_like)
    TextView tvDynamicContentLike;
    @InjectView(R.id.tv_dynamic_content_comment)
    TextView tvDynamicContentComment;
    @InjectView(R.id.tv_dynamic_content_teacher_num)
    TextView tvDynamicContentTeacherNum;
    @InjectView(R.id.tv_dynamic_content_comment_num)
    TextView tvDynamicContentCommentNum;
    @InjectView(R.id.iv_dynamic_content_ad)
    ImageView ivDynamicContentAd;
    @InjectView(R.id.dynameic_video)
    RelativeLayout dynameic_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_dynamic_content);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this, "详情");
        getIntentData();
    }


    @OnClick({R.id.bt_dynamic_content_comment, R.id.tv_dynamic_content_focus,R.id.ll_dynamic_content_music})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_dynamic_content_comment:
                UIUtil.showLog("iDynamic","TOKEN"+Config.ACCESS_TOKEN);
                if (Config.ACCESS_TOKEN != null && !Config.ACCESS_TOKEN.equals("")){
                    if ("".equals(etDynamicContentComment.getText().toString())) {
                        Toast.makeText(DynamicContent.this, "请输入评论内容...", Toast.LENGTH_SHORT).show();
                    } else if (etDynamicContentComment.getText().toString().length() >= 400) {
                        Toast.makeText(DynamicContent.this, "亲，您的评论太长啦...", Toast.LENGTH_SHORT).show();
                    } else {
                        //发布评论，刷新列表
                        dynamicContentTeacher.getCreateComment(status_id,stus_type,etDynamicContentComment.getText().toString());
                    }
                }else {
                    UIUtil.ToastshowShort(this,"请先登录...");
                }
                break;
            case R.id.tv_dynamic_content_focus:

                break;
            case R.id.ll_dynamic_content_music:

                if (music_position == 0) {
                    playAudioUtil.playUrl(attachmentBean.getStore_path());
                    music_position=2;
                }else if (music_position==2){
                    playAudioUtil.pause();
                    music_position--;
                }else if (music_position==1){
                    playAudioUtil.play();
                    music_position++;
                }else {
                    playAudioUtil.stop();
                    music_position =0;
                }
                break;
        }
    }


    private void getIntentData() {
        playAudioUtil = new PlayAudioUtil();
        Intent intent = getIntent();
        status_id = Integer.valueOf(intent.getStringExtra("status_id"));
        stus_type = intent.getStringExtra("stus_type");
        UIUtil.showLog("DateUtils-stus_type",stus_type);
        UIUtil.showLog("DateUtils-status_id",status_id+"");


        dynamicContentTeacher = new DynamicContentData(this, stus_type);
        dynamicContentTeacher.getDynamicContentTeacher(this, status_id);
    }

    public void getData() {
        //读取基本数据
        UIUtil.showLog("DateUtils",statusesDetailBean+"----1");
        String headerPath = statusesDetailBean.getOwner_head_pic();
        if(headerPath!=null && !headerPath.equals("")){
            Glide.with(DynamicContent.this).load(headerPath).transform(new GlideCircleTransform(this)).error(R.mipmap.default_user_header).into(ivDynamicContentHeader);
        }
        tvDynamicContentOrdinaryName.setText(statusesDetailBean.getOwner_name());
        tvDynamicContentAddress.setText(statusesDetailBean.getCity());
        tvDynamicContentIdentity.setText(statusesDetailBean.getIdentity());
        tvDynamicContentTime.setText(DateUtils.getDate(statusesDetailBean.getCreate_time()));
        if (statusesDetailBean.getContent() != null && !statusesDetailBean.getContent().equals("")) {
            tvDynamicContentText.setText(statusesDetailBean.getContent());
        }else {
            tvDynamicContentText.setVisibility(View.GONE);
        }
        tvDynamicContentBrowse.setText(statusesDetailBean.getBrowse_num() + "");
        tvDynamicContentLike.setText(statusesDetailBean.getLike_num() + "");
        tvDynamicContentComment.setText(statusesDetailBean.getComment_num() + "");
        tvDynamicContentCommentNum.setText("全部评论(" + statusesDetailBean.getComment_num() + ")");

        //判断附件类型
        List<AttachmentBean> attachmentBeanList = statusesDetailBean.getAtt();
        if (attachmentBeanList != null && attachmentBeanList.size() != 0) {
            for (int i = 0; i < 1; i++) {
                attachmentBean = attachmentBeanList.get(i);
                att_type = attachmentBean.getAtt_type();
            }
            switch (att_type) {
                case "pic":
                    gvDynamicContentImg.setVisibility(View.VISIBLE);
                    DynamicImageAdapter adapter = new DynamicImageAdapter(DynamicContent.this, attachmentBeanList);
                    gvDynamicContentImg.setAdapter(adapter);
                    break;
                case "music":
                    llDynamicContentMusic.setVisibility(View.VISIBLE);
                    break;
                case "video":
                    dynameic_video.setVisibility(View.VISIBLE);
                    String video_path = attachmentBean.getStore_path();
                    Config.test_video = video_path;
                    break;
                default:
                    break;
            }
        }

        //判断是测评还是动态
        switch (stus_type) {
            case "status":
                llDynamicTeacherComment.setVisibility(View.GONE);
                break;
            case "work":
                llDynamicTeacherComment.setVisibility(View.VISIBLE);
                tvDynamicContentTeacherNum.setText("老师点评(" + statusesDetailBean.getTec_comment_num() + ")");
                tec_comments_list = statusesDetailBean.getTec_comments_list();
                teacherContentAdapter = new DynamicContentTeacherAdapter(this, tec_comments_list);
                ivDynamicTeacher.setAdapter(teacherContentAdapter);
                break;
        }

        //插入广告
        AdvertisBean advertisBean = statusesDetailBean.getAd();
        if (advertisBean != null) {
            Glide.with(this).load(advertisBean.getAd_pic()).error(R.mipmap.default_advertisement).into(ivDynamicContentAd);
        }

        //全部评论
        commentList = statusesDetailBean.getComments();
        contentAdapter = new DynamicContentCommentAdapter(this, commentList);
        lvDynamicContentComment.setAdapter(contentAdapter);
    }

    @Override
    public void getDynamicData(StatusesDetailBean statusesDetailBean) {
        this.statusesDetailBean = statusesDetailBean;
        UIUtil.showLog("DateUtils",statusesDetailBean+"----3");
        getData();
    }

    @Override
    public void getWorkData(StatusesDetailBean statusesDetailBean) {
        UIUtil.showLog("DateUtils",statusesDetailBean+"----2");
        this.statusesDetailBean = statusesDetailBean;
        getData();
    }

    @Override
    public void OnFailure(String error_code) {

    }

    @Override
    public void getCreateComment(String result) {

        if (result .equals("ok") ) {
            CommentsBean info = new CommentsBean();
            info.setName(Config.User_Name);
            info.setTime(DateUtils.getCurrentDate());
            info.setCity(Config.CITY);
            info.setContent(etDynamicContentComment.getText().toString());
            commentList.add(0, info);
            contentAdapter.changeCount(commentList.size());
            contentAdapter.notifyDataSetChanged();
            etDynamicContentComment.setText("");
        } else {
            UIUtil.ToastshowShort(this, "发布失败");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        playAudioUtil.stop();
    }
}
