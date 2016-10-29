package com.example.kk.arttraining.ui.school.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.school.function.content.FocusListView;
import com.example.kk.arttraining.ui.school.function.content.InformListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/10/29.
 * QQ邮箱:515849594@qq.com
 */
public class SchoolContent extends Activity {
    @InjectView(R.id.tv_school_content_name)
    TextView tvSchoolContentName;
    @InjectView(R.id.tv_school_content_quality)
    TextView tvSchoolContentQuality;
    @InjectView(R.id.tv_school_content_subject)
    TextView tvSchoolContentSubject;
    @InjectView(R.id.tv_school_content_popularity)
    TextView tvSchoolContentPopularity;
    @InjectView(R.id.tv_school_content_address)
    TextView tvSchoolContentAddress;
    @InjectView(R.id.tv_school_content_url)
    TextView tvSchoolContentUrl;
    @InjectView(R.id.lv_school_content_inform)
    MyListView lvSchoolContentInform;
    @InjectView(R.id.lv_school_content_focus)
    MyListView lvSchoolContentFocus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_content);
        ButterKnife.inject(this);

        InformListView.initInform(this,lvSchoolContentInform);//Inform列表
        FocusListView.initFocus(this, lvSchoolContentFocus);//Focus列表

    }
}
