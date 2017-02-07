package com.example.kk.arttraining.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.JustifyText;

import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2017/2/6 14:33
 * 说明:显示老师点评dialog
 */
public class DialogShowComment extends Dialog implements View.OnClickListener {
    @InjectView(R.id.tv_dialog_comment)
    JustifyText tvDialogComment;
    @InjectView(R.id.btn_sure)
    Button btnSure;
    private CommentDialogListener listener;
    private String comment;

    public DialogShowComment(Context context, String comment, CommentDialogListener listener) {
        super(context, R.style.transparentDialog);
        this.listener = listener;
        this.comment = comment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_show_comment);

        tvDialogComment = (JustifyText) findViewById(R.id.tv_dialog_comment);
        btnSure = (Button) findViewById(R.id.btn_sure);
        if (comment != null && !comment.equals(""))
            tvDialogComment.setText(comment);

        btnSure.setOnClickListener(this);
    }

    public interface CommentDialogListener {
        void onClick(View view);
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v);
    }
}
