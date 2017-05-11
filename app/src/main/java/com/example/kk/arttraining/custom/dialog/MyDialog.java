package com.example.kk.arttraining.custom.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.me.bean.RechargeHelpBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.PreferencesUtils;
import com.example.kk.arttraining.utils.ScreenUtils;
import com.example.kk.arttraining.utils.UIUtil;

import static android.content.DialogInterface.*;

/**
 * Created by kanghuicong on 2016/11/17.
 * QQ邮箱:515849594@qq.com
 */
public class MyDialog {
    static Dialog dialog;
    static LinearLayout layout;
    static TextView tv_content;
    static Button bt_true;
    static Button bt_false;

    static TextView tv_chapter;
    static TextView tv_time;
    static TextView tv_price;
    static TextView tv_cloud;
    static TextView tv_room;

    static TextView tv_name;
    static TextView tv_phone;


    //是否更改城市
    public static void getProvinceDialog(final Context context, final String location, final TextView tv) {
        dialog = new Dialog(context);
        layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_homepage_province, null);
        tv_content = (TextView) layout.findViewById(R.id.tv_dialog_content);
        bt_true = (Button) layout.findViewById(R.id.btn_province_true);
        bt_false = (Button) layout.findViewById(R.id.btn_province_false);
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
    public static void getDeleteDialog(Context context,IDelete iDelete) {
        dialog = new Dialog(context);
        layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_delete_dynamic, null);
        bt_true = (Button) layout.findViewById(R.id.btn_delete_true);
        bt_false = (Button) layout.findViewById(R.id.btn_delete_false);
        addDialog(dialog, layout);

        bt_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                iDelete.getDelete();
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
    public static void getReportDialog(Context context) {
        dialog = new Dialog(context);
        layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_homepage_province, null);
        tv_content = (TextView) layout.findViewById(R.id.tv_dialog_content);
        bt_true = (Button) layout.findViewById(R.id.btn_province_true);
        bt_false = (Button) layout.findViewById(R.id.btn_province_false);
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

    //云币支付
    public static void getChapterDialog(final Context context, double price, double cloud, final IChapter iChapter) {
        dialog = new Dialog(context);
        layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.live_chapter_buy_dialog, null);
        tv_content = (TextView) layout.findViewById(R.id.tv_dialog_content);
        bt_true = (Button) layout.findViewById(R.id.btn_province_true);
        bt_false = (Button) layout.findViewById(R.id.btn_province_false);
        tv_cloud = (TextView) layout.findViewById(R.id.tv_dialog_cloud);
        addDialog(dialog, layout);

        tv_content.setText("是否花费" + price + "云币购买本章节？");
        tv_cloud.setText("云币：" + cloud);

        if (cloud > price) {
            bt_true.setText("确定");
        }else {
            bt_true.setText("充值");
        }


        bt_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (bt_true.getText().toString()) {
                    case "确定":
                        iChapter.getBuyChapter();
                        break;
                    case "充值":
                        iChapter.getRecharge();
                        break;
                }
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

    //直播未购买
    public static void getPayDialog(Context context,String room,String chapter,String time,String content,double price, IPay iPay) {

        dialog = new Dialog(context, R.style.transparentDialog);
        layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_pay, null);
        tv_room = (TextView) layout.findViewById(R.id.tv_dialog_room);
        tv_chapter = (TextView) layout.findViewById(R.id.tv_dialog_chapter);
        tv_time = (TextView) layout.findViewById(R.id.tv_dialog_time);
        tv_content = (TextView) layout.findViewById(R.id.tv_dialog_content);
        tv_price = (TextView) layout.findViewById(R.id.tv_dialog_price);
        bt_true = (Button) layout.findViewById(R.id.btn_province_true);
        bt_false = (Button) layout.findViewById(R.id.btn_province_false);
        addDialog(dialog, layout);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        tv_room.setText("直播间：" + room);
        ScreenUtils.accordWidth(tv_chapter,ScreenUtils.getScreenWidth(context),4,5);
        tv_chapter.setText("章节：" + chapter);
        tv_time.setText("直播时间：" + time);
        tv_content.setText(content);
        tv_price.setText("¥" + price);
        iPay.getDialog(dialog);

        bt_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPay.getPay();
            }
        });

        bt_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPay.onPayFailure();
                dialog.dismiss();
            }
        });
    }

    //用云币付费
    public static void getPayCloud(Context context,double cloud,String content,IPayCloud iPayCloud) {
        dialog = new Dialog(context);
        layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_pay_cloud, null);
        bt_true = (Button) layout.findViewById(R.id.btn_province_true);
        bt_false = (Button) layout.findViewById(R.id.btn_province_false);
        tv_cloud = (TextView) layout.findViewById(R.id.tv_dialog_cloud);
        tv_content = (TextView) layout.findViewById(R.id.tv_content);
        addDialog(dialog, layout);

        tv_content.setText(content);
        tv_cloud.setText(cloud + "");

        bt_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPayCloud.getPayCloud();
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

    //充值
    public static void getRechargeCloud(Context context, RechargeHelpBean helpBean, IRecharge iRecharge) {
        dialog = new Dialog(context);
        layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_recharge, null);
        bt_true = (Button) layout.findViewById(R.id.btn_province_true);
        bt_false = (Button) layout.findViewById(R.id.btn_province_false);
        tv_name = (TextView) layout.findViewById(R.id.tv_name);
        tv_phone = (TextView) layout.findViewById(R.id.tv_phone);
        addDialog(dialog, layout);

        tv_name.setText(helpBean.getName());
        tv_phone.setText("(" + helpBean.getTelephone() + ")");

        bt_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iRecharge.getRecharge();
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

    //询问是否购买
    public static void getBuyRecord(Context context,double price,IBuyRecord iBuyRecord) {
        dialog = new Dialog(context);
        layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_buy_record, null);
        bt_true = (Button) layout.findViewById(R.id.btn_province_true);
        bt_false = (Button) layout.findViewById(R.id.btn_province_false);
        tv_price = (TextView) layout.findViewById(R.id.tv_price);
        addDialog(dialog, layout);

        tv_price.setText("(¥"+price+")");

        bt_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iBuyRecord.getBuyRecord();
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
        dialog.show();
        dialog.getWindow().setContentView(layout);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
    }

    public interface IChapter{
        void getBuyChapter();

        void getRecharge();
    }

    public interface IDelete{
        void getDelete();
    }

    public interface IPay{
        void getPay();

        void onPayFailure();

        void getDialog(Dialog dialog);
    }

    public interface IPayCloud{
        void getPayCloud();
    }

    public interface IRecharge{
        void getRecharge();
    }

    public interface IBuyRecord{
        void getBuyRecord();
    }

}
