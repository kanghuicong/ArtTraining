package com.example.kk.arttraining.ui.homePage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.homePage.bean.ExamineCategoryBean;
import com.example.kk.arttraining.ui.homePage.bean.ExamineProvinceBean;
import com.example.kk.arttraining.ui.homePage.function.examine.ExamineFirstData;
import com.example.kk.arttraining.ui.homePage.function.examine.ExamineSpinnerDate;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/12/19 13:17
 * 说明:报考
 */
public class ThemeExamineFirstActivity extends BaseActivity implements ExamineFirstData.IExamineFirst{

    @InjectView(R.id.et_major_score)
    EditText etMajorScore;
    @InjectView(R.id.et_culture_score)
    EditText etCultureScore;
    @InjectView(R.id.btn_art)
    ImageView btnArt;
    @InjectView(R.id.btn_science)
    ImageView btnScience;
    @InjectView(R.id.btn_apply_sure)
    Button btnApplySure;
    @InjectView(R.id.Spinner_region)
    Spinner SpinnerRegion;

    @InjectView(R.id.Spinner_category)
    Spinner SpinnerCategory;

    private ArrayAdapter regionAdapter = null;
    private ArrayAdapter categoryAdapter = null;

    public static String majorScore = "majorScore";
    public static String cultureScore = "cultureScore";
    public static String examineProvince = "examineProvince";
    public static String examineCategory = "examineCategory";
    public static String examineSubject = "examineSubject";

    String type = "arts";
    ExamineFirstData examineFirstData;
    List<String> provinceList = new ArrayList<String>();
    List<String> categoryList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_examine_frist_activity);
        ButterKnife.inject(this);
        init();

        examineFirstData = new ExamineFirstData(this);
        examineFirstData.getExamineProvince();
        examineFirstData.getExamineCategory();

    }

    @Override
    public void init() {
        TitleBack.TitleBackActivity(this, "报考");
    }

    @OnClick({R.id.btn_art, R.id.btn_science, R.id.btn_apply_sure})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_art:
                if (type.equals("science")) {
                    btnArt.setImageResource(R.mipmap.icon_liberal_art_focus);
                    btnScience.setImageResource(R.mipmap.icon_apply_science_unfocus);
                    type = "arts";
                }
                break;
            case R.id.btn_science:
                if (type.equals("arts")) {
                    btnArt.setImageResource(R.mipmap.icon_apply_liberal_art_unfocus);
                    btnScience.setImageResource(R.mipmap.icon_apply_science_focus);
                    type = "science";
                }
                break;
            case R.id.btn_apply_sure:
                if (!TextUtils.isEmpty(etMajorScore.getText())){
                    if (!TextUtils.isEmpty(etCultureScore.getText())) {
                        if (!"地区/生源地".equals(SpinnerRegion.getSelectedItem().toString())){
                            if(!"类别".equals(SpinnerCategory.getSelectedItem().toString())) {
                                btnApplySure.isEnabled();
                                Intent intent = new Intent(this, ThemeExamineSecondActivity.class);
                                intent.putExtra(majorScore, etMajorScore.getText().toString());
                                intent.putExtra(cultureScore, etCultureScore.getText().toString());
                                intent.putExtra(examineProvince, SpinnerRegion.getSelectedItem().toString());
                                intent.putExtra(examineCategory, SpinnerCategory.getSelectedItem().toString());
                                intent.putExtra(examineSubject, type);
                                startActivity(intent);
                                btnApplySure.isClickable();
                            }else {
                                UIUtil.ToastshowShort(this, "请选择类别！");
                            }
                        }else {
                            UIUtil.ToastshowShort(this, "请选择地区/生源地！");
                        }
                    }else {
                        UIUtil.ToastshowShort(this, "请填写文化课分数！");
                    }
                }else {
                    UIUtil.ToastshowShort(this, "请填写专业分数！");
                }
                break;
        }
    }

    //获取省份成功
    @Override
    public void successExamineProvince(List<ExamineProvinceBean> examineProvinceList) {
        if (!provinceList.isEmpty()) provinceList.clear();
        provinceList.add("地区/生源地");
        for (int i = 0; i < examineProvinceList.size(); i++) {
            provinceList.add(examineProvinceList.get(i).getName());
        }
        regionAdapter = new ArrayAdapter(this, R.layout.home_examine__spinner_item, provinceList);
        regionAdapter.setDropDownViewResource(R.layout.home_examine__spinner_item);
        SpinnerRegion.setAdapter(regionAdapter);
    }

    //获取类别成功
    @Override
    public void successExamineCategory(List<ExamineCategoryBean> examineCategoryList) {
        if (!categoryList.isEmpty()) categoryList.clear();
        categoryList.add("类别");
        for (int i = 0; i < examineCategoryList.size(); i++) {
            categoryList.add(examineCategoryList.get(i).getName());
        }
        categoryAdapter = new ArrayAdapter(this, R.layout.home_examine__spinner_item, categoryList);
        categoryAdapter.setDropDownViewResource(R.layout.home_examine__spinner_item);
        SpinnerCategory.setAdapter(categoryAdapter);
    }

    @Override
    public void failureExamineFirst(String error_code, String error_msg) {
        UIUtil.ToastshowShort(this,error_msg);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        examineFirstData.cancelSubscription();
    }
}
