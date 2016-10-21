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

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TopicEntity;
import com.example.kk.arttraining.ui.homePage.activity.TopicContent;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

/**
 * Created by kanghuicong on 2016/10/19.
 * QQ邮箱:515849594@qq.com
 */
public class TopicAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{
    Context context;
    List<TopicEntity> list;
    TopicEntity molder;

    public TopicAdapter(Context context, List<TopicEntity> list) {
        this.context = context;
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
        molder = list.get(position);

        convertView = View.inflate(context, R.layout.homepage_dynamic_topic_item, null);
        ImageView iv = (ImageView) convertView.findViewById(R.id.iv_topic);
        TextView tv_title = (TextView) convertView.findViewById(R.id.tv_topic_title);
        TextView tv_number = (TextView) convertView.findViewById(R.id.tv_topic_number);
        TextView tv_participate = (TextView) convertView.findViewById(R.id.tv_topic_participate);


        tv_title.setText(molder.getPic());
//        tv_number.setText(molder.getNum());

        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(context, TopicContent.class);
        intent.putExtra("topic", position + "");
        context.startActivity(intent);
    }
}
