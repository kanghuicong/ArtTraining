package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecCommentsBean;
import com.example.kk.arttraining.utils.PlayAudioUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kanghuicong on 2016/11/8.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicContentTeacherCommentAdapter extends BaseAdapter {
    Context context;
    List<TecCommentsBean> tecCommentsBeanList;
    TecCommentsBean tecCommentsBean;
    PlayAudioUtil playAudioUtil;
    List<Boolean> music_position = new ArrayList<Boolean>();

    public DynamicContentTeacherCommentAdapter(Context context, List<TecCommentsBean> tecCommentsBeanList) {
        this.context = context;
        this.tecCommentsBeanList = tecCommentsBeanList;
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
                    teacher_holder.tv_teacher_music = (TextView) convertView.findViewById(R.id.tv_dynamic_teacher_music);
                    convertView.setTag(teacher_holder);
                } else {
                    teacher_holder = (ViewHolder) convertView.getTag();
                }

                String type = tecCommentsBean.getType();
                switch (type) {
                    case "word":
                        teacher_holder.tv_teacher_word.setVisibility(View.VISIBLE);
                        teacher_holder.tv_teacher_music.setVisibility(View.GONE);

                        teacher_holder.tv_teacher_word.setText(tecCommentsBean.getContent());

                        break;
                    case "music":
                        music_position.set(position,true);
                        teacher_holder.tv_teacher_word.setVisibility(View.GONE);
                        teacher_holder.tv_teacher_music.setVisibility(View.VISIBLE);

//                        teacher_holder.tv_teacher_music.setOnClickListener(new MusicClick());
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

                student_holder.tv_student_word.setText(tecCommentsBean.getContent());

                break;
        }

        return convertView;
    }

    class ViewHolder {
        TextView tv_teacher_word;
        TextView tv_teacher_music;
        TextView tv_student_word;
    }

    private class MusicClick implements View.OnClickListener {
        String path;
        int position;
        public MusicClick(int position,String path) {
            this.path = path;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            playAudioUtil.stop();
            if (!music_position.get(position)) {
                playAudioUtil.playUrl(path);
                music_position.set(position, true);
            }else if (music_position.get(position)){
                playAudioUtil.stop();
                music_position.set(position, false);
            }
        }
    }
}
