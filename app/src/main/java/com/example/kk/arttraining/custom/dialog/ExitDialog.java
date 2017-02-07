package com.example.kk.arttraining.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kk.arttraining.R;

/**
 * 作者：wschenyongyin on 2017/1/18 17:55
 * 说明:
 */
public class ExitDialog extends Dialog implements View.OnClickListener{
    ExitDialogListener listener;
    Context context;
    private String title;

    private TextView tvTitle;
    private Button btn_cancel;
    private Button btn_ok;
    public ExitDialog(Context context,String title,ExitDialogListener listener) {
        super(context, R.style.transparentDialog);
        this.context = context;
        this.title=title;
        this.listener=listener;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_exit);

        tvTitle= (TextView) findViewById(R.id.title);
        btn_cancel= (Button) findViewById(R.id.btn_cancel);
        btn_ok= (Button) findViewById(R.id.btn_sure);


        tvTitle.setText(title);
        btn_cancel.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
    }

    public interface ExitDialogListener {
        void onClick(View view);
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v);
    }
}
