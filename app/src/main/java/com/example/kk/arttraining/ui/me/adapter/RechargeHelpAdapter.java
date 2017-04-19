package com.example.kk.arttraining.ui.me.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.me.bean.RechargeHelpBean;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2017/4/7.
 * QQ邮箱:515849594@qq.com
 */
public class RechargeHelpAdapter extends BaseAdapter {
    ViewHolder holder;
    Context context;
    List<RechargeHelpBean> helpBeanList;
    RechargeHelpBean helpBean;
    RechargeHelpBean checkBean;
    List<Boolean> clickList = new ArrayList<Boolean>();
    int count;
    int chargeId = -1;


    public RechargeHelpAdapter(Context context, List<RechargeHelpBean> helpBeanList) {
        this.context = context;
        this.helpBeanList = helpBeanList;
        count = helpBeanList.size();
        if (!clickList.isEmpty()) clickList.clear();
        for (int i=0;i<count;i++) {
            clickList.add(i, false);
        }
    }

    @Override
    public int getCount() {
        UIUtil.showLog("getCount","111");


        return count;

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    public RechargeHelpBean getCheckItem() {
        return checkBean;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public int getChargeId() {
        return chargeId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        helpBean = helpBeanList.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.me_cloud_recharge_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(helpBean.getHead_pic()).error(R.mipmap.default_user_header).into(holder.ivRechargeHeader);
        holder.tvRechargeName.setText(helpBean.getName());
        holder.tvRechargePhone.setText(helpBean.getTelephone());
        holder.tvRechargeLogin.setText("("+helpBean.getLogin_type()+")");

        if ("f".equals(helpBean.getSex())) {
            holder.tvRechargeSex.setBackgroundResource(R.mipmap.sex_girl);
        } else {
            holder.tvRechargeSex.setBackgroundResource(R.mipmap.sex_male);
        }


        if (clickList.get(position)) {
            holder.cbRecharge.setBackgroundResource(R.drawable.pay_click);
        }else {
            holder.cbRecharge.setBackgroundResource(R.drawable.pay_unclick);
        }

        holder.llRechargeItem.setOnClickListener(new clickItem(position, holder.cbRecharge));
        return convertView;

    }

    public void changeCount(int count) {
        this.count = count;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_recharge_header)
        ImageView ivRechargeHeader;
        @InjectView(R.id.tv_recharge_name)
        TextView tvRechargeName;
        @InjectView(R.id.tv_recharge_sex)
        TextView tvRechargeSex;
        @InjectView(R.id.tv_recharge_phone)
        TextView tvRechargePhone;
        @InjectView(R.id.tv_recharge_login)
        TextView tvRechargeLogin;
        @InjectView(R.id.cb_recharge)
        ImageView cbRecharge;
        @InjectView(R.id.ll_recharge_item)
        LinearLayout llRechargeItem;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    private class clickItem implements View.OnClickListener {
        ImageView cbRecharge;
        int position;
        public clickItem(int position, ImageView cbRecharge) {
            this.cbRecharge = cbRecharge;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            UIUtil.showLog("cbRecharge","1111");
            if (!clickList.get(position)){
                chargeId = helpBeanList.get(position).getUid();
                checkBean = helpBeanList.get(position);
                clickList.set(position,true);
                for (int i=0;i<clickList.size();i++) {
                    if (i != position) {
                        clickList.set(i, false);
                    }
                }
                UIUtil.showLog("cbRecharge","2222");
            } else {
                UIUtil.showLog("cbRecharge","3333");
                chargeId = -1;
                checkBean = null;
                clickList.set(position, false);
            }
            notifyDataSetChanged();
        }
    }

}
