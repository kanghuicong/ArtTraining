package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.SearchEntity;
import com.example.kk.arttraining.custom.view.MyGridView;
import com.example.kk.arttraining.ui.homePage.adapter.ChoseProvinceAdapter;
import com.example.kk.arttraining.ui.me.MeMainActivity;
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
public class ChoseProvinceMain extends Activity implements AdapterView.OnItemClickListener {
    @InjectView(R.id.lv_province)
    ListView lvProvince;
    GetProvince callBack;
    @InjectView(R.id.tv_province_location)
    TextView tvProvinceLocation;
    @InjectView(R.id.tv_province_choose)
    TextView tvProvinceChoose;
    @InjectView(R.id.gv_province)
    MyGridView gvProvince;

    public ChoseProvinceMain(GetProvince callBack) {
        this.callBack = callBack;
    }

    public ChoseProvinceMain() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_province);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        ChoseProvinceAdapter adapter = new ChoseProvinceAdapter(this);
        lvProvince.setAdapter(adapter);
        lvProvince.setOnItemClickListener(this);

        List<Map<String, String>> mList = new ArrayList<Map<String, String>>();
        List<SearchEntity> hot_list = new ArrayList<SearchEntity>();

        for (int i = 0; i < 3; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("content", i + "");
            mList.add(map);
            SimpleAdapter gv_adapter = new SimpleAdapter(this, mList,
                    R.layout.homepage_province_grid_item, new String[]{"content"},
                    new int[]{R.id.tv_province_hot});
            gvProvince.setAdapter(gv_adapter);
            gvProvince.setOnItemClickListener(new ProvinceItemClick());
        }
    }

    private class ProvinceItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            UIUtil.ToastshowShort(ChoseProvinceMain.this,position+"");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String province_name = (String) parent.getItemAtPosition(position);
        callBack.getprovince(province_name);
        finish();
    }

    public interface GetProvince {
        public String getprovince(String provice);
    }


}
