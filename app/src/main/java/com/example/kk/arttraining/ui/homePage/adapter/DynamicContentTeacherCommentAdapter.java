package com.example.kk.arttraining.ui.homePage.adapter;

import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kk.arttraining.Media.recodevideo.PlayAudioListenter;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecCommentsBean;
import com.example.kk.arttraining.ui.homePage.function.homepage.MusicAnimator;
import com.example.kk.arttraining.ui.homePage.prot.IMusic;
import com.example.kk.arttraining.utils.PlayAudioUtil;
import com.example.kk.arttraining.utils.UIUtil;
import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kanghuicong on 2016/11/8.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicContentTeacherCommentAdapter extends BaseAdapter implements IMusic {
    Context context;
    List<TecCommentsBean> tecCommentsBeanList;
    TecCommentsBean tecCommentsBean;
    PlayAudioUtil playAudioUtil;
    List<Boolean> music_position = new ArrayList<Boolean>();
    TeacherCommentBack teacherCommentBack;
    int MusicStart = -1;
    AnimationDrawable MusicAnim = new AnimationDrawable();
    List<Boolean> musicPosition = new ArrayList<Boolean>();

    public DynamicContentTeacherCommentAdapter(Context context, List<TecCommentsBean> tecCommentsBeanList,TeacherCommentBack teacherCommentBack) {
        this.context = context;
        this.tecCommentsBeanList = tecCommentsBeanList;
        this.teacherCommentBack = teacherCommentBack;
        for (int i=0;i<tecCommentsBeanList.size();i++) {
            music_position.add(i,false);
        }
    }

    @Override
    public int getCount() {
        return tecCommentsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public int getItemViewType(int position) {
        int ret = 0;
        String type = tecCommentsBeanList.get(position).getComm_type();

        if (type.equals("comment")){
            ret = 0;
        }else if (type.equals("reply")){
            ret = 1;
        }
        return ret;
    }

    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        tecCommentsBean = tecCommentsBeanList.get(position);
        int viewType = getItemViewType(position);

        switch (viewType) {
            case 0:
                ViewHolder teacher_holder = null;
                if (convertView == null) {
                    convertView = View.inflate(context, R.layout.homepage_dynamic_teacher_comment_item_left, null);
                    teacher_holder = new ViewHolder();
                    teacher_holder.tv_teacher_word = (TextView) convertView.findViewById(R.id.tv_dynamic_teacher_word);
                    teacher_holder.ll_teacher_music = (LinearLayout) convertView.findViewById(R.id.ll_music);
                    teacher_holder.iv_teacher_music = (ImageView) convertView.findViewById(R.id.ivAdam);
                    convertView.setTag(teacher_holder);
                } else {
                    teacher_holder = (ViewHolder) convertView.getTag();
                }

                String type = tecCommentsBean.getType();
                switch (type) {
                    case "word":
                        musicPosition.add(position, false);
                        teacher_holder.tv_teacher_word.setVisibility(View.VISIBLE);
                        teacher_holder.ll_teacher_music.setVisibility(View.GONE);

                        teacher_holder.tv_teacher_word.setText(tecCommentsBean.getContent());

                        break;
                    case "music":
                        musicPosition.add(position, false);
                        MusicAnimator musicAnimatorSet = new MusicAnimator(this);
                        music_position.set(position,true);
                        teacher_holder.tv_teacher_word.setVisibility(View.GONE);
                        teacher_holder.ll_teacher_music.setVisibility(View.VISIBLE);

                        teacher_holder.ll_teacher_music.setOnClickListener(new MusicClick(position,tecCommentsBean.getAttr(),musicAnimatorSet, teacher_holder.iv_teacher_music));
                        break;
                }
                break;

            case 1:
                ViewHolder student_holder = null;
                if (convertView == null) {
                    convertView = View.inflate(context, R.layout.homepage_dynamic_teacher_comment_item_right, null);
                    student_holder = new ViewHolder();
                    student_holder.tv_student_word = (TextView) convertView.findViewById(R.id.tv_dynamic_student_word);
                    convertView.setTag(student_holder);
                } else {
                    student_holder = (ViewHolder) convertView.getTag();
                }
                musicPosition.add(position, false);
                student_holder.tv_student_word.setText(tecCommentsBean.getContent());

                break;
        }

        return convertView;
    }

    @Override
    public void StopArtMusic(AnimatorSet MusicSet) {

    }

    @Override
    public void StopMusic(AnimationDrawable MusicAnim) {
        this.MusicAnim = MusicAnim;
    }

    class ViewHolder {
        TextView tv_teacher_word;
        TextView tv_student_word;
        ImageView iv_teacher_music;
        LinearLayout ll_teacher_music;
    }

    private class MusicClick implements View.OnClickListener {
        String path;
        int position;
        MusicAnimator musicAnimatorSet;
        ImageView iv_teacher_music;
        public MusicClick(int position, String path, MusicAnimator musicAnimatorSet, ImageView iv_teacher_music) {
            this.path = path;
            this.position = position;
            this.musicAnimatorSet = musicAnimatorSet;
            this.iv_teacher_music = iv_teacher_music;
        }

        @Override
        public void onClick(View v) {
            teacherCommentBack.getTeacherCommentFlag();
            if (path!=null && !path.equals("")) {
                if (MusicStart != position) {
                    if (playAudioUtil != null) {
                        playAudioUtil.stop(1);

                        if (MusicAnim != null) {
                            MusicAnim.stop();
                        }

                        MusicStart = position;
                        musicAnimatorSet.doMusicAnimator(iv_teacher_music);

                        playAudioUtil = new PlayAudioUtil(new PlayAudioListenter() {
                            @Override
                            public void playCompletion() {}
                        });
                        UIUtil.showLog("playAudioUtilpath",path);
                        playAudioUtil.playUrl(path);
                        musicPosition.set(position, true);
                        teacherCommentBack.getTeacherCommentBack(playAudioUtil,MusicAnim);
                    } else {
                        if (!musicPosition.get(position)) {

                            musicAnimatorSet.doMusicAnimator(iv_teacher_music);
                            playAudioUtil = new PlayAudioUtil(new PlayAudioListenter() {
                                @Override
                                public void playCompletion() {

                                }
                            });
                            playAudioUtil.playUrl(path);
                            musicPosition.set(position, true);
                            MusicStart = position;
                            teacherCommentBack.getTeacherCommentBack(playAudioUtil, MusicAnim);
                        } else if (musicPosition.get(position)) {
                            UIUtil.showLog("MusicStart", "2");
                            if (MusicAnim != null) {
                                MusicAnim.stop();
                            }
                            playAudioUtil.stop(1);
                            musicPosition.set(position, false);
                        }
                    }
                    MusicStart = position;
                } else {
                    if (!musicPosition.get(position)) {
                        UIUtil.showLog("MusicStart", "3");

                        musicAnimatorSet.doMusicAnimator(iv_teacher_music);
                        playAudioUtil = new PlayAudioUtil(new PlayAudioListenter() {
                            @Override
                            public void playCompletion() {

                            }
                        });
                        playAudioUtil.playUrl(path);
                        musicPosition.set(position, true);
                        MusicStart = position;
                        teacherCommentBack.getTeacherCommentBack(playAudioUtil,MusicAnim);
                    } else if (musicPosition.get(position)) {
                        UIUtil.showLog("MusicStart", "4");
//                        if (MusicArtSet != null) {
//                            MusicArtSet.end();
//                        }
                        if (MusicAnim != null) {
                            MusicAnim.stop();
                        }
                        playAudioUtil.stop(1);
                        musicPosition.set(position, false);
                    }
                }
            }else {
                UIUtil.ToastshowShort(context,"发生错误，无法播放！");
            }
        }
    }

    public interface TeacherCommentBack{
        void getTeacherCommentFlag();

        void getTeacherCommentBack(PlayAudioUtil playAudioUtil, AnimationDrawable MusicAnim);
    }
}
