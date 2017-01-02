package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.InfoBean;
import com.example.kk.arttraining.custom.view.FilletImageView;
import com.example.kk.arttraining.ui.webview.CourseWebView;
import com.example.kk.arttraining.ui.webview.WebActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by kanghuicong on 2016/10/19.
 * QQ邮箱:515849594@qq.com
 */
public class TopicAdapter extends BaseAdapter {
    Context context;
    Map<String, Object> themesMap;
    InfoBean molder;
    List<InfoBean> list;

    public TopicAdapter(Context context, Map<String, Object> themesMap) {
        this.context = context;
        this.themesMap = themesMap;
        list= (List<InfoBean>) themesMap.get("data");
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
        FilletImageView iv = (FilletImageView) convertView.findViewById(R.id.iv_topic);
        LinearLayout ll_topic = (LinearLayout) convertView.findViewById(R.id.ll_topic);
        View view_topic = (View) convertView.findViewById(R.id.view_topic);
        TextView tv_title = (TextView) convertView.findViewById(R.id.tv_topic_title);
        TextView tv_number = (TextView) convertView.findViewById(R.id.tv_topic_number);
        TextView tv_time = (TextView) convertView.findViewById(R.id.tv_topic_time);

        Glide.with(context).load(molder.getPic()).error(R.mipmap.ic_launcher).diskCacheStrategy( DiskCacheStrategy.SOURCE ).into(iv);

        tv_title.setText(molder.getTitle());
        if (position == list.size() - 1) {
            view_topic.setVisibility(View.GONE);
        }
//        if (molder.getCreate_time()==null || molder.getCreate_time().equals("")){
//            tv_time.setVisibility(View.GONE);
//        }else {
//            tv_time.setText(DateUtils.getDate(molder.getCreate_time()));
//        }
//        tv_number.setText("关注数:"+ molder.getBrowse_num());

        ll_topic.setOnClickListener(new TopicClick(molder.getUrl()));

        return convertView;
    }

    private class TopicClick implements View.OnClickListener {
        String url;
        public TopicClick(String url) {
            this.url = url;
        }

        @Override
        public void onClick(View v) {
            if (url != null && !url.equals("")) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("title", "资讯");
                context.startActivity(intent);
            }
        }
    }
}
