package com.example.kk.arttraining.ui.homePage.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.parsebean.CommentsBean;

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

    public DynamicContentCommentAdapter(Activity activity){
        activity = activity;
        count=0;
    }
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
        holder.tv_name.setText(commentsBean.getName());
        holder.tv_time.setText(commentsBean.getTime());
        holder.tv_content.setText(commentsBean.getContent());

        return convertView;
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

}
