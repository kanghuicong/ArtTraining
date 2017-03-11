package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2017/3/11.
 * QQ邮箱:515849594@qq.com
 */
public class ExamineAdapter extends BaseAdapter {
    Context context;
    ViewHolder holder;
    String[] ranking = {"A", "B", "C", "D", "E"};

    public ExamineAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
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
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.home_examine_lv_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position < 5) {
            holder.tvExaminePosition.setText(ranking[position]);
        } else {
            holder.tvExaminePosition.setText("其他");
        }

        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 6; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("major", (i + 1) + "、" + "土木工程");
            listMap.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(context, listMap, R.layout.home_examine_gv_item, new String[]{"major"}, new int[]{R.id.tv_examine_gv_major});
        holder.gvExamine.setAdapter(simpleAdapter);

        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.tv_examine_position)
        TextView tvExaminePosition;
        @InjectView(R.id.tv_examine_school)
        TextView tvExamineSchool;
        @InjectView(R.id.tv_examine_points)
        TextView tvExaminePoints;
        @InjectView(R.id.tv_examine_number)
        TextView tvExamineNumber;
        @InjectView(R.id.gv_examine)
        MyGridView gvExamine;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
