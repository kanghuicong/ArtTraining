package com.example.kk.arttraining.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.activity.ShareDynamicImage;

/**
 * Created by kanghuicong on 2016/10/31.
 * QQ邮箱:515849594@qq.com
 */
public class SaveImageDialogUtil extends Dialog implements View.OnClickListener{
    Context context;
    int layout;
    int theme;
    Button bt_saveImage;
    Button bt_cancel;
    SaveImageClick listener;

    public SaveImageDialogUtil(Context context, int layout,int theme,SaveImageClick listener) {
        super(context,theme);
        this.context = context;
        this.layout = layout;
        this.listener = listener;
        this.theme = theme;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout);
        findView();

    }

    private void findView() {
        bt_saveImage = (Button) findViewById(R.id.bt_save_image_logout);
        bt_cancel = (Button)findViewById(R.id.bt_save_image_cancel);
        bt_saveImage.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v);
    }

    public interface SaveImageClick{
        void onClick(View view);
    }
}
