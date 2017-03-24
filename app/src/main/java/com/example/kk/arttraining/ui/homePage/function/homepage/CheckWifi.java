package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.prot.ICheckWifi;

/**
 * Created by kanghuicong on 2017/1/5.
 * QQ邮箱:515849594@qq.com
 */
public class CheckWifi {
    ICheckWifi iCheckWifi;
    String type;

    public CheckWifi(String type,ICheckWifi iCheckWifi) {
        this.iCheckWifi = iCheckWifi;
        this.type = type;
    }

    public void getWifiDialog(final Context context) {
        final Dialog dialog = new Dialog(context);
        final LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_homepage_province, null);
        TextView tv_content = (TextView) layout.findViewById(R.id.tv_dialog_content);
        Button bt_true = (Button) layout.findViewById(R.id.btn_province_true);
        Button bt_false = (Button) layout.findViewById(R.id.btn_province_false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setContentView(layout);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        tv_content.setText("当前网络环境非WIFI，是否继续"+type+"？");
        tv_content.setGravity(Gravity.CENTER);

        bt_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCheckWifi.CheckWifi();
                dialog.dismiss();
            }
        });

        bt_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}
