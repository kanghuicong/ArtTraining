package com.example.kk.arttraining.ui.me.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.bean.Follow;
import com.example.kk.arttraining.ui.me.bean.FansBean;
import com.example.kk.arttraining.ui.me.view.PersonalHomePageActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.GlideCircleTransform;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/9 11:19
 * 说明:粉丝、关注adapter
 */
public class FansAdapter extends BaseAdapter {
    List<Follow> followList;
    private Context context;
    private ViewHolder holder;
    private String type;
    private int count;

    public FansAdapter(Context context, List<Follow> followList, String type) {
        this.followList = followList;
        this.context = context;
        this.type = type;
        count = followList.size();

    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return followList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Follow followBean = followList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_fans, null);
            holder.btn_foucs = (Button) convertView.findViewById(R.id.btn_foucs);
            holder.head_pic = (ImageView) convertView.findViewById(R.id.fans_head_pic);
            holder.tv_city = (TextView) convertView.findViewById(R.id.tv_fans_city);
            holder.tv_type = (TextView) convertView.findViewById(R.id.tv_fans_type);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_fans_name);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(followBean.getHead_pic()).transform(new GlideCircleTransform(context)).into(holder.head_pic);
        holder.tv_name.setText(followBean.getName());
        holder.tv_city.setText(followBean.getCity());
        holder.tv_type.setText(followBean.getIdentity());
        holder.head_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PersonalHomePageActivity.class);
                intent.putExtra("uid", followBean.getUid());
                context.startActivity(intent);
            }
        });

        if(type.equals("fans")){
            holder.btn_foucs.setVisibility(View.GONE);
        }
//        holder.btn_foucs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (type.equals("fans")) {
//
//                }
//
//            }
//        });



        return convertView;
    }

    class ViewHolder {
        ImageView head_pic;
        TextView tv_name;
        TextView tv_city;
        TextView tv_type;
        Button btn_foucs;


    }

    public int getSelfId() {
        return followList.get(count - 1).getFollow_id();
    }
}
