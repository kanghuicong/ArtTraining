package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.InnerView;
import com.example.kk.arttraining.ui.homePage.function.homepage.Shuffling;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/11/2.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeInstitutionContent extends Activity {
    @InjectView(R.id.vp_institution)
    InnerView vpInstitution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_institution_content);
        ButterKnife.inject(this);

        Shuffling.initShuffling(vpInstitution,this);

    }
}
