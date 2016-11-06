package com.example.kk.arttraining.ui.homePage.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecCommentsBean;
import com.example.kk.arttraining.bean.parsebean.CommentsBean;
import com.example.kk.arttraining.bean.parsebean.TecCommentsList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanghuicong on 2016/11/5.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicContentTeacherAdapter extends BaseAdapter {
    List<TecCommentsBean> commentList = new ArrayList<TecCommentsBean>();
    TecCommentsBean commentsBean = new TecCommentsBean();
    Activity activity;
    int count;

    public DynamicContentTeacherAdapter(Activity activity, List<TecCommentsBean> commentList) {
        this.commentList = commentList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public TecCommentsBean getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        commentsBean = commentList.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.homepage_dynamic_content_comment, null);
            holder = new ViewHolder();
            holder.iv_header = (ImageView) convertView.findViewById(R.id.iv_dynamic_comment_header);
            holder.tv_name = (TextView) convertView.findViewById(R.id.iv_dynamic_comment_name);
            holder.tv_time = (TextView) convertView.findViewById(R.id.iv_dynamic_comment_time);
            holder.tv_content = (TextView) convertView.findViewById(R.id.iv_dynamic_comment_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    class ViewHolder {
        ImageView iv_header;
        TextView tv_name;
        TextView tv_time;
        TextView tv_content;
    }
}

