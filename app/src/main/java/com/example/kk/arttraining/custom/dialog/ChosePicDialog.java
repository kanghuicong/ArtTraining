package com.example.kk.arttraining.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kk.arttraining.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/10/25 15:23
 * 说明:
 */
public class ChosePicDialog extends Dialog implements View.OnClickListener {

    private ChosePicDialogListener listener;
    private Context context;
    private int theme;
    @InjectView(R.id.btn_takePic)
    Button btn_takePic;
    @InjectView(R.id.btn_chosePic)
    Button btn_chosePic;
    @InjectView(R.id.btn_header_cancel)
    Button btn_header_cancel;

    public ChosePicDialog(Context context, ChosePicDialogListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    public ChosePicDialog(Context context, int theme) {
        super(context);
        this.context = context;
        this.theme = theme;

    }

    public ChosePicDialog(Context context, int theme, ChosePicDialogListener listener) {
        super(context, theme);
        this.context = context;
        this.theme = theme;
        this.listener = listener;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_chose_header);
        ButterKnife.inject(this);
        btn_takePic.setOnClickListener(this);
        btn_chosePic.setOnClickListener(this);
        btn_header_cancel.setOnClickListener(this);

    }

    public interface ChosePicDialogListener {
        public void onClick(View view);
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v);

    }
}
