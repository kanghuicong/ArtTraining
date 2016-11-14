package com.example.kk.arttraining.ui.homePage.activity;

import android.content.Intent;
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
import com.example.kk.arttraining.ui.homePage.prot.IDynamic;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DateUtils;
import com.example.kk.arttraining.utils.GlideCircleTransform;
import com.example.kk.arttraining.utils.GlideRoundTransform;
import com.example.kk.arttraining.utils.ScreenUtils;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/10/30.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicContent extends HideKeyboardActivity implements IDynamic {

    String att_type;
    AttachmentBean attachmentBean;
    List<ParseCommentDetail> tec_comments_list = new ArrayList<ParseCommentDetail>();
    List<TecCommentsBean> tecCommentsList = new ArrayList<TecCommentsBean>();
    TecInfoBean tecInfoBean;

    List<CommentsBean> commentList = new ArrayList<CommentsBean>();
    DynamicContentTeacherAdapter teacherContentAdapter;
    DynamicContentCommentAdapter contentAdapter;
    DynamicContentData dynamicContentTeacher;
    IDynamic iTeacherComment;
    int status_id;
    String stus_type;
    String createCommentResult;
    StatusesDetailBean statusesDetailBean;
    AdvertisBean ad;

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
    //    @InjectView(R.id.iv_dynamic_content_video)
//    ImageView ivDynamicContentVideo;
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


    @OnClick({R.id.bt_dynamic_content_comment, R.id.tv_dynamic_content_focus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_dynamic_content_comment:
                if ("".equals(etDynamicContentComment.getText().toString())) {
                    Toast.makeText(DynamicContent.this, "请输入评论内容...", Toast.LENGTH_SHORT).show();
                } else if (etDynamicContentComment.getText().toString().length() >= 400) {
                    Toast.makeText(DynamicContent.this, "亲，您的评论太长啦...", Toast.LENGTH_SHORT).show();
                } else {
                    releaseComment();
                }
                break;
            case R.id.tv_dynamic_content_focus:

                break;
        }
    }

    //发布评论，刷新列表
    private void releaseComment() {

        dynamicContentTeacher.getCreateComment();

        if (createCommentResult == "ok") {
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

    private void getIntentData() {
        Intent intent = getIntent();
        status_id = Integer.valueOf(intent.getStringExtra("status_id"));
        stus_type = intent.getStringExtra("stus_type");

        dynamicContentTeacher = new DynamicContentData(this, stus_type);
        dynamicContentTeacher.getDynamicContentTeacher(this, status_id);
    }

    public void getData() {
        //读取基本数据
        String headerPath = statusesDetailBean.getOwner_head_pic();
        Glide.with(this).load(headerPath).transform(new GlideCircleTransform(this)).error(R.mipmap.default_user_header).into(ivDynamicContentHeader);
        tvDynamicContentOrdinaryName.setText(statusesDetailBean.getOwner_name());
        tvDynamicContentAddress.setText(statusesDetailBean.getCity());
        tvDynamicContentIdentity.setText(statusesDetailBean.getIdentity());
        tvDynamicContentTime.setText(DateUtils.getDate(statusesDetailBean.getCreate_time()));
        if (statusesDetailBean.getContent() != null && !statusesDetailBean.getContent().equals("")) {
            tvDynamicContentText.setText(statusesDetailBean.getContent());
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
//                    ScreenUtils.accordHeight(ivDynamicContentVideo, ScreenUtils.getScreenWidth(this), 1, 2);//设置video图片高度
//                    String imagePath = attachmentBean.getThumbnail();
                    String video_path = attachmentBean.getStore_path();
                    Config.test_video = video_path;
//                    Glide.with(DynamicContent.this).load(imagePath).transform(new GlideRoundTransform(DynamicContent.this)).error(R.mipmap.ic_launcher).into(ivDynamicContentVideo);
                    break;
                default:
                    break;
            }
        }

        //判断是评论还是动态
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
            Glide.with(this).load(advertisBean.getAd_pic()).into(ivDynamicContentAd);
        }

        //全部评论
        commentList = statusesDetailBean.getComments();
        contentAdapter = new DynamicContentCommentAdapter(this, commentList);
        lvDynamicContentComment.setAdapter(contentAdapter);
    }

    @Override
    public void getDynamicData(StatusesDetailBean statusesDetailBean) {
        this.statusesDetailBean = statusesDetailBean;
        getData();
    }

    @Override
    public void OnFailure(String error_code) {

    }

    @Override
    public void getCreateComment(String result) {
        createCommentResult = result;
    }

    @Override
    public void getWorkData(StatusesDetailBean statusesDetailBean) {

    }
}
