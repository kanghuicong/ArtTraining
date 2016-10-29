package com.example.kk.arttraining.prot;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * 作者：wschenyongyin on 2016/9/21 09:31
 * 说明:
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener {
    public abstract void init();

    @Override
    public abstract void onClick(View v);


}
