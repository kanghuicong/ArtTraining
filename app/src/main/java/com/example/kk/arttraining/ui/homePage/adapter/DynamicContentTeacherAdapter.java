package com.example.kk.arttraining.ui.homePage.adapter;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.media.recodevoice.PlayAudioListenter;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecCommentsBean;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.bean.parsebean.ParseCommentDetail;
import com.example.kk.arttraining.custom.dialog.PopWindowDialogUtil;
import com.example.kk.arttraining.ui.homePage.activity.DynamicContentTeacherVideo;
import com.example.kk.arttraining.ui.homePage.activity.ThemeTeacherContent;
import com.example.kk.arttraining.ui.homePage.bean.TeacherCommentBean;
import com.example.kk.arttraining.ui.homePage.function.homepage.CheckWifi;
import com.example.kk.arttraining.ui.homePage.function.homepage.MusicAnimator;
import com.example.kk.arttraining.ui.homePage.function.homepage.MusicTouch;
import com.example.kk.arttraining.ui.homePage.function.homepage.ReadTecComment;
import com.example.kk.arttraining.ui.homePage.function.homepage.TokenVerfy;
import com.example.kk.arttraining.ui.homePage.prot.ICheckWifi;
import com.example.kk.arttraining.ui.homePage.prot.IMusic;
import com.example.kk.arttraining.ui.homePage.prot.ITokenVerfy;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DateUtils;
import com.example.kk.arttraining.custom.view.GlideCircleTransform;
import com.example.kk.arttraining.utils.NetUtils;
import com.example.kk.arttraining.utils.PlayAudioUtil;
import com.example.kk.arttraining.utils.ScreenUtils;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanghuicong on 2016/11/5.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicContentTeacherAdapter extends BaseAdapter implements IMusic {
    List<ParseCommentDetail> parseCommentDetailList = new ArrayList<ParseCommentDetail>();
    Activity activity;
    DynamicContentTeacherAdapter.TeacherCommentBack teacherCommentBack;
    List<TeacherCommentBean> teacherCommentList = new ArrayList<TeacherCommentBean>();
    TeacherCommentBean teacherCommentBean;
    int TEACHER_INFO = 0;
    int TEACHER_COMMENT = 1;
    TokenVerfy tokenVerfy;
    CheckWifi checkWifi;

//    PlayAudioUtil playAudioUtil;
    AnimationDrawable MusicAnim = new AnimationDrawable();
    String voicePath = "voicePath";
    PopWindowDialogUtil wordDialogUtil;
    int width;

    public DynamicContentTeacherAdapter(Activity activity, List<ParseCommentDetail> parseCommentDetailList, DynamicContentTeacherAdapter.TeacherCommentBack teacherCommentBack) {
        this.parseCommentDetailList = parseCommentDetailList;
        this.activity = activity;
        this.teacherCommentBack = teacherCommentBack;
//        this.playAudioUtil = playAudioUtil;
        width = ScreenUtils.getScreenWidth(activity);

        for (int i = 0; i < parseCommentDetailList.size(); i++) {
            TeacherCommentBean teacherInfo = new TeacherCommentBean();
            teacherInfo.setTecInfo(parseCommentDetailList.get(i).getTec());
            teacherCommentList.add(teacherInfo);
            for (int j = 0; j < parseCommentDetailList.get(i).getTec_comments().size(); j++) {
                TeacherCommentBean teacherComment = new TeacherCommentBean();
                teacherComment.setTecComment(parseCommentDetailList.get(i).getTec_comments().get(j));
                teacherCommentList.add(teacherComment);
            }
        }
    }

    @Override
    public int getCount() {
        return teacherCommentList.size();
    }

    @Override
    public TeacherCommentBean getItem(int position) {
        return teacherCommentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getItemViewType(int position) {
        int ret = 0;
        switch (parseCommentDetailList.size()) {
            case 1:
                if (position == 0) {
                    ret = TEACHER_INFO;
                } else {
                    ret = TEACHER_COMMENT;
                }
                break;
            case 2:
                if (position == 0 || position == parseCommentDetailList.get(0).getTec_comments().size() + 1) {
                    ret = TEACHER_INFO;
                } else {
                    ret = TEACHER_COMMENT;
                }
                break;
            case 3:
                if (position == 0 || position == parseCommentDetailList.get(0).getTec_comments().size() + 1 || position == parseCommentDetailList.get(0).getTec_comments().size() + parseCommentDetailList.get(1).getTec_comments().size() + 2) {
                    ret = TEACHER_INFO;
                } else {
                    ret = TEACHER_COMMENT;
                }
                break;
        }
        return ret;
    }

    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        teacherCommentBean = teacherCommentList.get(position);
        int viewType = getItemViewType(position);

        switch (viewType) {
            case 0:
                ViewHolder holder = null;
                if (convertView == null) {
                    convertView = View.inflate(activity, R.layout.homepage_dynamic_teacher_item, null);
                    holder = new ViewHolder();
                    holder.iv_header = (ImageView) convertView.findViewById(R.id.iv_dynamic_teacher_header);
                    holder.tv_name = (TextView) convertView.findViewById(R.id.tv_dynamic_teacher_name);
                    holder.tv_time = (TextView) convertView.findViewById(R.id.tv_dynamic_teacher_time);
                    holder.tv_college = (TextView) convertView.findViewById(R.id.tv_dynamic_teacher_school);
                    holder.tv_professor = (TextView) convertView.findViewById(R.id.tv_dynamic_teacher_professor);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                TecInfoBean tecInfoBean = teacherCommentBean.getTecInfo();

                holder.tv_name.setText(tecInfoBean.getName());
                Glide.with(activity).load(tecInfoBean.getTec_pic()).transform(new GlideCircleTransform(activity)).error(R.mipmap.default_user_header).into(holder.iv_header);

                if (tecInfoBean.getTime() != null && !tecInfoBean.getTime().equals("")) {
                    holder.tv_time.setText(DateUtils.getDate(tecInfoBean.getTime()) + "");
                } else {
                    holder.tv_time.setVisibility(View.GONE);
                }

                holder.tv_college.setText(tecInfoBean.getSchool());
                holder.tv_professor.setText(tecInfoBean.getIdentity());

                holder.iv_header.setOnClickListener(new HeaderClick(position, tecInfoBean.getTec_id()));

                break;
            case 1:
                TecCommentsBean tecCommentsBean = teacherCommentBean.getTecComment();
                switch (tecCommentsBean.getComm_type()) {
                    case "comment":
                        ViewHolder teacher_holder = null;
                        if (convertView == null) {
                            convertView = View.inflate(activity, R.layout.homepage_dynamic_teacher_comment_item_left, null);
                            teacher_holder = new ViewHolder();
                            teacher_holder.tv_teacher_word = (TextView) convertView.findViewById(R.id.tv_dynamic_teacher_word);
                            teacher_holder.ll_teacher_music = (LinearLayout) convertView.findViewById(R.id.ll_teacher_comment_music);
                            teacher_holder.iv_teacher_music = (ImageView) convertView.findViewById(R.id.ivAdam);
                            teacher_holder.tv_teacher_music_time = (TextView) convertView.findViewById(R.id.tv_music_time);
                            teacher_holder.fl_teacher_video = (FrameLayout) convertView.findViewById(R.id.fl_teacher_comment_video);
                            teacher_holder.iv_teacher_video = (ImageView) convertView.findViewById(R.id.iv_teacher_comment_video);
                            convertView.setTag(teacher_holder);
                        } else {
                            teacher_holder = (ViewHolder) convertView.getTag();
                        }

                        switch (tecCommentsBean.getType()) {
                            case "word":
                                teacher_holder.tv_teacher_word.setVisibility(View.VISIBLE);
                                teacher_holder.ll_teacher_music.setVisibility(View.GONE);
                                teacher_holder.fl_teacher_video.setVisibility(View.GONE);
                                teacher_holder.tv_teacher_word.setOnClickListener(new WordCommentClick(tecCommentsBean.getContent(),tecCommentsBean.getComm_id(), tecCommentsBean.getTec_id(), tecCommentsBean.getComm_type()));

                                break;
                            case "voice":
                                MusicAnimator musicAnimatorSet = new MusicAnimator(this);
                                teacher_holder.tv_teacher_word.setVisibility(View.GONE);
                                teacher_holder.fl_teacher_video.setVisibility(View.GONE);
                                teacher_holder.ll_teacher_music.setVisibility(View.VISIBLE);

                                if (tecCommentsBean.getDuration() != null && !tecCommentsBean.getDuration().equals("")) {
                                    float time = Float.parseFloat(tecCommentsBean.getDuration());
                                    int mTime = (int) time;
                                    teacher_holder.tv_teacher_music_time.setText(DateUtils.getMusicTime(mTime));
                                    UIUtil.showLog("mTime", DateUtils.getMusicTime(mTime) + "--");
                                } else {
                                    teacher_holder.tv_teacher_music_time.setVisibility(View.GONE);
                                }
                                teacher_holder.ll_teacher_music.setOnClickListener(new MusicClick(position, tecCommentsBean.getContent(), musicAnimatorSet, teacher_holder.iv_teacher_music,tecCommentsBean.getComm_id(), tecCommentsBean.getTec_id(), tecCommentsBean.getComm_type()));
                                break;
                            case "video":
                                teacher_holder.tv_teacher_word.setVisibility(View.GONE);
                                teacher_holder.fl_teacher_video.setVisibility(View.VISIBLE);
                                teacher_holder.ll_teacher_music.setVisibility(View.GONE);

                                ScreenUtils.accordHeight(teacher_holder.iv_teacher_video, width, 3, 10);//设置video图片高度
                                ScreenUtils.accordWidth(teacher_holder.iv_teacher_video, width, 2, 5);//设置video图片宽度

                                Glide.with(activity).load(tecCommentsBean.getAttr()).error(R.mipmap.comment_video_pic).into(teacher_holder.iv_teacher_video);
                                teacher_holder.fl_teacher_video.setOnClickListener(new TeacherVideoClick(tecCommentsBean.getContent(), tecCommentsBean.getAttr(),tecCommentsBean.getComm_id(), tecCommentsBean.getTec_id(), tecCommentsBean.getComm_type()));

                                break;
                        }

                        break;
                    case "reply":
                        ViewHolder student_holder = null;
                        if (convertView == null) {
                            convertView = View.inflate(activity, R.layout.homepage_dynamic_teacher_comment_item_right, null);
                            student_holder = new ViewHolder();
                            student_holder.tv_student_word = (TextView) convertView.findViewById(R.id.tv_dynamic_student_word);
                            convertView.setTag(student_holder);
                        } else {
                            student_holder = (ViewHolder) convertView.getTag();
                        }
                        student_holder.tv_student_word.setText(tecCommentsBean.getContent());
                        break;
                }
                break;
        }
        return convertView;
    }

    private class HeaderClick implements View.OnClickListener {
        int position;
        int tec_id;

        public HeaderClick(int position, int tec_id) {
            this.position = position;
            this.tec_id = tec_id;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, ThemeTeacherContent.class);
            intent.putExtra("tec_id", tec_id + "");
            activity.startActivity(intent);
        }
    }

    private class MusicClick implements View.OnClickListener {
        String path;
        int position;
        MusicAnimator musicAnimatorSet;
        ImageView iv_teacher_music;
        int comm_id;
        int tec_id;
        String comm_type;

        public MusicClick(int position, String path, MusicAnimator musicAnimatorSet, ImageView iv_teacher_music,int comm_id, int tec_id, String comm_type) {
            this.path = path;
            this.position = position;
            this.musicAnimatorSet = musicAnimatorSet;
            this.iv_teacher_music = iv_teacher_music;
            this.comm_id = comm_id;
            this.tec_id = tec_id;
            this.comm_type = comm_type;
        }

        @Override
        public void onClick(View v) {
            if (Config.ACCESS_TOKEN == null || Config.ACCESS_TOKEN.equals("")) {
                TokenVerfy.Login(activity, 2);
            } else {
                tokenVerfy = new TokenVerfy(new ITokenVerfy() {
                    @Override
                    public void TokenSuccess() {
                        teacherCommentBack.getTeacherCommentFlag();
                        if (path != null && !path.equals("")) {
                            if (!voicePath.equals(path)) {
                                if (Config.playAudioUtil != null) {
                                    MusicTouch.stopMusicAnimation(MusicAnim);

                                    musicAnimatorSet.doMusicAnimator(iv_teacher_music);
                                    Config.playAudioUtil.playUrl(path);
                                    ReadTecComment.getReadTecComment(comm_id, tec_id, comm_type);
                                    voicePath = path;
                                    teacherCommentBack.getTeacherCommentBack(MusicAnim);
                                } else {
                                    musicAnimatorSet.doMusicAnimator(iv_teacher_music);

                                    if (Config.playAudioUtil == null) {
                                        Config.playAudioUtil = new PlayAudioUtil(new PlayAudioListenter() {
                                            @Override
                                            public void playCompletion() {
                                                MusicTouch.stopMusicAnimation(MusicAnim);
                                            }
                                        });
                                    }
                                    Config.playAudioUtil.playUrl(path);
                                    ReadTecComment.getReadTecComment(comm_id, tec_id, comm_type);
                                    voicePath = path;
                                    teacherCommentBack.getTeacherCommentBack(MusicAnim);
                                }
                            } else {
                                MusicTouch.stopMusicAnimation(MusicAnim);
                                voicePath = "voicePath";
                            }
                        } else {
                            UIUtil.ToastshowShort(activity, "发生错误，无法播放！");
                        }
                    }

                    @Override
                    public void TokenFailure(int flag) {
                        TokenVerfy.Login(activity, flag);
                    }
                });
                tokenVerfy.getTokenVerfy();
            }
        }
    }

    private class TeacherVideoClick implements View.OnClickListener {
        String path;
        String thumbnail;
        int comm_id;
        int tec_id;
        String comm_type;

        public TeacherVideoClick(String attr, String thumbnail,int comm_id, int tec_id, String comm_type) {
            path = attr;
            this.thumbnail = thumbnail;
            this.comm_id = comm_id;
            this.tec_id = tec_id;
            this.comm_type = comm_type;
        }

        @Override
        public void onClick(View v) {
            if (Config.ACCESS_TOKEN == null || Config.ACCESS_TOKEN.equals("")) {
                TokenVerfy.Login(activity, 2);
            } else {
                tokenVerfy = new TokenVerfy(new ITokenVerfy() {
                    @Override
                    public void TokenSuccess() {
                        if (NetUtils.isWifi(activity)) {
                            getVideo();
                        }else {
                            checkWifi = new CheckWifi("播放",new ICheckWifi() {
                                @Override
                                public void CheckWifi() {
                                    getVideo();
                                }
                            });
                            checkWifi.getWifiDialog(activity);
                        }
                    }

                    @Override
                    public void TokenFailure(int flag) {
                        TokenVerfy.Login(activity, flag);
                    }
                });
                tokenVerfy.getTokenVerfy();
            }
        }

        public void getVideo() {
            Intent intent = new Intent(activity, DynamicContentTeacherVideo.class);
            intent.putExtra("path", path);
            intent.putExtra("thumbnail", thumbnail);
            activity.startActivity(intent);
            ReadTecComment.getReadTecComment(comm_id, tec_id, comm_type);
        }
    }

    private class WordCommentClick implements View.OnClickListener {
        String content;
        int comm_id;
        int tec_id;
        String comm_type;
        public WordCommentClick(String content,int comm_id, int tec_id, String comm_type) {
            this.content = content;
            this.comm_id = comm_id;
            this.tec_id = tec_id;
            this.comm_type = comm_type;
        }

        @Override
        public void onClick(View v) {
            if (Config.ACCESS_TOKEN == null || Config.ACCESS_TOKEN.equals("")) {
                TokenVerfy.Login(activity, 2);
            } else {
                tokenVerfy = new TokenVerfy(new ITokenVerfy() {
                    @Override
                    public void TokenSuccess() {
                        wordDialogUtil = new PopWindowDialogUtil(activity, R.style.transparentDialog, R.layout.dialog_homepage_word, "word", content);
                        Window window = wordDialogUtil.getWindow();
                        wordDialogUtil.show();
                        window.setGravity(Gravity.CENTER);
                        window.getDecorView().setPadding(10, 0, 10, 0);
                        WindowManager.LayoutParams lp = window.getAttributes();
                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        window.setAttributes(lp);

                        ReadTecComment.getReadTecComment(comm_id, tec_id, comm_type);
                    }

                    @Override
                    public void TokenFailure(int flag) {
                        TokenVerfy.Login(activity, flag);
                    }
                });
                tokenVerfy.getTokenVerfy();
            }
        }
    }

    @Override
    public void StopArtMusic(AnimatorSet MusicSet) {

    }

    @Override
    public void StopMusic(AnimationDrawable MusicAnim) {
        this.MusicAnim = MusicAnim;
    }

    public interface TeacherCommentBack {
        void getTeacherCommentFlag();

        void getTeacherCommentBack(AnimationDrawable MusicAnim);

    }

    class ViewHolder {
        ImageView iv_header;
        TextView tv_name;
        TextView tv_time;
        TextView tv_college;
        TextView tv_professor;

        TextView tv_teacher_word;
        TextView tv_student_word;
        ImageView iv_teacher_music;
        TextView tv_teacher_music_time;
        LinearLayout ll_teacher_music;
        FrameLayout fl_teacher_video;
        ImageView iv_teacher_video;

    }

}

