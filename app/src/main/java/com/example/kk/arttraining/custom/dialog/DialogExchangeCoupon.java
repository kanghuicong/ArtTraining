package com.example.kk.arttraining.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kk.arttraining.R;

import butterknife.ButterKnife;

/**
 * 作者：wschenyongyin on 2016/10/28 11:27
 * 说明:
 */
public class DialogExchangeCoupon extends Dialog implements View.OnClickListener {
    private int layout;
    private String type;

    public DialogExchangeCoupon(Context context) {
        super(context);
    }

    protected DialogExchangeCoupon(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public DialogExchangeCoupon(Context context, int themeResId, int layout) {
        super(context, themeResId);
        this.layout = layout;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout);
        findView();

    }

    private void findView() {
        EditText et_coupon_code = (EditText) findViewById(R.id.et_coupon_code);
        Button btn_coupon_cancel = (Button) findViewById(R.id.btn_coupon_cancel);
        Button btn_coupon_save = (Button) findViewById(R.id.btn_coupon_save);

        btn_coupon_cancel.setOnClickListener(this);
        btn_coupon_save.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_coupon_cancel:

                break;
            case R.id.btn_coupon_save:

                break;
        }
    }

}
