package com.example.kk.arttraining.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.kk.arttraining.pay.view.PaySuccessActivity;
import com.example.kk.arttraining.pay.wxapi.Constants;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxApiManager;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxBus;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxHelper;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxSubscribe;
import com.example.kk.arttraining.sqlite.dao.UploadDao;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import rx.Subscription;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler, UpdateOrderPaySuccess {

    private IWXAPI api;
    private String orderId;
    private UpdatePayPresenter presenter;
    private Subscription subscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        UIUtil.showLog("baseResp------->", baseResp.errCode + "" + baseResp.errStr);
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) {
                if (Config.WxCallBackType.equals("recharge")){
                    //更新充值状态
                    updateRechargeState();
                }else {
                    presenter = new UpdatePayPresenter(this);
                    updateOrder();
                    UploadDao uploadDao = new UploadDao(this);
                    uploadDao.update("is_pay", "1", Config.order_num);
//                uploadDao.delete(Config.order_num);
                    Toast.makeText(this, "成功" + baseResp.errCode,
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, PaySuccessActivity.class);
                    intent.putExtra("file_path", Config.order_att_path);
                    intent.putExtra("token", Config.QINIUYUN_WORKS_TOKEN);
                    intent.putExtra("order_id", Config.order_num);
                    startActivity(intent);
                    finish();
                }

            } else {
                if (Config.WxCallBackType.equals("recharge")){
                    UIUtil.ToastshowShort(getApplicationContext(),"充值失败");
                    finish();
                }else {
                    Toast.makeText(this, "失败" + baseResp.errCode,
                            Toast.LENGTH_SHORT).show();
                    try {
                        UploadDao uploadDao = new UploadDao(this);
                        uploadDao.delete(Config.order_num);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            }
        }

    }

    //更新支付状态为成功
    @Override
    public void updateOrder() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("order_number", Config.order_num);
        map.put("is_pay", "1");
        presenter.updateOrder(map);
    }

    //更新支付状态成功
    @Override
    public void Success() {
        UIUtil.showLog("更新订单状态成功------》", "true");
    }

    //更新支付状态失败
    @Override
    public void Failure(String error_code, String error_msg) {
        UIUtil.showLog("更新订单状态失败------》", "false");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    void updateRechargeState(){
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("access_token",Config.ACCESS_TOKEN);
        map.put("uid",Config.UID);
        map.put("order_id",Config.rechargeId);
        map.put("order_number",Config.rechargeNum);
        map.put("pay_type","wxpay");
        subscription= HttpRequest.getCloudApi().updateOrder(map).compose(RxHelper.<Double>handleResult()).subscribe(new RxSubscribe<Double>() {
            @Override
            protected void _onNext(Double aDouble) {
                UIUtil.ToastshowShort(getApplicationContext(),"充值成功");
                RxBus.get().post("rechargeNum", aDouble);
                finish();
            }
            @Override
            protected void _onError(String error_code, String error_msg) {
                UIUtil.ToastshowShort(getApplicationContext(),"充值失败");
                finish();
            }
            @Override
            public void onCompleted() {
            }
        });
        RxApiManager.get().add("updateRecharge",subscription);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxApiManager.get().cancel("updateRecharge");
    }
}