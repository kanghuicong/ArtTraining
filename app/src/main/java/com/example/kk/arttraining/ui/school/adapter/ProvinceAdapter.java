package com.example.kk.arttraining.ui.school.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.school.bean.ParseProvinceListBean;
import com.example.kk.arttraining.ui.school.bean.ProvinceBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/26 11:47
 * 说明:省份listview适配器
 */
public class ProvinceAdapter extends BaseAdapter {

    private ViewHolder viewHolder;
    private Context context;
    private List<ProvinceBean> beanList;
    private int selectPostion;

    public ProvinceAdapter(Context context) {
        this.context = context;
    }

    public ProvinceAdapter(Context context, List<ProvinceBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    @Override
    public int getCount() {
        return beanList.size();
    }

    @Override
    public Object getItem(int position) {


        return beanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProvinceBean bean = beanList.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_school_province, null);
            viewHolder.province_name = (TextView) convertView.findViewById(R.id.tv_school_province);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.province_name.setText(bean.getName());
        try {
            if (position == selectPostion) {
                viewHolder.province_name.setTextColor(context.getResources().getColor(R.color.blue_overlay));
            } else {
                viewHolder.province_name.setTextColor(context.getResources().getColor(R.color.black));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return convertView;
    }

    public void selectPosition(int selectPostion) {
        this.selectPostion = selectPostion;
    }

    class ViewHolder {
        TextView province_name;
    }
}
