package com.example.kk.arttraining.ui.me.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.OrderBean;
import com.example.kk.arttraining.bean.UpdateBean;
import com.example.kk.arttraining.pay.PayActivity;
import com.example.kk.arttraining.sqlite.bean.UploadBean;
import com.example.kk.arttraining.sqlite.dao.UploadDao;
import com.example.kk.arttraining.ui.me.view.ValuationDetailActivity;
import com.example.kk.arttraining.ui.valuation.bean.AudioInfoBean;
import com.example.kk.arttraining.ui.valuation.bean.CommitOrderBean;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：wschenyongyin on 2016/10/23 13:47
 * 说明:
 */
public class OrderAdapter extends BaseAdapter {

    private List<OrderBean> list;
    private OrderBean orderBean;
    private Context context;
    private int count;
    ViewHolder holder;
    Map<Integer, Integer> map;

    public OrderAdapter(Context context, int count) {
        this.count = count;
        this.context = context;
    }

    public OrderAdapter(Context context, List<OrderBean> list) {
        this.list = list;
        this.context = context;
        count = list.size();
        putMap();
    }

    public void putMap() {
        map = new HashMap<Integer, Integer>();
        for (int i = 0; i < count; i++) {
            map.put(i, list.get(i).getOrder_status());
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        orderBean = list.get(position);
//        UIUtil.showLog("我的订单------》",orderBean.toString());
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_me_order, null);
            holder.orderId = (TextView) convertView.findViewById(R.id.item_tv_orderId);
            holder.orderNum = (TextView) convertView.findViewById(R.id.item_tv_orderNum);
            holder.orderTitle = (TextView) convertView.findViewById(R.id.item_tv_orderTitle);
            holder.orderPrice = (TextView) convertView.findViewById(R.id.item_tv_orderPrice);
            holder.btnOrder = (Button) convertView.findViewById(R.id.item_btn_order);
            holder.order_ll = (LinearLayout) convertView.findViewById(R.id.order_ll);
            holder.order_pic = (ImageView) convertView.findViewById(R.id.order_pic);
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

        switch (status) {
            //待支付
            case 0:
                holder.btnOrder.setBackgroundResource(R.mipmap.order_red_bg);
                holder.btnOrder.setText("立即支付");
                break;
            //已支付
            case 1:
                holder.btnOrder.setBackgroundResource(R.mipmap.order_red_gred);
                holder.btnOrder.setText("支付成功");
                break;
            //交易取消
            case 2:
                holder.btnOrder.setBackgroundResource(R.mipmap.order_red_gred);
                holder.btnOrder.setText("关闭交易");
                break;
            //作品待上传
            case 3:
                holder.btnOrder.setBackgroundResource(R.mipmap.order_red_bg);
                holder.btnOrder.setText("上传作品");
                break;
            //待测评
            case 4:
                holder.btnOrder.setBackgroundResource(R.mipmap.order_red_bg);
                holder.btnOrder.setText("查看详情");
                break;
            //已测评
            case 5:
                holder.btnOrder.setBackgroundResource(R.mipmap.order_red_gred);
                holder.btnOrder.setText("测评完成");
                break;
        }


        holder.orderId.setText(orderBean.getOrder_number() + "");
        holder.orderTitle.setText(orderBean.getWork_title() + "");
        holder.orderPrice.setText(orderBean.getOrder_total_price() + "");
        holder.orderNum.setText(orderBean.getOrder_element_num() + "");
        holder.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderBean = list.get(position);
                switch (status) {
                    case 0:
                        Intent intent = new Intent(context, PayActivity.class);
                        Bundle bundle = new Bundle();
                        UploadDao uploadDao = new UploadDao(context);
                        UploadBean uploadBean = uploadDao.queryOrder(orderBean.getOrder_number());

                        CommitOrderBean commitOrderBean = new CommitOrderBean();
                        commitOrderBean.setOrder_price(orderBean.getOrder_total_price() + "");
                        commitOrderBean.setOrder_title(orderBean.getWork_title());
                        commitOrderBean.setOrder_number(orderBean.getOrder_number());
                        commitOrderBean.setCreate_time(orderBean.getOrder_time());
                        AudioInfoBean audioInfoBean = new AudioInfoBean();
                        if (uploadBean != null) {
                            commitOrderBean.setFile_path(uploadBean.getFile_path());
                            audioInfoBean.setAudio_path(uploadBean.getFile_path());
                            audioInfoBean.setAudio_length(uploadBean.getAtt_length());
                            audioInfoBean.setMedia_type(uploadBean.getAtt_type());
                        }

                        bundle.putSerializable("order_bean", commitOrderBean);
                        bundle.putSerializable("att_bean", audioInfoBean);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                        break;
                    case 1:

                        break
                                ;
                }
            }
        });
        holder.order_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int status = map.get(position);
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
                    Intent intent = new Intent(context, ValuationDetailActivity.class);
                    intent.putExtra("work_id", orderBean.getWork_id());
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

    class ViewHolder {
        TextView orderId;
        TextView orderTitle;
        TextView orderNum;
        TextView orderPrice;
        Button btnOrder;
        LinearLayout order_ll;
        ImageView order_pic;
    }

}
