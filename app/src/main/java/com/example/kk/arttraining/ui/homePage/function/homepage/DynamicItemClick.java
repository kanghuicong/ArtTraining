package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import com.example.kk.arttraining.utils.UIUtil;

/**
 * Created by kanghuicong on 2016/10/18.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicItemClick implements AdapterView.OnItemClickListener {
    Activity activity;

    public DynamicItemClick(Activity activity) {
        this.activity = activity;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
