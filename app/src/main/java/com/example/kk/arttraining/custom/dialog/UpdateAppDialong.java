package com.example.kk.arttraining.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;

/**
 * 作者：wschenyongyin on 2016/11/30 17:57
 * 说明:
 */
public class UpdateAppDialong extends Dialog implements
        View.OnClickListener {
    int layout;// 自定义的布局
    Context context;// 上下文联系
    int theme;// dialog主题
    private UpdateAppListener listener;
    private TextView tv_version_name;
    private TextView tv_version_describe;
    private ImageView update_now;
    private ImageView update_monent;
    String version_name;
    String version_describe;

    public UpdateAppDialong(Context context, int layout, int theme, String version_name, String version_describe,
                            UpdateAppListener listener) {
        super(context, theme);
        this.context = context;
        this.theme = theme;
        this.layout = layout;
        this.listener = listener;
        this.version_describe = version_describe;
        this.version_name = version_name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout);

        tv_version_name= (TextView) findViewById(R.id.version_name);
        tv_version_describe= (TextView) findViewById(R.id.version_describe);
        update_monent = (ImageView) findViewById(R.id.update_monent);
        update_now = (ImageView) findViewById(R.id.update_now);

        tv_version_name.setText(version_name);
        tv_version_describe.setText(version_describe);
        update_monent.setOnClickListener(this);
        update_now.setOnClickListener(this);

    }

    public interface UpdateAppListener {
        void onClick(View view);
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v);
    }
}