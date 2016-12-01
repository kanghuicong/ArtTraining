package com.example.kk.arttraining.ui.homePage.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecCommentsBean;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.bean.parsebean.CommentsBean;
import com.example.kk.arttraining.bean.parsebean.ParseCommentDetail;
import com.example.kk.arttraining.bean.parsebean.TecCommentsList;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.homePage.activity.ThemeTeacherContent;
import com.example.kk.arttraining.utils.DateUtils;
import com.example.kk.arttraining.utils.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanghuicong on 2016/11/5.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicContentTeacherAdapter extends BaseAdapter {
    List<ParseCommentDetail> parseCommentDetailList = new ArrayList<ParseCommentDetail>();
    ParseCommentDetail parseCommentDetail = new ParseCommentDetail();
    List<TecCommentsBean> tec_comments;
    TecInfoBean tecInfoBean;
    Activity activity;
    DynamicContentTeacherCommentAdapter.TeacherCommentBack teacherCommentBack;

    public DynamicContentTeacherAdapter(Activity activity,List<ParseCommentDetail> parseCommentDetailList,DynamicContentTeacherCommentAdapter.TeacherCommentBack teacherCommentBack) {
        this.parseCommentDetailList = parseCommentDetailList;
        this.activity = activity;
        this.teacherCommentBack = teacherCommentBack;
    }

    @Override
    public int getCount() {
        return parseCommentDetailList.size();
    }

    @Override
    public ParseCommentDetail getItem(int position) {
        return parseCommentDetailList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        parseCommentDetail = parseCommentDetailList.get(position);
        tecInfoBean = parseCommentDetail.getTec();
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.homepage_dynamic_teacher_item, null);
            holder = new ViewHolder();
            holder.iv_header = (ImageView) convertView.findViewById(R.id.iv_dynamic_teacher_header);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_dynamic_teacher_name);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_dynamic_teacher_time);
            holder.tv_college = (TextView) convertView.findViewById(R.id.tv_dynamic_teacher_school);
            holder.tv_professor = (TextView) convertView.findViewById(R.id.tv_dynamic_teacher_professor);
            holder.lv_teacher_comment = (MyListView) convertView.findViewById(R.id.lv_dynamic_content_teacher_comment);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_name.setText(tecInfoBean.getName());
        Glide.with(activity).load(tecInfoBean.getTec_pic()).transform(new GlideCircleTransform(activity)).error(R.mipmap.default_user_header).into(holder.iv_header);
        if (tecInfoBean.getTime() != null && !tecInfoBean.getTime().equals("")) {
            holder.tv_time.setText(DateUtils.getDate(tecInfoBean.getTime())+"");
        }else {
            holder.tv_time.setVisibility(View.GONE);
        }

        holder.tv_college.setText(tecInfoBean.getSchool());
        holder.tv_professor.setText(tecInfoBean.getIdentity());

        tec_comments = parseCommentDetail.getTec_comments();
        DynamicContentTeacherCommentAdapter dynamicContentTeacherCommentAdapter = new DynamicContentTeacherCommentAdapter(activity,tec_comments,teacherCommentBack);
        holder.lv_teacher_comment.setAdapter(dynamicContentTeacherCommentAdapter);

        holder.iv_header.setOnClickListener(new HeaderClick(position,tecInfoBean.getTec_id()));

        return convertView;
    }
    private class HeaderClick implements View.OnClickListener {
        int position;
        int tec_id;
        public HeaderClick(int position,int tec_id) {
            this.position = position;
            this.tec_id  =tec_id;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, ThemeTeacherContent.class);
            intent.putExtra("tec_id", tec_id + "");
            activity.startActivity(intent);
        }
    }


    class ViewHolder {
        ImageView iv_header;
        TextView tv_name;
        TextView tv_time;
        TextView tv_college;
        TextView tv_professor;
        MyListView lv_teacher_comment;
    }
}

