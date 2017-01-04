package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.GeneralBean;
import com.example.kk.arttraining.custom.dialog.PopWindowDialogUtil;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2017/1/3.
 * QQ邮箱:515849594@qq.com
 */
public class ShareDialog {

    static PopWindowDialogUtil popWindowDialogUtil;

    public static void getShareDialog( final Context context, final String stus_type, final int stus_id) {
        popWindowDialogUtil = new PopWindowDialogUtil(context, R.style.transparentDialog, R.layout.dialog_homepage_share, "share", new PopWindowDialogUtil.ChosePicDialogListener() {
            @Override
            public void onClick(View view) {
                popWindowDialogUtil.dismiss();
                switch (view.getId()) {
                    case R.id.bt_homepage_share_collect:
                        if (Config.ACCESS_TOKEN == null || Config.ACCESS_TOKEN.equals("")) {
                            TokenVerfy.Login(context, 2);
                        } else {
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("access_token", Config.ACCESS_TOKEN);
                            map.put("uid", Config.UID);
                            map.put("type", stus_type);
                            map.put("utype", Config.USER_TYPE);
                            map.put("favorite_id", stus_id);

                            Callback<GeneralBean> callback = new Callback<GeneralBean>() {
                                @Override
                                public void onResponse(Call<GeneralBean> call, Response<GeneralBean> response) {
                                    GeneralBean generalBean = response.body();

                                    if (response.body() != null) {
                                        if (generalBean.getError_code().equals("0")) {
                                            UIUtil.ToastshowShort(context, "收藏成功！");
                                        } else {
                                            UIUtil.ToastshowShort(context.getApplicationContext(), generalBean.getError_msg());
                                            if (generalBean.getError_code().equals("20028")) {
                                                context.startActivity(new Intent(context, UserLoginActivity.class));
                                            }
                                        }
                                    } else {
                                        UIUtil.ToastshowShort(context.getApplicationContext(), "OnFailure");
                                    }
                                }

                                @Override
                                public void onFailure(Call<GeneralBean> call, Throwable t) {
                                    UIUtil.ToastshowShort(context.getApplicationContext(), "网络连接失败！");
                                }
                            };
                            Call<GeneralBean> call = HttpRequest.getStatusesApi().statusesFavoritesCreate(map);
                            call.enqueue(callback);
                        }
                        break;

                    case R.id.bt_homepage_share:

                        break;
                    case R.id.bt_homepage_share_report:
                        MyDialog.getReportDialog(context);
                        break;

                }
            }
        });
        //设置从底部显示
        Window window = popWindowDialogUtil.getWindow();
        popWindowDialogUtil.show();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }



}
