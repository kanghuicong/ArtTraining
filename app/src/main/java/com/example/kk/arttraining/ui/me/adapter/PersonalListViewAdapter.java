package com.example.kk.arttraining.ui.me.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.LocationBean;
import com.example.kk.arttraining.bean.OrgBean;
import com.example.kk.arttraining.ui.homePage.bean.ProvinceBean;
import com.example.kk.arttraining.ui.homePage.bean.SchoolBean;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/11 16:58
 * 说明:一个text列表adapter
 */
public class PersonalListViewAdapter extends BaseAdapter {
    private List<ProvinceBean> provinceBeanLis;
    private List<LocationBean> cityBeenList;
    private List<SchoolBean> schoolBeanList;
    private List<OrgBean> orgBeanList;
    private ViewHolder holder;
    private Context context;
    private int type;
    private int count;
    private ProvinceBean provinceBean;
    private LocationBean locationBean;
    private SchoolBean schoolBean;
    private OrgBean orgBean;
    private String content;

    //传入省份数据
    public PersonalListViewAdapter(Context context, List<ProvinceBean> provinceBeanLis) {
        this.provinceBeanLis = provinceBeanLis;
        this.context = context;
        type = 1;
        count = provinceBeanLis.size();
    }

    //传入城市列表数据
    public PersonalListViewAdapter(Context context, List<LocationBean> cityBeenList, int type1) {
        this.cityBeenList = cityBeenList;
        this.context = context;
        type = 2;
        count = cityBeenList.size();
    }

    //传入院校里表数据
    public PersonalListViewAdapter(Context context, List<SchoolBean> schoolBeanList, int type1, int type2) {
        this.schoolBeanList = schoolBeanList;
        this.context = context;
        type = 3;
        count = schoolBeanList.size();
    }

    //传入机构数据
    public PersonalListViewAdapter(Context context, List<OrgBean> orgBeanList, int type1, int type2, int type3) {
        this.orgBeanList = orgBeanList;
        this.context = context;
        type = 4;
        count = orgBeanList.size();
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        if (type == 1) {
            provinceBean = provinceBeanLis.get(position);
            return provinceBean;
        } else if (type == 2) {
            locationBean = cityBeenList.get(position);
            return locationBean;
        } else if (type == 3) {
            schoolBean = schoolBeanList.get(position);
            return schoolBean;
        } else if (type == 4) {
            orgBean = orgBeanList.get(position);
            return orgBean;
        }


        return null;
    }

    @Override
    public long getItemId(int position) {


        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (type) {
            case 1:
                provinceBean = provinceBeanLis.get(position);
                content = provinceBean.getName();
                break;
            case 2:
                locationBean = cityBeenList.get(position);
                UIUtil.showLog("我的页面获取城市成功-》",locationBean.toString());
                content = locationBean.getName();
                break;
            case 3:
                schoolBean = schoolBeanList.get(position);
                content = schoolBean.getName();
                break;
            case 4:
                orgBean = orgBeanList.get(position);
                content = orgBean.getName();
                break;
        }

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_signle_line, null);
            holder.tv_content= (TextView) convertView.findViewById(R.id.tv_signle_line);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        holder.tv_content.setText(content);
        return convertView;
    }

    class ViewHolder {
        TextView tv_content;
    }
}
