package com.example.kk.arttraining.ui.homePage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.util.AsyncListUtil;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.homePage.function.examine.ExamineListDate;
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
public class ThemeExamineFirstActivity extends BaseActivity {

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

    String type = "arts";
    private ArrayAdapter adapter = null;
    private List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_examine_frist_activity);
        ButterKnife.inject(this);
        init();


        UIUtil.showLog("ExamineListDate",ExamineListDate.getExamineListDate().size()+"");
        adapter = new ArrayAdapter(this, R.layout.home_examine__spinner_item, ExamineListDate.getExamineListDate());
        adapter.setDropDownViewResource(R.layout.home_examine__spinner_item);
        SpinnerRegion.setAdapter(adapter);

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
//                if (!TextUtils.isEmpty(etMajorScore.getText())){
//                    if (!TextUtils.isEmpty(etCultureScore.getText())) {
//                        if (!"地区/生源地".equals(SpinnerRegion.getSelectedItem().toString())){
//
//                            Intent intent = new Intent(this, ThemeExamineSecondActivity.class);
//                            startActivity(intent);
//                        }else {
//                            UIUtil.ToastshowShort(this, "请选择地区/生源地！");
//                        }
//                    }else {
//                        UIUtil.ToastshowShort(this, "请填写文化课分数！");
//                    }
//                }else {
//                    UIUtil.ToastshowShort(this, "请填写专业分数！");
//                }
                UIUtil.ToastshowShort(getApplicationContext(),"2017年分数线暂未发布，无法执行操作！");
                break;
        }
    }

}
