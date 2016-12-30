package com.example.kk.arttraining.ui.me.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.kk.arttraining.ui.me.bean.MessageBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/12/30 11:25
 * 说明:消息列表adapter
 */
public class MessageListAdapter extends BaseAdapter{

    private Context context;
    private List<MessageBean> messageBeanList;
    private int count;

    public MessageListAdapter(Context context, List<MessageBean> messageBeanList){
        this.context=context;
        this.messageBeanList=messageBeanList;
        count=messageBeanList.size();
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
        return null;
    }

    class ViewHolder{

        ImageView msg_user_pic;
    }
}
