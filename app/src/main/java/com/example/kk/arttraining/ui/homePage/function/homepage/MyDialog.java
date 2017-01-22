package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
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
import com.example.kk.arttraining.utils.UIUtil;

/**
 * Created by kanghuicong on 2016/11/17.
 * QQ邮箱:515849594@qq.com
 */
public class MyDialog {

    //是否更改城市
    public static void getProvinceDialog(final Context context, final String location, final TextView tv) {
        final Dialog dialog = new Dialog(context);
        final LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.homepage_province_province_dialog, null);
        TextView tv_content = (TextView) layout.findViewById(R.id.tv_dialog_content);
        Button bt_true = (Button) layout.findViewById(R.id.btn_province_true);
        Button bt_false = (Button) layout.findViewById(R.id.btn_province_false);
        addDialog(dialog, layout);

        tv_content.setText("选择城市与定位城市不符，是否更改为所定位城市？");
        bt_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.put(context,"province", location);
                Config.CITY = location;
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

    //删除帖子
    public static void getDeleteDialog(final Activity activity) {
        final Dialog dialog = new Dialog(activity);
        final LinearLayout layout = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.dialog_delete_dynamic, null);
        Button bt_true = (Button) layout.findViewById(R.id.btn_delete_true);
        Button bt_false = (Button) layout.findViewById(R.id.btn_delete_false);
        addDialog(dialog, layout);

        bt_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                activity.finish();
            }
        });

        bt_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    //举报
    public static void getReportDialog(final Context context) {
        final Dialog dialog = new Dialog(context);
        final LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.homepage_province_province_dialog, null);
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

    public static void getChapterDialog(final Context context, final IChapter iChapter) {
        final Dialog dialog = new Dialog(context);
        final LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.homepage_province_province_dialog, null);
        TextView tv_content = (TextView) layout.findViewById(R.id.tv_dialog_content);
        Button bt_true = (Button) layout.findViewById(R.id.btn_province_true);
        Button bt_false = (Button) layout.findViewById(R.id.btn_province_false);
        addDialog(dialog, layout);

        tv_content.setText("未购买该章节，是否购买？");
        tv_content.setGravity(Gravity.CENTER);

        bt_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iChapter.getBuyChapter();
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
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setContentView(layout);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
    }

    public interface IChapter{
        void getBuyChapter();
    }

}
