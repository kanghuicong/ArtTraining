package com.example.kk.arttraining.ui.homePage.function.institution;

import android.app.Activity;
import android.os.Bundle;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.homePage.adapter.InstitutionFragmentAdapter;
import com.example.kk.arttraining.utils.UIUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/11/2.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeInstitutionAll extends Activity {
    @InjectView(R.id.lv_institution)
    MyListView lvInstitution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_institution_fragment);
        ButterKnife.inject(this);

        ThemeInstitutionUntil.themeInstitutionUntil(this,lvInstitution,"");
    }
}
