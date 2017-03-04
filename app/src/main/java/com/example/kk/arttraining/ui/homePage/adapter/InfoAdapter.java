package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.modelbean.InfoBean;
import com.example.kk.arttraining.custom.view.FilletImageView;
import com.example.kk.arttraining.ui.webview.WebActivity;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by kanghuicong on 2016/10/19.
 * QQ邮箱:515849594@qq.com
 */
public class InfoAdapter extends BaseAdapter {
    Context context;
    Map<String, Object> themesMap;
    InfoBean molder;
    List<InfoBean> list;
    String type;
    int count;

    public InfoAdapter(Context context, List<InfoBean> list,String type) {
        this.context = context;
        this.list = list;
        this.type = type;
        count = list.size();
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        molder = list.get(position);

        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.homepage_dynamic_topic_item, null);
            holder = new ViewHolder();

//            holder.iv = (FilletImageView) convertView.findViewById(R.id.iv_topic);
            holder.tv_type = (TextView) convertView.findViewById(R.id.tv_info_type);
            holder.ll_topic = (LinearLayout) convertView.findViewById(R.id.ll_topic);
            holder.view_topic = (View) convertView.findViewById(R.id.view_topic);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_topic_title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        Glide.with(context).load(molder.getPic()).error(R.mipmap.ic_launcher).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.iv);

        if (type.equals("infoFragment")) {
            holder.tv_type.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        }

        holder.tv_type.setText(molder.getInfo_type());
        holder.tv_title.setText(molder.getTitle());
        if (position == list.size() - 1) {
            holder.view_topic.setVisibility(View.GONE);
        } else {
            holder.view_topic.setVisibility(View.VISIBLE);
        }
        holder.ll_topic.setOnClickListener(new TopicClick(molder.getUrl(), molder.getInfo_id()));


        return convertView;

    }

    class ViewHolder {
        FilletImageView iv;
        LinearLayout ll_topic;
        View view_topic;
        TextView tv_title;
        TextView tv_type;
    }

    private class TopicClick implements View.OnClickListener {
        String url;
        int info_id;

        public TopicClick(String url, int info_id) {
            this.url = url;
            this.info_id = info_id;
        }

        @Override
        public void onClick(View v) {
            if (url != null && !url.equals("")) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("title", "资讯");
                intent.putExtra("info_id", info_id + "");
                UIUtil.showLog("info_id", info_id + "");
                context.startActivity(intent);
            }
        }
    }

    public void ChangeCount(int changeCount) {
        count = changeCount;
    }

    public int getSelfId() {
        return list.get(list.size() - 1).getInfo_id();
    }

}
