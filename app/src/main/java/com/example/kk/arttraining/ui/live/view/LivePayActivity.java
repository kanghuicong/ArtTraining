package com.example.kk.arttraining.ui.live.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.custom.dialog.MyDialog;
import com.example.kk.arttraining.pay.bean.AliPay;
import com.example.kk.arttraining.pay.bean.WeChatBean;
import com.example.kk.arttraining.pay.presenter.PayPresenter;
import com.example.kk.arttraining.pay.view.IPayActivity;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.live.presenter.LiveBuyData;
import com.example.kk.arttraining.ui.live.presenter.LivePayData;
import com.example.kk.arttraining.ui.valuation.bean.CommitOrderBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.StringUtils;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2017/1/22.
 * QQ邮箱:515849594@qq.com
 */
public class LivePayActivity extends BaseActivity implements IPayActivity, ILiveBuy, LivePayData.IPayOther {

    @InjectView(R.id.pay_ali_check)
    CheckBox payAliCheck;
    @InjectView(R.id.pay_wechat_check)
    CheckBox payWechatCheck;
    @InjectView(R.id.btn_play)
    Button btnPlay;
    @InjectView(R.id.tv_payment_title)
    TextView tvPaymentTitle;
    @InjectView(R.id.tv_payment_price)
    TextView tvPaymentPrice;
    @InjectView(R.id.tv_cloud_num)
    TextView tvCloudNum;
    @InjectView(R.id.tv_deduction_num)
    TextView tvDeductionNum;
    @InjectView(R.id.cb_deduction_check)
    CheckBox cbDeductionCheck;
    boolean DeductionType = true;
    @InjectView(R.id.tv_pay_cloud)
    TextView tvPayCloud;
    @InjectView(R.id.tv_pay_money)
    TextView tvPayMoney;

    private AliPay aliPay;
    private WeChatBean weChat;
    public PayPresenter payPresenter;
    ExecutorService signleThreadService;

    private String pay_type = "alipay";
    String buy_type = "live";
    String chapter_name;
    double live_price;
    int room_id = 0;
    int chapter_id = 0;
    CommitOrderBean orderBean = new CommitOrderBean();

    LoadingDialog progressHUD;
    LiveBuyData liveBuyData;
    double Price = 0.01;
    double cloudNum;
    double cloudDeduction = 0.00;
    String is_check = "no";
    String liveType;
    LivePayData livePayData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_coure_pay);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(LivePayActivity.this, "支付");
        liveBuyData = new LiveBuyData(this);
        liveBuyData.getCouldData(0);
        init();
    }

    @Override
    public void init() {
        progressHUD = LoadingDialog.getInstance(this);
        signleThreadService = Executors.newSingleThreadExecutor();
        payPresenter = new PayPresenter(this, LivePayActivity.this);
        livePayData = new LivePayData(this, this);
        //获取订单信息
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        liveType = intent.getStringExtra("liveType");

        chapter_name = bundle.getString("chapter_name");
        live_price = bundle.getDouble("live_price");
        buy_type = bundle.getString("buy_type");
        room_id = bundle.getInt("room_id");
        chapter_id = bundle.getInt("chapter_id");

        tvPaymentTitle.setText("章节名称：" + chapter_name);
        tvPaymentPrice.setText("￥" + live_price);

        payAliCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    payAliCheck.setChecked(true);
                    payWechatCheck.setChecked(false);
                }
            }
        });
        payWechatCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    payAliCheck.setChecked(false);
                    payWechatCheck.setChecked(true);
                }
            }
        });
        cbDeductionCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbDeductionCheck.setChecked(true);
                }
                if (cbDeductionCheck.isChecked()) {
                    is_check = "yes";
                    if (cloudNum > 0) {
                        if (cloudNum >= live_price) {
                            tvDeductionNum.setText(live_price + "");
                            tvPayCloud.setText(live_price + "");
                            tvPayMoney.setText("￥" + "0.00");
                            Price = 0.00;
                            cloudDeduction = live_price;
                        } else {
                            double price = (live_price - cloudNum);
                            tvDeductionNum.setText(cloudNum + "");
                            tvPayCloud.setText(cloudNum + "");
                            tvPayMoney.setText("￥" + price);
                            Price = price;
                            cloudDeduction = cloudNum;
                        }
                    } else {
                        tvDeductionNum.setText("0.00");
                        tvPayCloud.setText("0.00");
                        tvPayMoney.setText("￥" + live_price);
                        Price = live_price;
                        cloudDeduction = 0.00;
                    }
                } else {
                    is_check = "no";
                    tvPayMoney.setText("￥" + live_price);
                    tvDeductionNum.setText("0.00");
                    tvPayCloud.setText("0.00");
                    Price = live_price;
                    cloudDeduction = 0.00;
                }
            }
        });
    }

    @OnClick({R.id.btn_play})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                if (Price == 0.00) {
                    MyDialog.getPayCloud(this,cloudDeduction , new MyDialog.IPayCloud() {
                        @Override
                        public void getPayCloud() {
                            liveBuyData.getPayCould(room_id, chapter_id, buy_type);
                        }
                    });
                } else {
                    livePayData.getPayOther(room_id, chapter_id, buy_type, is_check);
                }
                break;
        }
    }

    //获取支付宝支付的必要信息
    @Override
    public void getAliPayPermissions(AliPay aliPay) {
        this.aliPay = aliPay;
    }

    //获取微信支付信息成功  调用微信支付
    @Override
    public void wxPay(WeChatBean weChat) {
        progressHUD.dismiss();
        this.weChat = weChat;

        IWXAPI mWxApi = WXAPIFactory.createWXAPI(this, weChat.getAppid(), true);
        PayReq request = new PayReq();
        request.appId = weChat.getAppid();
        request.partnerId = weChat.getPartnerid();
        request.prepayId = weChat.getPrepayid();
        request.packageValue = "Sign=WXPay";
        request.nonceStr = weChat.getNoncestr();
        request.timeStamp = weChat.getTimestamp();
        request.sign = weChat.getSign();
        request.extData = "app data";
        mWxApi.registerApp(weChat.getAppid());
        mWxApi.sendReq(request);
    }


    //支付失败
    @Override
    public void showFailure(String error_code, String error_msg) {
        progressHUD.dismiss();
        switch (error_code) {
            case "500":
                UIUtil.ToastshowShort(LivePayActivity.this, "连接服务器超时！");
                break;
            case "600":
                UIUtil.ToastshowShort(LivePayActivity.this, "您未安装微信,请选择其他支付方式！");
                break;
        }
    }


    @Override
    public void getCloud(Double aDouble, int position) {
        cloudNum = StringUtils.getDouble(aDouble);
        tvCloudNum.setText(StringUtils.getDouble(aDouble) + "");
    }

    @Override
    public void getPayCloud() {
        UIUtil.ToastshowShort(this, "云币支付成功");
        if (liveType != null && liveType.equals("liveBeing")) {
            Config.liveType = true;
        }
        finish();
    }

    @Override
    public void onFailure(String code, String msg) {
        UIUtil.ToastshowShort(this, msg);
    }

    /*--------无用接口--------*/
    //支付成功
    @Override
    public void showSuccess() {
    }

    @Override
    public void sendPayResult() {
    }

    @Override
    public void SuccessRemainTime(int remainTime) {
    }

    @Override
    public void cancelOrderSuccess() {
    }

    @Override
    public void FailureRemainTime() {
    }

    @Override
    public void cancelOrderFailure(String error_code, String error_msg) {
    }
    /*----------------*/

    @Override
    public void getPayOther(CommitOrderBean commitOrderBean) {
        Config.order_number = commitOrderBean.getOrder_number();

        if (payAliCheck.isChecked()) {
            Map<String, Object> map = new HashMap<String, Object>();
            pay_type = "alipay";
//            payPresenter.AliPay(map, "alipay", orderBean,"live");
        } else if (payWechatCheck.isChecked()) {
            progressHUD.show();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("access_token", Config.ACCESS_TOKEN);
            map.put("uid", Config.UID);
            map.put("order_number", Config.order_number);
            map.put("pay_method", "wxpay");
            map.put("pay_source", "android");
            pay_type = "wxpay";
            payPresenter.AliPay(map, "wechat", orderBean, "live");
            Config.WxCallBackType = "live";
        } else {
            UIUtil.ToastshowShort(getApplicationContext(), "请选择支付方式");
        }
    }

    @Override
    public void onPayOtherFailure(String msg) {
        UIUtil.ToastshowShort(this, "订单生成失败！");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        livePayData.cancelSubscription();
    }

}
