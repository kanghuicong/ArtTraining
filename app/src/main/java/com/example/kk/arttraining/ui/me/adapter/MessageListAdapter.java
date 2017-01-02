package com.example.kk.arttraining.ui.me.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.activity.DynamicContent;
import com.example.kk.arttraining.ui.homePage.activity.ThemeTeacherContent;
import com.example.kk.arttraining.ui.me.bean.MessageBean;
import com.example.kk.arttraining.ui.me.view.PersonalHomePageActivity;
import com.example.kk.arttraining.utils.DateUtils;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/12/30 11:25
 * 说明:消息列表adapter
 */
public class MessageListAdapter extends BaseAdapter {

    private Context context;
    private List<MessageBean> messageBeanList;
    private int count;
    private ViewHolder viewHolder;
    MessageBean messageBean;

    public MessageListAdapter(Context context, List<MessageBean> messageBeanList) {
        this.context = context;
        this.messageBeanList = messageBeanList;
        count = messageBeanList.size();
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
        messageBean = messageBeanList.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_msg_list, null);
            viewHolder.msg_user_pic = (ImageView) convertView.findViewById(R.id.msg_user_pic);
            viewHolder.msg_like = (ImageView) convertView.findViewById(R.id.msg_like);
            viewHolder.status_pic = (ImageView) convertView.findViewById(R.id.msg_user_pic);
            viewHolder.msg_user_name = (TextView) convertView.findViewById(R.id.msg_user_name);
            viewHolder.msg_content = (TextView) convertView.findViewById(R.id.msg_content);
            viewHolder.msg_time = (TextView) convertView.findViewById(R.id.msg_time);
//            viewHolder.status_content = (TextView) convertView.findViewById(R.id.status_content);
            viewHolder.ll_msg_list = (LinearLayout) convertView.findViewById(R.id.ll_msg_list);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(messageBean.getHead_pic()).asBitmap().error(R.mipmap.default_user_header).skipMemoryCache(true).into(viewHolder.msg_user_pic);
        //判断消息类型
        String msg_type = messageBean.getMsg_type();
        if (msg_type != null && msg_type.equals("like")) {
            viewHolder.msg_content.setVisibility(View.GONE);
            viewHolder.msg_like.setVisibility(View.VISIBLE);
        } else {
            viewHolder.msg_content.setVisibility(View.GONE);
            viewHolder.msg_like.setVisibility(View.VISIBLE);
            viewHolder.msg_content.setText(messageBean.getMsg_content() + "");
            if (msg_type.equals("follow")) {
                viewHolder.ll_msg_list.setEnabled(false);
            }
        }
        Glide.with(context).load(messageBean.getStatus_pic()).asBitmap().error(R.mipmap.dynamic_music_pic).skipMemoryCache(true).into(viewHolder.status_pic);
        //消息时间
        if (!messageBean.getMsg_time().equals("")) {
            String dateTime = DateUtils.getDate(messageBean.getMsg_time());
            viewHolder.msg_time.setText(dateTime + "");
        }

        viewHolder.ll_msg_list.setOnClickListener(new onclick(context,position));
        viewHolder.msg_user_pic.setOnClickListener(new onclick(context,position));
        return convertView;
    }


    //点击事件
     class onclick implements View.OnClickListener {
        //用户id
        int uid;
        //用户类型
        String utype;
        //上下文
        Context context;
        //动态类型
        String status_type;
        //动态id
        int status_id;

        int position;

        MessageBean messageBean=null;

        public onclick(Context context,int position) {
            this.context = context;
            this.position = position;
            messageBean=messageBeanList.get(position);
            utype=messageBean.getUtype();
            status_type=messageBean.getStatus_type();

        }



        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.msg_user_pic:
                    if (utype.equals("tec")) {
                        Intent intent = new Intent(context, ThemeTeacherContent.class);
                        intent.putExtra("type", "msg");
                        intent.putExtra("tec_id", messageBean.getUid() + "");
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, PersonalHomePageActivity.class);
                        intent.putExtra("uid", messageBean.getUid() + "");
                        context.startActivity(intent);
                    }
                    break;
                case R.id.ll_msg_list:
                    Intent intent;
                    switch (status_type) {
                        //作品
                        case "work":
                            intent = new Intent(context.getApplicationContext(), DynamicContent.class);
                            intent.putExtra("status_id", messageBean.getStatus_id());
                            intent.putExtra("stus_type", "work");
                            context.startActivity(intent);
                            break;
                        //动态
                        case "status":
                            intent = new Intent(context.getApplicationContext(), DynamicContent.class);
                            intent.putExtra("status_id", messageBean.getStatus_id());
                            intent.putExtra("stus_type", "status");
                            context.startActivity(intent);
                            break;
                        //小组动态
                        case "g_stus":
                            break;
                    }
                    break;
            }

        }
    }

    public  void refreshCount(int count){
        this.count=count;
    }

    class ViewHolder {

        ImageView msg_user_pic;
        ImageView msg_like;
        ImageView status_pic;
        TextView msg_user_name;
        TextView msg_content;
        TextView msg_time;
        TextView status_content;
        LinearLayout ll_msg_list;

    }
}
