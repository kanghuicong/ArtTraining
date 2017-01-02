package com.example.kk.arttraining;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;

import com.example.kk.arttraining.prot.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/12/29 09:04
 * 说明:
 */
public class testEmoji extends FragmentActivity {

    @InjectView(R.id.test)
    EditText test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa_test_activity);
        ButterKnife.inject(this);

    }



}
