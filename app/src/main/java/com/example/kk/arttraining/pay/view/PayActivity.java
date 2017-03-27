package com.example.kk.arttraining.pay.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.custom.dialog.MyDialog;
import com.example.kk.arttraining.media.recodevideo.PlayActivity;
import com.example.kk.arttraining.pay.bean.AliPay;
import com.example.kk.arttraining.pay.bean.WeChatBean;
import com.example.kk.arttraining.pay.presenter.PayPresenter;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.sqlite.bean.UploadBean;
import com.example.kk.arttraining.sqlite.dao.UploadDao;
import com.example.kk.arttraining.ui.live.presenter.LiveBuyData;
import com.example.kk.arttraining.ui.live.presenter.LivePayData;
import com.example.kk.arttraining.ui.live.presenter.LiveUpdateData;
import com.example.kk.arttraining.ui.live.view.ILiveBuy;
import com.example.kk.arttraining.ui.valuation.bean.AudioInfoBean;
import com.example.kk.arttraining.ui.valuation.bean.CommitOrderBean;
import com.example.kk.arttraining.ui.valuation.presenter.PayValuationData;
import com.example.kk.arttraining.ui.valuation.view.ValuationMain;
import com.example.kk.arttraining.utils.ActivityManage;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.StringUtils;
import com.example.kk.arttraining.utils.TimeDelayClick;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;
import com.example.kk.arttraining.utils.upload.view.UploadDialog;
import com.example.kk.arttraining.wxapi.UpdateOrderPaySuccess;
import com.example.kk.arttraining.wxapi.UpdatePayPresenter;
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
 * 作者：wschenyongyin on 2016/10/30 19:30
 * 说明:
 */
public class PayActivity extends BaseActivity implements IPayActivity, ILiveBuy, PayValuationData.IOderNumber {

    @InjectView(R.id.pay_ali_check)
    CheckBox payAliCheck;
    @InjectView(R.id.pay_wechat_check)
    CheckBox payWechatCheck;
    @InjectView(R.id.btn_play)
    Button btnPlay;
    @InjectView(R.id.tv_payment_title)
    TextView tvPaymentTitle;
    @InjectView(R.id.tv_payment_order)
    TextView tvPaymentOrder;
    @InjectView(R.id.tv_payment_price)
    TextView tvPaymentPrice;
    @InjectView(R.id.tv_minute_left)
    TextView tvMinuteLeft;
    @InjectView(R.id.tv_minute_right)
    TextView tvMinuteRight;
    @InjectView(R.id.tv_second_left)
    TextView tvSecondLeft;
    @InjectView(R.id.tv_second_right)
    TextView tvSecondRight;
    @InjectView(R.id.view_f)
    View viewF;
    @InjectView(R.id.tv_pay_price)
    TextView tvPayPrice;
    @InjectView(R.id.tv_cloud_num)
    TextView tvCloudNum;
    @InjectView(R.id.tv_deduction_num)
    TextView tvDeductionNum;
    @InjectView(R.id.cb_deduction_check)
    CheckBox cbDeductionCheck;
    @InjectView(R.id.tv_pay_cloud)
    TextView tvPayCloud;
    @InjectView(R.id.tv_pay_money)
    TextView tvPayMoney;
    private AliPay aliPay;
    private WeChatBean weChat;
    private PayPresenter payPresenter;
    ExecutorService signleThreadService;
    private CommitOrderBean orderBean;
    private UploadDialog uploadDialog;

    private String order_num;
    private String order_title;
    private AudioInfoBean audioInfoBean;
    private String pay_type = "wxpay";
    LoadingDialog progressHUD;
    //订单剩余支付时间
    private int remaining_time = 0;
    //计时器
    private MyCountDownTimer mc;

    double live_price;
    double Price = 0.01;
    double cloudNum;
    double cloudDeduction = 0.00;
    String is_check = "no";
    String liveType;
    LiveBuyData liveBuyData;
    PayValuationData payValuationData;
    String order_number;
    String order_id;
    UpdatePayPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(PayActivity.this, "支付");
        init();
    }

    @Override
    public void init() {
        progressHUD = LoadingDialog.getInstance(this);
        signleThreadService = Executors.newSingleThreadExecutor();
        payPresenter = new PayPresenter(this, PayActivity.this);
        payValuationData = new PayValuationData(this);
        liveBuyData = new LiveBuyData(this);
        liveBuyData.getCouldData(0);
        //获取订单信息
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        orderBean = (CommitOrderBean) bundle.getSerializable("order_bean");
        audioInfoBean = (AudioInfoBean) bundle.getSerializable("att_bean");
        live_price = orderBean.getChapter_price();
        order_number = orderBean.getOrder_number();
        order_id = orderBean.getOrder_id();
        //获取订单剩余时间
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("order_id", orderBean.getOrder_id());
        map.put("order_number", orderBean.getOrder_number());
        payPresenter.getRemainTime(map);

        tvPaymentTitle.setText("作品名称：" + orderBean.getOrder_title());
        tvPaymentOrder.setText("订单号：" + orderBean.getOrder_number());
        tvPaymentPrice.setText("￥" + orderBean.getOrder_price());
        tvPayMoney.setText(orderBean.getOrder_price());

        //保存订单信息到本地数据库
        updateOrderUpload();

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
                            double price = StringUtils.getDouble(live_price - cloudNum);
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
                if (TimeDelayClick.isFastClick(500)) {
                    return;
                } else {
                    if (Price == 0.00) {
                        MyDialog.getPayCloud(this, cloudDeduction, new MyDialog.IPayCloud() {
                            @Override
                            public void getPayCloud() {
                                presenter = new UpdatePayPresenter(new UpdateOrderPaySuccess() {
                                    @Override
                                    public void updateOrder() {
                                    }

                                    @Override
                                    public void Success() {
                                        UIUtil.ToastshowShort(PayActivity.this, "云币支付成功！");
                                        updateOrderUpload();
                                        Intent intent = new Intent(PayActivity.this, PaySuccessActivity.class);
                                        intent.putExtra("file_path", Config.order_att_path);
                                        intent.putExtra("token", Config.QINIUYUN_WORKS_TOKEN);
                                        intent.putExtra("pay_type", "wallet");
                                        intent.putExtra("order_id", Config.order_num);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void Failure(String error_code, String error_msg) {
                                        UIUtil.ToastshowShort(PayActivity.this, error_msg);
                                    }
                                });
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("access_token", Config.ACCESS_TOKEN);
                                map.put("uid", Config.UID);
                                map.put("order_number", order_number);
                                map.put("pay_type", "wallet");
                                map.put("is_pay", "1");
                                presenter.updateOrder(map);
                            }
                        });
                    } else {
                        if (cbDeductionCheck.isChecked() && payWechatCheck.isChecked()) {
                            progressHUD.show();
                            payValuationData.getOrderNumber(is_check, order_id, order_number);
                        } else if (payWechatCheck.isChecked() && !cbDeductionCheck.isChecked()) {
                            progressHUD.show();
                            getPayValuation(orderBean);
                        } else if (!payWechatCheck.isChecked() && !cbDeductionCheck.isChecked()) {
                            UIUtil.ToastshowShort(this, "请选择支付方式！");
                        }
                    }
                }
        }
    }

    public void getPayValuation(CommitOrderBean commitOrderBean) {
        if (payAliCheck.isChecked()) {
            Map<String, Object> map = new HashMap<String, Object>();
            pay_type = "alipay";
            payPresenter.AliPay(map, "alipay", orderBean, "valuation");
        } else if (payWechatCheck.isChecked()) {

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("access_token", Config.ACCESS_TOKEN);
            map.put("uid", Config.UID);
            map.put("order_number", commitOrderBean.getOrder_number());
            map.put("pay_method", "wxpay");
            map.put("pay_source", "android");
            pay_type = "wxpay";
            payPresenter.AliPay(map, "wechat", orderBean, "valuation");
            Config.WxCallBackType = "valuation";
        } else {
            UIUtil.showLog("payAliCheck", "---1");
            progressHUD.dismiss();
            UIUtil.ToastshowShort(getApplicationContext(), "请选择支付方式");
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
        updateOrderUpload();
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


    //将支付结果提交到服务器
    @Override
    public void sendPayResult() {

    }

    //支付失败
    @Override
    public void showFailure(String error_code, String error_msg) {
        progressHUD.dismiss();
        switch (error_code) {
            case "500":
                UIUtil.ToastshowShort(PayActivity.this, "连接服务器超时！");
                break;
            case "600":
                UIUtil.ToastshowShort(PayActivity.this, "您未安装微信,请选择其他支付方式！");
                break;
            case "101":
                UIUtil.ToastshowShort(PayActivity.this, "获取上传作品权限失败");
                break;
        }

    }

    @Override
    public void SuccessRemainTime(int remainTime) {
        //订单倒计时
        if (remainTime != 0) {
            int minute = remainTime / 60;
            int second = remainTime % 60;
            if (minute > 10) {
                tvMinuteLeft.setText((int) (minute / 10) + "");
                tvMinuteRight.setText((int) (minute % 10) + "");
            } else {
                tvMinuteLeft.setText("0");
                tvMinuteRight.setText(minute + "");
            }
            if (second > 10) {
                tvSecondLeft.setText((int) (second / 10) + "");
                tvSecondRight.setText((int) (second % 10) + "");
            } else {
                tvSecondLeft.setText("0");
                tvSecondRight.setText(second + "");
            }
            mc = new MyCountDownTimer((minute * 60 + second) * 1000, 1000);
            mc.start();


        } else {
            FailureRemainTime();
        }
    }

    //支付成功
    @Override
    public void showSuccess() {
        UIUtil.ToastshowShort(this, "1233");
        updateOrderUpload();
        Intent intent = new Intent(this, PaySuccessActivity.class);
        intent.putExtra("file_path", orderBean.getFile_path());
        intent.putExtra("token", Config.QINIUYUN_WORKS_TOKEN);
        intent.putExtra("order_id", orderBean.getOrder_number());
        startActivity(intent);
        finish();
    }

    //取消订单成功
    @Override
    public void cancelOrderSuccess() {

    }

    //获取订单支付时间失败
    @Override
    public void FailureRemainTime() {
        tvMinuteLeft.setText("0");
        tvMinuteRight.setText("0");
        tvSecondLeft.setText("0");
        tvMinuteRight.setText("0");
        btnPlay.setBackgroundColor(getResources().getColor(R.color.grey));
        btnPlay.setEnabled(false);
    }

    //取消订单失败
    @Override
    public void cancelOrderFailure(String error_code, String error_msg) {

    }


    //将订单上传信息信息插入数据库
    void updateOrderUpload() {
        UploadDao uploadDao = new UploadDao(PayActivity.this);
        UploadBean uploadBean = new UploadBean();
        uploadBean.setOrder_pic("");
        uploadBean.setProgress(0);
        uploadBean.setOrder_title(orderBean.getOrder_title());
        uploadBean.setCreate_time(orderBean.getCreate_time());
        uploadBean.setOrder_id(orderBean.getOrder_number());
        uploadBean.setAtt_length(audioInfoBean.getAudio_length() + "");
        uploadBean.setAtt_size(audioInfoBean.getAudio_size() + "");
        uploadBean.setAtt_type(audioInfoBean.getMedia_type());
        uploadBean.setFile_path(orderBean.getFile_path());
        uploadBean.setPay_type(pay_type);
        Config.order_num = orderBean.getOrder_number();
        UIUtil.showLog("payActivity----->", "uploadbean---->" + uploadBean.toString());
        uploadDao.insert(uploadBean);
    }

    @Override
    public void getCloud(Double aDouble, int position) {
        cloudNum = StringUtils.getDouble(aDouble);
        tvCloudNum.setText(StringUtils.getDouble(aDouble) + "");
    }

    @Override
    public void getPayCloud() {
    }

    @Override
    public void onFailure(String code, String msg) {
        UIUtil.ToastshowShort(this, msg);
    }

    @Override
    public void getPayOther(CommitOrderBean commitOrderBean) {
        orderBean.setOrder_number(commitOrderBean.getOrder_number());
        orderBean.setOrder_id(commitOrderBean.getOrder_id());
        getPayValuation(commitOrderBean);
    }

    @Override
    public void onPayOtherFailure(String error_msg) {
        progressHUD.dismiss();
    }


    //订单支付倒计时
    class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            //支付剩余时间到期 设置支付按钮背景为灰色  并且不能点击  同时执行取消订单请求
            tvSecondRight.setText("0");
            btnPlay.setBackgroundColor(getResources().getColor(R.color.grey));
            btnPlay.setEnabled(false);

        }

        @Override
        public void onTick(long millisUntilFinished) {
            UIUtil.showLog("OrderAdapter", millisUntilFinished + "");
            //如果时间大于60秒
            if ((millisUntilFinished / 1000) > 60) {
                int minute = (int) ((millisUntilFinished / 1000) / 60);
                int second = (int) ((millisUntilFinished / 1000) % 60);
                //分钟数大于10
                if (minute > 10) {
                    tvMinuteLeft.setText((int) (minute / 10) + "");
                    tvMinuteRight.setText((int) (minute % 10) + "");
                } else {
                    tvMinuteLeft.setText("0");

                    tvMinuteRight.setText(minute + "");
                }

                //秒数大于10
                if (second > 10) {
                    tvSecondLeft.setText((int) (second / 10) + "");
                    tvSecondRight.setText((int) (second % 10) + "");
                } else {
                    tvSecondLeft.setText("0");
                    tvSecondRight.setText(second + "");
                }
            }
            //时间小于60秒
            else {
                tvMinuteRight.setText("0");
                int second = (int) ((millisUntilFinished / 1000) % 60);
                if (second > 10) {
                    tvSecondLeft.setText((int) (second / 10) + "");
                    tvSecondRight.setText((int) (second % 10) + "");
                } else {
                    tvSecondLeft.setText("0");
                    tvSecondRight.setText(second + "");
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mc != null)
            mc.cancel();
    }
}
