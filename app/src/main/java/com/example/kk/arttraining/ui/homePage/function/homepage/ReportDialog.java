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
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.PreferencesUtils;
import com.example.kk.arttraining.utils.UIUtil;

/**
 * Created by kanghuicong on 2017/1/2.
 * QQ邮箱:515849594@qq.com
 */
public class ReportDialog {
    //选择性别
    public static void getReportDialog(final Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.homepage_province_province_dialog, null);
        TextView tv_content = (TextView) layout.findViewById(R.id.tv_dialog_content);
        Button bt_true = (Button) layout.findViewById(R.id.btn_province_true);
        Button bt_false = (Button) layout.findViewById(R.id.btn_province_false);
        addDialog(dialog, layout);

        tv_content.setText("确定举报该帖子？");
        tv_content.setGravity(Gravity.CENTER);

        bt_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.ToastshowShort(context, "举报成功！");
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

    private static void addDialog(Dialog dialog, LinearLayout layout) {
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setContentView(layout);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
    }
}
