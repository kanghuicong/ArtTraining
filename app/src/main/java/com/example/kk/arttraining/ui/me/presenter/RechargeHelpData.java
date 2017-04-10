package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.prot.rxjava_retrofit.RxApiManager;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxHelper;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxSubscribe;
import com.example.kk.arttraining.ui.me.bean.RechargeHelpBean;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.HashMap;
import java.util.List;

import rx.Subscription;

/**
 * Created by kanghuicong on 2017/4/6.
 * QQ邮箱:515849594@qq.com
 */
public class RechargeHelpData {

    private Subscription rechargeHelpSub;
    IRechargeHelp iRechargeHelp;

    public RechargeHelpData(IRechargeHelp iRechargeHelp) {
        this.iRechargeHelp = iRechargeHelp;
    }

    public void getRechargeHelp(String telephone) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("telephone", telephone);

        rechargeHelpSub = HttpRequest.getCloudApi().rechargeHelp(map).compose(RxHelper.<List<RechargeHelpBean>>handleResult()).subscribe(new RxSubscribe<List<RechargeHelpBean>>() {
            @Override
            protected void _onNext(List<RechargeHelpBean> helpBeanList) {
                iRechargeHelp.successRechargeHelp(helpBeanList);
            }
            @Override
            protected void _onError(String error_code, String error_msg) {
                iRechargeHelp.failureRechargeHelp(error_msg);
            }
            @Override
            public void onCompleted() {}
        });
        RxApiManager.get().add("rechargeHelpSub", rechargeHelpSub);
    }

    public interface IRechargeHelp{
        void successRechargeHelp(List<RechargeHelpBean> helpBeanList);

        void failureRechargeHelp(String error_msg);
    }

    public void cancelSubscription() {
        RxApiManager.get().unsubscribe("rechargeHelpSub");
    }


}
