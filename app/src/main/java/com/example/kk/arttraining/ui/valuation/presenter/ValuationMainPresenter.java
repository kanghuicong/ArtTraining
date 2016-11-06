package com.example.kk.arttraining.ui.valuation.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.NoDataResponseBean;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.ui.valuation.adapter.TeacherAdapter;
import com.example.kk.arttraining.ui.valuation.view.IValuationMain;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/10/31 09:52
 * 说明:
 */
public class ValuationMainPresenter {
    private IValuationMain iValuationMain;

    public ValuationMainPresenter(IValuationMain iValuationMain) {
        this.iValuationMain = iValuationMain;
    }

    //提交订单
    public void CommitOrder(Map<String, String> map) {
        if (CheckOrderData()) {
            iValuationMain.showLoading();
            Callback<NoDataResponseBean> callback = new Callback<NoDataResponseBean>() {
                @Override
                public void onResponse(Call<NoDataResponseBean> call, Response<NoDataResponseBean> response) {
                    iValuationMain.hideLoading();
                    if (response.body() != null) {
                        NoDataResponseBean responseBean = response.body();
                        if (responseBean.getError_code().equals("0")) {
                            iValuationMain.CommitOrder();
                        } else {
                            iValuationMain.OnFailure(responseBean.getError_code());
                        }
                    } else {
                        iValuationMain.OnFailure("500");
                    }
                }

                @Override
                public void onFailure(Call<NoDataResponseBean> call, Throwable t) {
                    iValuationMain.hideLoading();
                    iValuationMain.OnFailure("500");

                }
            };

            Call<NoDataResponseBean> call = HttpRequest.getPayApi().commitOrder(map);
            call.enqueue(callback);
        } else {
            iValuationMain.OnFailure("501");
        }


    }

    //检查订单信息是否填写完整
    Boolean CheckOrderData() {
        String production_describe = iValuationMain.getProductionDescribe();
        String production_name = iValuationMain.getProductionName();
        String production_path = iValuationMain.getProductionPath();
        List<TecInfoBean> tecInfoBean = iValuationMain.getTeacherInfo();

        UIUtil.showLog("production_describe",production_describe+"-->"+production_name+"-->"+production_path+"-->"+tecInfoBean.size());
        if (production_describe != null && production_name != null && production_path != null && tecInfoBean != null) {
            return true;
        } else {
            return false;
        }
    }

    public void showPopwindow(Activity context) {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.popwindow_valuation, null);
        View view = View.inflate(context, R.layout.valuation_choseteacher_activity, null);
        // 得到宽度和高度 getWindow().getDecorView().getWidth()
        ListView listView = (ListView) view.findViewById(R.id.lv_chose_content);
        TeacherAdapter teacherAdapter = new TeacherAdapter(context);
        listView.setAdapter(teacherAdapter);



        PopupWindow window;
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
//        ColorDrawable dw = new ColorDrawable(0x00000000);
//        //设置SelectPicPopupWindow弹出窗体的背景
//        window.setBackgroundDrawable(dw);
//        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
//        lp.alpha = 0.2f;
//        context.getWindow().setAttributes(lp);
        // 在底部显示
        window.showAtLocation(
                context.findViewById(R.id.ll_valuation),
                Gravity.CENTER, 0, 0);
        // 这里检验popWindow里的button是否可以点击
        // popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                System.out.println("popWindow消失");
            }
        });
    }


}
