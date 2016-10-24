package com.example.kk.arttraining.ui.me.view;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.OrderInfoBean;
import com.example.kk.arttraining.prot.BaseActivity;
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
    private List<OrderInfoBean> allOrderList;
    private List<OrderInfoBean> unPayOrderList;
    private List<OrderInfoBean> payOrderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_order);
        ButterKnife.inject(this);
    }

    @Override
    public void init() {

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
                FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                if (OrderPayFragment == null) {
                    OrderPayFragment = new OrderFragment(payOrderList,3);
                    transaction1.add(R.id.fl_order, OrderPayFragment);
                };
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
                FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                if (OrderAllFragment == null) {
                    OrderAllFragment = new OrderFragment(allOrderList,4);
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
                FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
                if (OrderNoPayFragment == null) {
                    OrderNoPayFragment = new OrderFragment(unPayOrderList,5);
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
    public void getAllOrder(List<OrderInfoBean> allOrderList) {
        this.allOrderList = allOrderList;
    }

    //获取未付款订单
    @Override
    public void unPayOrder(List<OrderInfoBean> unPayOrderList) {
        this.unPayOrderList = unPayOrderList;
    }

    //获取已付款订单
    @Override
    public void AlreadyPaid(List<OrderInfoBean> payOrderList) {
        this.payOrderList = payOrderList;
    }

    @Override
    public void ToPayActivity() {

    }

    @Override
    public void toOrderDetail(OrderInfoBean orderInfoBean) {

    }

    @Override
    public void showLoging() {

    }

    @Override
    public void hideLoading() {

    }


}
