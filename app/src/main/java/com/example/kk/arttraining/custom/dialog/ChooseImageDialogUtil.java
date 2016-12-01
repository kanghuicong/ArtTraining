package com.example.kk.arttraining.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kk.arttraining.R;

/**
 * Created by kanghuicong on 2016/10/31.
 * QQ邮箱:515849594@qq.com
 */
public class ChooseImageDialogUtil extends Dialog implements
        View.OnClickListener {
    int layout;// 自定义的布局
    Context context;// 上下文联系
    int theme;// dialog主题
    private LeaveMyDialogListener listener;

    public ChooseImageDialogUtil(Context context, int layout, int theme,
                                 LeaveMyDialogListener listener) {
        super(context, theme);
        this.context = context;
        this.theme = theme;
        this.layout = layout;
        this.listener = listener;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout);

        Button btn_takephoto = (Button) findViewById(R.id.btn_takephoto);
        Button btn_picture = (Button) findViewById(R.id.btn_picture);
        Button btn_cancel = (Button) findViewById(R.id.btn_cancel);

        btn_takephoto.setOnClickListener(this);
        btn_picture.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    }

    public interface LeaveMyDialogListener {
        void onClick(View view);
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v);
    }

}
