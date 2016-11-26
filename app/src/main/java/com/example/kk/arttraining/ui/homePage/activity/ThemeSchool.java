package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.baidu.platform.comapi.map.A;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.ConditionBean;
import com.example.kk.arttraining.custom.view.AutoSwipeRefreshLayout;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.ui.school.adapter.ProvinceAdapter;
import com.example.kk.arttraining.ui.school.adapter.SchoolAdapter;
import com.example.kk.arttraining.ui.school.bean.ProvinceBean;
import com.example.kk.arttraining.ui.school.bean.SchoolBean;
import com.example.kk.arttraining.ui.school.presenter.SchoolMainPresenter;
import com.example.kk.arttraining.ui.school.view.ISchoolMain;
import com.example.kk.arttraining.ui.school.view.SchoolContent;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/11/19.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeSchool extends Activity implements ISchoolMain {
    @InjectView(R.id.lv_school_left)
    ListView lvSchoolLeft;
    @InjectView(R.id.lv_school_right)
    ListView lvSchoolRight;
    @InjectView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @InjectView(R.id.iv_title_image)
    ImageView ivTitleImage;
    @InjectView(R.id.iv_no_wifi)
    ImageView ivNoWifi;

    private SchoolMainPresenter presenter;

    private ProvinceAdapter provinceAdapter;
    private SchoolAdapter schoolAdapter;

//    private AutoSwipeRefreshLayout swipeRefreshLayout;
    private boolean FIRST_SET_ADAPTER = true;
    List<SchoolBean> schoolBeanList = new ArrayList<SchoolBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_main);
        ButterKnife.inject(this);
        init();
    }

    //初始化
    private void init() {
        TitleBack.SearchBackActivity(this, "院校", R.mipmap.icon_search_white, "school");
        ItemClick();
        //q请求省份数据
        presenter = new SchoolMainPresenter(this);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        presenter.getProvinceData(map);
//        swipeRefreshLayout = new AutoSwipeRefreshLayout(getApplicationContext());
//        swipeRefreshLayout = (AutoSwipeRefreshLayout) findViewById(R.id.idschool_main_swipe);
//        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#87CEFA"));
//        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.autoRefresh();


    }

    //点击事件
    @OnClick({R.id.iv_title_image})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_image:
                startActivity(new Intent(this, SearchMain.class));
                break;
        }

    }

    //获取省份列表成功
    @Override
    public void getProvinceList(List<ConditionBean> conditionBeanList) {
        provinceAdapter = new ProvinceAdapter(getApplicationContext(), conditionBeanList);
        lvSchoolLeft.setAdapter(provinceAdapter);

        String condition_name = conditionBeanList.get(0).getName();
        String condition_type = conditionBeanList.get(0).getType();

        getSchoolList(condition_name,condition_type);
    }

    //获取院校列表成功
    @Override
    public void getSchoolList(List<SchoolBean> schoolBeanList1) {
        ivNoWifi.setVisibility(View.GONE);
//        swipeRefreshLayout.setRefreshing(false);

        if (FIRST_SET_ADAPTER) {
            schoolBeanList.addAll(schoolBeanList1);
            schoolAdapter = new SchoolAdapter(getApplicationContext(), schoolBeanList);
            lvSchoolRight.setAdapter(schoolAdapter);
            FIRST_SET_ADAPTER = false;
        } else {
            schoolBeanList.clear();
            schoolBeanList.addAll(schoolBeanList1);
            schoolAdapter.ChangeCount(schoolBeanList.size());
            schoolAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void DefaultSchoolList(List<SchoolBean> schoolBeanList) {

    }


    private void ItemClick() {
        lvSchoolLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                provinceAdapter.selectPosition(position);
                provinceAdapter.notifyDataSetChanged();
                //请求院校列表
                ConditionBean bean = (ConditionBean) parent.getItemAtPosition(position);
                String condition_name = bean.getName();
                String condition_type = bean.getType();
                getSchoolList(condition_name,condition_type);
            }
        });

        lvSchoolRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SchoolBean schoolBean = (SchoolBean) parent.getItemAtPosition(position);
                Intent intent = new Intent(ThemeSchool.this, SchoolContent.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("school_info", schoolBean);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    private void getSchoolList(String condition_name,String condition_type) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("condition_type",condition_type);
        map.put("condition_name",condition_name);

        presenter.getSchoolData(map);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onFailure(String error_code, String error_msg) {
//        swipeRefreshLayout.setRefreshing(false);
        if (error_code.equals(Config.TOKEN_INVALID)) {
            UIUtil.ToastshowShort(ThemeSchool.this, error_msg);
            Intent intent = new Intent(ThemeSchool.this, UserLoginActivity.class);
            startActivity(intent);

        } else {
            ivNoWifi.setVisibility(View.VISIBLE);
            UIUtil.ToastshowShort(ThemeSchool.this, error_msg);
        }
    }

//    @Override
//    public void onRefresh() {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("access_token", Config.ACCESS_TOKEN);
//        presenter.getProvinceData(map);
//    }
}
