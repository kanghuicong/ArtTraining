package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.SearchEntity;
import com.example.kk.arttraining.custom.view.MyGridView;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.homePage.adapter.ChoseProvinceAdapter;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/10/18.
 * QQ邮箱:515849594@qq.com
 */
public class ChooseProvinceMain extends Activity {
    @InjectView(R.id.lv_province)
    ListView lvProvince;
    String[] province = {"北京", "天津", "上海", "江西", "河北", "山西", "辽宁", "吉林", "黑龙江", "江苏", "浙江", "安徽", "福建", "重庆", "山东", "河南", "湖北", "湖南", "广东", "海南", "四川", "贵州", "云南", "陕西", "甘肃", "青海", "内蒙古", "广西", "西藏", "宁夏", "新疆", "香港", "澳门", "台湾"};

    View view;
    MyGridView gvProvince;
    List<Boolean> isClick = new ArrayList<Boolean>();
    List<Boolean> firstClick = new ArrayList<Boolean>();
    List<Map<String, String>> countyList = new ArrayList<Map<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_province);
        view = View.inflate(this, R.layout.homepage_province_header, null);
        FindView();
        ButterKnife.inject(this);
        init();
    }

    private void FindView() {
        gvProvince = (MyGridView) view.findViewById(R.id.gv_province);
        for(int i=0;i< province.length;i++) {
            isClick.add(false);
            firstClick.add(true);
        }
    }

    private void init() {
        lvProvince.addHeaderView(view);

        ChoseProvinceAdapter adapter = new ChoseProvinceAdapter(this);
        lvProvince.setAdapter(adapter);
        lvProvince.setOnItemClickListener(new ProvinceItemClick());

        List<Map<String, String>> mList = new ArrayList<Map<String, String>>();
        List<SearchEntity> hot_list = new ArrayList<SearchEntity>();

        for (int i = 0; i < 3; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("content", i + "");
            mList.add(map);
        }

        SimpleAdapter gv_adapter = new SimpleAdapter(this, mList,
                R.layout.homepage_province_grid_item, new String[]{"content"},
                new int[]{R.id.tv_province_hot});
        gvProvince.setAdapter(gv_adapter);
        gvProvince.setOnItemClickListener(new HotProvinceItemClick());
    }

    private class HotProvinceItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            UIUtil.ToastshowShort(ChooseProvinceMain.this, position + "");
        }
    }

    private class ProvinceItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MyListView lv_province_county = (MyListView) view.findViewById(R.id.lv_province_county);

            if (!isClick.get(position)) {
                if(firstClick.get(position)) {

                    for (int i = 0; i < 3; i++) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("content", i + "");
                        countyList.add(map);
                    }
                    firstClick.add(position, false);
                }
                SimpleAdapter gv_adapter = new SimpleAdapter(ChooseProvinceMain.this, countyList,
                        R.layout.homepage_province_grid_item, new String[]{"content"},
                        new int[]{R.id.tv_province_hot});
                lv_province_county.setVisibility(View.VISIBLE);
                lv_province_county.setAdapter(gv_adapter);
                lv_province_county.setOnItemClickListener(new ProvinceCountyItemClick());
                isClick.add(position,true);
            }else if(isClick.get(position)){
                lv_province_county.setVisibility(View.GONE);
            }
        }

        private class ProvinceCountyItemClick implements AdapterView.OnItemClickListener {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }
    }
}
