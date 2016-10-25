package com.example.kk.arttraining.ui.me.view;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.OrderBean;
import com.example.kk.arttraining.bean.parsebean.ParseOrderListBean;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.me.presenter.OrderPresenter;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/10/23 11:43
 * 说明:订单信息
 */
public class OrderActivity extends BaseActivity implements IOrderView {
    @InjectView(R.id.iv_title_back)
    ImageView btn_bcak;
    @InjectView(R.id.rb_order_alreadyPay)
    RadioButton rb_order_alreadyPay;
    @InjectView(R.id.rb_order_all)
    RadioButton rb_order_all;
    @InjectView(R.id.rb_order_noPay)
    RadioButton rb_order_noPay;

    //    @InjectView(R.id.lv_order)
//    ListView lv_order;
    //声明三个fragment；
    private OrderFragment OrderAllFragment;
    private OrderFragment OrderNoPayFragment;
    private OrderFragment OrderPayFragment;
    //数据
    private ParseOrderListBean allOrderList;
    private ParseOrderListBean unPayOrderList;
    private ParseOrderListBean payOrderList;
    private Dialog dialog;

    private OrderPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_order);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {
        dialog = DialogUtils.createLoadingDialog(OrderActivity.this, "正在加载...");
        TitleBack.TitleBackActivity(OrderActivity.this, "我的订单");
        presenter = new OrderPresenter(OrderActivity.this, this);
        presenter.getAllOrderData();
        DefaultFragment();

    }

    @OnClick({R.id.iv_title_back, R.id.rb_order_alreadyPay, R.id.rb_order_all, R.id.rb_order_noPay})
    public void onClick(View v) {
        switch (v.getId()) {
            //返回按钮
            case R.id.iv_title_back:
                finish();
                break;
            //已付款按钮
            case R.id.rb_order_alreadyPay:
                //请求数据
                presenter.getAlreadyPayOrderData();
                FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                if (OrderPayFragment == null) {
                    OrderPayFragment = new OrderFragment(payOrderList, 3);
                    transaction1.add(R.id.fl_order, OrderPayFragment);
                }
                ;
                hideFragment(transaction1);
                transaction1.show(OrderPayFragment);
                transaction1.commit();
                rb_order_alreadyPay.setSelected(true);
                rb_order_noPay.setSelected(false);
                rb_order_all.setSelected(false);
                UIUtil.showLog("执行了", "------->");
                break;
            //全部订单按钮
            case R.id.rb_order_all:
                //请求数据
                presenter.getAllOrderData();
                FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                if (OrderAllFragment == null) {
                    OrderAllFragment = new OrderFragment(allOrderList, 4);
                    transaction2.add(R.id.fl_order, OrderAllFragment);
                }
                ;
                hideFragment(transaction2);
                transaction2.show(OrderAllFragment);
                transaction2.commit();
                rb_order_alreadyPay.setSelected(false);
                rb_order_noPay.setSelected(false);
                rb_order_all.setSelected(true);
                break;
            //未付款按钮
            case R.id.rb_order_noPay:
                presenter.getUnPayOrderData();
                FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
                if (OrderNoPayFragment == null) {
                    OrderNoPayFragment = new OrderFragment(unPayOrderList, 5);
                    transaction3.add(R.id.fl_order, OrderNoPayFragment);
                }
                ;
                hideFragment(transaction3);
                transaction3.show(OrderNoPayFragment);
                transaction3.commit();
                rb_order_alreadyPay.setSelected(false);
                rb_order_noPay.setSelected(true);
                rb_order_all.setSelected(false);
                break;
        }

    }

    //隐藏所有的fragment
    private void hideFragment(FragmentTransaction transaction) {
        if (OrderAllFragment != null) {
            transaction.hide(OrderAllFragment);
        }
        if (OrderNoPayFragment != null) {
            transaction.hide(OrderNoPayFragment);
        }
        if (OrderPayFragment != null) {
            transaction.hide(OrderPayFragment);
        }
    }


    //获取全部订单信息
    @Override
    public void getAllOrder(ParseOrderListBean allOrderList) {
        this.allOrderList = allOrderList;
    }

    //获取未付款订单
    @Override
    public void unPayOrder(ParseOrderListBean unPayOrderList) {
        this.unPayOrderList = unPayOrderList;
    }

    //获取已付款订单
    @Override
    public void AlreadyPaid(ParseOrderListBean payOrderList) {
        this.payOrderList = payOrderList;
    }

    @Override
    public void ToPayActivity() {

    }

    @Override
    public void toOrderDetail(OrderBean orderInfoBean) {

    }

    //显示加载的dialog
    @Override
    public void showLoging() {
        dialog.show();
    }

    //隐藏加载的dialog
    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    //显示错误信息
    @Override
    public void showFailedError(String error_code, String errorMsg) {

    }

    //设置默认的显示页面
    private void DefaultFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        OrderAllFragment = new OrderFragment(payOrderList, 6);
        transaction.add(R.id.fl_order, OrderAllFragment);

        hideFragment(transaction);
        transaction.show(OrderAllFragment);
        transaction.commit();
        rb_order_alreadyPay.setSelected(false);
        rb_order_noPay.setSelected(false);
        rb_order_all.setSelected(true);
    }


}
