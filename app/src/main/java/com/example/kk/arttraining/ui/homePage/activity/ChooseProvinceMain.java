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

    View view;
    MyGridView gvProvince;

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
    }

    private void init() {
        lvProvince.addHeaderView(view);

        ChoseProvinceAdapter adapter = new ChoseProvinceAdapter(this);
        lvProvince.setAdapter(adapter);

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
}
