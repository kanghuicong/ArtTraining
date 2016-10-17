package com.example.kk.arttraining.ui.homePage.adapter;

import android.app.Activity;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.MessageEntity;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

/**
 * Created by kanghuicong on 2016/10/10.
 * QQ邮箱:515849594@qq.com
 */
public class MessageListAdapter extends BaseAdapter{
    Activity activity;
    List<MessageEntity> list;
    MessageEntity molder;

    public MessageListAdapter(Activity activity, List<MessageEntity> list){
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        molder = list.get(position);
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.homepage_message_listview, null);
            holder = new ViewHolder();
            holder.iv_header = (ImageView) convertView.findViewById(R.id.iv_message_header);
            holder.tv_name = (TextView)convertView.findViewById(R.id.tv_message_name);
            holder.tv_content = (TextView)convertView.findViewById(R.id.tv_message_content);
            holder.tv_time = (TextView)convertView.findViewById(R.id.tv_message_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(molder.getName());

        return convertView;
    }


    class ViewHolder {
        ImageView iv_header;
        TextView tv_name;
        TextView tv_content;
        TextView tv_time;
    }
}
