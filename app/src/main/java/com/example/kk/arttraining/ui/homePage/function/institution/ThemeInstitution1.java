package com.example.kk.arttraining.ui.homePage.function.institution;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullToRefreshLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/11/2.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeInstitution1 extends Activity  {
    @InjectView(R.id.lv_institution)
    MyListView lvInstitution;
//    @InjectView(R.id.refresh_view)
//    PullToRefreshLayout refreshView;

//    View view_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_institution_fragment);
        ButterKnife.inject(this);
//        view_header = (View) findViewById(R.id.ll_refresh_header);
//        view_header.setVisibility(View.GONE);

        ThemeInstitutionUntil.themeInstitutionUntil(this, lvInstitution, "江西");
//        refreshView.setOnRefreshListener(this);
    }

//    @Override
//    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
//
//    }
//
//    @Override
//    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
//
//    }
}
