package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.homePage.adapter.ExamineAdapter;
import com.example.kk.arttraining.ui.homePage.bean.ExamineCollegeBean;
import com.example.kk.arttraining.ui.homePage.function.examine.ExamineSecondData;
import com.example.kk.arttraining.ui.webview.WebActivity;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2017/3/8.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeExamineSecondActivity extends Activity implements ExamineSecondData.IExamineSecond {
    @InjectView(R.id.lv_examine_content)
    MyListView lvExamineContent;

    @InjectView(R.id.tv_examine_major)
    TextView tvExamineMajor;
    @InjectView(R.id.tv_examine_province)
    TextView tvExamineProvince;
    @InjectView(R.id.tv_examine_culture)
    TextView tvExamineCulture;


    ExamineAdapter examineAdapter;
    LoadingDialog loadingDialog;
    ExamineSecondData examineData;
    List<ExamineCollegeBean> examineList = new ArrayList<ExamineCollegeBean>();

    double majorScore;
    double cultureScore;
    String examineProvince;
    String examineCategory;
    String examineSubject;
    @InjectView(R.id.tv_examine_category)
    TextView tvExamineCategory;
    @InjectView(R.id.tv_text1)
    TextView tvText1;
    @InjectView(R.id.tv_text2)
    TextView tvText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_examine_second_activity);
        ButterKnife.inject(this);

        TitleBack.TitleBackActivity(this, "最优志愿");
        loadingDialog = new LoadingDialog(ThemeExamineSecondActivity.this);
        loadingDialog.show();

        majorScore = Double.valueOf(getIntent().getStringExtra(ThemeExamineFirstActivity.majorScore));
        cultureScore = Double.valueOf(getIntent().getStringExtra(ThemeExamineFirstActivity.cultureScore));
        examineProvince = getIntent().getStringExtra(ThemeExamineFirstActivity.examineProvince);
        examineCategory = getIntent().getStringExtra(ThemeExamineFirstActivity.examineCategory);
        examineSubject = getIntent().getStringExtra(ThemeExamineFirstActivity.examineSubject);

        tvExamineCategory.setText("类别：" + examineCategory);
        tvExamineProvince.setText("地区/生源地：" + examineProvince);
        tvExamineMajor.setText("术科成绩：" + majorScore);
        tvExamineCulture.setText("文化成绩：" + cultureScore);
        tvText1.setText(examineProvince + "——历年高考各批次录取分数线");
        tvText2.setText(examineProvince + "——历年艺术类术科统考涉及到的本科专业资格线");

        examineData = new ExamineSecondData(this);
        examineData.getExamineData(majorScore, cultureScore, examineProvince, examineCategory, examineSubject);
    }

    @Override
    public void successExamineSecond(List<ExamineCollegeBean> examineCollegeList) {
        examineList.addAll(examineCollegeList);
        examineAdapter = new ExamineAdapter(this, examineList);
        lvExamineContent.setAdapter(examineAdapter);
        loadingDialog.dismiss();
    }

    @Override
    public void failureExamineSecond(String error_code, String msg) {
        UIUtil.ToastshowShort(this, msg);
        loadingDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        examineData.cancelSubscription();
    }

    @OnClick({R.id.tv_text1, R.id.tv_text2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_text1:
                Intent intent1 = new Intent(ThemeExamineSecondActivity.this, WebActivity.class);
                intent1.putExtra("url","http://www.artforyou.cn/entrance/admission.html");
                intent1.putExtra("title", "历年高考各批次录取分数线");
                startActivity(intent1);
                break;
            case R.id.tv_text2:
                Intent intent2 = new Intent(ThemeExamineSecondActivity.this, WebActivity.class);
                intent2.putExtra("url","http://www.artforyou.cn/entrance/line.html");
                intent2.putExtra("title", "本科专业资格线");
                startActivity(intent2);
                break;
        }
    }
}
