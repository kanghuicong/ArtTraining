package com.example.kk.arttraining.ui.school.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.activity.SearchMain;
import com.example.kk.arttraining.ui.school.adapter.ProvinceAdapter;
import com.example.kk.arttraining.ui.school.adapter.SchoolAdapter;
import com.example.kk.arttraining.ui.school.bean.ProvinceBean;
import com.example.kk.arttraining.ui.school.bean.SchoolBean;
import com.example.kk.arttraining.ui.school.presenter.SchoolMainPresenter;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/10/26 11:32
 * 说明:院校主页面
 */
public class SchoolMain extends Fragment implements ISchoolMain {

    @InjectView(R.id.lv_school_left)
    ListView lvSchoolLeft;
    @InjectView(R.id.lv_school_right)
    ListView lvSchoolRight;
    @InjectView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @InjectView(R.id.iv_title_image)
    ImageView ivTitleImage;
    private List<SchoolBean> schoolList;
    private SchoolMainPresenter presenter;

    private ProvinceAdapter provinceAdapter;
    private SchoolAdapter schoolAdapter;
    Activity activity;
    View view_school;

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

        provinceAdapter = new ProvinceAdapter(activity);
        lvSchoolLeft.setAdapter(provinceAdapter);
        schoolAdapter = new SchoolAdapter(activity.getApplicationContext());
        lvSchoolRight.setAdapter(schoolAdapter);
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
                startActivity(new Intent(activity, SearchMain.class));
                break;
        }

    }

    @Override
    public void getProvinceList(List<ProvinceBean> provinceBeanList) {
        provinceAdapter = new ProvinceAdapter(activity.getApplicationContext(), provinceBeanList);
        lvSchoolLeft.setAdapter(provinceAdapter);

    }

    @Override
    public void getSchoolList(List<SchoolBean> schoolBeanList) {
        schoolList = schoolBeanList;
        schoolAdapter.notifyDataSetChanged();
    }

    @Override
    public void DefaultSchoolList(List<SchoolBean> schoolBeanList) {
        schoolAdapter = new SchoolAdapter(activity.getApplicationContext(), schoolBeanList);
        lvSchoolRight.setAdapter(schoolAdapter);
    }


    private void ItemClick() {
        lvSchoolLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                provinceAdapter.selectPosition(position);
                provinceAdapter.notifyDataSetChanged();
            }
        });

        lvSchoolRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UIUtil.IntentActivity(activity,new SchoolContent());
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
