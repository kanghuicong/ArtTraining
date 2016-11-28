package com.example.kk.arttraining.pay;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.pay.bean.AliPay;
import com.example.kk.arttraining.pay.bean.WeChat;
import com.example.kk.arttraining.pay.bean.WeChatBean;
import com.example.kk.arttraining.pay.wxapi.HttpUtils;
import com.example.kk.arttraining.pay.wxapi.Util;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.sqlite.bean.UploadBean;
import com.example.kk.arttraining.sqlite.dao.UploadDao;
import com.example.kk.arttraining.ui.valuation.bean.AudioInfoBean;
import com.example.kk.arttraining.ui.valuation.bean.CommitOrderBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;
import com.example.kk.arttraining.utils.upload.view.IUploadProgressListener;
import com.example.kk.arttraining.utils.upload.view.UploadDialog;
import com.google.gson.Gson;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

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
public class PayActivity extends BaseActivity implements IPayActivity {

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
    private AliPay aliPay;
    private WeChatBean weChat;
    private PayPresenter payPresenter;
    ExecutorService signleThreadService;
    private CommitOrderBean orderBean;
    private UploadDialog uploadDialog;

    private String order_num;
    private String order_title;
    private AudioInfoBean audioInfoBean;
    private String pay_type = "alipay";
    LoadingDialog progressHUD;

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
        //获取订单信息
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        orderBean = (CommitOrderBean) bundle.getSerializable("order_bean");
        audioInfoBean = (AudioInfoBean) bundle.getSerializable("att_bean");
        UIUtil.showLog("PayActivity---->", "audioInfoBean---->" + audioInfoBean.toString());
        UIUtil.showLog("PayActivity---->", "orderBean---->" + orderBean.toString());
        tvPaymentTitle.setText("作品名称：" + orderBean.getOrder_title());
        tvPaymentOrder.setText("订单号：" + orderBean.getOrder_number());
        tvPaymentPrice.setText("￥" + orderBean.getOrder_price());
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

    }

    @OnClick({R.id.btn_play})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                if (payAliCheck.isChecked()) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    pay_type = "alipay";
                    payPresenter.AliPay(map, "alipay", orderBean);
                } else if (payWechatCheck.isChecked()) {
                    progressHUD.show();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("access_token", Config.ACCESS_TOKEN);
                    map.put("uid", Config.UID);
                    map.put("order_number", orderBean.getOrder_number());
                    map.put("pay_method", "wxpay");
                    map.put("pay_source", "android");
                    pay_type = "wxpay";
                    payPresenter.AliPay(map, "wechat", orderBean);
                } else {
                    UIUtil.ToastshowShort(getApplicationContext(), "请选择支付方式");
                }
//                showSuccess();
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

    //支付成功
    @Override
    public void showSuccess() {

        updateOrderUpload();
        Intent intent = new Intent(this, PaySuccessActivity.class);
        intent.putExtra("file_path", orderBean.getFile_path());
        intent.putExtra("token", Config.QINIUYUN_WORKS_TOKEN);
        intent.putExtra("order_id", orderBean.getOrder_number());
        startActivity(intent);
        finish();
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

}
