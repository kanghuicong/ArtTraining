package com.example.kk.arttraining.ui.me.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.me.bean.FansBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.GlideCircleTransform;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/9 11:19
 * 说明:粉丝、关注adapter
 */
public class FansAdapter extends BaseAdapter {
    private List<FansBean> fansBeanList;
    private Context context;
    private ViewHolder holder;

    public FansAdapter(Context context, List<FansBean> fansBeanList) {
        this.fansBeanList = fansBeanList;
        this.context = context;

    }

    @Override
    public int getCount() {
        return 10;
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
//        FansBean fansBean = fansBeanList.get(position);
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
        Glide.with(context).load(Config.USER_HEADER_Url).transform(new GlideCircleTransform(context)).into(holder.head_pic);
        return convertView;
    }

    class ViewHolder {
        ImageView head_pic;
        TextView tv_name;
        TextView tv_city;
        TextView tv_type;
        Button btn_foucs;


    }
}
