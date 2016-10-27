package com.example.kk.arttraining.ui.school.view;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.homePage.activity.SearchMain;
import com.example.kk.arttraining.ui.school.adapter.ProvinceAdapter;
import com.example.kk.arttraining.ui.school.adapter.SchoolAdapter;
import com.example.kk.arttraining.ui.school.bean.ParseProvinceListBean;
import com.example.kk.arttraining.ui.school.bean.ParseSchoolListBean;
import com.example.kk.arttraining.ui.school.bean.ProvinceBean;
import com.example.kk.arttraining.ui.school.bean.SchoolBean;
import com.example.kk.arttraining.ui.school.presenter.SchoolMainPresenter;
import com.example.kk.arttraining.utils.TitleBack;

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
public class SchoolMain extends BaseActivity implements ISchoolMain {
    @InjectView(R.id.lv_school_left)
    ListView lv_school_left;

    @InjectView(R.id.lv_school_right)
    ListView lv_school_right;

    @InjectView(R.id.iv_title_image)
    ImageView iv_title_image;
    @InjectView(R.id.iv_title_back)
    ImageView iv_title_back;

    private List<SchoolBean> schoolList;
    private SchoolMainPresenter presenter;
    private ProvinceAdapter provinceAdapter;
    private SchoolAdapter schoolAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_main);
        init();

    }

    //初始化
    @Override
    public void init() {
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(SchoolMain.this, "学校");
        iv_title_image.setImageResource(R.mipmap.icon_search_white);
        iv_title_image.setVisibility(View.VISIBLE);
        iv_title_back.setVisibility(View.GONE);

        provinceAdapter = new ProvinceAdapter(getApplicationContext());
        lv_school_left.setAdapter(provinceAdapter);
        schoolAdapter = new SchoolAdapter(getApplicationContext());
        lv_school_right.setAdapter(schoolAdapter);
        ItemClick();
        //q请求省份数据
//        Map<String, String> map = new HashMap<String, String>();
//        presenter.getProvinceData(map);


    }

    //点击事件
    @OnClick({R.id.iv_title_image})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_image:
                startActivity(new Intent(SchoolMain.this, SearchMain.class));
                break;
        }

    }

    @Override
    public void getProvinceList(List<ProvinceBean> provinceBeanList) {
        provinceAdapter = new ProvinceAdapter(getApplicationContext(), provinceBeanList);
        lv_school_left.setAdapter(provinceAdapter);

    }

    @Override
    public void getSchoolList(List<SchoolBean> schoolBeanList) {
        schoolList = schoolBeanList;
        schoolAdapter.notifyDataSetChanged();
    }

    @Override
    public void DefaultSchoolList(List<SchoolBean> schoolBeanList) {
        schoolAdapter = new SchoolAdapter(getApplicationContext(), schoolBeanList);
        lv_school_right.setAdapter(schoolAdapter);
    }


    private void ItemClick() {
        lv_school_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                provinceAdapter.selectPosition(position);
                provinceAdapter.notifyDataSetChanged();
            }
        });

        lv_school_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onFailure() {

    }


}
