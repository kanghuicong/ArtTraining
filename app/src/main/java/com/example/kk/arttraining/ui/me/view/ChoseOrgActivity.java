package com.example.kk.arttraining.ui.me.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.utils.TitleBack;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/11/11 14:16
 * 说明:选择机构或选择院校
 */
public class ChoseOrgActivity extends Activity implements View.OnClickListener {
    @InjectView(R.id.tv_province)
    TextView tvProvince;
    @InjectView(R.id.ll_province)
    LinearLayout llProvince;
    @InjectView(R.id.tv_city)
    TextView tvCity;
    @InjectView(R.id.ll_city)
    LinearLayout llCity;
    @InjectView(R.id.tv_org_school)
    TextView tvOrgSchool;
    @InjectView(R.id.ll_org_or_school)
    LinearLayout llOrgOrSchool;
    //用于判断是什么类型
    private String fromType;

    //跳转码
    public static final int CHOSE_PROVINCE_CODE = 1001;
    public static final int CHOSE_CITY_CODE = 1002;
    public static final int CHOSE_ORG_CODE = 1003;
    public static final int CHOSE_SCHOOL_CODE = 1004;

    private String province_name;
    private String city_name;
    private String school_name;
    private String org_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_chose_org_school_layout);
        ButterKnife.inject(this);
        init();
    }


    //初始化
    void init() {

        Intent intent = getIntent();
        fromType = intent.getStringExtra("fromType");
        if (fromType.equals("org")) {
            tvOrgSchool.setText("选择机构");
            TitleBack.TitleBackActivity(this,"选择机构");
        } else {
            TitleBack.TitleBackActivity(this,"选择院校");
            tvOrgSchool.setText("选择院校");
        }
    }


    @OnClick({R.id.ll_province,R.id.ll_city,R.id.ll_org_or_school})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_province:
                Intent intent = new Intent(this, OrgSchoolShowActivity.class);
                intent.putExtra("fromType", "province");
                startActivityForResult(intent, CHOSE_PROVINCE_CODE);
                break;
            case R.id.ll_city:
                Intent intentCity = new Intent(this, OrgSchoolShowActivity.class);
                intentCity.putExtra("fromType", "city");
                intentCity.putExtra("province_name", province_name);
                startActivityForResult(intentCity, CHOSE_CITY_CODE);
                break;
            case R.id.ll_org_or_school:
                if (fromType.equals("org")) {
                    Intent intentSchool = new Intent(this, OrgSchoolShowActivity.class);
                    intentSchool.putExtra("fromType", "city");
                    intentSchool.putExtra("province_name", province_name);
                    startActivityForResult(intentSchool, CHOSE_ORG_CODE);
                } else {
                    Intent intentSchool = new Intent(this, OrgSchoolShowActivity.class);
                    intentSchool.putExtra("fromType", "city");
                    intentSchool.putExtra("province_name", province_name);
                    startActivityForResult(intentSchool, CHOSE_CITY_CODE);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            //省份
            case CHOSE_PROVINCE_CODE:
                province_name = data.getStringExtra("province_name");
                tvProvince.setText(province_name);
                break;
            //城市
            case CHOSE_CITY_CODE:
                city_name = data.getStringExtra("city_name");
                tvCity.setText(city_name);
                break;
            //院校
            case CHOSE_SCHOOL_CODE:
                school_name = data.getStringExtra("school_name");
                tvOrgSchool.setText(school_name);
                break;
            //机构
            case CHOSE_ORG_CODE:
                org_name = data.getStringExtra("org_name");
                tvOrgSchool.setText(org_name);
                break;
        }
    }
}
