package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.adapter.ChoseProvinceAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/10/18.
 * QQ邮箱:515849594@qq.com
 */
public class ChoseProvinceMain extends Activity implements AdapterView.OnItemClickListener{
    @InjectView(R.id.lv_province)
    ListView lvProvince;
    GetProvince callBack;

    public ChoseProvinceMain(GetProvince callBack) {
        this.callBack = callBack;
    }

    public ChoseProvinceMain(){}

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
