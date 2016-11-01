package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.homePage.adapter.TeacherListViewAdapter;
import com.example.kk.arttraining.utils.TitleBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/10/18.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeTeacher extends Activity {
    @InjectView(R.id.lv_teacher)
    MyListView lvTeacher;

    TeacherListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_teacher);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this, "名师");

        List<TecInfoBean> tecInfoBeanList = new ArrayList<TecInfoBean>();
        adapter = new TeacherListViewAdapter(this,tecInfoBeanList);

        lvTeacher.setAdapter(adapter);
        lvTeacher.setOnItemClickListener(new TeacherItemClick());
    }

    private class TeacherItemClick implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(ThemeTeacher.this, ThemeTeacherContent.class);
            startActivity(intent);
        }
    }
}