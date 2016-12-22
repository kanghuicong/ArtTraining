package com.example.kk.arttraining.ui.me.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.LocationBean;
import com.example.kk.arttraining.bean.OrgBean;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.ui.me.adapter.PersonalListViewAdapter;
import com.example.kk.arttraining.ui.me.presenter.PersonalDataPresenter;
import com.example.kk.arttraining.ui.homePage.bean.ProvinceBean;
import com.example.kk.arttraining.ui.homePage.bean.SchoolBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/11/11 15:23
 * 说明:选择院校，机构显示页面
 */
public class OrgSchoolShowActivity extends Activity implements IOrgSchoolShowActivity, AdapterView.OnItemClickListener {

    @InjectView(R.id.lv_me_org_school)
    ListView lv_me_org_school;
    PersonalDataPresenter presenter;
    String fromType;
    //省份名称
    private String province_name;
    //城市名称
    private String city_name;

    private PersonalListViewAdapter adapter;
    //用于标记传入adapter数据
    private int type;

    private LoadingDialog dialog;
    private ProvinceBean provinceBean;
    private LocationBean locationBean;
    private SchoolBean schoolBean;
    private OrgBean orgBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_org_school_show_layoout);
        init();
    }

    void init() {
        ButterKnife.inject(this);
        presenter = new PersonalDataPresenter(this);
        dialog = LoadingDialog.getInstance(this);
        Intent intent = getIntent();
        fromType = intent.getStringExtra("fromType");

        lv_me_org_school.setOnItemClickListener(this);

        switch (fromType) {
            case "province":
                getProvinceData();
                TitleBack.TitleBackActivity(this, "省份");
                type = 1;
                break;
            case "city":
                province_name = intent.getStringExtra("province_name");
                TitleBack.TitleBackActivity(this, "城市");
                getCityData();
                type = 2;
                break;
            case "school":
                province_name = intent.getStringExtra("province_name");
                city_name = intent.getStringExtra("city_name");
                TitleBack.TitleBackActivity(this, "院校");
                type = 3;
                getSchoolData();
                break;
            case "org":

                province_name = intent.getStringExtra("province_name");
                city_name = intent.getStringExtra("city_name");
                TitleBack.TitleBackActivity(this, "机构");
                getOrgData();
                type = 4;
                break;
        }

    }

    //获取省份数据
    @Override
    public void getProvinceData() {
        dialog.show();
        Map<String, Object> mapProvince = new HashMap<String, Object>();
        mapProvince.put("access_token", Config.ACCESS_TOKEN);
        presenter.getProvinceData(mapProvince);
    }

    //获取城数据
    @Override
    public void getCityData() {
        dialog.show();
        Map<String, Object> mapCity = new HashMap<String, Object>();
        mapCity.put("access_token", Config.ACCESS_TOKEN);
        if (province_name != null && !province_name.equals("")) {
            mapCity.put("province", province_name);
        }
        presenter.getCityData(mapCity);
    }

    //获取院校数据
    @Override
    public void getSchoolData() {
        dialog.show();
        Map<String, Object> mapSchool = new HashMap<String, Object>();
        mapSchool.put("access_token", Config.ACCESS_TOKEN);
        mapSchool.put("uid", Config.UID);


        if (city_name != null && !city_name.equals("")) {
            mapSchool.put("condition_type", "city");
            mapSchool.put("condition_name", city_name);
        } else if (province_name != null && !province_name.equals("")) {
            mapSchool.put("condition_type", "province");
            mapSchool.put("condition_name", province_name);
        }
        presenter.getSchoolData(mapSchool);
    }

    //获取机构数据
    @Override
    public void getOrgData() {
        dialog.show();
        Map<String, Object> mapOrg = new HashMap<String, Object>();
        mapOrg.put("access_token", Config.ACCESS_TOKEN);
        mapOrg.put("uid", Config.UID);
        if (city_name != null && !city_name.equals("")) {
            mapOrg.put("city_name", city_name);
        } else if (province_name != null && !province_name.equals("")) {
            mapOrg.put("province_name", province_name);
        }
        presenter.getOrgData(mapOrg);
    }

    //获取省份数据成功
    @Override
    public void SuccessProvince(List<ProvinceBean> provinceBeanList) {
        dialog.dismiss();
        adapter = new PersonalListViewAdapter(this, provinceBeanList);
        lv_me_org_school.setAdapter(adapter);
    }

    //获取城数据成功
    @Override
    public void SuccessCity(List<LocationBean> cityBeenList) {
        dialog.dismiss();
        adapter = new PersonalListViewAdapter(this, cityBeenList, 0);
        lv_me_org_school.setAdapter(adapter);
    }

    //获取机构数据成功
    @Override
    public void SuccessOrg(List<OrgBean> orgBeanList) {
        dialog.dismiss();
        adapter = new PersonalListViewAdapter(this, orgBeanList, 0, 0, 0);
        lv_me_org_school.setAdapter(adapter);
    }

    //获取院校数据成功
    @Override
    public void SuccessSchool(List<SchoolBean> schoolBeanList) {
        dialog.dismiss();
        adapter = new PersonalListViewAdapter(this, schoolBeanList, 0, 0);
        lv_me_org_school.setAdapter(adapter);
    }

    //获取数据失败
    @Override
    public void Failure(String error_code) {
        dialog.dismiss();
        switch (fromType) {
            case "province":
                UIUtil.ToastshowShort(this, "获取省份数据失败");
                break;
            case "city":
                UIUtil.ToastshowShort(this, "获取城市数据失败");
                break;
            case "school":
                UIUtil.ToastshowShort(this, "没有查到相关院校");
                break;
            case "org":
                UIUtil.ToastshowShort(this, "没有查到相关机构");
                break;
        }

    }

    //加载dialog
    @Override
    public void showLoading() {
        dialog.show();
    }

    //隐藏dialog
    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (type) {
            //省份
            case 1:
                provinceBean = (ProvinceBean) parent.getItemAtPosition(position);
                province_name = provinceBean.getName();
                Intent intent = new Intent();
                intent.putExtra("province_name", province_name);
                setResult(ChoseOrgActivity.CHOSE_PROVINCE_CODE, intent);
                finish();
                break;
            //城市
            case 2:
                locationBean = (LocationBean) parent.getItemAtPosition(position);
                city_name = locationBean.getName();
                Intent intentCity = new Intent();
                intentCity.putExtra("city_name", city_name);
                setResult(ChoseOrgActivity.CHOSE_CITY_CODE, intentCity);
                finish();
                break;
            //院校
            case 3:
                schoolBean = (SchoolBean) parent.getItemAtPosition(position);
                Intent intentSchool = new Intent();
                String school_name = schoolBean.getName();
                int school_id = schoolBean.getInstitution_id();
                intentSchool.putExtra("school_name", school_name);
                intentSchool.putExtra("school_id", school_id);
                setResult(ChoseOrgActivity.CHOSE_SCHOOL_CODE, intentSchool);
                finish();
                break;
            //机构
            case 4:
                orgBean = (OrgBean) parent.getItemAtPosition(position);
                Intent intentOrg = new Intent();
                String org_name = orgBean.getName();
                intentOrg.putExtra("org_name", orgBean.getName());
                intentOrg.putExtra("org_id",orgBean.getOrg_id());
                setResult(ChoseOrgActivity.CHOSE_ORG_CODE, intentOrg);
                finish();
                break;
        }
    }
}
