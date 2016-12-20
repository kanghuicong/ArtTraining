package com.example.kk.arttraining.ui.homePage.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/12/19 13:17
 * 说明:报考
 */
public class ThemeApplyExamineActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_apply_examine_activity);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {
        TitleBack.TitleBackActivity(this,"报考");

    }

    @OnClick({R.id.btn_art, R.id.btn_science, R.id.btn_apply_sure})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_art:
                btnArt.setImageResource(R.mipmap.icon_liberal_art_focus);
                btnScience.setImageResource(R.mipmap.icon_apply_science_unfocus);
                break;
            case R.id.btn_science:
                btnArt.setImageResource(R.mipmap.icon_apply_liberal_art_unfocus);
                btnScience.setImageResource(R.mipmap.icon_apply_science_focus);
                break;
            case R.id.btn_apply_sure:
                UIUtil.ToastshowShort(getApplicationContext(),"2017年分数线暂未发布，无法执行操作！");
                break;
        }
    }
}
