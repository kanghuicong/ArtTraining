package com.example.kk.arttraining.pay.presenter;

import com.example.kk.arttraining.bean.OrderBean;
import com.example.kk.arttraining.pay.bean.AliPay;
import com.example.kk.arttraining.pay.bean.RechargeBean;
import com.example.kk.arttraining.pay.bean.RechargeOrderBean;
import com.example.kk.arttraining.pay.bean.WeChat;
import com.example.kk.arttraining.pay.bean.WeChatBean;
import com.example.kk.arttraining.pay.view.IRechargeICloudView;
import com.example.kk.arttraining.pay.wxapi.Constants;
import com.example.kk.arttraining.prot.rxjava_retrofit.BaseModel;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxHelper;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxSubscribe;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;

/**
 * 作者：wschenyongyin on 2017/3/2 09:23
 * 说明:充值presenter
 */
public class RechargePresenter {
    IRechargeICloudView iRechargeICloudView;

    private Subscription rechargeListSub;

    private Subscription wxSub;

    public RechargePresenter(IRechargeICloudView iRechargeICloudView) {
        this.iRechargeICloudView = iRechargeICloudView;
    }


    //获取充值列表
    public void getRechargeList(HashMap<String, Object> map) {
        rechargeListSub = HttpRequest.getCloudApi().getRechargeList(map).compose(RxHelper.<List<RechargeBean>>handleResult()).subscribe(new RxSubscribe<List<RechargeBean>>() {
            @Override
            protected void _onNext(List<RechargeBean> rechargeBeanList) {
                iRechargeICloudView.SuccessRechargeList(rechargeBeanList);
            }

            @Override
            protected void _onError(String error_code, String error_msg) {
                iRechargeICloudView.FailureRechargeList(error_code, error_msg);
            }

            @Override
            public void onCompleted() {

            }
        });

    }

    //创建订单，创建成功返回orderi_id,根据order_id获取微信支付预支付id
    public void createRechargeOrder(HashMap<String, Object> map) {
        wxSub = HttpRequest.getCloudApi().createICloudOrder(map).flatMap(new Func1<BaseModel<RechargeOrderBean>, Observable<BaseModel<WeChatBean>>>() {
            @Override
            public Observable<BaseModel<WeChatBean>> call(BaseModel<RechargeOrderBean> orderBean) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("access_token", Config.ACCESS_TOKEN);
                map.put("uid", Config.UID);
                map.put("order_number", orderBean.data.getOrder_number());
                map.put("pay_method", "wxpay");
                map.put("pay_source", "android");
                //将创建订单返回的订单id和订单编号保存
                Config.rechargeId=orderBean.data.getOrder_id();
                Config.rechargeNum=orderBean.data.getOrder_number();
                return HttpRequest.getScoreApi().getWxPartnerId(map);
            }
        }).compose(RxHelper.<WeChatBean>handleResult())
                .subscribe(new RxSubscribe<WeChatBean>() {
                    @Override
                    protected void _onNext(WeChatBean weChatBean) {
                        iRechargeICloudView.wxPay(weChatBean);
                    }

                    @Override
                    protected void _onError(String error_code, String error_msg) {
                        iRechargeICloudView.FailureRecharge(error_code, error_msg);
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }


}
