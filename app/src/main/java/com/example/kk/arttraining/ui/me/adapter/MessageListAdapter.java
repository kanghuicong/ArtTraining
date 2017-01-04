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
import com.example.kk.arttraining.utils.UIUtil;

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
            viewHolder.status_pic = (ImageView) convertView.findViewById(R.id.status_pic);
            viewHolder.msg_user_name = (TextView) convertView.findViewById(R.id.msg_user_name);
            viewHolder.msg_content = (TextView) convertView.findViewById(R.id.msg_content);
            viewHolder.msg_time = (TextView) convertView.findViewById(R.id.msg_time);
//            viewHolder.status_content = (TextView) convertView.findViewById(R.id.status_content);
            viewHolder.ll_msg_list = (LinearLayout) convertView.findViewById(R.id.ll_msg_list);
            viewHolder.tvMsgContent = (TextView) convertView.findViewById(R.id.tv_msg_status_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        UIUtil.showLog("head_pic------->", messageBean.getHead_pic() + "");
        UIUtil.showLog("Status_pic------->", messageBean.getStatus_pic() + "----");
        UIUtil.showLog("getStatus_content------->", messageBean.getStatus_content() + "-----》");
        Glide.with(context).load(messageBean.getHead_pic()).asBitmap().error(R.mipmap.default_user_header).skipMemoryCache(true).into(viewHolder.msg_user_pic);
        //先判断动态封面是否为kong
        if (messageBean.getStatus_pic().equals("")) {
            //再判断动态内容是否为空
            if (messageBean.getStatus_content().equals("")) {
                //說明是语音
                if (viewHolder.status_pic.getVisibility() == View.GONE)
                    viewHolder.status_pic.setVisibility(View.VISIBLE);
                if (viewHolder.tvMsgContent.getVisibility() == View.VISIBLE)
                    viewHolder.tvMsgContent.setVisibility(View.GONE);
                Glide.with(context).load(R.mipmap.dynamic_music_pic).skipMemoryCache(true).into(viewHolder.status_pic);

            } else {
                if (viewHolder.status_pic.getVisibility() == View.VISIBLE)
                    viewHolder.status_pic.setVisibility(View.GONE);
                if (viewHolder.tvMsgContent.getVisibility() == View.GONE)
                    viewHolder.tvMsgContent.setVisibility(View.VISIBLE);
                viewHolder.tvMsgContent.setText(messageBean.getStatus_content() + "");
            }
        } else {
            Glide.with(context).load(messageBean.getStatus_pic()).asBitmap().error(R.mipmap.dynamic_music_pic).skipMemoryCache(true).into(viewHolder.status_pic);
        }


        //判断消息类型
        String msg_type = messageBean.getMsg_type();
        if (msg_type != null && (msg_type.equals("like_bbs") || msg_type.equals("like_work") || msg_type.equals("like_gstus"))) {
            viewHolder.msg_content.setVisibility(View.GONE);
            viewHolder.msg_like.setVisibility(View.VISIBLE);
        } else {
            viewHolder.msg_content.setVisibility(View.VISIBLE);
            viewHolder.msg_like.setVisibility(View.GONE);
            viewHolder.msg_content.setText(messageBean.getMsg_content() + "");
            if (msg_type.equals("follow")) {
                viewHolder.ll_msg_list.setEnabled(false);
            }
        }
        viewHolder.msg_user_name.setText(messageBean.getName() + "");
        //消息时间
//        if (!messageBean.getMsg_time().equals("")) {
//            String dateTime = DateUtils.getDate(messageBean.getMsg_time());
//            viewHolder.msg_time.setText(dateTime + "");
//        }
        viewHolder.msg_time.setText(messageBean.getMsg_time() + "");
        viewHolder.ll_msg_list.setOnClickListener(new onclick(context, position));
        viewHolder.msg_user_pic.setOnClickListener(new onclick(context, position));
        viewHolder.status_pic.setOnClickListener(new onclick(context, position));
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
        MessageBean messageBean = null;

        public onclick(Context context, int position) {
            this.context = context;
            this.position = position;
            messageBean = messageBeanList.get(position);
            utype = messageBean.getUtype();
            status_type = messageBean.getStatus_type();

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
                        intent.putExtra("uid", messageBean.getUid() );
                        context.startActivity(intent);
                    }
                    break;
                case R.id.ll_msg_list:
                    Intent intent;
                    switch (status_type) {
                        //作品
                        case "work":
                            intent = new Intent(context.getApplicationContext(), DynamicContent.class);
                            intent.putExtra("status_id", messageBean.getStatus_id() + "");
                            intent.putExtra("stus_type", "work");
                            intent.putExtra("type", "message");
                            context.startActivity(intent);
                            break;
                        //动态
                        case "status":
                            intent = new Intent(context.getApplicationContext(), DynamicContent.class);
                            intent.putExtra("status_id", messageBean.getStatus_id() + "");
                            intent.putExtra("stus_type", "status");
                            intent.putExtra("type", "message");
                            context.startActivity(intent);
                            break;
                        //小组动态
                        case "g_stus":
                            break;
                    }
                    break;

                case R.id.status_pic:
                    Intent intent2;
                    switch (status_type) {
                        //作品
                        case "work":
                            intent2 = new Intent(context.getApplicationContext(), DynamicContent.class);
                            intent2.putExtra("status_id", messageBean.getStatus_id() + "");
                            intent2.putExtra("stus_type", "work");
                            intent2.putExtra("type", "message");
                            context.startActivity(intent2);
                            break;
                        //动态
                        case "status":
                            intent2 = new Intent(context.getApplicationContext(), DynamicContent.class);
                            intent2.putExtra("status_id", messageBean.getStatus_id() + "");
                            intent2.putExtra("stus_type", "status");
                            intent2.putExtra("type", "message");
                            context.startActivity(intent2);
                            break;
                        //小组动态
                        case "g_stus":
                            break;
                    }
                    break;
            }

        }
    }

    //刷新count
    public void refreshCount(int count) {
        this.count = count;
    }

    //获取上拉加载的msg_id
    public int getSelfId() {
        return messageBeanList.get(count - 1).getMsg_id();
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
        TextView tvMsgContent;

    }
}
