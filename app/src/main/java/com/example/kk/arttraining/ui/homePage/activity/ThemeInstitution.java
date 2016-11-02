package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.kk.arttraining.R;
import com.jaeger.library.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/10/18.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeInstitution extends FragmentActivity {

    @InjectView(R.id.rb_institution_all)
    RadioButton rbInstitutionAll;
    @InjectView(R.id.rb_institution_1)
    RadioButton rbInstitution1;
    @InjectView(R.id.rb_institution_2)
    RadioButton rbInstitution2;
    @InjectView(R.id.rb_institution_3)
    RadioButton rbInstitution3;
    @InjectView(R.id.rb_institution_4)
    RadioButton rbInstitution4;
    @InjectView(R.id.rb_institution_more)
    RadioButton rbInstitutionMore;
    @InjectView(R.id.rg_institution)
    RadioGroup rgInstitution;

    private ThemeInstitutionFragment institutionFragmentAll;
    private ThemeInstitutionFragment institutionFragment1;
    private ThemeInstitutionFragment institutionFragment2;
    private ThemeInstitutionFragment institutionFragment3;
    private ThemeInstitutionFragment institutionFragment4;
    private ConnectivityManager connectivityManager;
    private FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        setContentView(R.layout.homepage_institution);
        ButterKnife.inject(this);

        StatusBarUtil.setColor(ThemeInstitution.this, getResources().getColor(R.color.blue_overlay));

        initView();

    }

    private void initView() {
        fm = getSupportFragmentManager();
        institutionFragmentAll = new ThemeInstitutionFragment();
        fm.beginTransaction().replace(R.id.fl_institution, institutionFragmentAll).addToBackStack(null).commit();

        rgInstitution.check(R.id.rb_institution_all);
    }

    @OnClick({R.id.rb_institution_all, R.id.rb_institution_1, R.id.rb_institution_2, R.id.rb_institution_3, R.id.rb_institution_4, R.id.rb_institution_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_institution_all:
                initFragment(institutionFragmentAll);
                break;
            case R.id.rb_institution_1:
                initFragment(institutionFragment1);
                break;
            case R.id.rb_institution_2:
                initFragment(institutionFragment2);
                break;
            case R.id.rb_institution_3:
                initFragment(institutionFragment3);
                break;
            case R.id.rb_institution_4:
                initFragment(institutionFragment4);
                break;
            case R.id.rb_institution_more:

                break;
        }
    }

    public void initFragment( Fragment fragment) {
        FragmentTransaction transaction = fm.beginTransaction();
        hideAllFragment(transaction,fragment);
        if (fragment == null) {
            fragment = new ThemeInstitutionFragment();
            transaction.add(R.id.fl_institution, fragment);
        } else {
            transaction.show(fragment);
        }
        transaction.commit();
    }

    private void hideAllFragment(FragmentTransaction transaction, Fragment fragment) {
        if (fragment != null) {
            transaction.hide(fragment);
        }
    }
}
