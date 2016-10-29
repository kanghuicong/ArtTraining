package com.example.kk.arttraining.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;

/**
 * 作者：wschenyongyin on 2016/10/28 11:27
 * 说明:
 */
public class UpdateDialogUtil extends Dialog implements View.OnClickListener {
    private UpdateDialogListener listener;
    private int layout;
    private String type;

    public UpdateDialogUtil(Context context) {
        super(context);
    }

    protected UpdateDialogUtil(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public UpdateDialogUtil(Context context, int themeResId, String type, int layout, UpdateDialogListener listener) {
        super(context, themeResId);
        this.layout = layout;
        this.listener = listener;
        this.type = type;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout);
        findView();

    }

    private void findView() {
        switch (type) {
            case "updateName":

                break;

        }
    }


    public interface UpdateDialogListener {
        public void onClick(View view);
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v);

    }
}
