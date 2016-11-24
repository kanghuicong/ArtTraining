package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.CitysBean;
import com.example.kk.arttraining.bean.SearchEntity;
import com.example.kk.arttraining.bean.parsebean.ParseLocationBean;
import com.example.kk.arttraining.custom.view.MyGridView;
import com.example.kk.arttraining.ui.homePage.adapter.ChoseProvinceAdapter;
import com.example.kk.arttraining.ui.homePage.function.province.ProvinceData;
import com.example.kk.arttraining.ui.homePage.prot.IChoseCity;
import com.example.kk.arttraining.ui.homePage.prot.IProvince;
import com.example.kk.arttraining.ui.me.AboutActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.TitleBack;
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
public class ChooseProvinceMain extends Activity implements IProvince , IChoseCity {
    @InjectView(R.id.lv_province)
    ListView lvProvince;

    View view;
    MyGridView gvProvince;
    TextView tvLocation;
    @InjectView(R.id.tv_province_suspension)
    TextView tvProvinceSuspension;
    @InjectView(R.id.ll_province_suspension)
    LinearLayout llProvinceSuspension;

    List<CitysBean> cityList = new ArrayList<CitysBean>();
//    @InjectView(R.id.province_back)
//    ImageView provinceBack;

    private String fromType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_province);
        ButterKnife.inject(this);
        FindView();

        TitleBack.TitleBackActivity(this, "选择城市");
        init();
    }

    private void FindView() {
        view = View.inflate(this, R.layout.homepage_province_header, null);
        gvProvince = (MyGridView) view.findViewById(R.id.gv_province);
        tvLocation = (TextView) view.findViewById(R.id.tv_province_location);
        if (!Config.CITY.equals("")) {
            tvLocation.setText(Config.CITY);
        }

        Intent intent = getIntent();
        fromType = intent.getStringExtra("fromType");
//        provinceBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }

    private void init() {
//        lvProvince.addHeaderView(view);

        //获取城市列表数据
        ProvinceData provinceData = new ProvinceData(this);
        provinceData.getProvinceData();

        lvProvince.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 1) {
                    if (firstVisibleItem % 2 == 0) {
                        tvProvinceSuspension.setText(cityList.get(firstVisibleItem/2).getSort_word());
                    }else {
                        tvProvinceSuspension.setText(cityList.get((firstVisibleItem - 1)/2).getSort_word());
                    }
                }
            }
        });


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

    @Override
    public void getProvince(ParseLocationBean parseLocationBean) {
        cityList = parseLocationBean.getCitys();
        if (fromType.equals("about_city")) {
            ChoseProvinceAdapter adapter = new ChoseProvinceAdapter(ChooseProvinceMain.this, cityList, "me",this);
            lvProvince.setAdapter(adapter);
        } else {
            ChoseProvinceAdapter adapter = new ChoseProvinceAdapter(ChooseProvinceMain.this, cityList, "home",this);
            lvProvince.setAdapter(adapter);
        }

    }

    @Override
    public void OnFailure(String error_code) {

    }

    @Override
    public void getCity(String city_name, int city_id) {
        Intent intent=new Intent();
        intent.putExtra("city_name",city_name);
        intent.putExtra("city_id",city_id);
        UIUtil.showLog("city_name111",city_name+"");
        setResult(AboutActivity.CHOSE_CITY,intent);
        finish();
    }

    private class HotProvinceItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            UIUtil.ToastshowShort(ChooseProvinceMain.this, position + "");
        }
    }
}
