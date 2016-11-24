package com.example.kk.arttraining.ui.school.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.ConditionBean;
import com.example.kk.arttraining.custom.view.AutoSwipeRefreshLayout;
import com.example.kk.arttraining.ui.homePage.activity.SearchMain;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.ui.school.adapter.ProvinceAdapter;
import com.example.kk.arttraining.ui.school.adapter.SchoolAdapter;
import com.example.kk.arttraining.ui.school.bean.ProvinceBean;
import com.example.kk.arttraining.ui.school.bean.SchoolBean;
import com.example.kk.arttraining.ui.school.presenter.SchoolMainPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/10/26 11:32
 * 说明:院校主页面
 */
public class SchoolMain extends Fragment implements ISchoolMain, SwipeRefreshLayout.OnRefreshListener {

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
    private List<SchoolBean> schoolList;
    private SchoolMainPresenter presenter;

    private ProvinceAdapter provinceAdapter;
    private SchoolAdapter schoolAdapter;
    private Activity activity;
    private View view_school;
    private String default_province_name;
    private AutoSwipeRefreshLayout swipeRefreshLayout;

    private boolean FIRST_SET_ADAPTER = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        activity = getActivity();
        if (view_school == null) {
            view_school = View.inflate(activity, R.layout.school_main, null);
            ButterKnife.inject(this, view_school);
            init();
        }
        ViewGroup parent = (ViewGroup) view_school.getParent();
        if (parent != null) {
            parent.removeView(view_school);
        }
        return view_school;
    }

    //初始化
    private void init() {
        TitleBack.schoolTitleBackFragment(view_school, "学校", R.mipmap.icon_search_white);
        ItemClick();
        //q请求省份数据
        presenter = new SchoolMainPresenter(this);

        swipeRefreshLayout = new AutoSwipeRefreshLayout(activity.getApplicationContext());
        swipeRefreshLayout = (AutoSwipeRefreshLayout) view_school.findViewById(R.id.idschool_main_swipe);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#87CEFA"));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.autoRefresh();


    }

    //点击事件
    @OnClick({R.id.iv_title_image})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_image:
                startActivity(new Intent(activity, SearchMain.class));
                break;
        }

    }

    //获取省份列表成功
//    @Override
//    public void getProvinceList(List<ProvinceBean> provinceBeanList) {
//        provinceAdapter = new ProvinceAdapter(activity.getApplicationContext(), provinceBeanList);
//        lvSchoolLeft.setAdapter(provinceAdapter);
//        default_province_name = provinceBeanList.get(0).getName();
//        getSchoolList(default_province_name);
//
//    }

    @Override
    public void getProvinceList(List<ConditionBean> provinceBeanList) {

    }

    //获取院校列表成功
    @Override
    public void getSchoolList(List<SchoolBean> schoolBeanList) {
        ivNoWifi.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        schoolList = schoolBeanList;
        if (FIRST_SET_ADAPTER) {

            schoolAdapter = new SchoolAdapter(activity.getApplicationContext(), schoolList);
            lvSchoolRight.setAdapter(schoolAdapter);
            FIRST_SET_ADAPTER = false;
        } else {

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
                Intent intent = new Intent(activity, SchoolContent.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("school_info", schoolBean);
                intent.putExtras(bundle);
                startActivity(intent);
                UIUtil.IntentActivity(activity, new SchoolContent());
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
        swipeRefreshLayout.setRefreshing(false);
        if (error_code.equals(Config.TOKEN_INVALID)) {
            UIUtil.ToastshowShort(activity, error_msg);
            Intent intent = new Intent(activity, UserLoginActivity.class);
            startActivity(intent);

        } else {
            ivNoWifi.setVisibility(View.VISIBLE);
            UIUtil.ToastshowShort(activity, error_msg);
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onRefresh() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        presenter.getProvinceData(map);
    }
}
