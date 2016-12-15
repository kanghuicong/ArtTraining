package com.example.kk.arttraining.ui.me.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.OrderBean;
import com.example.kk.arttraining.bean.UpdateBean;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.custom.dialog.PopWindowDialogUtil;
import com.example.kk.arttraining.custom.view.EmptyGridView;
import com.example.kk.arttraining.pay.PayActivity;
import com.example.kk.arttraining.prot.GeneralResultListener;
import com.example.kk.arttraining.sqlite.bean.UploadBean;
import com.example.kk.arttraining.sqlite.dao.UploadDao;
import com.example.kk.arttraining.ui.me.bean.OrderTecBean;
import com.example.kk.arttraining.ui.me.presenter.OrderPresenter;
import com.example.kk.arttraining.ui.homePage.activity.DynamicContent;
import com.example.kk.arttraining.ui.me.view.IOrderChoseProduction;
import com.example.kk.arttraining.ui.valuation.bean.AudioInfoBean;
import com.example.kk.arttraining.ui.valuation.bean.CommitOrderBean;
import com.example.kk.arttraining.ui.valuation.chooseimage.ProductionImgFileList;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.GlideRoundTransform;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 作者：wschenyongyin on 2016/10/23 13:47
 * 说明:
 */
public class OrderAdapter extends BaseAdapter implements GeneralResultListener {

    TecHeaderAdapter tecHeaderAdapter;
    private List<OrderBean> list;
    private OrderBean orderBean;
    private Context context;
    private int count;
    ViewHolder holder;
    Map<Integer, Integer> map;
    private UploadDao uploadDao;
    IOrderChoseProduction iOrderChoseProduction;

    Map<Integer, String> timerMap;
    //加载dialog
    LoadingDialog loadingDialog;
    //取消订单处理类
    OrderPresenter presenter;
    //position标记
    private int positionTag;

    private List<OrderTecBean> orderTecBeenList;

    public OrderAdapter(Context context, int count) {
        this.count = count;
        this.context = context;
    }

    public OrderAdapter(Context context, List<OrderBean> list, IOrderChoseProduction iOrderChoseProduction) {
        this.list = list;
        this.context = context;
        count = list.size();
        uploadDao = new UploadDao(context);
        this.iOrderChoseProduction = iOrderChoseProduction;
        presenter = new OrderPresenter(this);
        loadingDialog = LoadingDialog.getInstance(context);
        putMap();
    }

    public void putMap() {
        map = new HashMap<Integer, Integer>();
        timerMap = new HashMap<Integer, String>();
        for (int i = 0; i < count; i++) {
            int status = list.get(i).getOrder_status();
            map.put(i, status);
            if (status == 0) {
//                timerMap.put(i, list.get(i).getRemaining_time());
                timerMap.put(i, "25:60");
            }
        }
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        orderBean = list.get(position);
        orderTecBeenList = list.get(position).getAss_tec_list();
//        UIUtil.showLog("我的订单------》",orderBean.toString());
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_me_order, null);
            holder.orderId = (TextView) convertView.findViewById(R.id.item_tv_orderId);
            holder.orderNum = (TextView) convertView.findViewById(R.id.item_tv_orderNum);
            holder.orderTitle = (TextView) convertView.findViewById(R.id.item_tv_orderTitle);
            holder.item_tv_right_title = (TextView) convertView.findViewById(R.id.item_tv_right_title);
            holder.orderPrice = (TextView) convertView.findViewById(R.id.item_tv_orderPrice);
            holder.btnOrder = (TextView) convertView.findViewById(R.id.item_btn_order);
            holder.order_ll = (LinearLayout) convertView.findViewById(R.id.order_ll);
            holder.order_pic = (ImageView) convertView.findViewById(R.id.order_pic);
            holder.item_btn_colse_order = (TextView) convertView.findViewById(R.id.item_btn_colse_order);
            holder.emptyGridView = (EmptyGridView) convertView.findViewById(R.id.me_order_gridview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final int status = map.get(position);
        if (status == 1) {

        } else if (status == 0) {

        } else if (status == 4) {
            holder.btnOrder.setBackgroundResource(R.mipmap.icon_pay_success);
        }

        final int isUploading = uploadDao.isUploading(orderBean.getOrder_number());
        switch (status) {
            //待支付
            case 0:
                holder.btnOrder.setBackgroundResource(R.mipmap.order_red_bg);
                holder.btnOrder.setText("立即支付");
                holder.item_tv_right_title.setText("等待付款");
                holder.item_btn_colse_order.setVisibility(View.VISIBLE);
                holder.item_btn_colse_order.setText("关闭交易");
                break;
            //已支付
            case 1:
                holder.btnOrder.setBackgroundResource(R.mipmap.order_red_gred);
                holder.btnOrder.setText("支付成功");
                holder.item_tv_right_title.setText("支付成功");
                holder.item_btn_colse_order.setVisibility(View.GONE);
                break;
            //交易取消
            case 2:
//                holder.btnOrder.setBackgroundResource(R.mipmap.order_red_gred);
////                holder.btnOrder.setText("查看详情");
                holder.item_tv_right_title.setText("交易关闭");
                holder.btnOrder.setVisibility(View.GONE);
                holder.item_btn_colse_order.setVisibility(View.GONE);
                break;
            //作品待上传
            case 3:
                holder.item_tv_right_title.setText("支付成功");
                if (isUploading == 1) {
                    holder.btnOrder.setText("正在上传");
                    holder.btnOrder.setBackgroundResource(R.mipmap.order_blue_bg);
                } else {
                    holder.btnOrder.setText("上传作品");
                    holder.btnOrder.setBackgroundResource(R.mipmap.order_red_bg);
                }
                holder.item_btn_colse_order.setVisibility(View.GONE);
                break;
            //待测评
            case 4:
                holder.btnOrder.setBackgroundResource(R.mipmap.order_blue_bg);
                holder.btnOrder.setText("查看详情");
                holder.item_tv_right_title.setText("待测评");
                holder.item_btn_colse_order.setVisibility(View.GONE);
                break;
            //已测评
            case 5:
                holder.btnOrder.setBackgroundResource(R.mipmap.order_blue_bg);
                holder.item_tv_right_title.setText("测评完成");
                holder.btnOrder.setText("查看详情");
                holder.item_btn_colse_order.setVisibility(View.GONE);
                break;
        }

        if (orderBean.getWork_pic() != null && !orderBean.getWork_pic().equals("")) {
            Glide.with(context).load(orderBean.getWork_pic()).error(R.mipmap.bg_page_03).transform(new GlideRoundTransform(context)).into(holder.order_pic);
        }
        holder.orderId.setText(orderBean.getOrder_number() + "");
        holder.orderTitle.setText(orderBean.getWork_title() + "");
        holder.orderPrice.setText(orderBean.getOrder_total_price() + "");
        holder.orderNum.setText(orderBean.getOrder_element_num() + "");
        if (orderTecBeenList != null && orderTecBeenList.size() != 0) {
            tecHeaderAdapter = new TecHeaderAdapter(context, orderTecBeenList);
            holder.emptyGridView.setAdapter(tecHeaderAdapter);
        }
        holder.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderBean = list.get(position);
                if (status == 0) {
                    Intent intent = new Intent(context, PayActivity.class);
                    Bundle bundle = new Bundle();
                    UploadDao uploadDao = new UploadDao(context);
                    UploadBean uploadBean = uploadDao.queryOrder(orderBean.getOrder_number());

                    CommitOrderBean commitOrderBean = new CommitOrderBean();
                    commitOrderBean.setOrder_price(orderBean.getOrder_total_price() + "");
                    commitOrderBean.setOrder_title(orderBean.getWork_title());
                    commitOrderBean.setOrder_number(orderBean.getOrder_number());
                    commitOrderBean.setCreate_time(orderBean.getOrder_time());
                    commitOrderBean.setOrder_id(orderBean.getOrder_id());
                    AudioInfoBean audioInfoBean = new AudioInfoBean();
                    if (uploadBean != null) {
                        commitOrderBean.setFile_path(uploadBean.getFile_path());
                        audioInfoBean.setAudio_path(uploadBean.getFile_path());
                        audioInfoBean.setAudio_length(uploadBean.getAtt_length());
                        audioInfoBean.setMedia_type(uploadBean.getAtt_type());
                    }

                    bundle.putSerializable("order_bean", commitOrderBean);
                    bundle.putSerializable("att_bean", audioInfoBean);
                    bundle.putInt("remaining_time", orderBean.getRemaining_time());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } else if (status == 3 && isUploading == 0) {
                    iOrderChoseProduction.choseProduction(orderBean);
                } else {
UIUtil.showLog("status_id---->",orderBean.getWork_id()+"");
                    Intent intent = new Intent(context, DynamicContent.class);
                    intent.putExtra("status_id", orderBean.getWork_id()+"");
                    intent.putExtra("stus_type", "work");
                    intent.putExtra("type", "valuationContent");

                    context.startActivity(intent);
                }
            }
        });

        //关闭订单
        holder.item_btn_colse_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionTag = position;
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("order_id", orderBean.getOrder_id());
                map.put("order_number", orderBean.getOrder_number());
                map.put("access_token", Config.ACCESS_TOKEN);
                map.put("uid", Config.UID);
                presenter.cancelOrder(map);
                loadingDialog.show();

                final int status = (int) map.get(position);
                if (status == 0 || status == 2) {
                    Intent intent = new Intent(context, PayActivity.class);
                    Bundle bundle = new Bundle();

                    CommitOrderBean commitOrderBean = new CommitOrderBean();
                    commitOrderBean.setOrder_price(orderBean.getOrder_total_price() + "");
                    commitOrderBean.setOrder_title(orderBean.getWork_title());
                    commitOrderBean.setOrder_number(orderBean.getOrder_number());
                    commitOrderBean.setCreate_time(orderBean.getOrder_time());
                    UploadDao uploadDao = new UploadDao(context);
                    UploadBean uploadBean = uploadDao.queryOrder(orderBean.getOrder_number());
                    AudioInfoBean audioInfoBean = new AudioInfoBean();
                    try {

                        commitOrderBean.setFile_path(uploadBean.getFile_path());

                        audioInfoBean.setAudio_path(uploadBean.getFile_path());
                        audioInfoBean.setAudio_length(uploadBean.getAtt_length());
                        audioInfoBean.setMedia_type(uploadBean.getAtt_type());
                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                    bundle.putSerializable("order_bean", commitOrderBean);
                    bundle.putSerializable("att_bean", audioInfoBean);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } else {
                    orderBean = list.get(position);
                    Intent intent = new Intent(context, DynamicContent.class);
                    intent.putExtra("status_id", orderBean.getWork_id());
                    intent.putExtra("stus_type", "work");
                    intent.putExtra("type", "valuationContent");
                    context.startActivity(intent);
                }


            }
        });

        return convertView;
    }

    public int getSelfId() {
        return list.get(count - 1).getOrder_id();
    }

    public void refreshCount(int count) {
        this.count = count;
        putMap();
    }

    //关闭订单成功
    @Override
    public void GeneralSuccess() {
        loadingDialog.dismiss();
        //更新订单状态
        map.put(positionTag, 2);
        //更新adapter
        notifyDataSetChanged();

    }

    //关闭订单失败
    @Override
    public void GeneralFailure(String error_code, String error_msg) {
        loadingDialog.dismiss();
        UIUtil.ToastshowShort(context, error_msg);
    }


    class ViewHolder {
        TextView orderId;
        TextView orderTitle;
        TextView orderNum;
        TextView orderPrice;
        TextView btnOrder;
        LinearLayout order_ll;
        ImageView order_pic;
        TextView item_tv_right_title;
        //关闭交易按钮
        TextView item_btn_colse_order;
        EmptyGridView emptyGridView;
    }


}
