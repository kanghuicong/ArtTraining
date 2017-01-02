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
import com.example.kk.arttraining.bean.parsebean.CommentsBean;
import com.example.kk.arttraining.ui.me.view.PersonalHomePageActivity;
import com.example.kk.arttraining.utils.DateUtils;
import com.example.kk.arttraining.custom.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanghuicong on 2016/10/30.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicContentCommentAdapter extends BaseAdapter {
    List<CommentsBean> commentList = new ArrayList<CommentsBean>();
    CommentsBean commentsBean = new CommentsBean();
    Activity activity;
    int count;

    public DynamicContentCommentAdapter(Activity activity, List<CommentsBean> commentList) {
        this.commentList = commentList;
        this.activity = activity;
        count = commentList.size();
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public CommentsBean getItem(int position) {
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

        Glide.with(activity).load(commentsBean.getUser_pic()).transform(new GlideCircleTransform(activity)).error(R.mipmap.default_user_header).into(holder.iv_header);
        holder.tv_name.setText(commentsBean.getName());
        holder.tv_time.setText(DateUtils.getDate(commentsBean.getTime()));
        holder.tv_content.setText(commentsBean.getContent());

        holder.iv_header.setOnClickListener(new HeaderClick(commentsBean.getUser_id()));

        return convertView;
    }

    private class HeaderClick implements View.OnClickListener {
        int id;
        public HeaderClick(int user_id) {
            id = user_id;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, PersonalHomePageActivity.class);
            intent.putExtra("uid", id);
            activity.startActivity(intent);
        }
    }

    class ViewHolder {
        ImageView iv_header;
        TextView tv_name;
        TextView tv_time;
        TextView tv_content;
    }

    public void changeCount(int changecount){
        count=changecount;
    }

    public int getSelf() {
        return commentList.get(commentList.size()-1).getComment_id();
    }


}
