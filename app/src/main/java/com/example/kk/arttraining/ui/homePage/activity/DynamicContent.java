package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.AttachmentBean;
import com.example.kk.arttraining.bean.UserInfoBean;
import com.example.kk.arttraining.bean.parsebean.CommentsBean;
import com.example.kk.arttraining.custom.view.EmptyGridView;
import com.example.kk.arttraining.custom.view.HideKeyboardActivity;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.custom.view.VipTextView;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicContentCommentAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicImageAdapter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DateUtils;
import com.example.kk.arttraining.utils.GlideRoundTransform;
import com.example.kk.arttraining.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/10/30.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicContent extends HideKeyboardActivity {

    @InjectView(R.id.iv_dynamic_content_header)
    ImageView ivDynamicContentHeader;
    @InjectView(R.id.tv_dynamic_content_vip_name)
    VipTextView tvDynamicContentVipName;
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
    @InjectView(R.id.tv_dynamic_content_music_time)
    TextView tvDynamicContentMusicTime;
    @InjectView(R.id.ll_dynamic_content_music)
    LinearLayout llDynamicContentMusic;
    @InjectView(R.id.iv_dynamic_content_video)
    ImageView ivDynamicContentVideo;
    @InjectView(R.id.lv_dynamic_content_comment)
    MyListView lvDynamicContentComment;
    @InjectView(R.id.et_dynamic_content_comment)
    EditText etDynamicContentComment;
    @InjectView(R.id.bt_dynamic_content_comment)
    Button btDynamicContentComment;

    String att_type;
    AttachmentBean attachmentBean;
    List<CommentsBean> list = new ArrayList<CommentsBean>();
    List<CommentsBean> commentList = new ArrayList<CommentsBean>();
    DynamicContentCommentAdapter contentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_dynamic_content);
        ButterKnife.inject(this);
        getData();
        initComment();

    }
    @OnClick(R.id.bt_dynamic_content_comment)
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
        }
    }

    private void releaseComment() {
        CommentsBean info = new CommentsBean();
        info.setName("123");
        info.setTime(DateUtils.getCurrentDate());
        info.setCity(Config.CITY);
        info.setContent(etDynamicContentComment.getText().toString());
        commentList.add(0, info);
        contentAdapter.changeCount(commentList.size());
        contentAdapter.notifyDataSetChanged();
        etDynamicContentComment.setText("");
    }

    private void initComment() {

        for (int i = 0; i < 10; i++) {
            CommentsBean molder = new CommentsBean();
            molder.setName("kk" + i);
            molder.setContent("内容" + i);
            molder.setTime("9月" + i + "日");
            commentList.add(molder);
        }
        list.addAll(commentList);
        contentAdapter = new DynamicContentCommentAdapter(this, commentList);
        lvDynamicContentComment.setAdapter(contentAdapter);
    }

    private void getData() {
        Intent intent = getIntent();
        tvDynamicContentOrdinaryName.setText(intent.getStringExtra("Owner_name"));
        tvDynamicContentAddress.setText(intent.getStringExtra("City"));
        tvDynamicContentIdentity.setText(intent.getStringExtra("Identity"));
        tvDynamicContentText.setText(intent.getStringExtra("Content"));
        List<AttachmentBean> attachmentBeanList = (List) intent.getStringArrayListExtra("attachmentBeanList");

        for (int i = 0; i < attachmentBeanList.size(); i++) {
            attachmentBean = attachmentBeanList.get(i);
            att_type = attachmentBean.getAtt_type();
        }

        ScreenUtils.accordHeight(ivDynamicContentVideo, ScreenUtils.getScreenWidth(this), 1, 2);//设置video图片高度

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
                ivDynamicContentVideo.setVisibility(View.VISIBLE);
                String imagePath = attachmentBean.getThumbnail();
                Glide.with(DynamicContent.this).load(imagePath).transform(new GlideRoundTransform(DynamicContent.this)).error(R.mipmap.ic_launcher).into(ivDynamicContentVideo);
                break;
        }
    }
}
