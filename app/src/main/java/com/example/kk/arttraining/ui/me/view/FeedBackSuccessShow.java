package com.example.kk.arttraining.ui.me.view;

import android.app.Activity;
import android.os.Bundle;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.utils.TitleBack;

/**
 * 作者：wschenyongyin on 2016/11/17 12:10
 * 说明:
 */
public class FeedBackSuccessShow extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_feedback_success);
        TitleBack.TitleBackActivity(this,"意见反馈");
    }
}
