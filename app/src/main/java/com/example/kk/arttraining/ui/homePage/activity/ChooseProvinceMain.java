package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.CitysBean;
import com.example.kk.arttraining.bean.LocationBean;
import com.example.kk.arttraining.bean.SearchEntity;
import com.example.kk.arttraining.bean.parsebean.ParseLocationBean;
import com.example.kk.arttraining.bean.parsebean.StatusesBean;
import com.example.kk.arttraining.custom.view.MyGridView;
import com.example.kk.arttraining.playvideo.activity.VideoListLayout;
import com.example.kk.arttraining.ui.homePage.adapter.ChoseProvinceAdapter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.JsonTools;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/10/18.
 * QQ邮箱:515849594@qq.com
 */
public class ChooseProvinceMain extends Activity {
    @InjectView(R.id.lv_province)
    ListView lvProvince;

    View view;
    MyGridView gvProvince;
    TextView tvLocation;
    @InjectView(R.id.tv_province_suspension)
    TextView tvProvinceSuspension;
    @InjectView(R.id.ll_province_suspension)
    LinearLayout llProvinceSuspension;

    List<CitysBean> cityList=new ArrayList<CitysBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_province);
        FindView();
        ButterKnife.inject(this);
        init();
    }

    private void FindView() {
        view = View.inflate(this, R.layout.homepage_province_header, null);
        gvProvince = (MyGridView) view.findViewById(R.id.gv_province);
        tvLocation = (TextView) view.findViewById(R.id.tv_province_location);
        if (!Config.CITY.equals("")) {
            tvLocation.setText(Config.CITY);
        }
    }

    private void init() {
        lvProvince.addHeaderView(view);

        //城市列表
//        HashMap<String, String> province_map = new HashMap<String, String>();
//        province_map.put("access_token", "");
//
//        Callback<ParseLocationBean> callback = new Callback<ParseLocationBean>() {
//            @Override
//            public void onResponse(Call<ParseLocationBean> call, Response<ParseLocationBean> response) {
//                ParseLocationBean parseLocationBean = response.body();
//                cityList = parseLocationBean.getCitys();

                ChoseProvinceAdapter adapter = new ChoseProvinceAdapter(ChooseProvinceMain.this, cityList);
                lvProvince.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure(Call<ParseLocationBean> call, Throwable t) {
//            }
//        };
//
//        Call<ParseLocationBean> call = HttpRequest.getCommonApi().locationCity(province_map);
//        call.enqueue(callback);

        lvProvince.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 1) {
                    tvProvinceSuspension.setText(cityList.get(firstVisibleItem).getSort_word());
                    llProvinceSuspension.setVisibility(View.VISIBLE);
                } else {
                    llProvinceSuspension.setVisibility(View.GONE);
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

    private class HotProvinceItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            UIUtil.ToastshowShort(ChooseProvinceMain.this, position + "");
        }
    }
}
