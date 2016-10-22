package com.example.kk.arttraining.ui.homePage.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.ThemesBean;
import com.example.kk.arttraining.bean.TopicEntity;
import com.example.kk.arttraining.ui.homePage.activity.TopicContent;
import com.example.kk.arttraining.utils.GlideRoundTransform;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by kanghuicong on 2016/10/19.
 * QQ邮箱:515849594@qq.com
 */
public class TopicAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{
    Context context;
    Map<String, Object> themesMap;
    ThemesBean molder;
    List<ThemesBean> list;

    public TopicAdapter(Context context, Map<String, Object> themesMap) {
        this.context = context;
        this.themesMap = themesMap;
        list= (List<ThemesBean>) themesMap.get("data");
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

        molder =list.get(position) ;

        convertView = View.inflate(context, R.layout.homepage_dynamic_topic_item, null);
        ImageView iv = (ImageView) convertView.findViewById(R.id.iv_topic);
        TextView tv_title = (TextView) convertView.findViewById(R.id.tv_topic_title);
        TextView tv_number = (TextView) convertView.findViewById(R.id.tv_topic_number);
        TextView tv_participate = (TextView) convertView.findViewById(R.id.tv_topic_participate);


        Glide.with(context).load(molder.getPic()).transform(new GlideRoundTransform(context)).into(iv);
        tv_title.setText(molder.getTitle());
        tv_number.setText("话题数:"+ molder.getNum());

        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(context, TopicContent.class);
        intent.putExtra("topic", position + "");
        context.startActivity(intent);
    }
}
