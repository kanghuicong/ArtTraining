package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.homePage.adapter.ExamineAdapter;
import com.example.kk.arttraining.ui.homePage.bean.ExamineListBean;
import com.example.kk.arttraining.ui.homePage.function.examine.ExamineData;
import com.example.kk.arttraining.ui.homePage.prot.IExamine;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2017/3/8.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeExamineSecondActivity extends Activity implements IExamine{
    @InjectView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @InjectView(R.id.tv_title_bar)
    TextView tvTitleBar;
    @InjectView(R.id.iv_title_image)
    ImageView ivTitleImage;
    @InjectView(R.id.tv_title_subtitle)
    TextView tvTitleSubtitle;
    @InjectView(R.id.rl_title)
    RelativeLayout rlTitle;
    @InjectView(R.id.lv_examine_content)
    MyListView lvExamineContent;

    ExamineAdapter examineAdapter;
    LoadingDialog loadingDialog;
    ExamineData examineData;
    List<ExamineListBean> examineList = new ArrayList<ExamineListBean>();

    String majorScore;
    String cultureScore;
    String examineProvince;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_examine_second_activity);
        ButterKnife.inject(this);

        TitleBack.TitleBackActivity(this, "最优志愿");
        loadingDialog = new LoadingDialog(ThemeExamineSecondActivity.this);
        loadingDialog.show();

        majorScore = getIntent().getStringExtra(ThemeExamineFirstActivity.majorScore);
        cultureScore = getIntent().getStringExtra(ThemeExamineFirstActivity.cultureScore);
        examineProvince = getIntent().getStringExtra(ThemeExamineFirstActivity.examineProvince);

        examineData = new ExamineData(this);
        examineData.getExamineData(majorScore,cultureScore,examineProvince);
    }

    @Override
    public void getExamine(List<ExamineListBean> mExamineList) {
        examineList.addAll(mExamineList);
        examineAdapter = new ExamineAdapter(this,examineList);
        lvExamineContent.setAdapter(examineAdapter);
        loadingDialog.dismiss();
    }

    @Override
    public void onExamineFailure(String msg) {
        UIUtil.ToastshowShort(this, msg);
        loadingDialog.dismiss();
    }
}
