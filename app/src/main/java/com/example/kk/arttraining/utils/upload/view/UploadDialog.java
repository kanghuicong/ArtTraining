package com.example.kk.arttraining.utils.upload.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.example.kk.arttraining.R;

/**
 * 作者：wschenyongyin on 2016/11/2 20:36
 * 说明:
 */
public class UploadDialog extends Dialog {

    private int layout;
    private Context context;

    public UploadDialog(Context context) {
        super(context);
        this.context = context;
    }

    public UploadDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    // 传入布局，activity，主题
    public UploadDialog(Context context, int layout, int theme) {
        super(context, theme);
        this.layout = layout;
        this.context = context;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);



    }
}
