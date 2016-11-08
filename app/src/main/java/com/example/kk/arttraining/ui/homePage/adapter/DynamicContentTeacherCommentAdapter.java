package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecCommentsBean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/8.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicContentTeacherCommentAdapter extends BaseAdapter {
    Context context;
    List<TecCommentsBean> tecCommentsBeanList;
    TecCommentsBean tecCommentsBean;

    public DynamicContentTeacherCommentAdapter(Context context, List<TecCommentsBean> tecCommentsBeanList) {
        this.context = context;
        this.tecCommentsBeanList = tecCommentsBeanList;
    }

    @Override
    public int getCount() {
        return 3;
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
        tecCommentsBean = tecCommentsBeanList.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.homepage_dynamic_teacher_comment_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    class ViewHolder {

    }
}
