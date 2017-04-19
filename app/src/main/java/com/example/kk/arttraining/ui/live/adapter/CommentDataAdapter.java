package com.example.kk.arttraining.ui.live.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.live.bean.LiveCommentBean;
import com.example.kk.arttraining.utils.UIUtil;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * 作者：wschenyongyin on 2017/1/9 15:29
 * 说明:直播间
 */
public class CommentDataAdapter extends BaseAdapter {

    private Context context;
    private List<LiveCommentBean> liveCommentBeanList;
    LiveCommentBean liveCommentBean;
    private int count;
    private ViewHolder holder;
    URL url;
    Drawable drawable = null;

    public CommentDataAdapter(Context context, List<LiveCommentBean> liveCommentBeanList) {
        this.context = context;
        this.liveCommentBeanList = liveCommentBeanList;
        count = liveCommentBeanList.size();
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UIUtil.showLog("CommentData","position");
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_live_comment, null);
            holder.tv_content = (TextView) convertView.findViewById(R.id.live_comment_content);
            holder.tv_name = (TextView) convertView.findViewById(R.id.live_comment_name);
            holder.tv_img = (ImageView) convertView.findViewById(R.id.live_comment_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        liveCommentBean = liveCommentBeanList.get(position);
        if (liveCommentBean.getType()!=null&&liveCommentBean.getType().equals("gift")){
            holder.tv_img.setVisibility(View.VISIBLE);
            Glide.with(context).load(liveCommentBean.getGift_pic()).error(R.mipmap.default_logo).into(holder.tv_img);
            holder.tv_name.setText(liveCommentBean.getName() + ":");
            holder.tv_content.setText("我送了一个");
        }else {
            holder.tv_img.setVisibility(View.GONE);
            holder.tv_name.setText(liveCommentBean.getName() + ":");
            holder.tv_content.setText(liveCommentBean.getContent());
        }

        return convertView;
    }


    //获取最后一条comment_id
    public int getLastCommentId() {
        return liveCommentBeanList.get(count - 1).getComm_id();
    }

    //更新count
    public void RefreshCount(int count) {
        this.count = count;
    }


    class ViewHolder {
        TextView tv_name;
        TextView tv_content;
        ImageView tv_img;
    }

}
