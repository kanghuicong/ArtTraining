package com.example.kk.arttraining.ui.homePage.activity;

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
import com.example.kk.arttraining.bean.StatusesDetailBean;
import com.example.kk.arttraining.bean.TecCommentsBean;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.bean.parsebean.CommentsBean;
import com.example.kk.arttraining.custom.view.EmptyGridView;
import com.example.kk.arttraining.custom.view.HideKeyboardActivity;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.custom.view.VipTextView;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicContentCommentAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicContentTeacherAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicImageAdapter;
import com.example.kk.arttraining.ui.homePage.function.homepage.DynamicContentTeacher;
import com.example.kk.arttraining.ui.homePage.prot.ITeacherComment;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DateUtils;
import com.example.kk.arttraining.utils.GlideCircleTransform;
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
public class DynamicContent extends HideKeyboardActivity implements ITeacherComment{

    String att_type;
    AttachmentBean attachmentBean;
    List<CommentsBean> list = new ArrayList<CommentsBean>();
    List<CommentsBean> commentList = new ArrayList<CommentsBean>();
    List<TecCommentsBean> tecCommentsList = new ArrayList<TecCommentsBean>();
    TecInfoBean tecInfoBean;
    DynamicContentTeacherAdapter teacherContentAdapter;
    DynamicContentCommentAdapter contentAdapter;
    ITeacherComment iTeacherComment;
    int status_id;

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
    @InjectView(R.id.iv_dynamic_content_video)
    ImageView ivDynamicContentVideo;
    @InjectView(R.id.lv_dynamic_content_comment)
    MyListView lvDynamicContentComment;
    @InjectView(R.id.et_dynamic_content_comment)
    EditText etDynamicContentComment;
    @InjectView(R.id.iv_dynamic_teacher_header)
    ImageView ivDynamicTeacherHeader;
    @InjectView(R.id.tv_dynamic_teacher_name)
    TextView tvDynamicTeacherName;
    @InjectView(R.id.tv_dynamic_teacher_time)
    TextView tvDynamicTeacherTime;
    @InjectView(R.id.tv_dynamic_teacher_school)
    TextView tvDynamicTeacherSchool;
    @InjectView(R.id.tv_dynamic_teacher_professor)
    TextView tvDynamicTeacherProfessor;
    @InjectView(R.id.lv_dynamic_content_teacher_comment)
    MyListView lvDynamicContentTeacherComment;

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
        info.setName(Config.User_Name);
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
        status_id = Integer.valueOf(intent.getStringExtra("status_id"));
        DynamicContentTeacher dynamicContentTeacher = new DynamicContentTeacher(iTeacherComment);
        dynamicContentTeacher.getDynamicContentTeacher(this,status_id);
    }

    @Override
    public void getTeacherComment(StatusesDetailBean statusesDetailBean) {
//        String headerPath = statusesDetailBean.getStringExtra("Owner_head_pic");
//        Glide.with(this).load(headerPath).transform(new GlideCircleTransform(this)).error(R.mipmap.ic_launcher).into(ivDynamicContentHeader);
//        tvDynamicContentOrdinaryName.setText(intent.getStringExtra("Owner_name"));
//         
//        tvDynamicContentAddress.setText(intent.getStringExtra("City"));
//        tvDynamicContentIdentity.setText(intent.getStringExtra("Identity"));
//        tvDynamicContentText.setText(intent.getStringExtra("Content"));
//        List<AttachmentBean> attachmentBeanList = (List) intent.getStringArrayListExtra("attachmentBeanList");
//
//        for (int i = 0; i < attachmentBeanList.size(); i++) {
//            attachmentBean = attachmentBeanList.get(i);
//            att_type = attachmentBean.getAtt_type();
//        }
//
//        ScreenUtils.accordHeight(ivDynamicContentVideo, ScreenUtils.getScreenWidth(this), 1, 2);//设置video图片高度
//
//        switch (att_type) {
//            case "pic":
//                gvDynamicContentImg.setVisibility(View.VISIBLE);
//                DynamicImageAdapter adapter = new DynamicImageAdapter(DynamicContent.this, attachmentBeanList);
//                gvDynamicContentImg.setAdapter(adapter);
//                break;
//            case "music":
//                llDynamicContentMusic.setVisibility(View.VISIBLE);
//                break;
//            case "video":
//                ivDynamicContentVideo.setVisibility(View.VISIBLE);
//                String imagePath = attachmentBean.getThumbnail();
//                Glide.with(DynamicContent.this).load(imagePath).transform(new GlideRoundTransform(DynamicContent.this)).error(R.mipmap.ic_launcher).into(ivDynamicContentVideo);
//                break;
//            default:
//                break;
//        }






//
//        tecInfoBean = statusesDetailBean.getTec();//老师信息
////        Glide.with(this).load(tecInfoBean.getPic()).transform(new GlideCircleTransform(this)).error(R.mipmap.ic_launcher).into(ivDynamicTeacherHeader);
//        tvDynamicTeacherName.setText(tecInfoBean.getName());
//        tvDynamicTeacherProfessor.setText(tecInfoBean.getIdentity());
//        tvDynamicTeacherTime.setText(tecInfoBean.getTime());
//        tvDynamicTeacherSchool.setText(tecInfoBean.getCollege());
//
////        tecCommentsList = statusesDetailBean.getTec_comments();//老师评论内容
//        teacherContentAdapter = new DynamicContentTeacherAdapter(DynamicContent.this,tecCommentsList);
//        lvDynamicContentTeacherComment.setAdapter(teacherContentAdapter);



    }

    @Override
    public void OnFailure(String error_code) {

    }
}
