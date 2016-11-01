package com.example.kk.arttraining.pay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.pay.bean.AliPay;
import com.example.kk.arttraining.pay.bean.WeChat;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.valuation.bean.CommitOrderBean;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;
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
    private AliPay aliPay;
    private WeChat weChat;
    private PayPresenter payPresenter;
    ExecutorService signleThreadService;
    private CommitOrderBean orderBean;


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
                    Map<String, String> map = new HashMap<String, String>();
                    payPresenter.AliPay(map, "alipay", orderBean);
                } else if (payWechatCheck.isChecked()) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("ss", "sss");
                    payPresenter.AliPay(map, "wechat", orderBean);
                }
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
        }

    }

    //支付成功
    @Override
    public void showSuccess() {

    }



}
