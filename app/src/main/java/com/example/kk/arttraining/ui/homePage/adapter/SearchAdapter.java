package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.bean.SearchHomepagerBean;

/**
 * Created by kanghuicong on 2016/11/11.
 * QQ邮箱:515849594@qq.com
 */
public class SearchAdapter extends BaseAdapter{
    Context context;
    SearchHomepagerBean searchHomepagerBean;

    public SearchAdapter(Context context, SearchHomepagerBean searchHomepagerBean) {
        this.context = context;
        this.searchHomepagerBean = searchHomepagerBean;
    }

    @Override
    public int getCount() {
        return searchHomepagerBean.getGroups().size()+searchHomepagerBean.getOrg().size()+searchHomepagerBean.getTec().size();
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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.home_search_listview_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position<searchHomepagerBean.getGroups().size()-1){

        }



        return convertView;
    }

    class ViewHolder {
    }
}
