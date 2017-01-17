package com.example.kk.arttraining;

import android.os.Bundle;
import android.view.View;

import com.example.kk.arttraining.custom.view.HideKeyboardActivity;
import com.example.kk.arttraining.prot.BaseActivity;

/**
 * 作者：wschenyongyin on 2017/1/11 18:44
 * 说明:
 */
public class TestActivity extends HideKeyboardActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa_test_activity);
    }


}
