package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.PreferencesUtils;

/**
 * Created by kanghuicong on 2016/11/17.
 * QQ邮箱:515849594@qq.com
 */
public class ProvinceDialog {
    //选择性别
    public static void getProvinceDialog(final Context context, final String location, final TextView tv) {

        LayoutInflater inflater = LayoutInflater.from(context);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.homepage_province_province_dialog, null);
        Button bt_true = (Button) layout.findViewById(R.id.btn_province_true);
        Button bt_false = (Button) layout.findViewById(R.id.btn_province_false);
        addDialog(dialog, layout);

        bt_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PreferencesUtils.remove(context,"province");
                PreferencesUtils.put(context,"province", location);
                tv.setText(location);
                dialog.dismiss();
            }
        });

        bt_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(Config.CITY);
                dialog.dismiss();
            }
        });

    }

    private static void addDialog(Dialog dialog, LinearLayout layout) {
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setContentView(layout);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
    }

}
