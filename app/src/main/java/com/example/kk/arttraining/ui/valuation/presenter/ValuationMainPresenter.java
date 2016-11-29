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
import com.example.kk.arttraining.ui.valuation.bean.CommitOrderBean;
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
    public void CommitOrder(Map<String, Object> map) {
        if (CheckOrderData().equals("true")) {
            iValuationMain.showLoading();
            Callback<CommitOrderBean> callback = new Callback<CommitOrderBean>() {
                @Override
                public void onResponse(Call<CommitOrderBean> call, Response<CommitOrderBean> response) {
                    UIUtil.showLog("提交订单信息onResponse", response.code() + "----->" + response.message());
                    iValuationMain.hideLoading();
                    if (response.body() != null) {
                        CommitOrderBean commitOrderBean = response.body();
                        UIUtil.showLog("提交订单信息返回信息", "----->" + commitOrderBean.toString()+"---code--->"+commitOrderBean.getError_code()+"msg-->"+commitOrderBean.getError_msg());
                        if (commitOrderBean.getError_code().equals("0")) {
                            iValuationMain.CommitOrder(commitOrderBean);
                        } else {
                            iValuationMain.OnFailure(commitOrderBean.getError_code());
                        }
                    } else {
                        iValuationMain.OnFailure("500");
                    }
                }

                @Override
                public void onFailure(Call<CommitOrderBean> call, Throwable t) {
                    UIUtil.showLog("提交订单错误信息onFailure", t.toString() + "----->" + t.getMessage());
                    iValuationMain.hideLoading();
                    iValuationMain.OnFailure("500");

                }
            };

            Call<CommitOrderBean> call = HttpRequest.getPayApi().commitOrder(map);
            call.enqueue(callback);
        } else {
            iValuationMain.OnFailure(CheckOrderData());
        }


    }

    //检查订单信息是否填写完整
    String CheckOrderData() {
        String aBoolean = null;
        String production_describe = iValuationMain.getProductionDescribe();
        String production_name = iValuationMain.getProductionName();
        String production_path = iValuationMain.getProductionPath();
        List<TecInfoBean> tecInfoBean = iValuationMain.getTeacherInfo();

        UIUtil.showLog("production_describe", production_describe + "-->" + production_name + "-->" + production_path + "-->" + tecInfoBean.size());
        if (production_describe != null && production_name != null && production_describe.length() < 200 && production_name.length() < 20 && production_path != null && tecInfoBean != null && tecInfoBean.size() != 0) {
            aBoolean = "true";

        } else if (production_describe.length() > 200) {
            aBoolean = "606";
        } else if (production_name.length() >20){
            aBoolean = "607";
        }else {
            aBoolean = "501";
        }
        return aBoolean;
    }
}
