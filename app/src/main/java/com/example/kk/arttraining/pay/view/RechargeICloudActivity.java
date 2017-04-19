package com.example.kk.arttraining.pay.view;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.custom.dialog.MyDialog;
import com.example.kk.arttraining.custom.view.GlideCircleTransform;
import com.example.kk.arttraining.custom.view.MyGridView;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.pay.RechargeListAdapter;
import com.example.kk.arttraining.pay.bean.RechargeBean;
import com.example.kk.arttraining.pay.bean.WeChatBean;
import com.example.kk.arttraining.pay.presenter.RechargePresenter;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.prot.rxjava_retrofit.ServerException;
import com.example.kk.arttraining.ui.me.adapter.RechargeHelpAdapter;
import com.example.kk.arttraining.ui.me.bean.CouponBean;
import com.example.kk.arttraining.ui.me.bean.RechargeHelpBean;
import com.example.kk.arttraining.ui.me.presenter.RechargeHelpData;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.KeyBoardUtils;
import com.example.kk.arttraining.utils.StringUtils;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：wschenyongyin on 2017/3/2 09:07
 * 说明:云币充值
 */
public class RechargeICloudActivity extends BaseActivity implements IRechargeICloudView, AdapterView.OnItemClickListener, RechargeHelpData.IRechargeHelp {

    @InjectView(R.id.gv_recharge_cloud)
    MyGridView gvRechargeCloud;
    @InjectView(R.id.tv_wechat)
    TextView tvWechat;
    @InjectView(R.id.cb_pay_wechat)
    CheckBox cbPayWechat;
    @InjectView(R.id.btn_recharge)
    Button btnRecharge;
    @InjectView(R.id.cb_pay_ali)
    CheckBox cbPayAli;
    @InjectView(R.id.ed_recharge_phone)
    EditText edRechargePhone;
    @InjectView(R.id.tv_recharge_search)
    TextView tvRechargeSearch;
    @InjectView(R.id.lv_recharge)
    MyListView lvRecharge;
    //处理类
    private RechargePresenter rechargePresenter;
    //优惠券
    private CouponBean couponBean;
    //设配器
    private RechargeListAdapter rechargeListAdapter;
    //
    private RechargeBean rechargeBean;
    //充值列表数据
    private List<RechargeBean> rechargeDataList;
    //加载dialog
    LoadingDialog loadingDialog;

    String name = "";
    String phone = "";
    String pic = "";
    String sex = "";
    int rechargeid;
    RechargeHelpData rechargeHelpData;
    RechargeHelpAdapter helpAdapter;
    List<RechargeHelpBean> helpBeanList = new ArrayList<RechargeHelpBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_cloud);
        ButterKnife.inject(this);
        init();
    }

    //初始化
    @Override
    public void init() {
        TitleBack.TitleBackActivity(this, "充值");

        tvRechargeSearch.setOnClickListener(new clickRechargeSearch());

        rechargePresenter = new RechargePresenter(this);
        rechargeHelpData = new RechargeHelpData(this);
        loadingDialog = LoadingDialog.getInstance(this);
        //获取充值列表
        getRechargeList();
        gvRechargeCloud.setOnItemClickListener(this);
        //选择支付宝支付
        cbPayAli.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbPayAli.setChecked(true);
                    cbPayWechat.setChecked(false);
                }
            }
        });
        //选择微信支付
        cbPayWechat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbPayAli.setChecked(false);
                    cbPayWechat.setChecked(true);
                }
            }
        });

        edRechargePhone.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // 修改回车键功能
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    KeyBoardUtils.closeKeybord(edRechargePhone, RechargeICloudActivity.this);
                    loadingDialog.show();
                    rechargeHelpData.getRechargeHelp(edRechargePhone.getText().toString());
                }
                return false;
            }
        });
    }

    //查询号码
    private class clickRechargeSearch implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            loadingDialog.show();
            rechargeHelpData.getRechargeHelp(edRechargePhone.getText().toString());
        }
    }

    //查询号码成功
    @Override
    public void successRechargeHelp(List<RechargeHelpBean> rechargeHelpBeanList) {
        if (!helpBeanList.isEmpty()){
            helpBeanList.clear();
        }
        helpBeanList.addAll(rechargeHelpBeanList);
        helpAdapter = new RechargeHelpAdapter(this, helpBeanList);
        lvRecharge.setAdapter(helpAdapter);
        lvRecharge.setOnItemClickListener(new clickRechargeItem());

//        if (helpBeanList == null || helpBeanList.size() == 0) {
//            helpBeanList.addAll(rechargeHelpBeanList);
//            helpAdapter = new RechargeHelpAdapter(this, helpBeanList);
//            lvRecharge.setAdapter(helpAdapter);
//            lvRecharge.setOnItemClickListener(new clickRechargeItem());
//        } else {
//            helpBeanList.clear();
//            helpBeanList.addAll(rechargeHelpBeanList);
//            helpAdapter.changeCount(helpBeanList.size());
//            helpAdapter.notifyDataSetChanged();
//        }
        loadingDialog.dismiss();
    }

    //查询号码失败
    @Override
    public void failureRechargeHelp(String error_msg) {
        UIUtil.ToastshowShort(this, error_msg);
        loadingDialog.dismiss();
    }

    //点击立即充值按钮
    @OnClick(R.id.btn_recharge)
    public void onClick(View v) {
        Observable.create(new Observable.OnSubscribe<RechargeBean>() {
            @Override
            public void call(Subscriber<? super RechargeBean> subscriber) {
                if (rechargeBean != null && cbPayWechat.isChecked() ) {
                    if (helpAdapter != null && helpAdapter.getChargeId()!=-1) {
                        subscriber.onNext(rechargeBean);
                    }else {
                        Throwable throwable = new ServerException("20024", "请选择充值账号");
                        subscriber.onError(throwable);
                    }
                } else if (rechargeBean == null) {
                    Throwable throwable = new ServerException("20021", "请选择充值金额");
                    subscriber.onError(throwable);
                } else if (!cbPayWechat.isChecked() && cbPayAli.isChecked()) {
                    Throwable throwable = new ServerException("20022", "功能开发中，敬请期待！");
                    subscriber.onError(throwable);
                } else if (!cbPayWechat.isChecked() && !cbPayAli.isChecked()) {
                    Throwable throwable = new ServerException("20023", "请选择支付方式");
                    subscriber.onError(throwable);
                }
            }
        }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<RechargeBean>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        UIUtil.ToastshowShort(getApplicationContext(), e.getMessage());
                    }
                    @Override
                    public void onNext(RechargeBean rechargeBean) {
                        //执行微信充值方法
                        MyDialog.getRechargeCloud(RechargeICloudActivity.this, helpAdapter.getCheckItem(), new MyDialog.IRecharge() {
                            @Override
                            public void getRecharge() {
                                wxRecharge(rechargeBean, helpAdapter.getChargeId(), couponBean);
                            }
                        });
                    }
                });
    }


    //选择充值的金额
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        rechargeBean = (RechargeBean) parent.getItemAtPosition(position);
        for (int i = 0; i < rechargeDataList.size(); i++) {
            if (position == i) {//当前选中的Item改变背景颜色
                rechargeDataList.get(i).setSelect(true);
            } else {
                rechargeDataList.get(i).setSelect(false);
            }
        }
        //刷新adapter
        rechargeListAdapter.notifyDataSetChanged();
    }


    //获取充值列表
    @Override
    public void getRechargeList() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        rechargePresenter.getRechargeList(map);
    }

    //获取冲值列表成功
    @Override
    public void SuccessRechargeList(List<RechargeBean> rechargeBeanList) {
        rechargeDataList = rechargeBeanList;
        rechargeListAdapter = new RechargeListAdapter(this, rechargeDataList);
        gvRechargeCloud.setAdapter(rechargeListAdapter);
    }

    //获取充值列表失败
    @Override
    public void FailureRechargeList(String error_code, String error_msg) {
        // TODO: 2017/3/7
    }

    //使用微信支付充值云币
    @Override
    public void wxRecharge(RechargeBean rechargeBean, int rechargeid, CouponBean couponBean) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("charge_uid", rechargeid);
        map.put("transform_id", rechargeBean.getTranform_id());
        map.put("total_pay", rechargeBean.getMoney());
        map.put("cloud_money", rechargeBean.getCloud_money());
        Config.WxCallBackType = "recharge";
        loadingDialog.show();
        //判断是否使用优惠券
        if (couponBean != null) {
            map.put("coupon_pay", StringUtils.toDouble(couponBean.getFace_value()));
            map.put("coupon_id", couponBean.getCoupon_id());
            map.put("coupon_type", couponBean.getCoupon_type());
            map.put("final_pay", rechargeBean.getMoney() - StringUtils.toDouble(couponBean.getFace_value()));
        } else {
            map.put("final_pay", rechargeBean.getMoney());
        }
        rechargePresenter.createRechargeOrder(map);
    }

    //调用微信支付
    @Override
    public void wxPay(WeChatBean weChatBean) {
        loadingDialog.dismiss();
        IWXAPI mWxApi = WXAPIFactory.createWXAPI(this, weChatBean.getAppid(), true);
        PayReq request = new PayReq();
        request.appId = weChatBean.getAppid();
        request.partnerId = weChatBean.getPartnerid();
        request.prepayId = weChatBean.getPrepayid();
        request.packageValue = "Sign=WXPay";
        request.nonceStr = weChatBean.getNoncestr();
        request.timeStamp = weChatBean.getTimestamp();
        request.sign = weChatBean.getSign();
        request.extData = "app data";
        mWxApi.registerApp(weChatBean.getAppid());
        mWxApi.sendReq(request);
    }

    //获取微信支付失败
    @Override
    public void FailureRecharge(String error_code, String error_msg) {
        loadingDialog.dismiss();
    }


    private class clickRechargeItem implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
}
