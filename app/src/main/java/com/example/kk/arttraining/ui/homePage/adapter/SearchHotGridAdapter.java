package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.modelbean.SearchEntity;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

/**
 * Created by kanghuicong on 2016/9/23.
 * QQ邮箱:515849594@qq.com
 */
public class SearchHotGridAdapter extends BaseAdapter {
    Context context;
    List<SearchEntity> list;
    SearchEntity molder;

    public SearchHotGridAdapter(Context context,List<SearchEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public SearchEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        molder = list.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.homepage_search_hot_gridview, null);
            holder = new ViewHolder();
            holder.bt_search_hot = (Button) convertView.findViewById(R.id.bt_search_hot);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bt_search_hot.setText(molder.getUser_search());
        holder.bt_search_hot.setOnClickListener(new ClickListener(position));

        return convertView;
    }

    class ClickListener implements View.OnClickListener{
        int position;
        ClickListener(int position){
            this.position = position;
        }
        @Override
        public void onClick(View v) {
            UIUtil.ToastshowShort(context,position+"");
        }
    }

    class ViewHolder {
        Button bt_search_hot;
    }
}

