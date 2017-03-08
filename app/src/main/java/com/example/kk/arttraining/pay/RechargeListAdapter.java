package com.example.kk.arttraining.pay;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.pay.bean.RechargeBean;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2017/3/2 14:17
 * 说明:云币充值列表
 */
public class RechargeListAdapter extends BaseAdapter {
    Context context;
    List<RechargeBean> rechargeBeanList;
    ViewHolder viewHolder;
    RechargeBean rechargeBean;

    public RechargeListAdapter(Context context, List<RechargeBean> rechargeBeanList) {
        this.context = context;
        this.rechargeBeanList = rechargeBeanList;
    }

    @Override
    public int getCount() {
        return rechargeBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return rechargeBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_rechare_cloud, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        rechargeBean = rechargeBeanList.get(position);
        //用于更新选中后的文字及背景的变化
        if (rechargeBean.isSelect()) {
            viewHolder.llRecharge.setBackground(UIUtil.getDrawable(R.drawable.bg_item_recharge_cloud_focus));
            viewHolder.tvCloudNum.setTextColor(UIUtil.getColor(R.color.white));
            viewHolder.tvRechargeMoney.setTextColor(UIUtil.getColor(R.color.white));
        } else {
            viewHolder.llRecharge.setBackground(UIUtil.getDrawable(R.drawable.bg_item_recharge_cloud_unfocus));
            viewHolder.tvCloudNum.setTextColor(UIUtil.getColor(R.color.blue_overlay));
            viewHolder.tvRechargeMoney.setTextColor(UIUtil.getColor(R.color.blue_overlay));
        }

        viewHolder.tvCloudNum.setText(rechargeBean.getCloud_money() + "云币");
        viewHolder.tvRechargeMoney.setText("售价" + rechargeBean.getMoney() + "元");
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_cloudNum)
        TextView tvCloudNum;
        @InjectView(R.id.tv_recharge_money)
        TextView tvRechargeMoney;
        @InjectView(R.id.ll_item_recharge)
        LinearLayout llRecharge;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
