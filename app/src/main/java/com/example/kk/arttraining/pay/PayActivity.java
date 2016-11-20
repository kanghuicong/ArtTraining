package com.example.kk.arttraining.pay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.pay.bean.AliPay;
import com.example.kk.arttraining.pay.bean.WeChat;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.sqlite.bean.UploadBean;
import com.example.kk.arttraining.sqlite.dao.UploadDao;
import com.example.kk.arttraining.ui.valuation.bean.CommitOrderBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;
import com.example.kk.arttraining.utils.upload.service.UploadQiNiuService;
import com.example.kk.arttraining.utils.upload.view.UploadDialog;

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
    private AliPay aliPay;
    private WeChat weChat;
    private PayPresenter payPresenter;
    ExecutorService signleThreadService;
    private CommitOrderBean orderBean;
    private UploadDialog uploadDialog;

    private String order_num;
    private String order_title;

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
        signleThreadService = Executors.newSingleThreadExecutor();
        payPresenter = new PayPresenter(this, PayActivity.this);
        //获取订单信息
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        orderBean = (CommitOrderBean) bundle.getSerializable("order_bean");
        tvPaymentTitle.setText("作品名称：" + orderBean.getOrder_title());
        tvPaymentOrder.setText("订单号：" + orderBean.getOrder_number());
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
//                if (payAliCheck.isChecked()) {
//                    Map<String, String> map = new HashMap<String, String>();
//                    payPresenter.AliPay(map, "alipay", orderBean);
//                } else if (payWechatCheck.isChecked()) {
//                    Map<String, String> map = new HashMap<String, String>();
//                    map.put("ss", "sss");
//                    payPresenter.AliPay(map, "wechat", orderBean);
//                }
                showSuccess();
                break;
        }
    }

    //获取支付宝支付的必要信息
    @Override
    public void getAliPayPermissions(AliPay aliPay) {
        this.aliPay = aliPay;
    }

    //从服务器获取微信支付所需的数据
    @Override
    public void getWeChatPayPermissions(WeChat weChat) {
        this.weChat = weChat;
    }

    //将支付结果提交到服务器
    @Override
    public void sendPayResult() {

    }

    //支付失败
    @Override
    public void showFailure(String error_code) {

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
        UploadDao uploadDao = new UploadDao(PayActivity.this);
        UploadBean uploadBean = new UploadBean();
        uploadBean.setOrder_pic("");
        uploadBean.setFile_path(orderBean.getFile_path());
        uploadBean.setProgress(0);
        uploadBean.setOrder_title(orderBean.getOrder_title());
        uploadBean.setCreate_time(orderBean.getCreate_time());
        uploadBean.setOrder_id(orderBean.getOrder_number());
        UIUtil.showLog("payActivity-->", "true");
        uploadDao.insert(uploadBean);
        startUpload();
    }


    //开始传
    void startUpload() {
        UIUtil.showLog("payactivity-->", "startUpload");
        Intent intent = new Intent(PayActivity.this, UploadQiNiuService.class);
        intent.setAction(UploadQiNiuService.ACTION_START);
        intent.putExtra("file_path", orderBean.getFile_path());
        intent.putExtra("token", Config.QINIUYUN_TOKEN);
        intent.putExtra("order_id", orderBean.getOrder_number());
        startService(intent);

        uploadDialog = new UploadDialog(this, R.layout.dialog_upload, R.style.Dialog, new UploadDialog.UploadListener() {
            @Override
            public void onClick(View view) {
                uploadDialog.dismiss();
            }
        });
        uploadDialog.show();
    }


}
