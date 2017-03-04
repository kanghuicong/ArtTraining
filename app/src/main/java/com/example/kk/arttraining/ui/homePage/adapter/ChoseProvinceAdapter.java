package com.example.kk.arttraining.ui.homePage.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.modelbean.CitysBean;
import com.example.kk.arttraining.bean.modelbean.LocationBean;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.homePage.prot.IChoseCity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.PreferencesUtils;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;


/**
 * 作者：wschenyongyin on 2016/8/25 11:40
 * 说明:
 */
public class ChoseProvinceAdapter extends BaseAdapter {
    Context context;
    List<CitysBean> cityList;
    List<LocationBean> locationList;
    String sort_word;
    String from;

    IChoseCity iChoseCity;

    public ChoseProvinceAdapter(Context context, List<CitysBean> cityList, String from, IChoseCity iChoseCity) {
        this.context = context;
        this.cityList = cityList;
        this.from = from;
        this.iChoseCity=iChoseCity;
    }

    @Override
    public int getCount() {
        return cityList.size() * 2;
    }

    @Override
    public Object getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getItemViewType(int position) {
        int ret;
        if (position % 2 == 0) {
            ret = 0;
        } else {
            ret = 1;
        }
        return ret;
    }

    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        int viewType = getItemViewType(position);
        switch (viewType) {
            case 0:
                sort_word = cityList.get(position / 2).getSort_word();
                convertView = View.inflate(context, R.layout.homepage_province_item_position, null);
                TextView province_name = (TextView) convertView.findViewById(R.id.tv_province_suspension1);

                province_name.setText(sort_word);
                break;
            case 1:
                locationList = cityList.get((position - 1) / 2).getSort_citys();
                if (convertView == null) {
                    convertView = View.inflate(context, R.layout.homepage_province_item, null);
                    holder = new ViewHolder();
                    holder.lv_province_county = (MyListView) convertView.findViewById(R.id.lv_province_county);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                ChoseProvincePositionAdapter choseProvincePostionAdapter = new ChoseProvincePositionAdapter(context, locationList);
                holder.lv_province_county.setAdapter(choseProvincePostionAdapter);
                holder.lv_province_county.setOnItemClickListener(new ProvinceClick(locationList));
                break;
        }

        return convertView;
    }


    class ViewHolder {
        MyListView lv_province_county;
    }

    private class ProvinceClick implements AdapterView.OnItemClickListener {
        List<LocationBean> locationList;

        public ProvinceClick(List<LocationBean> locationList) {
            this.locationList = locationList;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           LocationBean locationBean= locationList.get(position);

            if(from.equals("me")){
                iChoseCity.getCity(locationBean.getName(),locationBean.getCity_id());
            }else {
                Config.CITY=locationBean.getName();
                PreferencesUtils.put(context, "province", locationList.get(position).getName());
                UIUtil.showLog("tvHomepageAddress1", locationList.get(position).getName());

                Activity activity = (Activity) context;
                activity.finish();
            }
        }
    }
}
